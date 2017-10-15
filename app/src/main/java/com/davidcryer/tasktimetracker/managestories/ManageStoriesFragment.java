package com.davidcryer.tasktimetracker.managestories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;

import java.util.List;

public class ManageStoriesFragment extends UiFragment<ManageStoriesUi.Listener, UiWrapperRepository> implements ManageStoriesUi {
    private final static int REQUEST_CODE_ADD_STORY = 100;
    private final static String RETURN_KEY_ADD_STORY = "story";
    private final StoriesAdapter storiesAdapter;
    private ManageStoriesNavigator navigator;

    public ManageStoriesFragment() {
        storiesAdapter = new StoriesAdapter();
        storiesAdapter.onClickStoryListener(new StoriesAdapter.OnClickStoryListener() {
            @Override
            public void onClick(UiStory story) {
                if (hasListener()) {
                    listener().onClickStory(ManageStoriesFragment.this, story);
                }
            }

            @Override
            public void onLongClick(UiStory story, int i) {
                if (hasListener()) {
                    listener().onLongClickStory(ManageStoriesFragment.this, story, i);
                }
            }

            @Override
            public void onClick(UiTask task) {
                if (hasListener()) {
                    listener().onClickTask(ManageStoriesFragment.this, task);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manage_stories, container, false);
        ((RecyclerView) view.findViewById(R.id.stories)).setAdapter(storiesAdapter);
        return view;
    }

    @Override
    public void showStories(List<UiStory> stories) {
        storiesAdapter.stories(stories);
    }

    @Override
    public void addStory(UiStory story) {
        storiesAdapter.add(story);
    }

    @Override
    public void insertStory(UiStory story, int i) {
        storiesAdapter.insert(story, i);
    }

    @Override
    public void removeStory(int i) {
        storiesAdapter.remove(i);
    }

    @Override
    public void showRemoveStory(final UiStory story, int i) {
        new AlertDialog.Builder(getContext())
                .setTitle("Remove story")
                .setMessage(String.format("Remove story %1$s?", story.getTitle()))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (hasListener()) {
                            listener().onClickRemoveStory(ManageStoriesFragment.this, story, i);
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public void showUndoStoryRemoval(final Runnable onUndo) {
        final View root = getView();
        if (root != null) {
            Snackbar.make(root, "Story deleted", BaseTransientBottomBar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onUndo != null) {
                        onUndo.run();
                    }
                }
            });
        }
    }

    @Override
    public void showManageStoryScreen(UiStory story) {
        navigator.toManageStoryScreen(UiStoryMapper.toManageStoryIntentModel(story));
    }

    @Override
    public void showManageTaskScreen(UiTask task) {
        navigator.toManageTaskScreen(UiTaskMapper.toManageTaskIntentModel(task));
    }

    @Override
    public void showAddStoryScreen() {
        navigator.toAddStoryScreen(REQUEST_CODE_ADD_STORY, RETURN_KEY_ADD_STORY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_STORY: {
                onAddStoryResult(data);
            }
        }
    }

    private void onAddStoryResult(final Intent data) {
        if (data.hasExtra(RETURN_KEY_ADD_STORY)) {
            if (hasListener()) {
                listener().onAddStoryResult(this, (UiStory) data.getParcelableExtra(RETURN_KEY_ADD_STORY));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (ManageStoriesNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }

    @Override
    protected Listener bind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiBinder binder) {
        return uiWrapperRepository.bind(this, binder);
    }

    @Override
    protected void unbind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiUnbinder unbinder) {
        uiWrapperRepository.unbind(this, unbinder);
    }
}
