package com.davidcryer.tasktimetracker.managecategories;

import com.davidc.uiwrapper.UiModel;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUiModel extends UiModel<ManageCategoriesUi> {
    void showCategories(List<Category> categories, ManageCategoriesUi ui);
    void addCategory(Category category, ManageCategoriesUi ui);
    void updateCategory(Category category, ManageCategoriesUi ui);
    void removeCategory(UUID categoryId, ManageCategoriesUi ui);
    void addTask(Task task, Category category, ManageCategoriesUi ui);
    void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui);
    void removeFilter(ManageCategoriesUi ui);
    void activate(Task task, ManageCategoriesUi ui);
    void deactivate(Task task, ManageCategoriesUi ui);
    void updateFilter(int selected, ManageCategoriesUi ui);
    boolean isPopulated();
    Category category(UUID id);
    Task task(UUID id);
}
