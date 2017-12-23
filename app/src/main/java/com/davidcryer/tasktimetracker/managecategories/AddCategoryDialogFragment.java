package com.davidcryer.tasktimetracker.managecategories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.davidcryer.tasktimetracker.R;

public class AddCategoryDialogFragment extends DialogFragment implements ManageCategoriesUi.InputPrompt {
    private CategoryLayout categoryLayout;
    private AddCategoryNavigator navigator;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryLayout = new CategoryLayout(getContext());
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(categoryLayout)
                .setTitle("Add category")
                .setPositiveButton("Add", null)
                .setNeutralButton(android.R.string.cancel, (dialogInterface, i) -> {

                })
                .show();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> onClickAdd()));
        return dialog;
    }

    private void onClickAdd() {
        if (navigator != null) {
            navigator.onClickAddCategory(this, categoryLayout.title(), categoryLayout.note());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryLayout = null;
    }

    @Override
    public void showTitleError(String message) {
        if (categoryLayout != null) {
            categoryLayout.showTitleError(message);
        }
    }

    @Override
    public void showNoteError(String message) {
        if (categoryLayout != null) {
            categoryLayout.showNoteError(message);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (AddCategoryNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }

    private static class CategoryLayout extends LinearLayout {
        private final EditText titleEdit;
        private final EditText noteEdit;

        public CategoryLayout(Context context) {
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
