package com.davidcryer.tasktimetracker;

import android.content.Context;

import com.davidcryer.tasktimetracker.common.domain.CategoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.PrePopulatedCategorySharedPreferences;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.google.gson.Gson;

class ApplicationDependencyProvider {

    private ApplicationDependencyProvider() {}

    static UiWrapperRepository uiWrapperRepository(final Context context) {
        return new UiWrapperRepository(uiWrapperFactory(context));
    }

    private static UiWrapperFactory uiWrapperFactory(final Context context) {
        return new UiWrapperFactory(categoryDatabase(context));
    }

    private static CategoryDatabase categoryDatabase(final Context context) {
        return new PrePopulatedCategorySharedPreferences(context, new Gson());
    }
}
