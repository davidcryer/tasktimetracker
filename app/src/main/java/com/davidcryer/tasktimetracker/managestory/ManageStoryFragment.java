package com.davidcryer.tasktimetracker.managestory;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public class ManageStoryFragment extends UiFragment<ManageStoryUi.Listener, UiWrapperRepository> implements ManageStoryUi {
    private final static String ARGS_INTENT_MODEL = "intent model";
    private ManageStoryNavigator navigator;

    public static ManageStoryFragment newInstance(final ManageStoryIntentModel intentModel) {
        final ManageStoryFragment fragment = new ManageStoryFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_INTENT_MODEL, intentModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showTasks(List<UiTask> tasks) {

    }

    @Override
    public void showManageLayout(UiStory story) {

    }

    @Override
    public void showEditLayout(UiStory story) {

    }

    @Override
    public void showSaveError(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onDismissSaveErrorDialog();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onDismissSaveErrorDialog();
                    }
                })
                .show();
    }

    private void onDismissSaveErrorDialog() {
        if (hasListener()) {
            listener().onDismissSaveError();
        }
    }

    @Override
    public void showManageTask(UiTask task, int i) {

    }

    @Override
    public void showEditTask(UiTask task, int i) {

    }

    @Override
    public void showManageTaskScreen(ManageTaskIntentModel intentModel) {
        navigator.toManageTaskScreen(intentModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (ManageStoryNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected ManageStoryUi.Listener bind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiBinder binder) {
        final Bundle args = getArguments();
        if (args != null) {
            return uiWrapperRepository.bind(this, binder, (ManageStoryIntentModel) args.getParcelable(ARGS_INTENT_MODEL));
        }
        throw new IllegalStateException("Fragment requires ManageStoryIntentModel in args");
    }

    @Override
    protected void unbind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiUnbinder unbinder) {
        uiWrapperRepository.unbind(this, unbinder);
    }
}
