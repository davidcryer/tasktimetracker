package com.davidcryer.tasktimetracker.managecategories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CategoriesAdapter extends RecyclerView.Adapter<UiListItem.ViewHolder> implements UiCategory.Listener, UiTask.Listener {
    private List<UiListItem> items;
    private Listener listener;

    CategoriesAdapter() {
        this.items = new ArrayList<>();
    }

    void onClickCategoryListener(final Listener listener) {
        this.listener = listener;
    }

    void items(final List<UiListItem> items) {
        this.items = ListUtils.newList(items);
        notifyDataSetChanged();
    }

    void add(final UiListItem item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    void insert(final UiListItem item, final int i) {
        items.add(i, item);
        notifyItemInserted(i);
    }

    void remove(final int i) {
        items.remove(i);
        notifyItemRemoved(i);
    }

    void remove(final int i, final int count) {
        items.remove(i);
        notifyItemRangeRemoved(i, count);
    }

    @NonNull
    @Override
    public UiListItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewType.values()[viewType].viewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final UiListItem.ViewHolder holder, int position) {
        items.get(position).bind(holder, this);
    }

    @Override
    public void onClickCategory(final UiCategory category, final int i) {
        if (listener != null) {
            listener.onClick(category, i);
        }
    }

    @Override
    public void onClickTask(final UiTask task) {
        if (listener != null) {
            listener.onClick(task);
        }
    }

    @Override
    public void onToggleActiveStatus(UiTask task, boolean isActive) {
        if (listener != null) {
            listener.onToggleActiveStatus(task, isActive);
        }
    }

    @Override
    public void onClickAddTask(UUID categoryId) {
        if (listener != null) {
            listener.onClickAddTask(categoryId);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).viewType().ordinal();
    }

    interface Listener {
        void onClick(UiCategory category, int i);
        void onClick(UiTask task);
        void onToggleActiveStatus(UiTask task, boolean isActive);
        void onClickAddTask(UUID categoryId);
    }
}
