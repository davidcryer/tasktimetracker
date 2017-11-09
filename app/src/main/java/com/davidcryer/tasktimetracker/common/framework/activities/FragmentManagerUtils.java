package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

class FragmentManagerUtils {

    static void add(
            final Fragment fragment,
            final String tag,
            @IdRes final int container,
            final String backStackTag,
            final FragmentManager fragmentManager
    ) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .add(container, fragment, tag)
                    .addToBackStack(backStackTag)
                    .commit();
        }
    }

    static void show(final DialogFragment fragment, final String tag, final FragmentManager fragmentManager) {
        fragment.show(fragmentManager, tag);
    }
}
