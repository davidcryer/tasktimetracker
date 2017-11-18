package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class ManageCategoriesUiModelImpl implements ManageCategoriesUiModel {
    private List<UiCategory> categories;
    private Integer filteredCategory;

    ManageCategoriesUiModelImpl(final List<UiCategory> categories) {
        this.categories = categories == null ? null : new LinkedList<>(categories);
    }

    @Override
    public void onto(@NonNull ManageCategoriesUi ui) {
        showCategoriesAndFilters(categories, ui);
    }

    @Override
    public void showCategories(List<UiCategory> categories, ManageCategoriesUi ui) {
        if (ui != null) {
            showCategoriesAndFilters(categories, ui);
        }
        this.categories = categories;
    }

    private void showCategoriesAndFilters(final List<UiCategory> categories, final ManageCategoriesUi ui) {
        ui.showCategories(ListUtils.newList(filteredItems(filteredCategory)));
        final List<String> titles = categoryTitles(categories);
        if (filteredCategory == null) {
            ui.showFilterOptions(titles);
        } else {
            ui.showFilterOptions(titles, filteredCategory);
        }
    }

    private List<String> categoryTitles(final List<UiCategory> categories) {
        if (categories == null) {
            return new LinkedList<>();
        }
        final List<String> titles = new LinkedList<>();
        for (final UiCategory category : categories) {
            titles.add(category.getTitle());
        }
        return titles;
    }

    @Override
    public void addCategory(UiCategory category, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.addCategory(category);
        }
        if (categories == null) {
            categories = new LinkedList<>();
        }
        categories.add(category);
    }

    @Override
    public void insertCategory(UiCategory category, int i, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.insertCategory(category, i);
        }
        categories.set(i, category);
    }

    @Override
    public void updateCategory(UiCategory category, ManageCategoriesUi ui) {
        final int categoryIndex = indexOf(category.getId());
        if (ui != null) {
            ui.setCategory(category, categoryIndex);
        }
        categories.set(categoryIndex, category);
    }

    @Override
    public void removeCategory(UUID categoryId, ManageCategoriesUi ui) {
        final int i = indexOf(categoryId);
        if (ui != null) {
            ui.removeCategory(i);
        }
        categories.remove(i);
    }

    private int indexOf(final UUID categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTask(UiTask task, int categoryInd) {
        categories.get(categoryInd).addTask(task);
    }

    @Override
    public void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui) {
        final UiCategory category = category(categoryId);
        if (category != null) {
            final int taskIndex = category.taskIndex(taskId);
            if (category.removeTask(taskId) && ui != null) {
                ui.removeTask(indexOf(category), taskIndex);
            }
        }
    }

    private int indexOf(final UiCategory category) {
        return categories.indexOf(category);
    }

    private UiCategory category(final UUID id) {
        for (final UiCategory category : categories) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void removeFilter(ManageCategoriesUi ui) {
        if (ui != null) {
            ui.showCategories(filteredItems(null));
        }
        filteredCategory = null;
    }

    @Override
    public void updateFilter(int selected, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.showCategories(filteredItems(selected));//TODO show tasks for filtered category
        }
        filteredCategory = selected;
    }

    private List<UiCategory> filteredItems(Integer selected) {
        if (selected == null) {
            return ListUtils.newList(categories);
        }
        return new ArrayList<>(Collections.singletonList(categories.get(selected)));
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
        dest.writeValue(this.filteredCategory);
    }

    private ManageCategoriesUiModelImpl(Parcel in) {
        this.categories = in.createTypedArrayList(UiCategory.CREATOR);
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
