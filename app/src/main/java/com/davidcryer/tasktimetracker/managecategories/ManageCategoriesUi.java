package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUi {
    void showCategories(List<UiCategory> categories);//TODO change to show list of items - categories or tasks
    void addCategory(UiCategory category);
    void insertCategory(UiCategory category, int i);
    void setCategory(UiCategory category, int i);
    void removeCategory(int i);
    void addTask(UiTask task, int categoryInd);
    void removeTask(int categoryInd, int taskInd);
    void expandCategory(int i, int pos);
    void shrinkCategory(int i, int pos);
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
        void onLongClickCategory(ManageCategoriesUi ui, UiCategory category);
        void onClickTask(ManageCategoriesUi ui, UiTask task, UiCategory category);
        void onLongClickTask(ManageCategoriesUi ui, UiTask task, UiCategory category);
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
