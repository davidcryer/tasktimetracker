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
        void onClickCategory(ManageCategoriesUi ui, UiCategory category, int pos);
        void onClickTask(ManageCategoriesUi ui, UiTask task);
        void onClickAddCategory(ManageCategoriesUi ui);
        void onClickAddTask(ManageCategoriesUi ui, UUID categoryId);
        void onActivateTask(ManageCategoriesUi ui, UiTask task);
        void onDeactivateTask(ManageCategoriesUi ui, UiTask task);
        void onAddCategory(InputPrompt prompt, String title, String note);
        void onAddTask(InputPrompt prompt, String title, String note, UUID categoryId);
        void onRemoveCategory(ManageCategoriesUi ui, UiCategory category);
        void onRemoveTask(ManageCategoriesUi ui, UiTask task, UiCategory category);
        void onFilterRemoved(ManageCategoriesUi ui);
        void onFilterSelected(ManageCategoriesUi ui, int i);
    }
}
