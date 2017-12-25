package com.davidcryer.tasktimetracker.addcategory;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidc.uiwrapper.UiWrapper;
import com.davidc.uiwrapper.UiWrapperFactoryFragment;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;

public class AddCategoryFragment extends UiWrapperFactoryFragment<Object, Object, UiWrapperFactory> {
    private final static String ARGS_CATEGORY_RETURN_KEY = "category return key";

    public static AddCategoryFragment newInstance(final String categoryReturnKey) {
        final AddCategoryFragment fragment = new AddCategoryFragment();
        final Bundle args = new Bundle();
        args.putString(ARGS_CATEGORY_RETURN_KEY, categoryReturnKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UiWrapper<Object, Object, ?> uiWrapper(UiWrapperFactory uiWrapperFactory, @Nullable Bundle savedState) {
        return null;
    }
}
