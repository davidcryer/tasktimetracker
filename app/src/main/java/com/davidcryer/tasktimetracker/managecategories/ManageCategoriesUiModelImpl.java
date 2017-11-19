package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class ManageCategoriesUiModelImpl implements ManageCategoriesUiModel {
    private List<Category> categories;
    private Date categoriesSet;
    private Integer filteredCategory;

    ManageCategoriesUiModelImpl(final List<Category> categories, final Date categoriesSet, final Integer filteredCategory) {
        this.categories = categories == null ? null : new LinkedList<>(categories);
        this.categoriesSet = categoriesSet == null ? null : new Date(categoriesSet.getTime());
        this.filteredCategory = filteredCategory;
    }

    @Override
    public void onto(@NonNull ManageCategoriesUi ui) {
        showCategoriesAndFilters(categories, ui);
    }

    @Override
    public void showCategories(List<Category> categories, ManageCategoriesUi ui) {
        if (ui != null) {
            showCategoriesAndFilters(categories, ui);
        }
        this.categories = categories;
        categoriesSet = new Date();
    }

    private void showCategoriesAndFilters(final List<Category> categories, final ManageCategoriesUi ui) {
        ui.show(filteredItems(filteredCategory));
        final List<String> titles = categoryTitles(categories);
        if (filteredCategory == null) {
            ui.showFilterOptions(titles);
        } else {
            ui.showFilterOptions(titles, filteredCategory);
        }
    }

    private List<String> categoryTitles(final List<Category> categories) {
        if (categories == null) {
            return new LinkedList<>();
        }
        final List<String> titles = new LinkedList<>();
        for (final Category category : categories) {
            titles.add(category.title());
        }
        return titles;
    }

    @Override
    public void addCategory(Category item, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.add(UiCategoryMapper.from(item));
        }
        if (categories == null) {
            categories = new LinkedList<>();
        }
        categories.add(item);
    }

    @Override
    public void insertCategory(Category item, int i, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.insert(UiCategoryMapper.from(item), i);
        }
        categories.set(i, item);
    }

    @Override
    public void updateCategory(Category item, ManageCategoriesUi ui) {
        final int categoryIndex = indexOf(item.id());
        if (ui != null) {
            ui.set(UiCategoryMapper.from(item), categoryIndex);
        }
        categories.set(categoryIndex, item);
    }

    @Override
    public void removeCategory(UUID categoryId, ManageCategoriesUi ui) {
        final int i = indexOf(categoryId);
        if (ui != null) {
            ui.remove(i);
        }
        categories.remove(i);
    }

    private int indexOf(final UUID categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).id().equals(categoryId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTask(Task task, UUID categoryId, ManageCategoriesUi ui) {
        final Category category = category(categoryId);
        if (category == null) {
            throw new IllegalStateException(String.format("Category not found for %1$s", categoryId.toString()));
        }
        int taskPosition = 0;//TODO refactor - maybe pass value through method params
        for (int i = 0; i < categories.indexOf(category); i++) {
            taskPosition += 2 + categories.get(i).tasks().size();
        }
        if (ui != null) {
            ui.insert(UiTaskMapper.from(task, category), taskPosition);
        }
        category.addTask(task);
    }

    @Override
    public void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui) {
        final Category category = category(categoryId);
        int taskIndex = 0;//TODO refactor - maybe pass value through method params
        for (final Category cat : categories) {
            taskIndex++;
            for (final Task task : cat.tasks()) {
                if (task.id().equals(taskId)) {
                    break;
                }
                taskIndex++;
            }
            if (cat.id().equals(categoryId)) {
                break;
            }
        }
        if (category != null) {
            if (category.deleteTask(taskId) && ui != null) {
                ui.remove(indexOf(category), taskIndex);
            }
        }
    }

    private int indexOf(final Category category) {
        return categories.indexOf(category);
    }

    private Category category(final UUID id) {
        for (final Category category : categories) {
            if (category.id().equals(id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void removeFilter(ManageCategoriesUi ui) {
        if (ui != null) {
            ui.show(filteredItems(null));
        }
        filteredCategory = null;
    }

    @Override
    public void updateFilter(int selected, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.show(filteredItems(selected));
        }
        filteredCategory = selected;
    }

    private List<UiListItem> filteredItems(Integer selected) {
        if (selected == null) {
            return unfilteredItems();
        }
        return taskItems(categories.get(selected));
    }

    private List<UiListItem> unfilteredItems() {
        final List<UiListItem> items = new LinkedList<>();
        for (final Category category: categories) {
            items.add(UiCategoryMapper.from(category));
            items.addAll(UiTaskMapper.from(category));
            items.add(AddTaskMapper.from(category));
        }
        return items;
    }

    private List<UiListItem> taskItems(final Category category) {
        final LinkedList<UiListItem> items = new LinkedList<>();
        items.addAll(UiTaskMapper.from(category));
        items.add(AddTaskMapper.from(category));
        return items;
    }

    @Override
    public boolean isPopulated() {
        return categories != null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categories);
        dest.writeLong(this.categoriesSet != null ? this.categoriesSet.getTime() : -1);
        dest.writeValue(this.filteredCategory);
    }

    private ManageCategoriesUiModelImpl(Parcel in) {
        this.categories = in.createTypedArrayList(Category.CREATOR);
        long tmpCategoriesSet = in.readLong();
        this.categoriesSet = tmpCategoriesSet == -1 ? null : new Date(tmpCategoriesSet);
        this.filteredCategory = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ManageCategoriesUiModelImpl> CREATOR = new Creator<ManageCategoriesUiModelImpl>() {
        @Override
        public ManageCategoriesUiModelImpl createFromParcel(Parcel source) {
            return new ManageCategoriesUiModelImpl(source);
        }

        @Override
        public ManageCategoriesUiModelImpl[] newArray(int size) {
            return new ManageCategoriesUiModelImpl[size];
        }
    };
}
