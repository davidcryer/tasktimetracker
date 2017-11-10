package com.davidcryer.tasktimetracker.managestories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.davidcryer.tasktimetracker.R;

public class RemoveStoryDialogFragment extends DialogFragment {
    private final static String ARGS_UI_STORY = "ui story";
    private RemoveStoryNavigator navigator;

    public static RemoveStoryDialogFragment newInstance(final UiStory story) {
        final RemoveStoryDialogFragment fragment = new RemoveStoryDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_UI_STORY, story);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final UiStory story = args == null ? null : (UiStory) args.getParcelable(ARGS_UI_STORY);
        if (story == null) {
            throw new IllegalStateException("Args must contain UiStory for ARGS_UI_STORY key");
        }
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.prompt_remove_story_title)
                .setMessage(String.format(getString(R.string.prompt_remove_story_message), story.getTitle()))
                .setPositiveButton(R.string.prompt_button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClickDelete(story);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void onClickDelete(final UiStory story) {
        if (navigator != null) {
            final RemoveStoryListener listener = navigator.listener();
            if (listener != null) {
                listener.onClickDelete(story);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (RemoveStoryNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
