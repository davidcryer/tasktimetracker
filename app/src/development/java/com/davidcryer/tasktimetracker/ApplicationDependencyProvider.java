package com.davidcryer.tasktimetracker;

import android.content.Context;

import com.davidcryer.tasktimetracker.common.domain.CategoryFactory;
import com.davidcryer.tasktimetracker.common.domain.DomainManager;
import com.davidcryer.tasktimetracker.common.domain.OngoingTaskRegister;
import com.davidcryer.tasktimetracker.common.domain.PrePopulatedCategorySharedPreferences;
import com.davidcryer.tasktimetracker.common.domain.TaskFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.google.gson.Gson;

class ApplicationDependencyProvider {

    private ApplicationDependencyProvider() {}

    static UiWrapperFactory uiWrapperFactory(final Context context) {
        return new UiWrapperFactory(domainManager(context));//TODO change DomainManager to handle task creation
    }

    private static DomainManager domainManager(final Context context) {
        return new PrePopulatedCategorySharedPreferences(context, new Gson(), categoryFactory());
    }

    private static CategoryFactory categoryFactory() {
        return new CategoryFactory(taskFactory());
    }

    private static TaskFactory taskFactory() {
        return new TaskFactory(ongoingTaskRegister());
    }

    private static OngoingTaskRegister ongoingTaskRegister() {
        return new OngoingTaskRegister();
    }
}
