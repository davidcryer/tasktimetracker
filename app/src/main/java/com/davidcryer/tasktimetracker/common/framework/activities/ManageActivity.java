package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.davidc.uiwrapper.SingleContentContainerWithAppBarActivity;
import com.davidcryer.tasktimetracker.managestories.AddStoryNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesFragment;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managestories.RemoveStoryListener;
import com.davidcryer.tasktimetracker.managestories.RemoveStoryNavigator;
import com.davidcryer.tasktimetracker.managetask.ManageTaskFragment;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public class ManageActivity extends SingleContentContainerWithAppBarActivity implements ManageStoriesNavigator, AddStoryNavigator, RemoveStoryNavigator {
    private final static String FRAGMENT_TAG_ADD_STORY_PROMPT = "add story prompt";
    private final static String FRAGMENT_TAG_REMOVE_STORY_PROMPT = "remove story prompt";
    private final static String FRAGMENT_TAG_MANAGE_TASK = "manage task";
    private final static String FRAGMENT_TAG_MANAGE_STORIES = "manage stories";

    @Override
    protected void setupActionBar(@NonNull ActionBar actionBar) {

    }

    @Override
    protected void addInitialFragment() {
        addFragment(new ManageStoriesFragment(), FRAGMENT_TAG_MANAGE_STORIES);
    }

    @Override
    public void showAddStoryPrompt(DialogFragmentFactory factory) {
        if (noFragmentExists(FRAGMENT_TAG_ADD_STORY_PROMPT)) {
            show(factory, FRAGMENT_TAG_ADD_STORY_PROMPT);
        }
    }

    @Override
    public void showRemoveStoryPrompt(DialogFragmentFactory factory) {
        if (noFragmentExists(FRAGMENT_TAG_REMOVE_STORY_PROMPT)) {
            show(factory, FRAGMENT_TAG_REMOVE_STORY_PROMPT);
        }
    }

    private void show(final DialogFragmentFactory factory, final String tag) {
        FragmentManagerUtils.show(factory.create(), tag, this);
    }

    @Override
    public void toManageTaskScreen(ManageTaskIntentModel intentModel) {
        if (noFragmentExists(FRAGMENT_TAG_MANAGE_TASK)) {
            addFragment(ManageTaskFragment.newInstance(intentModel), FRAGMENT_TAG_MANAGE_TASK);
        }
    }

    @Override
    public void onClickAdd(ManageStoriesUi.InputStoryPrompt prompt, String title, String note) {
        final Fragment fragment = findFragmentByTag(FRAGMENT_TAG_MANAGE_STORIES);
        if (fragment != null) {
            ((ManageStoriesNavigator.Callback) fragment).onAddStory(prompt, title, note);
        }
    }

    @Override
    public RemoveStoryListener listener() {
        return (RemoveStoryListener) findFragmentByTag(FRAGMENT_TAG_MANAGE_STORIES);
    }

    private Fragment findFragmentByTag(final String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private boolean noFragmentExists(final String tag) {
        return FragmentManagerUtils.noFragmentExists(tag, this);
    }
}
