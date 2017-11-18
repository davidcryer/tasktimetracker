package com.davidcryer.tasktimetracker.managecategories;

import com.davidc.uiwrapper.UiModel;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUiModel extends UiModel<ManageCategoriesUi> {
    void showCategories(List<UiCategory> categories, ManageCategoriesUi ui);
    void addCategory(UiCategory category, ManageCategoriesUi ui);
    void insertCategory(UiCategory category, int i, ManageCategoriesUi ui);
    void updateCategory(UiCategory category, ManageCategoriesUi ui);
    void removeCategory(UUID categoryId, ManageCategoriesUi ui);
    void addTask(UiTask task, int categoryInd);
    void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui);
    void removeFilter(ManageCategoriesUi ui);
    void updateFilter(int selected, ManageCategoriesUi ui);
    boolean isPopulated();
}
