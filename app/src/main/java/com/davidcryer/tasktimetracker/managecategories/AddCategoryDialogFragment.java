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

public class AddCategoryDialogFragment extends DialogFragment implements ManageCategoriesUi.InputCategoryPrompt {
    private CategoryLayout categoryLayout;
    private AddCategoryNavigator listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryLayout = new CategoryLayout(getContext());
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(categoryLayout)
                .setTitle("Add category")
                .setPositiveButton("Add", null)
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickAdd();
                    }
                });
            }
        });
        return dialog;
    }

    private void onClickAdd() {
        if (listener != null) {
            listener.onClickAdd(this, categoryLayout.title(), categoryLayout.note());
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
        listener = (AddCategoryNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
