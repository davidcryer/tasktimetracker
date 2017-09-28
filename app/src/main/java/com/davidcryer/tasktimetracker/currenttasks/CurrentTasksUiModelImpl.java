package com.davidcryer.tasktimetracker.currenttasks;

import android.os.Parcel;
import android.support.annotation.NonNull;

class CurrentTasksUiModelImpl implements CurrentTasksUiModel {

    @Override
    public void onto(@NonNull CurrentTasksUi ui) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public final static Creator<CurrentTasksUiModel> CREATOR = new Creator<CurrentTasksUiModel>() {
        @Override
        public CurrentTasksUiModel createFromParcel(Parcel parcel) {
            return new CurrentTasksUiModelImpl();
        }

        @Override
        public CurrentTasksUiModel[] newArray(int i) {
            return new CurrentTasksUiModelImpl[i];
        }
    };
}
