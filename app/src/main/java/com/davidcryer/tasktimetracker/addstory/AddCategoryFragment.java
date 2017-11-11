package com.davidcryer.tasktimetracker.addstory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;

public class AddCategoryFragment extends UiFragment {
    private final static String ARGS_CATEGORY_RETURN_KEY = "category return key";

    public static AddCategoryFragment newInstance(final String categoryReturnKey) {
        final AddCategoryFragment fragment = new AddCategoryFragment();
        final Bundle args = new Bundle();
        args.putString(ARGS_CATEGORY_RETURN_KEY, categoryReturnKey);
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
