package com.davidcryer.tasktimetracker.managestories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.davidcryer.tasktimetracker.R;

public class RemoveTaskDialogFragment extends DialogFragment {
    private final static String ARGS_UI_TASK = "ui task";
    private final static String ARGS_UI_CATEGORY = "ui category";
    private RemoveTaskNavigator navigator;

    public static RemoveTaskDialogFragment newInstance(final UiTask task, final UiCategory category) {
        final RemoveTaskDialogFragment fragment = new RemoveTaskDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_UI_TASK, task);
        args.putParcelable(ARGS_UI_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException("Args must not be null");
        }
        final UiTask task = args.getParcelable(ARGS_UI_TASK);
        final UiCategory category = args.getParcelable(ARGS_UI_CATEGORY);
        if (task == null || category == null) {
            throw new IllegalStateException("Args must contain UiTask for ARGS_UI_TASK key and UiCategory for ARGS_UI_CATEGORY key");
        }
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.prompt_remove_task_title)
                .setMessage(String.format(getString(R.string.prompt_remove_task_message), task.getTitle(), category.getTitle()))
                .setPositiveButton(R.string.prompt_button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClickDelete(task, category);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void onClickDelete(final UiTask task, final UiCategory category) {
        if (navigator != null) {
            final RemoveTaskListener listener = navigator.removeTaskListener();
            if (listener != null) {
                listener.onClickDelete(task, category);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (RemoveTaskNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
