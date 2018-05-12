package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcelable;

public enum UiListItemFactory {
    CATEGORY {
        @Override
        public Parcelable.Creator<UiListItem> creator() {
            return UiCategory.CREATOR;
        }
    }, TASK {
        @Override
        public Parcelable.Creator<UiListItem> creator() {
            return UiTask.CREATOR;
        }
    };

    public abstract Parcelable.Creator<UiListItem> creator();
}
