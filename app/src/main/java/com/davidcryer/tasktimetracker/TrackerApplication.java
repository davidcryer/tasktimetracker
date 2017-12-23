package com.davidcryer.tasktimetracker;

import android.app.Application;

import com.davidc.uiwrapper.UiWrapperFactoryProvider;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;

public class TrackerApplication extends Application implements UiWrapperFactoryProvider<UiWrapperFactory> {

    @Override
    public UiWrapperFactory getUiWrapperFactory() {
        return ApplicationDependencyProvider.uiWrapperFactory(this);
    }
}
