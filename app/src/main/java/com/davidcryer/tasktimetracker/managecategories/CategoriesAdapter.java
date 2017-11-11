package com.davidcryer.tasktimetracker.managecategories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private final static int VIEW_TYPE_CATEGORY = 1;
    private final static int VIEW_TYPE_TASK = 2;
    private List<UiCategory> categories;
    private OnClickCategoryListener onClickCategoryListener;
    private int cachedItemCount;
    private boolean invalidatedCachedItemCount = true;

    CategoriesAdapter() {
        this.categories = new ArrayList<>();
    }

    void Categories(final List<UiCategory> categories) {
        invalidateCachedItemCount();
        this.categories = ListUtils.newList(categories);
        notifyDataSetChanged();
    }

    void onClickCategoryListener(final OnClickCategoryListener onClickCategoryListener) {
        this.onClickCategoryListener = onClickCategoryListener;
    }

    void add(final UiCategory category) {
        invalidateCachedItemCount();
        categories.add(category);
        notifyItemInserted(getItemCount() - 1);
    }

    void insert(final UiCategory category, final int i) {
        invalidateCachedItemCount();
        categories.add(i, category);
        notifyItemInserted(categoryPosition(i));
    }

    void set(final UiCategory category, final int i) {
        categories.set(i, category);
        notifyItemChanged(categoryPosition(i));
    }

    void remove(final int i) {
        invalidateCachedItemCount();
        final UiCategory category = categories.remove(i);
        notifyItemRangeRemoved(categoryPosition(i), category.expandedTaskCount() + 1);
    }

    void removeTask(final int categoryInd, final int taskInd) {
        final UiCategory category = categories.get(categoryInd);
        if (category.isExpanded()) {
            invalidateCachedItemCount();
            notifyItemRemoved(categoryPosition(categoryInd) + 1 + taskInd);
        }
    }

    private int categoryPosition(final int index) {
        int pos = 0;
        for (int i = 0; i < categories.size(); i++) {
            if (i == index) {
                return pos;
            }
            pos += 1 + categories.get(i).expandedTaskCount();
        }
        return pos;
    }

    void expandCategory(final int i, final int pos) {
        invalidateCachedItemCount();
        notifyItemRangeInserted(pos + 1, categories.get(i).taskCount());
    }


    void shrinkCategory(final int i, final int pos) {
        invalidateCachedItemCount();
        notifyItemRangeRemoved(pos + 1, categories.get(i).taskCount());
    }

    private void invalidateCachedItemCount() {
        invalidatedCachedItemCount = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CATEGORY: {
                return new CategoryViewHolder(categoryLayout(parent));
            }
            case VIEW_TYPE_TASK: {
                return new TaskViewHolder(taskLayout(parent));
            }
        }
        throw new IllegalStateException(String.format("viewType is not recognised: %1$s", viewType));
    }

    private static CategoryLayout categoryLayout(final ViewGroup group) {
        return (CategoryLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_category, group, false);
    }

    private static TaskLayout taskLayout(final ViewGroup group) {
        return (TaskLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_task, group, false);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        for (int i = 0, posItr = 0; i < categories.size(); i++) {
            final UiCategory category = categories.get(i);
            if (bindCategory(holder, position, category, posItr)) {
                break;
            }
            posItr++;
            if (bindCategoryTask(holder, position, category, posItr)) {
                break;
            }
            posItr += category.expandedTaskCount();
        }
    }

    private boolean bindCategory(final ViewHolder holder, final int holderPos, final UiCategory category, final int categoryPos) {
        if (holderPos == categoryPos) {
            holder.category(category);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickCategory(category, holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClickCategory(category);
                    return true;
                }
            });
            return true;
        }
        return false;
    }

    private void onClickCategory(final UiCategory category, final int pos) {
        if (onClickCategoryListener != null) {
            onClickCategoryListener.onClick(category, pos);
        }
    }

    private void onLongClickCategory(final UiCategory category) {
        if (onClickCategoryListener != null) {
            onClickCategoryListener.onLongClick(category);
        }
    }

    private boolean bindCategoryTask(final ViewHolder holder, final int holderPos, final UiCategory category, final int firstTaskPos) {
        if (holderPos < firstTaskPos + category.expandedTaskCount()) {
            final UiTask task = category.task(holderPos - firstTaskPos);
            holder.task(task);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickTask(task, category);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClickTask(task, category);
                    return true;
                }
            });
            return true;
        }
        return false;
    }

    private void onClickTask(final UiTask task, final UiCategory category) {
        if (onClickCategoryListener != null) {
            onClickCategoryListener.onClick(task, category);
        }
    }

    private void onLongClickTask(final UiTask task, final UiCategory category) {
        if (onClickCategoryListener != null) {
            onClickCategoryListener.onLongClick(task, category);
        }
    }

    @Override
    public int getItemCount() {
        if (invalidatedCachedItemCount) {
            cachedItemCount = 0;
            for (final UiCategory category : categories) {
                cachedItemCount += 1 + category.expandedTaskCount();
            }
            invalidatedCachedItemCount = false;
        }
        return cachedItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0, posItr = 0; i < categories.size(); i++) {
            final UiCategory category = categories.get(i);
            if (position == posItr) {
                return VIEW_TYPE_CATEGORY;
            }
            posItr++;
            if (position < posItr + category.expandedTaskCount()) {
                return VIEW_TYPE_TASK;
            }
            posItr += category.expandedTaskCount();
        }
        throw new IllegalStateException("Item neither category nor task");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewHolder(View itemView) {
            super(itemView);
        }

        void category(final UiCategory category) {
            throw new UnsupportedOperationException("Categories.ViewHolder.category() method should not be used");
        }

        void task(final UiTask task) {
            throw new UnsupportedOperationException("Categories.ViewHolder.task() method should not be used");
        }
    }

    private static class CategoryViewHolder extends ViewHolder {
        private final CategoryLayout layout;

        private CategoryViewHolder(CategoryLayout itemView) {
            super(itemView);
            layout = itemView;
        }

        @Override
        void category(UiCategory category) {
            layout.category(category);
        }
    }

    private static class TaskViewHolder extends ViewHolder {
        private final TaskLayout layout;

        private TaskViewHolder(TaskLayout itemView) {
            super(itemView);
            layout = itemView;
        }

        @Override
        void task(UiTask task) {
            layout.task(task);
        }
    }

    interface OnClickCategoryListener {
        void onClick(UiCategory category, int pos);
        void onLongClick(UiCategory category);
        void onClick(UiTask task, UiCategory category);
        void onLongClick(UiTask task, UiCategory category);
    }
}
