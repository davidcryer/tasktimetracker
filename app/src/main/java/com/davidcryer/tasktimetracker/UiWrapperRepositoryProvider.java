package com.davidcryer.tasktimetracker;

import android.content.Context;

import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.StorySharedPreferences;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.google.gson.Gson;

class UiWrapperRepositoryProvider {

    private UiWrapperRepositoryProvider() {}

    static UiWrapperRepository get(final Context context) {
        return new UiWrapperRepository(uiWrapperFactory(context));
    }

    private static UiWrapperFactory uiWrapperFactory(final Context context) {
        return new UiWrapperFactory(storyDatabase(context));
    }

    private static StoryDatabase storyDatabase(final Context context) {
        return new StorySharedPreferences(context, new Gson());
    }
}
