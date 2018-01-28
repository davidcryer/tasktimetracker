package com.davidcryer.tasktimetracker.managetask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidc.uiwrapper.UiWrapper;
import com.davidc.uiwrapper.UiWrapperFactoryFragment;
import com.davidcryer.tasktimetracker.R;

public class ManageTaskFragment extends UiWrapperFactoryFragment<Object, Object, Object> {
    private final static String ARGS_INTENT_MODEL = "intent model";

    public static ManageTaskFragment newInstance(final ManageTaskIntentModel intentModel) {
        final ManageTaskFragment fragment = new ManageTaskFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_INTENT_MODEL, intentModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object ui() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_task, container, false);
    }

    @Override
    protected UiWrapper<Object, Object, ?> uiWrapper(Object uiWrapperFactory, @Nullable Bundle savedState) {
        return null;//TODO
    }
}
