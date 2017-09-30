package com.davidcryer.tasktimetracker.taskhistory;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TaskHistoryUiModelImpl implements TaskHistoryUiModel {

    @Override
    public void onto(@NonNull TaskHistoryUi ui) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public final static Creator<TaskHistoryUiModel> CREATOR = new Creator<TaskHistoryUiModel>() {
        @Override
        public TaskHistoryUiModel createFromParcel(Parcel parcel) {
            return new TaskHistoryUiModelImpl();
        }

        @Override
        public TaskHistoryUiModel[] newArray(int i) {
            return new TaskHistoryUiModelImpl[i];
        }
    };
}
