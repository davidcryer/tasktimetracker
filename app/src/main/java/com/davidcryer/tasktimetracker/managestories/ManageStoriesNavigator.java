package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public interface ManageStoriesNavigator {
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddStoryPrompt(DialogFragmentFactory factory);
    void showRemoveStoryPrompt(DialogFragmentFactory factory);

    interface Callback {
        void onAddStory(ManageStoriesUi.InputStoryPrompt prompt, String title, String note);
    }
}
