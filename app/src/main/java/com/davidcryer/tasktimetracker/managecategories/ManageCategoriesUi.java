package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUi {
    void show(List<UiListItem> items);
    void add(UiListItem item, int i);
    void add(List<UiListItem> items, int i);
    void remove(int i);
    void remove(int i, int count);
    void showFilterOptions(List<String> options, int selected);
    void showAddCategoryPrompt();
    void showAddTaskPrompt(UUID categoryId);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);

    interface InputPrompt {
        void showTitleError(String message);
        void showNoteError(String message);
        void dismiss();
    }

    interface Listener {
        void onClickAddCategory(ManageCategoriesUi ui);
        void onFilterRemoved(ManageCategoriesUi ui);
        void onFilterSelected(ManageCategoriesUi ui, int i);
    }

    interface ListItemListener {
        void onClickCategory(ManageCategoriesUi ui, UiCategory category, int pos);
        void onClickTask(ManageCategoriesUi ui, UiTask task);
        void onClickAddTask(ManageCategoriesUi ui, UUID categoryId);
    }
}
