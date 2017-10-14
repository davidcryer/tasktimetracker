package com.davidcryer.tasktimetracker.managestories;

import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.framework.uiwrapper.UiWrapperRepository;

import java.util.List;

public class ManageStoriesFragment extends UiFragment<ManageStoriesUi.Listener, UiWrapperRepository> implements ManageStoriesUi {



    @Override
    public void showStories(List<UiStory> stories) {

    }

    @Override
    public void addStory(UiStory story) {

    }

    @Override
    public void insertStory(UiStory story, int i) {

    }

    @Override
    public void removeStory(int i) {

    }

    @Override
    public void showUndoStoryRemoval(Runnable onUndo) {

    }

    @Override
    public void showManageStoryScreen(UiStory story) {

    }

    @Override
    public void showAddStoryScreen() {

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
