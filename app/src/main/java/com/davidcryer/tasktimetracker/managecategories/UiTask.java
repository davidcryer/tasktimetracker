package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;

import java.util.UUID;

class UiTask extends UiListItem implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private final UUID categoryId;

    public UiTask(UUID id, String title, String note, UUID categoryId) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public UUID getCategoryId() {
        return categoryId;
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
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickTask(task);
                }
            });
        }
    }

    interface Listener extends UiListItem.Listener {
        void onClickTask(UiTask task);
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
        dest.writeSerializable(this.categoryId);
    }

    private UiTask(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
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
