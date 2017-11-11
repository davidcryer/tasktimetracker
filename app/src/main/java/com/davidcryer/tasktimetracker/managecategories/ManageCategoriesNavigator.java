package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public interface ManageCategoriesNavigator {
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddCategoryPrompt(DialogFragmentFactory factory);
    void showRemoveCategoryPrompt(DialogFragmentFactory factory);
    void showRemoveTaskPrompt(DialogFragmentFactory factory);

    interface Callback {
        void onAddCategory(ManageCategoriesUi.InputCategoryPrompt prompt, String title, String note);
    }
}
