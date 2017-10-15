package com.davidcryer.tasktimetracker.framework.activities;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

class FragmentManagerUtils {

    static void replace(
            final Fragment fragment,
            final String tag,
            @IdRes final int container,
            final String backStackTag,
            final FragmentManager fragmentManager
    ) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(container, fragment, tag)
                    .addToBackStack(backStackTag)
                    .commit();
        }
    }
}
