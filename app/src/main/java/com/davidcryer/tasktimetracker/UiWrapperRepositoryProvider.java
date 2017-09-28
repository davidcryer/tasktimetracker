package com.davidcryer.tasktimetracker;

import com.davidcryer.tasktimetracker.framework.uiwrapper.UiWrapperFactory;
import com.davidcryer.tasktimetracker.framework.uiwrapper.UiWrapperRepository;

class UiWrapperRepositoryProvider {

    private UiWrapperRepositoryProvider() {}

    static UiWrapperRepository get() {
        return new UiWrapperRepository(uiWrapperFactory());
    }

    private static UiWrapperFactory uiWrapperFactory() {
        return new UiWrapperFactory();
    }
}
