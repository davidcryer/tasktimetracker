package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class CategoryFilter implements Parcelable {
    private final UUID id;
    private final String title;

    CategoryFilter(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    UUID getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.title);
    }

    private CategoryFilter(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
    }

    public static final Creator<CategoryFilter> CREATOR = new Creator<CategoryFilter>() {
        @Override
        public CategoryFilter createFromParcel(Parcel source) {
            return new CategoryFilter(source);
        }

        @Override
        public CategoryFilter[] newArray(int size) {
            return new CategoryFilter[size];
        }
    };
}
