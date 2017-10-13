package com.davidcryer.tasktimetracker.managetasks;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.Arrays;
import java.util.List;

class ManageTasksUiModelImpl implements ManageTasksUiModel {
    private List<UiStory> stories;

    ManageTasksUiModelImpl(final List<UiStory> stories) {
        this.stories = stories;
    }

    private ManageTasksUiModelImpl(final Parcel parcel) {
        stories = Arrays.asList((UiStory[]) parcel.readParcelableArray(UiStory.class.getClassLoader()));
    }

    @Override
    public void onto(@NonNull ManageTasksUi ui) {
        ui.showStories(stories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        stories = Arrays.asList((UiStory[]) parcel.readParcelableArray(UiStory.class.getClassLoader()));
    }

    public final static Creator<ManageTasksUiModel> CREATOR = new Creator<ManageTasksUiModel>() {
        @Override
        public ManageTasksUiModel createFromParcel(Parcel parcel) {
            return new ManageTasksUiModelImpl(parcel);
        }

        @Override
        public ManageTasksUiModel[] newArray(int i) {
            return new ManageTasksUiModelImpl[i];
        }
    };

    @Override
    public void showStories(List<UiStory> stories, ManageTasksUi ui) {
        if (ui != null) {
            ui.showStories(ListUtils.emptyIfNull(stories));
        }
        this.stories = stories;
    }

    @Override
    public boolean isPopulated() {
        return stories != null;
    }
}
