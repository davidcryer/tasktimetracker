package com.davidcryer.tasktimetracker.addcategory;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUi;

public class AddCategoryDialogFragment extends DialogFragment implements ManageCategoriesUi.InputPrompt {
    private CategoryLayout categoryLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Creating dialog with null context");
        }
        categoryLayout = new CategoryLayout(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(categoryLayout)
                .setTitle("Add category")
                .setPositiveButton("Add", null)
                .setNeutralButton(android.R.string.cancel, (dialogInterface, i) -> {})
                .create();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> onClickAdd()));
        dialog.show();
        return dialog;
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
