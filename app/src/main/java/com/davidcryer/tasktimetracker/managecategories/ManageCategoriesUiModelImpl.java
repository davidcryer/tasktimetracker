package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.List;

class ManageCategoriesUiModelImpl implements ManageCategoriesUiModel {
    private List<UiListItem> items;
    private CategoryFilters filters;

    ManageCategoriesUiModelImpl(List<UiListItem> items, CategoryFilters filters) {
        this.items = items;
        this.filters = filters;
    }

    @Override
    public void onto(@NonNull ManageCategoriesUi ui) {
        ui.show(items);
        ui.showFilterOptions(filters.options(), filters.selected());
    }

    @Override
    public void setItems(List<UiListItem> items, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.show(items);
        }
        this.items = items;
    }

    @Override
    public void setItems(List<UiListItem> items, CategoryFilters filters, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.show(items);
            ui.showFilterOptions(filters.options(), filters.selected());
        }
        this.items = items;
        this.filters = filters;
    }

    @Override
    public void addItem(UiListItem item, int i, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.add(item, i);
        }
        items.add(i, item);
    }

    @Override
    public void addItems(List<UiListItem> items, int i, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.add(items, i);
        }
        this.items.addAll(i, items);
    }

    @Override
    public void removeItem(int i, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.remove(i);
        }
        items.remove(i);
    }

    @Override
    public void removeItems(int i, int count, ManageCategoriesUi ui) {
        if (ui != null) {
            ui.remove(i, count);
        }
        ListUtils.remove(items, i, count);
    }

    @Override
    public boolean isPopulated() {
        return items != null;
    }

    @Override
    public CategoryFilters filters() {
        return new CategoryFilters(filters);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.items);
        dest.writeParcelable(this.filters, flags);
    }

    private ManageCategoriesUiModelImpl(Parcel in) {
        this.items = in.createTypedArrayList(UiListItem.CREATOR);
        this.filters = in.readParcelable(CategoryFilters.class.getClassLoader());
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
