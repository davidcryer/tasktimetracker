package com.davidcryer.tasktimetracker.common.framework.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import com.davidcryer.simpleactivities.SimpleAppBarActivity;
import com.davidcryer.tasktimetracker.addcategory.AddCategoryFragment;

public class AddCategoryActivity extends SimpleAppBarActivity {
    private final static String FRAGMENT_TAG_ADD_CATEGORY = "add category";
    private final static String INTENT_DATA_RETURN_KEY = "data return key";

    static void startActivityForResult(final Activity activity, final int requestCode, final String dataReturnKey) {
        final Intent intent = new Intent(activity, AddCategoryActivity.class);
        intent.putExtra(INTENT_DATA_RETURN_KEY, dataReturnKey);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void setupActionBar(@NonNull ActionBar actionBar) {

    }

    @Override
    protected void addInitialFragment() {
        add(getContentFragmentViewContainer(), FRAGMENT_TAG_ADD_CATEGORY, () -> AddCategoryFragment.newInstance(getIntent().getStringExtra(INTENT_DATA_RETURN_KEY)));
    }
}
