package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.UUID;

public interface ManageCategoriesNavigator {
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddCategoryPrompt(DialogFragmentFactory factory);
    void showAddTaskPrompt(DialogFragmentFactory factory);
    void showRemoveCategoryPrompt(DialogFragmentFactory factory);
    void showRemoveTaskPrompt(DialogFragmentFactory factory);
}
