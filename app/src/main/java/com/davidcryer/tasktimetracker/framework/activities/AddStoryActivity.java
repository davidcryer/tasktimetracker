package com.davidcryer.tasktimetracker.framework.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.davidc.uiwrapper.SingleContentContainerWithAppBarActivity;
import com.davidcryer.tasktimetracker.addstory.AddStoryFragment;

public class AddStoryActivity extends SingleContentContainerWithAppBarActivity {
    private final static String INTENT_DATA_RETURN_KEY = "data return key";

    static void startActivityForResult(final Activity activity, final int requestCode, final String dataReturnKey) {
        final Intent intent = new Intent(activity, AddStoryActivity.class);
        intent.putExtra(INTENT_DATA_RETURN_KEY, dataReturnKey);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void setupActionBar(@NonNull ActionBar actionBar) {

    }

    @NonNull
    @Override
    protected Fragment initialFragment() {
        return AddStoryFragment.newInstance(getIntent().getStringExtra(INTENT_DATA_RETURN_KEY));
    }
}
