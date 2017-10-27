package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.davidc.uiwrapper.SingleContentContainerWithAppBarActivity;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.managestories.AddStoryDialogFragment;
import com.davidcryer.tasktimetracker.managestories.AddStoryNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesFragment;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managetask.ManageTaskFragment;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public class ManageActivity extends SingleContentContainerWithAppBarActivity implements ManageStoriesNavigator, AddStoryNavigator {
    private final static String FRAGMENT_TAG_ADD_STORY_PROMPT = "add story prompt";
    private final static String FRAGMENT_TAG_MANAGE_TASK = "manage task";

    @Override
    protected void setupActionBar(@NonNull ActionBar actionBar) {

    }

    @NonNull
    @Override
    protected Fragment initialFragment() {
        return new ManageStoriesFragment();
    }

    @Override
    public void showAddStoryPrompt() {
        FragmentManagerUtils.show(new AddStoryDialogFragment(), FRAGMENT_TAG_ADD_STORY_PROMPT, getSupportFragmentManager());
    }

    @Override
    public void toManageTaskScreen(ManageTaskIntentModel intentModel) {
        replaceFragment(ManageTaskFragment.newInstance(intentModel), FRAGMENT_TAG_MANAGE_TASK);
    }

    private void replaceFragment(final Fragment fragment, final String tag) {
        FragmentManagerUtils.replace(fragment, tag, R.id.content, null, getSupportFragmentManager());//FIXME should not be using parents R.id.content
    }

    @Override
    public void onClickAdd(ManageStoriesUi.InputStoryPrompt prompt, String title, String note) {
        final Fragment fragment = findTopFragment();
        if (fragment != null && fragment instanceof ManageStoriesNavigator.Callback) {
            ((ManageStoriesNavigator.Callback) fragment).onAddStory(prompt, title, note);
        }
    }

    private Fragment findTopFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content);
    }
}
