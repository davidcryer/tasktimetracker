package com.davidcryer.tasktimetracker.managecategories;

import java.util.UUID;

public interface AddTaskNavigator {
    void onClickAddTask(ManageCategoriesUi.InputPrompt prompt, String title, String note, UUID categoryId);
}
