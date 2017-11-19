package com.davidcryer.tasktimetracker.managecategories;

import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract class UiListItem {

    abstract ViewType viewType();

    abstract void bind(final ViewHolder holder, final Listener listener);

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        void category(final UiCategory category, final UiCategory.Listener listener) {
            throw new UnsupportedOperationException(String.format("%1$s.category() method should not be used", getClass().getCanonicalName()));
        }

        void task(final UiTask task, final UiTask.Listener listener) {
            throw new UnsupportedOperationException(String.format("%1$s.task() method should not be used", getClass().getCanonicalName()));
        }
    }

    interface Listener {

    }
}
