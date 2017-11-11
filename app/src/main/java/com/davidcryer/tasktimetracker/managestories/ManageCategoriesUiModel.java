package com.davidcryer.tasktimetracker.managestories;

import com.davidc.uiwrapper.UiModel;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUiModel extends UiModel<ManageCategoriesUi> {
    void showCategories(List<UiCategory> categories, ManageCategoriesUi ui);
    void removeCategory(UUID categoryId, ManageCategoriesUi ui);
    void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui);
    void addCategory(UiCategory category, ManageCategoriesUi ui);
    void insertCategory(UiCategory category, int i, ManageCategoriesUi ui);
    void updateCategory(UiCategory category, ManageCategoriesUi ui);
    void expandCategory(UiCategory category, int pos, ManageCategoriesUi ui);
    void shrinkCategory(UiCategory category, int pos, ManageCategoriesUi ui);
    boolean isPopulated();
}
