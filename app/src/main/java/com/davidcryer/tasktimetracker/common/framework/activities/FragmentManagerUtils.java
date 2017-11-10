package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

class FragmentManagerUtils {

    static boolean noFragmentExists(final String tag, final FragmentActivity activity) {
        return activity.getSupportFragmentManager().findFragmentByTag(tag) == null;
    }

    static void show(final DialogFragment fragment, final String tag, final FragmentActivity activity) {
        fragment.show(activity.getSupportFragmentManager(), tag);
    }
}
