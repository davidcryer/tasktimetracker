package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public interface ManageStoriesNavigator {
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddStoryPrompt();

    interface Callback {
        void onAddStory(ManageStoriesUi.InputStoryPrompt prompt, String title, String note);
    }
}
