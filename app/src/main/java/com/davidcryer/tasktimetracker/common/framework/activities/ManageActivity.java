package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.davidc.uiwrapper.SingleContentContainerWithAppBarActivity;
import com.davidcryer.tasktimetracker.managestories.AddCategoryNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageCategoriesFragment;
import com.davidcryer.tasktimetracker.managestories.ManageCategoriesNavigator;
import com.davidcryer.tasktimetracker.managestories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managestories.RemoveCategoryListener;
import com.davidcryer.tasktimetracker.managestories.RemoveCategoryNavigator;
import com.davidcryer.tasktimetracker.managestories.RemoveTaskListener;
import com.davidcryer.tasktimetracker.managestories.RemoveTaskNavigator;
import com.davidcryer.tasktimetracker.managetask.ManageTaskFragment;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public class ManageActivity extends SingleContentContainerWithAppBarActivity
        implements ManageCategoriesNavigator, AddCategoryNavigator, RemoveCategoryNavigator, RemoveTaskNavigator {
    private final static String FRAGMENT_TAG_ADD_CATEGORY_PROMPT = "add category prompt";
    private final static String FRAGMENT_TAG_REMOVE_CATEGORY_PROMPT = "remove category prompt";
    private final static String FRAGMENT_TAG_REMOVE_TASK_PROMPT = "remove task prompt";
    private final static String FRAGMENT_TAG_MANAGE_TASK = "manage task";
    private final static String FRAGMENT_TAG_MANAGE_CATEGORIES = "manage categories";

    @Override
    protected void setupActionBar(@NonNull ActionBar actionBar) {

    }

    @Override
    protected void addInitialFragment() {
        addFragment(new ManageCategoriesFragment(), FRAGMENT_TAG_MANAGE_CATEGORIES);
    }

    @Override
    public void showAddCategoryPrompt(DialogFragmentFactory factory) {
        if (noFragmentExists(FRAGMENT_TAG_ADD_CATEGORY_PROMPT)) {
            show(factory, FRAGMENT_TAG_ADD_CATEGORY_PROMPT);
        }
    }

    @Override
    public void showRemoveCategoryPrompt(DialogFragmentFactory factory) {
        if (noFragmentExists(FRAGMENT_TAG_REMOVE_CATEGORY_PROMPT)) {
            show(factory, FRAGMENT_TAG_REMOVE_CATEGORY_PROMPT);
        }
    }

    @Override
    public void showRemoveTaskPrompt(DialogFragmentFactory factory) {
        if (noFragmentExists(FRAGMENT_TAG_REMOVE_TASK_PROMPT)) {
            show(factory, FRAGMENT_TAG_REMOVE_TASK_PROMPT);
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
    public void onClickAdd(ManageCategoriesUi.InputCategoryPrompt prompt, String title, String note) {
        final Fragment fragment = findFragmentByTag(FRAGMENT_TAG_MANAGE_CATEGORIES);
        if (fragment != null) {
            ((ManageCategoriesNavigator.Callback) fragment).onAddCategory(prompt, title, note);
        }
    }

    @Override
    public RemoveCategoryListener removeCategoryListener() {
        return (RemoveCategoryListener) findFragmentByTag(FRAGMENT_TAG_MANAGE_CATEGORIES);
    }

    @Override
    public RemoveTaskListener removeTaskListener() {
        return (RemoveTaskListener) findFragmentByTag(FRAGMENT_TAG_MANAGE_CATEGORIES);
    }

    private Fragment findFragmentByTag(final String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private boolean noFragmentExists(final String tag) {
        return FragmentManagerUtils.noFragmentExists(tag, this);
    }
}
