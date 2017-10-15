package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

public interface ManageStoriesNavigator {
    void toManageStoryScreen(ManageStoryIntentModel intentModel);
    void toManageTaskScreen(ManageTaskIntentModel intentModel);
    void toAddStoryScreen(int requestCode, String storyReturnKey);
}
