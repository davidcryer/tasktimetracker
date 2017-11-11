package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class ManageCategoriesUiModelImpl implements ManageCategoriesUiModel {
    private List<UiCategory> categories;

    ManageCategoriesUiModelImpl(final List<UiCategory> categories) {
        this.categories = categories == null ? null : new LinkedList<>(categories);
    }

    @Override
    public void onto(@NonNull ManageCategoriesUi ui) {
        ui.showCategories(ListUtils.newList(categories));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(categories);
    }

    private ManageCategoriesUiModelImpl(Parcel in) {
        categories = in.createTypedArrayList(UiCategory.CREATOR);
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

    @Override
    public void showCategories(List<UiCategory> categories, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.showCategories(ListUtils.newList(categories));
        }
        this.categories = categories;
    }

    @Override
    public void removeCategory(UUID categoryId, ManageCategoriesUi ui) {
        final int i = indexOf(categoryId);
        if (ui != null) {
            ui.removeCategory(i);
        }
        categories.remove(i);
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
    public void expandCategory(UiCategory category, int pos, ManageCategoriesUi ui) {
        category.expand();
        if (ui != null) {
            ui.expandCategory(indexOf(category), pos);
        }
    }

    @Override
    public void shrinkCategory(UiCategory category, int pos, ManageCategoriesUi ui) {
        category.shrink();
        if (ui != null) {
            ui.shrinkCategory(indexOf(category), pos);
        }
    }

    private int indexOf(final UiCategory category) {
        return categories.indexOf(category);
    }

    private int indexOf(final UUID categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId)) {
                return i;
            }
        }
        return -1;
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
    public boolean isPopulated() {
        return categories != null;
    }
}
