package com.davidcryer.tasktimetracker.managetask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.R;

public class ManageTaskFragment extends UiFragment {
    private final static String ARGS_INTENT_MODEL = "intent model";

    public static ManageTaskFragment newInstance(final ManageTaskIntentModel intentModel) {
        final ManageTaskFragment fragment = new ManageTaskFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_INTENT_MODEL, intentModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_task, container, false);
    }

    @Override
    protected Object bind(@NonNull Object uiWrapperRepository, @NonNull UiBinder binder) {
        return null;
    }

    @Override
    protected void unbind(@NonNull Object uiWrapperRepository, @NonNull UiUnbinder binder) {

    }
}
