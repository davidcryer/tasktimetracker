package com.davidcryer.tasktimetracker.managestories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalStoryArgsException;
import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ManageStoriesUiWrapper extends UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> {
    private final StoryDatabase storyDatabase;

    private ManageStoriesUiWrapper(@NonNull final ManageStoriesUiModel uiModel, final StoryDatabase storyDatabase) {
        super(uiModel);
        this.storyDatabase = storyDatabase;
    }

    public static ManageStoriesUiWrapper newInstance(final ManageStoriesUiModelFactory modelFactory, final StoryDatabase storyDatabase) {
        return new ManageStoriesUiWrapper(modelFactory.create(), storyDatabase);
    }

    public static ManageStoriesUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstanceState,
            final ManageStoriesUiModelFactory modelFactory,
            final StoryDatabase storyDatabase
    ) {
        final ManageStoriesUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory, storyDatabase) : new ManageStoriesUiWrapper(savedModel, storyDatabase);
    }

    @Override
    protected ManageStoriesUi.Listener uiListener() {
        return new ManageStoriesUi.Listener() {
            @Override
            public void onClickStory(ManageStoriesUi ui, UiStory story, int pos) {
                if (story.isExpanded()) {
                    uiModel().shrinkStory(story, pos, ui);
                } else {
                    uiModel().expandStory(story, pos, ui);
                }
            }

            @Override
            public void onLongClickStory(ManageStoriesUi ui, UiStory story) {
                ui.showRemoveStoryPrompt(story);
            }

            @Override
            public void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story) {
                ui.showManageTaskScreen(UiTaskMapper.toManageTaskIntentModel(task, story.getId()));
            }

            @Override
            public void onClickAddStory(ManageStoriesUi ui) {
                ui.showAddStoryPrompt();
            }

            @Override
            public void onClickAddTask(ManageStoriesUi ui, UiStory story) {
                throw new UnsupportedOperationException("onClickAddTask not supported");
            }

            @Override
            public void onAddStory(ManageStoriesUi.InputStoryPrompt prompt, String title, String note) {
                try {
                    final Story story = new Story(title, note);
                    storyDatabase.save(story);
                    uiModel().addStory(UiStoryMapper.from(story), ui());
                    prompt.dismiss();
                } catch (IllegalStoryArgsException iae) {
                    showErrors(prompt, iae.args());
                }
            }

            private void showErrors(final ManageStoriesUi.InputStoryPrompt prompt, final IllegalStoryArgsException.Args args) {
                if (args.titleIsIllegal()) {
                    prompt.showTitleError(args.titleError());
                }
            }

            @Override
            public void onEditStory(ManageStoriesUi.InputStoryPrompt prompt, UUID storyId, String title, String note) {
                try {
                    final Story story = storyDatabase.find(storyId);
                    story.writer().title(title).note(note).commit();
                    storyDatabase.save(story);
                    uiModel().updateStory(UiStoryMapper.from(story), ui());
                    prompt.dismiss();
                } catch (IllegalStoryArgsException iae) {
                    showErrors(prompt, iae.args());
                }
            }

            @Override
            public void onRemoveStory(ManageStoriesUi ui, UiStory story) {
                storyDatabase.delete(story.getId());
                uiModel().removeStory(story.getId(), ui);
            }
        };
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulated()) {
            uiModel().showStories(UiStoryMapper.from(alphabeticallyOrderedStories()), ui());
        }
    }

    private List<Story> alphabeticallyOrderedStories() {
        final List<Story> stories = storyDatabase.findAll();
        Collections.sort(stories, new Comparator<Story>() {
            @Override
            public int compare(Story l, Story r) {
                return l.title().compareTo(r.title());
            }
        });
        return stories;
    }
}
