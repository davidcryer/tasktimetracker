package com.davidcryer.tasktimetracker;

import android.app.Application;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapperRepositoryFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;

public class TrackerApplication extends Application implements UiWrapperRepositoryFactory<UiWrapperRepository> {

    @NonNull
    @Override
    public UiWrapperRepository create() {
        return UiWrapperRepositoryProvider.get(this);
    }
}
