package com.davidcryer.tasktimetracker.common.framework.activities;

import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

class FragmentManagerUtils {

    static boolean noFragmentExists(final String tag, final FragmentManagerProvider fmProvider) {
        return !hasFragment(fmProvider.fragmentManager(), tag);
    }

    static boolean noFragmentExsist(final int view, final FragmentManagerProvider fmProvider) {
        return !hasFragment(fmProvider.fragmentManager(), view);
    }

    static void show(final DialogFragment fragment, final String tag, final FragmentManagerProvider fmProvider) {
        fragment.show(fmProvider.fragmentManager(), tag);
    }

    private static boolean hasFragment(final FragmentManager fm, final String tag) {
        return fragment(fm, tag) != null;
    }

    private static boolean hasFragment(final FragmentManager fm, final int view) {
        return fragment(fm, view) != null;
    }

    static void addFragment(
            final FragmentManager fragmentManager,
            final Fragment fragment,
            @IdRes final int view,
            final String tag
    ) {
        fragmentManager
                .beginTransaction()
                .add(view, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    static void replaceFragment(
            final FragmentManager fragmentManager,
            final Fragment fragment,
            @IdRes final int view,
            final String tag
    ) {
        fragmentManager
                .beginTransaction()
                .replace(view, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    static Fragment fragment(final FragmentManager fm, final String tag) {
        return fm.findFragmentByTag(tag);
    }

    static Fragment fragment(final FragmentManager fm, final int view) {
        return fm.findFragmentById(view);
    }

    static boolean hasMoreThanFragmentOnBackStack(final FragmentManagerProvider fmProvider) {
        return fmProvider.fragmentManager().getBackStackEntryCount() > 1;
    }

    interface FragmentManagerProvider {
        FragmentManager fragmentManager();
    }
}
