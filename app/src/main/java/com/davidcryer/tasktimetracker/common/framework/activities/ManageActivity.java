package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.davidc.uiwrapper.SingleContentContainerWithAppBarActivity;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesFragment;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesNavigator;
import com.davidcryer.tasktimetracker.managestory.ManageStoryFragment;
import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;
import com.davidcryer.tasktimetracker.managetask.ManageTaskFragment;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public class ManageActivity extends SingleContentContainerWithAppBarActivity implements ManageStoriesNavigator {
    private final static String FRAGMENT_TAG_MANAGE_STORY = "manage story";
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
    public void toManageStoryScreen(ManageStoryIntentModel intentModel) {
        replaceFragment(ManageStoryFragment.newInstance(intentModel), FRAGMENT_TAG_MANAGE_STORY);
    }

    @Override
    public void toManageTaskScreen(ManageTaskIntentModel intentModel) {
        replaceFragment(ManageTaskFragment.newInstance(intentModel), FRAGMENT_TAG_MANAGE_TASK);
    }

    private void replaceFragment(final Fragment fragment, final String tag) {
        FragmentManagerUtils.replace(fragment, tag, R.id.content, null, getSupportFragmentManager());//FIXME should not be using parents R.id.content
    }

    @Override
    public void toAddStoryScreen(int requestCode, String storyReturnKey) {
        AddStoryActivity.startActivityForResult(this, requestCode, storyReturnKey);
    }
}
