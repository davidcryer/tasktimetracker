package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CategoryFilters implements Parcelable {
    public final static int DELELECTED = 0;
    private final List<CategoryFilter> filters;
    private int selected;

    CategoryFilters(List<CategoryFilter> filters, int selected) {
        this.filters = filters;
        this.selected = selected;
    }

    CategoryFilters(CategoryFilters categoryFilters) {
        this.filters = new ArrayList<>(categoryFilters.filters);
        this.selected = categoryFilters.selected;
    }

    CategoryFilter select(final int index) {
        selected = index;
        return filters.get(index);
    }

    List<String> options() {
        return filters.stream().map(CategoryFilter::getTitle).collect(Collectors.toList());
    }

    int selected() {
        return selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.filters);
        dest.writeInt(this.selected);
    }

    private CategoryFilters(Parcel in) {
        this.filters = in.createTypedArrayList(CategoryFilter.CREATOR);
        this.selected = in.readInt();
    }

    public static final Creator<CategoryFilters> CREATOR = new Creator<CategoryFilters>() {
        @Override
        public CategoryFilters createFromParcel(Parcel source) {
            return new CategoryFilters(source);
        }

        @Override
        public CategoryFilters[] newArray(int size) {
            return new CategoryFilters[size];
        }
    };
}
