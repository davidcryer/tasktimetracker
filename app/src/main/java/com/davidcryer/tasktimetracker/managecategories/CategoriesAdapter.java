package com.davidcryer.tasktimetracker.managecategories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class CategoriesAdapter extends RecyclerView.Adapter<UiListItem.ViewHolder> {
    private List<UiListItem> items;

    CategoriesAdapter() {
        this.items = new ArrayList<>();
    }

    void items(final List<UiListItem> items) {
        this.items = ListUtils.newList(items);
        notifyDataSetChanged();
    }

    void add(final UiListItem item, final int i) {
        items.add(i, item);
        notifyItemInserted(i);
    }

    void add(final List<UiListItem> items, final int i) {
        this.items.addAll(i, items);
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
        items.get(position).bind(holder);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).viewType().ordinal();
    }
}
