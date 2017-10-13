package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.Arrays;
import java.util.List;

class ManageStoriesUiModelImpl implements ManageStoriesUiModel {
    private List<UiStory> stories;

    ManageStoriesUiModelImpl(final List<UiStory> stories) {
        this.stories = stories;
    }

    private ManageStoriesUiModelImpl(final Parcel parcel) {
        stories = Arrays.asList((UiStory[]) parcel.readParcelableArray(UiStory.class.getClassLoader()));
    }

    @Override
    public void onto(@NonNull ManageStoriesUi ui) {
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

    public final static Creator<ManageStoriesUiModel> CREATOR = new Creator<ManageStoriesUiModel>() {
        @Override
        public ManageStoriesUiModel createFromParcel(Parcel parcel) {
            return new ManageStoriesUiModelImpl(parcel);
        }

        @Override
        public ManageStoriesUiModel[] newArray(int i) {
            return new ManageStoriesUiModelImpl[i];
        }
    };

    @Override
    public void showStories(List<UiStory> stories, ManageStoriesUi ui) {
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
