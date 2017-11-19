package com.davidcryer.tasktimetracker.managecategories;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class CategoriesAdapter extends RecyclerView.Adapter<UiListItem.ViewHolder> implements UiCategory.Listener, UiTask.Listener {
    private List<UiListItem> items;
    private OnClickListener onClickListener;

    CategoriesAdapter() {
        this.items = new ArrayList<>();
    }

    void onClickCategoryListener(final OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

    void set(final UiListItem item, final int i) {
        items.set(i, item);
        notifyItemChanged(i);
    }

    void remove(final int i) {
        items.remove(i);
        notifyItemRemoved(i);
    }

    void remove(final int i, final int count) {
        items.remove(i);
        notifyItemRangeRemoved(i, count);
    }

    @Override
    public UiListItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewType.values()[viewType].viewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final UiListItem.ViewHolder holder, int position) {
        items.get(position).bind(holder, this);
    }

    @Override
    public void onClickCategory(final UiCategory category, final int i) {
        if (onClickListener != null) {
            onClickListener.onClick(category, i);
        }
    }

    @Override
    public void onClickTask(final UiTask task) {
        if (onClickListener != null) {
            onClickListener.onClick(task);
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

    interface OnClickListener {
        void onClick(UiCategory category, int i);
        void onClick(UiTask task);
    }
}
