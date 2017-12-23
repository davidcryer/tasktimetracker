package com.davidcryer.tasktimetracker;

import android.content.Context;

import com.davidcryer.tasktimetracker.common.domain.CategoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.OngoingTaskRegister;
import com.davidcryer.tasktimetracker.common.domain.PrePopulatedCategorySharedPreferences;
import com.davidcryer.tasktimetracker.common.domain.TaskFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.google.gson.Gson;

class ApplicationDependencyProvider {

    private ApplicationDependencyProvider() {}

    static UiWrapperFactory uiWrapperFactory(final Context context) {
        return new UiWrapperFactory(categoryDatabase(context), taskFactory());//TODO change CategoryDatabase to handle task creation
    }

    private static CategoryDatabase categoryDatabase(final Context context) {
        return new PrePopulatedCategorySharedPreferences(context, new Gson(), taskFactory());
    }

    private static TaskFactory taskFactory() {
        return new TaskFactory(ongoingTaskRegister());
    }

    private static OngoingTaskRegister ongoingTaskRegister() {
        return new OngoingTaskRegister();
    }
}
