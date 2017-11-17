package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<UiTask> tasks;
    private OnClickTaskListener onClickTaskListener;

    TasksAdapter() {
        this.tasks = new ArrayList<>();
    }

    void tasks(final List<UiTask> tasks) {
        this.tasks = ListUtils.newList(tasks);
        notifyDataSetChanged();
    }

    void onClickTaskListener(final OnClickTaskListener onClickTaskListener) {
        this.onClickTaskListener = onClickTaskListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task, parent));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UiTask task = tasks.get(position);
        final ItemLayout layout = holder.layout;
        layout.task(task);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickTaskListener != null) {
                    onClickTaskListener.onClick(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayout layout;

        private ViewHolder(View itemView) {
            super(itemView);
            layout = (ItemLayout) itemView;
        }
    }

    public static class ItemLayout extends LinearLayout {
        private final TextView titleView;

        public ItemLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            titleView = findViewById(R.id.title);
        }

        private void task(final UiTask task) {
            title(task.getTitle());
        }

        private void title(final String title) {
            titleView.setText(title);
        }
    }

    interface OnClickTaskListener {
        void onClick(UiTask task);
    }
}
