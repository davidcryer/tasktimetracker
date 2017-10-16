package com.davidcryer.tasktimetracker.managestory;

import java.util.List;

public class ManageStoryUiModelFactory {
    private final static ManageStoryUiModelImpl.State DEFAULT_STATE = ManageStoryUiModelImpl.State.MANAGE;
    private final static List<UiTask> DEFAULT_TASKS = null;
    private final ManageStoryIntentModel intentModel;

    public ManageStoryUiModelFactory(ManageStoryIntentModel intentModel) {
        this.intentModel = intentModel;
    }

    ManageStoryUiModel create() {
        return new ManageStoryUiModelImpl(DEFAULT_STATE, UiStoryMapper.from(intentModel), DEFAULT_TASKS);
    }
}
