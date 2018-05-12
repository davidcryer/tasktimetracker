package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract class UiListItem implements Parcelable {

    abstract ViewType viewType();

    abstract void bind(final ViewHolder holder);

    int index() {
        return 0;//TODO get index of list item
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        void category(final UiCategory category) {
            throw new UnsupportedOperationException(String.format("%1$s.category() method should not be used", getClass().getCanonicalName()));
        }

        void task(final UiTask task) {
            throw new UnsupportedOperationException(String.format("%1$s.task() method should not be used", getClass().getCanonicalName()));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(factory());
    }

    private static UiListItem fromParcel(Parcel in) {
        final UiListItemFactory factory = (UiListItemFactory) in.readSerializable();
        return factory.creator().createFromParcel(in);
    }

    abstract UiListItemFactory factory();

    public static final Creator<UiListItem> CREATOR = new Creator<UiListItem>() {
        @Override
        public UiListItem createFromParcel(Parcel source) {
            return fromParcel(source);
        }

        @Override
        public UiListItem[] newArray(int size) {
            return new UiListItem[size];
        }
    };
}
