package com.davidcryer.tasktimetracker.managestory;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

class ManageStoryUiModelImpl implements ManageStoryUiModel {
    enum State {MANAGE, EDIT}
    private State state;
    private UiStory story;
    private List<UiTask> tasks;
    private SaveError saveError;

    ManageStoryUiModelImpl(State state, UiStory story, List<UiTask> tasks) {
        this.state = state;
        this.story = story;
        this.tasks = tasks;
    }

    @Override
    public void onto(@NonNull ManageStoryUi ui) {
        switch (state) {
            case MANAGE: {
                ui.showManageLayout(story);
            } break;
            case EDIT: {
                ui.showEditLayout(story);
            } break;
        }
        if (saveError != null) {
            ui.showSaveError(saveError.title, saveError.message);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state == null ? -1 : state.ordinal());
        dest.writeParcelable(story, flags);
    }

    private ManageStoryUiModelImpl(Parcel in) {
        int tmpState = in.readInt();
        state = tmpState == -1 ? null : State.values()[tmpState];
        story = in.readParcelable(UiStory.class.getClassLoader());
    }

    public static final Creator<ManageStoryUiModelImpl> CREATOR = new Creator<ManageStoryUiModelImpl>() {
        @Override
        public ManageStoryUiModelImpl createFromParcel(Parcel source) {
            return new ManageStoryUiModelImpl(source);
        }

        @Override
        public ManageStoryUiModelImpl[] newArray(int size) {
            return new ManageStoryUiModelImpl[size];
        }
    };

    @Override
    public void showManageLayout(ManageStoryUi ui) {
        if (ui != null) {
            ui.showEditLayout(story);
        }
        state = State.MANAGE;
    }

    @Override
    public void showManageLayout(ManageStoryUi ui, UiStory story) {
        if (ui != null) {
            ui.showEditLayout(story);
        }
        state = State.MANAGE;
        this.story = story;
    }

    @Override
    public void showEditLayout(ManageStoryUi ui) {
        if (ui != null) {
            ui.showEditLayout(story);
        }
        state = State.EDIT;
    }

    @Override
    public void showManageTask(ManageStoryUi ui, UiTask task, int i) {
        task.setInEditMode(false);
        if (ui != null) {
            ui.showManageTask(task, i);
        }
    }

    @Override
    public void showEditTask(ManageStoryUi ui, UiTask task, int i) {
        task.setInEditMode(true);
        if (ui != null) {
            ui.showEditTask(task, i);
        }
    }

    @Override
    public void showSaveError(ManageStoryUi ui, String title, String message) {
        if (ui != null) {
            ui.showSaveError(title, message);
        }
        saveError = new SaveError(title, message);
    }

    @Override
    public void onDismissSaveError() {
        saveError = null;
    }

    private static class SaveError implements Parcelable {
        private final String title;
        private final String message;

        private SaveError(String title, String message) {
            this.title = title;
            this.message = message;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(message);
        }

        private SaveError(Parcel in) {
            title = in.readString();
            message = in.readString();
        }

        public static final Creator<SaveError> CREATOR = new Creator<SaveError>() {
            @Override
            public SaveError createFromParcel(Parcel source) {
                return new SaveError(source);
            }

            @Override
            public SaveError[] newArray(int size) {
                return new SaveError[size];
            }
        };
    }
}
