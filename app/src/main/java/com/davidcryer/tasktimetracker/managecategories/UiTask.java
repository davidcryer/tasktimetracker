package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;

import java.util.UUID;

class UiTask extends UiListItem {
    private final UUID id;
    private final String title;
    private final String note;
    private long totalTimeActive;
    private boolean isActive;
    private final UUID categoryId;

    UiTask(UUID id, String title, String note, long totalTimeActive, boolean isActive, UUID categoryId) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.totalTimeActive = totalTimeActive;
        this.isActive = isActive;
        this.categoryId = categoryId;
    }

    UUID getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getNote() {
        return note;
    }

    long getTotalTimeActive() {
        return totalTimeActive;
    }

    boolean isActive() {
        return isActive;
    }

    UUID getCategoryId() {
        return categoryId;
    }

    void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    void incrementActiveTime(final long time) {
        totalTimeActive += time;
    }

    @Override
    ViewType viewType() {
        return ViewType.TASK;
    }

    @Override
    void bind(UiListItem.ViewHolder holder, UiListItem.Listener listener) {
        bind((ViewHolder) holder, (Listener) listener);
    }

    private void bind(final ViewHolder holder, final Listener listener) {
        holder.task(this, listener);
    }

    static class ViewHolder extends UiListItem.ViewHolder {
        private final TaskLayout layout;

        private ViewHolder(TaskLayout itemView) {
            super(itemView);
            layout = itemView;
        }

        static ViewHolder newInstance(final ViewGroup group) {
            return new ViewHolder((TaskLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_task, group, false));
        }

        @Override
        void task(final UiTask task, final Listener listener) {
            layout.task(task);
            layout.setOnClickListener(view -> listener.onClickTask(task));
            layout.setOnCheckedChangeListener((buttonView, isChecked) -> {
                listener.onToggleActiveStatus(task, isChecked);
            });
        }
    }

    interface Listener extends UiListItem.Listener {
        void onClickTask(UiTask task);
        void onToggleActiveStatus(UiTask task, boolean isActive);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.title);
        dest.writeString(this.note);
        dest.writeLong(this.totalTimeActive);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.categoryId);
    }

    private UiTask(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.totalTimeActive = in.readLong();
        this.isActive = in.readByte() != 0;
        this.categoryId = (UUID) in.readSerializable();
    }

    public static final Creator<UiTask> CREATOR = new Creator<UiTask>() {
        @Override
        public UiTask createFromParcel(Parcel source) {
            return new UiTask(source);
        }

        @Override
        public UiTask[] newArray(int size) {
            return new UiTask[size];
        }
    };
}
