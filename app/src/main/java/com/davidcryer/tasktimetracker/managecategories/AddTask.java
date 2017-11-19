package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;

import java.util.UUID;

class AddTask extends UiListItem {
    private final UUID categoryId;

    AddTask(UUID categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    ViewType viewType() {
        return ViewType.ADD_TASK;
    }

    @Override
    void bind(UiListItem.ViewHolder holder, UiListItem.Listener listener) {
        bind((ViewHolder) holder, (Listener) listener);
    }

    private void bind(final ViewHolder holder, final Listener listener) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAddTask(categoryId);
            }
        });
    }

    static class ViewHolder extends UiListItem.ViewHolder {
        private final AddTaskLayout layout;

        private ViewHolder(final AddTaskLayout layout) {
            super(layout);
            this.layout = layout;
        }

        static ViewHolder newInstance(final ViewGroup group) {
            return new ViewHolder((AddTaskLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_add_task, group, false));
        }
    }

    interface Listener extends UiListItem.Listener {
        void onClickAddTask(UUID categoryId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.categoryId);
    }

    private AddTask(Parcel in) {
        this.categoryId = (UUID) in.readSerializable();
    }

    public static final Creator<AddTask> CREATOR = new Creator<AddTask>() {
        @Override
        public AddTask createFromParcel(Parcel source) {
            return new AddTask(source);
        }

        @Override
        public AddTask[] newArray(int size) {
            return new AddTask[size];
        }
    };
}
