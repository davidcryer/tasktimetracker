package com.davidcryer.tasktimetracker.managetasks;

import android.os.Parcel;
import android.support.annotation.NonNull;

class ManageTasksUiModelImpl implements ManageTasksUiModel {

    @Override
    public void onto(@NonNull ManageTasksUi ui) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public final static Creator<ManageTasksUiModel> CREATOR = new Creator<ManageTasksUiModel>() {
        @Override
        public ManageTasksUiModel createFromParcel(Parcel parcel) {
            return new ManageTasksUiModelImpl();
        }

        @Override
        public ManageTasksUiModel[] newArray(int i) {
            return new ManageTasksUiModelImpl[i];
        }
    };
}
