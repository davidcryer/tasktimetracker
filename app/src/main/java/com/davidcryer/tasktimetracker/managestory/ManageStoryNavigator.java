package com.davidcryer.tasktimetracker.managestory;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public interface ManageStoryNavigator {
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
}
