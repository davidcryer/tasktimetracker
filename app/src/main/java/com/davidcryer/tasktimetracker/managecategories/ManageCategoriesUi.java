package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUi {
    void show(List<UiListItem> items);//TODO change to showCategories list of items - items or tasks
    void add(UiListItem item);
    void insert(UiListItem item, int i);
    void set(UiListItem item, int i);
    void remove(int i);
    void remove(int i, int count);
    void showFilterOptions(List<String> options );
    void showFilterOptions(List<String> options, int selected);
    void showAddCategoryPrompt();
    void showRemoveCategoryPrompt(UiCategory category);
    void showRemoveTaskPrompt(UiTask task, UiCategory category);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);

    interface InputCategoryPrompt {
        void showTitleError(String message);
        void showNoteError(String message);
        void dismiss();
    }

    interface Listener {
        void onClickCategory(ManageCategoriesUi ui, UiCategory category, int pos);
        void onClickTask(ManageCategoriesUi ui, UiTask task);
        void onClickAddCategory(ManageCategoriesUi ui);
        void onClickAddTask(ManageCategoriesUi ui, UiCategory category);
        void onAddCategory(InputCategoryPrompt prompt, String title, String note);
        void onEditCategory(InputCategoryPrompt prompt, UUID categoryId, String title, String note);
        void onRemoveCategory(ManageCategoriesUi ui, UiCategory category);
        void onRemoveTask(ManageCategoriesUi ui, UiTask task, UiCategory category);
        void onFilterRemoved(ManageCategoriesUi ui);
        void onFilterSelected(ManageCategoriesUi ui, int i);
    }
}
