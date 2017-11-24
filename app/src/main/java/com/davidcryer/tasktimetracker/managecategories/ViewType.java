package com.davidcryer.tasktimetracker.managecategories;

import android.view.ViewGroup;

enum ViewType {
    CATEGORY {
        @Override
        UiListItem.ViewHolder viewHolder(ViewGroup group) {
            return UiCategory.ViewHolder.newInstance(group);
        }
    }, TASK {
        @Override
        UiListItem.ViewHolder viewHolder(ViewGroup group) {
            return UiTask.ViewHolder.newInstance(group);
        }
    };

    abstract UiListItem.ViewHolder viewHolder(final ViewGroup group);
}
