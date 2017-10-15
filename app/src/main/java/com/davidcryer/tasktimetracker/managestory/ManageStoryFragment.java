package com.davidcryer.tasktimetracker.managestory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;

public class ManageStoryFragment extends UiFragment {
    private final static String ARGS_INTENT_MODEL = "intent model";

    public static ManageStoryFragment newInstance(final ManageStoryIntentModel intentModel) {
        final ManageStoryFragment fragment = new ManageStoryFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_INTENT_MODEL, intentModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object bind(@NonNull Object uiWrapperRepository, @NonNull UiBinder binder) {
        return null;
    }

    @Override
    protected void unbind(@NonNull Object uiWrapperRepository, @NonNull UiUnbinder binder) {

    }
}
