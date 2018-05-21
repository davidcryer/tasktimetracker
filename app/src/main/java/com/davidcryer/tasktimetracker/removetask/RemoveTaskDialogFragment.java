package com.davidcryer.tasktimetracker.removetask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.davidcryer.tasktimetracker.R;

public class RemoveTaskDialogFragment extends DialogFragment {
    private final static String ARGS_UI_TASK = "ui task";
    private final static String ARGS_UI_CATEGORY = "ui category";

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
            throw new IllegalStateException("ArgRules must not be null");
        }
        final UiTask task = args.getParcelable(ARGS_UI_TASK);
        final UiCategory category = args.getParcelable(ARGS_UI_CATEGORY);
        if (task == null || category == null) {
            throw new IllegalStateException("ArgRules must contain UiTask for ARGS_UI_TASK key and UiCategory for ARGS_UI_CATEGORY key");
        }
        final Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Creating dialog with null context");
        }
        return new AlertDialog.Builder(context)
                .setTitle(R.string.prompt_remove_task_title)
                .setMessage(String.format(getString(R.string.prompt_remove_task_message), task.getTitle(), category.getTitle()))
                .setPositiveButton(R.string.prompt_button_delete, (dialogInterface, i) -> onClickDelete(task, category))
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {

                })
                .show();
    }
}
