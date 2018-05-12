package com.davidcryer.tasktimetracker;

import android.content.Context;

import com.davidcryer.tasktimetracker.common.domain.CategoryRepository;
import com.davidcryer.tasktimetracker.common.domain.PrePopulatedCategorySharedPreferences;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.google.gson.Gson;

import static com.davidcryer.tasktimetracker.common.domain.DomainDependencyProvider.categoryFactory;

class ApplicationDependencyProvider {

    private ApplicationDependencyProvider() {}

    static UiWrapperFactory uiWrapperFactory(final Context context) {
        return new UiWrapperFactory(domainManager(context));
    }

    private static CategoryRepository domainManager(final Context context) {
        return new PrePopulatedCategorySharedPreferences(context, new Gson(), categoryFactory());
    }
}
