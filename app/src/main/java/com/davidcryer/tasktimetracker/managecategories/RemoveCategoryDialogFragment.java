package com.davidcryer.tasktimetracker.managecategories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.davidcryer.tasktimetracker.R;

public class RemoveCategoryDialogFragment extends DialogFragment {
    private final static String ARGS_UI_CATEGORY = "ui category";
    private RemoveCategoryNavigator navigator;

    public static RemoveCategoryDialogFragment newInstance(final UiCategory category) {
        final RemoveCategoryDialogFragment fragment = new RemoveCategoryDialogFragment();
        final Bundle args = new Bundle();
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
        final UiCategory category = args.getParcelable(ARGS_UI_CATEGORY);
        if (category == null) {
            throw new IllegalStateException("Args must contain UiCategory for ARGS_UI_CATEGORY key");
        }
        final Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Creating dialog with null context");
        }
        return new AlertDialog.Builder(context)
                .setTitle(R.string.prompt_remove_category_title)
                .setMessage(String.format(getString(R.string.prompt_remove_category_message), category.getTitle()))
                .setPositiveButton(R.string.prompt_button_delete, (dialogInterface, i) -> onClickDelete(category))
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> {

                })
                .show();
    }

    private void onClickDelete(final UiCategory category) {
        if (navigator != null) {
            final RemoveCategoryListener listener = navigator.removeCategoryListener();
            if (listener != null) {
                listener.onClickDelete(category);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (RemoveCategoryNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
