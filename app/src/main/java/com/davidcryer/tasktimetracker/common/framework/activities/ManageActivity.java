package com.davidcryer.tasktimetracker.common.framework.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.davidc.uiwrapper.UiWrapperRepositoryActivity;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.framework.FabListener;
import com.davidcryer.tasktimetracker.managecategories.AddCategoryNavigator;
import com.davidcryer.tasktimetracker.managecategories.AddTaskNavigator;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesFragment;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesNavigator;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managecategories.RemoveCategoryListener;
import com.davidcryer.tasktimetracker.managecategories.RemoveCategoryNavigator;
import com.davidcryer.tasktimetracker.managecategories.RemoveTaskListener;
import com.davidcryer.tasktimetracker.managecategories.RemoveTaskNavigator;
import com.davidcryer.tasktimetracker.managetask.ManageTaskFragment;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public class ManageActivity extends UiWrapperRepositoryActivity
        implements ManageCategoriesNavigator,
        AddCategoryNavigator,
        AddTaskNavigator,
        RemoveCategoryNavigator,
        RemoveTaskNavigator,
        FragmentManagerUtils.FragmentManagerProvider
{
    private final static String FRAGMENT_TAG_ADD_CATEGORY_PROMPT = "add category prompt";
    private final static String FRAGMENT_TAG_ADD_TASK_PROMPT = "add task prompt";
    private final static String FRAGMENT_TAG_REMOVE_CATEGORY_PROMPT = "remove category prompt";
    private final static String FRAGMENT_TAG_REMOVE_TASK_PROMPT = "remove task prompt";
    private final static String FRAGMENT_TAG_MANAGE_TASK = "manage task";
    private final static String FRAGMENT_TAG_MANAGE_CATEGORIES = "manage categories";

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setUpToolbar();
        setUpFab();
        addInitialFragmentIfNone();
    }

    private void setUpToolbar() {
        setSupportActionBar(findViewById(com.davidc.uiwrapper.R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setUpActionBar(actionBar);
        }
    }

    private void setUpFab() {
        findViewById(R.id.fab).setOnClickListener(v -> passFabClickToFragments());
    }

    private void passFabClickToFragments() {
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = fragments.size() - 1; i > -1; i++) {
            final Fragment fragment = fragments.get(i);
            if (fragment instanceof FabListener) {
                final boolean consumedClick = ((FabListener) fragment).onFabClicked();
                if (consumedClick) {
                    break;
                }
            }
        }
    }

    private void setUpActionBar(@NonNull ActionBar actionBar) {
        actionBar.setTitle("");
    }

    private void addInitialFragmentIfNone() {
        if (FragmentManagerUtils.noFragmentExsist(R.id.content, this)) {
            addInitialFragment();
        }
    }

    private void addInitialFragment() {
        addFragment(new ManageCategoriesFragment(), FRAGMENT_TAG_MANAGE_CATEGORIES);
    }

    @IdRes
    protected int getContentFragmentViewContainer() {
        return com.davidc.uiwrapper.R.id.content;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (FragmentManagerUtils.hasMoreThanFragmentOnBackStack(this)) {
            super.onBackPressed();
            return;
        }
        finish();
    }

    @Override
    public void showAddCategoryPrompt(DialogFragmentFactory factory) {
        showIfNonExists(factory, FRAGMENT_TAG_ADD_CATEGORY_PROMPT);
    }

    @Override
    public void showAddTaskPrompt(DialogFragmentFactory factory) {
        showIfNonExists(factory, FRAGMENT_TAG_ADD_TASK_PROMPT);
    }

    @Override
    public void showRemoveCategoryPrompt(DialogFragmentFactory factory) {
        showIfNonExists(factory, FRAGMENT_TAG_REMOVE_CATEGORY_PROMPT);
    }

    @Override
    public void showRemoveTaskPrompt(DialogFragmentFactory factory) {
        showIfNonExists(factory, FRAGMENT_TAG_REMOVE_TASK_PROMPT);
    }

    @Override
    public void toManageTaskScreen(ManageTaskIntentModel intentModel) {
        if (noFragmentExists(FRAGMENT_TAG_MANAGE_TASK)) {
            replaceFragment(ManageTaskFragment.newInstance(intentModel), FRAGMENT_TAG_MANAGE_TASK);
        }
    }

    @Override
    public void onClickAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
        final Fragment fragment = findFragmentByTag(FRAGMENT_TAG_MANAGE_CATEGORIES);
        if (fragment != null) {
            ((ManageCategoriesNavigator.Callback) fragment).onAddCategory(prompt, title, note);
        }
    }

    @Override
    public void onClickAddTask(ManageCategoriesUi.InputPrompt prompt, String title, String note, UUID categoryId) {
        final Fragment fragment = findFragmentByTag(FRAGMENT_TAG_MANAGE_CATEGORIES);
        if (fragment != null) {
            ((ManageCategoriesNavigator.Callback) fragment).onAddTask(prompt, title, note, categoryId);
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

    private void addFragment(final Fragment fragment, final String tag) {
        FragmentManagerUtils.addFragment(getSupportFragmentManager(), fragment, getContentFragmentViewContainer(), tag);
    }

    private void replaceFragment(final Fragment fragment, final String tag) {
        FragmentManagerUtils.replaceFragment(getSupportFragmentManager(), fragment, getContentFragmentViewContainer(), tag);
    }

    private Fragment findFragmentByTag(final String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private void showIfNonExists(final DialogFragmentFactory factory, final String tag) {
        if (noFragmentExists(tag)) {
            show(factory, tag);
        }
    }

    private void show(final DialogFragmentFactory factory, final String tag) {
        FragmentManagerUtils.show(factory.create(), tag, this);
    }

    @Override
    public FragmentManager fragmentManager() {
        return getSupportFragmentManager();
    }

    private boolean noFragmentExists(final String tag) {
        return FragmentManagerUtils.noFragmentExists(tag, this);
    }
}
