package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.TimeInterval;
import com.davidcryer.tasktimetracker.common.Timer;

import java.util.UUID;

class UiTask extends UiListItem {
    private final UUID id;
    private final String title;
    private final String note;
    private long totalTimeActive;
    private View view;

    UiTask(UUID id, String title, String note, long totalTimeActive, boolean isActive) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.totalTimeActive = totalTimeActive;
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

    void setActive(final boolean isActive) {
        if (this.isActive == isActive) {
            return;
        }
        if (isActive) {
            timeOfLastTick = System.currentTimeMillis();
            if (view != null && view.isAttachedToWindow()) {
                scheduleTimer();
            }
        } else {
            incrementActiveTime();
            cancelTimer();
        }
        if (view != null) {
            view.totalTimeActive(totalTimeActive, isActive);
        }
        this.isActive = isActive;
    }

    public void register(View view) {
        this.view = view;
        view.title(title);
        view.totalTimeActive(totalTimeActive, isActive);
        view.activationState(isActive);
        if (view.isAttachedToWindow()) {
            if (isActive()) {
                incrementActiveTime();
                scheduleTimer();
            }
        }
    }

    public void attachedToWindow(View view) {
        this.view = view;
        if (isActive) {
            incrementActiveTime();
            scheduleTimer();
        }
    }

    private void scheduleTimer() {
        timer.schedule(this::onTick, TimeInterval.MILLIS_IN_SECOND, TimeInterval.MILLIS_IN_SECOND);
    }

    private void onTick() {
        incrementActiveTime();
        if (view != null) {
            view.totalTimeActive(getTotalTimeActive(), isActive());
        }
    }

    private void incrementActiveTime() {
        final long currentTimeMillis = System.currentTimeMillis();
        totalTimeActive += currentTimeMillis - timeOfLastTick;
        timeOfLastTick = currentTimeMillis;
    }

    public void detachedFromWindow(View view) {
        if (this.view == view) {
            cancelTimer();
            this.view = null;
        }
    }
    private void cancelTimer() {
        if (timer.isRunning()) {
            timer.cancel();
        }
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
            layout.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onToggleActiveStatus(task, isChecked));
        }
    }

    interface Listener extends UiListItem.Listener {
        void onClickTask(UiTask task);
        void onToggleActiveStatus(UiTask task, boolean isActive);
    }

    @Override
    UiListItemFactory factory() {
        return UiListItemFactory.TASK;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeSerializable(this.id);
        dest.writeString(this.title);
        dest.writeString(this.note);
        dest.writeLong(this.totalTimeActive);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.categoryId);
        dest.writeLong(this.timeOfLastTick);
    }

    private UiTask(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.totalTimeActive = in.readLong();
        this.isActive = in.readByte() != 0;
        this.categoryId = (UUID) in.readSerializable();
        this.timeOfLastTick = in.readLong();
    }

    public static final Creator<UiListItem> CREATOR = new Creator<UiListItem>() {
        @Override
        public UiListItem createFromParcel(Parcel source) {
            return new UiTask(source);
        }

        @Override
        public UiTask[] newArray(int size) {
            return new UiTask[size];
        }
    };

    interface View {
        void title(String title);
        void totalTimeActive(long time, boolean isActive);
        void activationState(boolean isActive);
        boolean isAttachedToWindow();
    }
}
