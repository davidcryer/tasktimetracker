package com.davidcryer.tasktimetracker.addstory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;

public class AddStoryFragment extends UiFragment {
    private final static String ARGS_STORY_RETURN_KEY = "story return key";

    public static AddStoryFragment newInstance(final String storyReturnKey) {
        final AddStoryFragment fragment = new AddStoryFragment();
        final Bundle args = new Bundle();
        args.putString(ARGS_STORY_RETURN_KEY, storyReturnKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object bind(@NonNull Object uiWrapperRepository, @NonNull UiBinder binder) {
        return null;
    }

    @Override
    protected void unbind(@NonNull Object uiWrapperRepository, @NonNull UiUnbinder binder) {

    }
}
