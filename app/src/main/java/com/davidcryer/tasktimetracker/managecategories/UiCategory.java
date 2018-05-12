package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

class UiCategory extends UiListItem {
    private final UUID id;
    private final String title;
    private final String note;
    private boolean expanded;
    private Listener listener;

    public UiCategory(UUID id, String title, String note, boolean expanded) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.expanded = expanded;
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

    void expand() {
        expanded = true;

    }

    void condense() {
        expanded = false;
    }

    boolean isExpanded() {
        return expanded;
    }

    void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    ViewType viewType() {
        return ViewType.CATEGORY;
    }

    @Override
    void bind(UiListItem.ViewHolder holder) {
        bind((ViewHolder) holder);
    }

    private void bind(ViewHolder holder) {
        holder.category(this);
    }

    static class ViewHolder extends UiListItem.ViewHolder {
        private final CategoryLayout layout;

        private ViewHolder(final CategoryLayout layout) {
            super(layout);
            this.layout = layout;
        }

        static ViewHolder newInstance(final ViewGroup group) {
            return new ViewHolder((CategoryLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_category, group, false));
        }

        @Override
        void category(final UiCategory category) {
            layout.category(category);
            layout.setOnClickListener(view -> category.listener.onClickCategory(category, ViewHolder.this.getAdapterPosition()));
            layout.setOnClickAddTaskListener(v -> category.listener.onClickAddTask(category.id));
            setUpDivider();
        }

        private void setUpDivider() {
            if (getAdapterPosition() == 0) {
                layout.hideDivider();
            } else {
                layout.showDivider();
            }
        }
    }

    interface Listener {
        void onClickCategory(UiCategory category, int i);
        void onClickAddTask(UUID categoryId);
        void onClickExpand(UUID categoryId);
    }

    @Override
    UiListItemFactory factory() {
        return UiListItemFactory.CATEGORY;
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
        dest.writeByte(this.expanded ? (byte) 1 : (byte) 0);
    }

    private UiCategory(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.expanded = in.readByte() != 0;
    }

    public static final Creator<UiListItem> CREATOR = new Creator<UiListItem>() {
        @Override
        public UiListItem createFromParcel(Parcel source) {
            return new UiCategory(source);
        }

        @Override
        public UiListItem[] newArray(int size) {
            return new UiCategory[size];
        }
    };
}
