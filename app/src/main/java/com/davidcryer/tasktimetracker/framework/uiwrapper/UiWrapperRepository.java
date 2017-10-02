package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUi;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel>> manageTasksUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public ManageTasksUi.Listener bind(final ManageTasksUi ui, final UiBinder binder) {
        return binder.bind(ui, manageTasksUiWrapperMap, new UiBinder.UiWrapperProvider<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel>() {
            @NonNull
            @Override
            public UiWrapper<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createManageTasksUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final ManageTasksUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(manageTasksUiWrapperMap);
    }
}
