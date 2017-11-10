package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageStoriesUiModelTests {

    @Test
    public void notPopulated() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        Assert.assertFalse(uiModel.isPopulated());
    }

    @Test
    public void isPopulated() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(new ArrayList<UiStory>());
        Assert.assertTrue(uiModel.isPopulated());
    }

    @Test
    public void showStories() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        final TestUi ui = new TestUi();
        uiModel.showStories(new ArrayList<UiStory>(), ui);
        Assert.assertTrue(uiModel.isPopulated());
        Assert.assertTrue(ui.stories != null && ui.stories.isEmpty());
    }

    @Test
    public void showStories_nullList() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        final TestUi ui = new TestUi();
        uiModel.showStories(null, ui);
        Assert.assertFalse(uiModel.isPopulated());
        Assert.assertTrue(ui.stories != null && ui.stories.isEmpty());
    }

    @Test
    public void addStory() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        final TestUi ui = new TestUi(new ArrayList<UiStory>());
        uiModel.addStory(uiStory(), ui);
        Assert.assertTrue(uiModel.isPopulated());
        Assert.assertTrue(ui.stories != null && !ui.stories.isEmpty());
    }

    @Test
    public void insertStory() {
        final List<UiStory> initialStories = Collections.singletonList(uiStory());
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(initialStories);
        final TestUi ui = new TestUi(initialStories);
        uiModel.insertStory(uiStory(), 0, ui);
        Assert.assertTrue(ui.stories != null && !ui.stories.isEmpty());
    }

    @Test
    public void removeStory() {
        final List<UiStory> initialStories = Collections.singletonList(uiStory());
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(initialStories);
        final TestUi ui = new TestUi(initialStories);
        uiModel.removeStory(initialStories.get(0).getId(), ui);
        Assert.assertTrue(ui.stories != null && ui.stories.isEmpty());
    }

    private static UiStory uiStory() {
        return new UiStory(null, null, null, false, null);
    }

    private static class TestUi implements ManageStoriesUi {
        List<UiStory> stories;

        private TestUi() {
        }

        private TestUi(List<UiStory> stories) {
            this.stories = new ArrayList<>(stories);
        }

        @Override
        public void showStories(List<UiStory> stories) {
            this.stories = stories;
        }

        @Override
        public void addStory(UiStory story) {
            this.stories.add(story);
        }

        @Override
        public void insertStory(UiStory story, int i) {
            this.stories.set(i, story);
        }

        @Override
        public void removeStory(int pos) {
            this.stories.remove(pos);
        }

        @Override
        public void setStory(UiStory story, int i) {

        }

        @Override
        public void expandStory(int i, int pos) {

        }

        @Override
        public void shrinkStory(int i, int pos) {

        }

        @Override
        public void showManageTaskScreen(ManageTaskIntentModel intentModel) {

        }

        @Override
        public void showAddStoryPrompt() {

        }

        @Override
        public void showRemoveStoryPrompt(UiStory story) {

        }
    }
}
