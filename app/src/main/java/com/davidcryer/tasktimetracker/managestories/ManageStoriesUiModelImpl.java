package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.LinkedList;
import java.util.List;

class ManageStoriesUiModelImpl implements ManageStoriesUiModel {
    private List<UiStory> stories;

    ManageStoriesUiModelImpl(final List<UiStory> stories) {
        this.stories = stories == null ? null : new LinkedList<>(stories);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(stories);
    }

    private ManageStoriesUiModelImpl(Parcel in) {
        stories = in.createTypedArrayList(UiStory.CREATOR);
    }

    public static final Creator<ManageStoriesUiModelImpl> CREATOR = new Creator<ManageStoriesUiModelImpl>() {
        @Override
        public ManageStoriesUiModelImpl createFromParcel(Parcel source) {
            return new ManageStoriesUiModelImpl(source);
        }

        @Override
        public ManageStoriesUiModelImpl[] newArray(int size) {
            return new ManageStoriesUiModelImpl[size];
        }
    };

    @Override
    public void showStories(List<UiStory> stories, ManageStoriesUi ui) {
        if (ui != null) {
            ui.showStories(ListUtils.newList(stories));
        }
        this.stories = stories;
    }

    @Override
    public void removeStory(int i, ManageStoriesUi ui) {
        if (ui != null) {
            ui.removeStory(i);
        }
        stories.remove(i);
    }

    @Override
    public void addStory(UiStory story, ManageStoriesUi ui) {
        if (ui != null) {
            ui.addStory(story);
        }
        if (stories == null) {
            stories = new LinkedList<>();
        }
        stories.add(story);
    }

    @Override
    public void insertStory(UiStory story, int i, ManageStoriesUi ui) {
        if (ui != null) {
            ui.insertStory(story, i);
        }
        stories.set(i, story);
    }

    @Override
    public boolean isPopulated() {
        return stories != null;
    }
}
