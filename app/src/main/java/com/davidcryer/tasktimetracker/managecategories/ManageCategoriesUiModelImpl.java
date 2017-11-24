package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.CategoryUtils;
import com.davidcryer.tasktimetracker.common.domain.Task;

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
    public void addCategory(Category category, ManageCategoriesUi ui) {
        ensureCategoriesNonNull();
        categories.add(category);
        CategoryUtils.sortAlphabetically(categories);
        if (ui != null) {
            ui.insert(UiCategoryMapper.from(category), position(category.id()));
        }
    }

    private void ensureCategoriesNonNull() {
        if (categories == null) {
            categories = new LinkedList<>();
        }
    }

    private int position(final UUID categoryId) {
        int position = 0;
        for (final Category category : categories) {
            if (category.id().equals(categoryId)) {
                break;
            }
            position += 1 + category.tasks().size();
        }
        return position;
    }

    @Override
    public void updateCategory(Category category, ManageCategoriesUi ui) {
        final int categoryIndex = position(category.id());
        if (ui != null) {
            ui.set(UiCategoryMapper.from(category), categoryIndex);
        }
        categories.set(categoryIndex, category);
    }

    @Override
    public void removeCategory(UUID categoryId, ManageCategoriesUi ui) {
        final int i = position(categoryId);
        if (ui != null) {
            ui.remove(i);
        }
        categories.remove(i);
    }

    @Override
    public void addTask(Task task, UUID categoryId, ManageCategoriesUi ui) {
        final Category category = category(categoryId);
        if (category == null) {
            throw new IllegalStateException(String.format("Category not found for %1$s", categoryId.toString()));
        }
        if (ui != null) {
            ui.insert(UiTaskMapper.from(task, category), lastTaskPosition(category));
        }
        category.addTask(task);
    }

    private int lastTaskPosition(final Category category) {
        int position = 0;
        if (filteredCategory == null) {
            final int categoryOffset = 1;
            for (int i = 0; i < categories.indexOf(category) + 1; i++) {
                position += categoryOffset + categories.get(i).tasks().size();
            }
        } else if (filteredCategory == categories.indexOf(category)) {
            position = 1 + category.tasks().size();
        } else {
            position = -1;
        }
        return position;
    }

    @Override
    public void removeTask(UUID taskId, UUID categoryId, ManageCategoriesUi ui) {
        final Category category = category(categoryId);
        final int position = removedTaskPosition(taskId, category);
        if (category != null) {
            if (category.deleteTask(taskId) && ui != null) {
                ui.remove(indexOf(category), position);
            }
        }
    }

    private int removedTaskPosition(final UUID taskId, final Category category) {
        int position = 0;
        if (filteredCategory == null) {
            for (int i = 0; i < categories.indexOf(category); i++) {
                position++;
                final Category c = categories.get(i);
                for (final Task task : c.tasks()) {
                    if (task.id().equals(taskId)) {
                        break;
                    }
                    position++;
                }
                if (c.id().equals(category.id())) {
                    break;
                }
            }
        } else if (filteredCategory == categories.indexOf(category)) {
            for (final Task task : category.tasks()) {
                if (task.id().equals(taskId)) {
                    break;
                }
                position++;
            }
        } else {
            position = -1;
        }
        return position;
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
        return categoryItems(categories.get(selected));
    }

    private List<UiListItem> unfilteredItems() {
        final List<UiListItem> items = new LinkedList<>();
        for (final Category category: categories) {
            addCategoryItems(category, items);
        }
        return items;
    }

    private static List<UiListItem> categoryItems(final Category category) {
        final LinkedList<UiListItem> items = new LinkedList<>();
        addCategoryItems(category, items);
        return items;
    }

    private static void addCategoryItems(final Category category, final List<UiListItem> items) {
        items.add(UiCategoryMapper.from(category));
        items.addAll(UiTaskMapper.from(category));
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
