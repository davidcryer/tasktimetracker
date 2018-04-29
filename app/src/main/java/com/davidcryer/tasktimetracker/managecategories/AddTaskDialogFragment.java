package com.davidcryer.tasktimetracker.managecategories;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.davidcryer.tasktimetracker.R;

import java.util.UUID;

public class AddTaskDialogFragment extends DialogFragment implements ManageCategoriesUi.InputPrompt {
    private final static String ARGS_CATEGORY_ID = "category id";
    private AddTaskNavigator navigator;
    private TaskLayout taskLayout;

    static AddTaskDialogFragment newInstance(final UUID categoryId) {
        final AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARGS_CATEGORY_ID, categoryId);
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
        final UUID categoryId = (UUID) args.getSerializable(ARGS_CATEGORY_ID);
        if (categoryId == null) {
            throw new IllegalStateException("ArgRules must contain UUID for ARGS_CATEGORY_ID key");
        }
        final Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Creating dialog with null context");
        }
        taskLayout = new TaskLayout(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(taskLayout)
                .setTitle("Add task")
                .setPositiveButton("Add", null)
                .setNeutralButton(android.R.string.cancel, (dialogInterface, i) -> {})
                .create();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> onClickAdd(categoryId)));
        dialog.show();
        return dialog;
    }

    private void onClickAdd(final UUID categoryId) {
        if (navigator != null) {
            navigator.onClickAddTask(this, taskLayout.title(), taskLayout.note(), categoryId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskLayout = null;
    }

    @Override
    public void showTitleError(String message) {
        if (taskLayout != null) {
            taskLayout.showTitleError(message);
        }
    }

    @Override
    public void showNoteError(String message) {
        if (taskLayout != null) {
            taskLayout.showNoteError(message);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (AddTaskNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }

    private static class TaskLayout extends LinearLayout {
        private final EditText titleEdit;
        private final EditText noteEdit;

        public TaskLayout(Context context) {
            super(context);
            inflate(context, R.layout.component_category_input, this);
            titleEdit = findViewById(R.id.title);
            noteEdit = findViewById(R.id.note);
        }

        void showTitleError(String message) {
            titleEdit.setError(message);
        }

        void showNoteError(String message) {
            noteEdit.setError(message);
        }

        String title() {
            return titleEdit.getText().toString();
        }

        String note() {
            return noteEdit.getText().toString();
        }
    }
}
