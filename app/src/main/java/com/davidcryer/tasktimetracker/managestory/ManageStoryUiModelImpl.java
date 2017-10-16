package com.davidcryer.tasktimetracker.managestory;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.davidcryer.tasktimetracker.common.ArgsInspector;

import java.util.List;
import java.util.UUID;

class ManageStoryUiModelImpl implements ManageStoryUiModel {
    enum State {MANAGE, EDIT}
    private State state;
    private UiStory story;
    private List<UiTask> tasks;
    private SaveError saveError;

    ManageStoryUiModelImpl(@NonNull State state, @NonNull UiStory story, List<UiTask> tasks) {
        ArgsInspector.inspect(stateCheck(state), storyCheck(story));
        this.state = state;
        this.story = story;
        this.tasks = tasks;
    }

    private static ArgsInspector.ArgCheck stateCheck(final State state) {
        return ArgsInspector.nonNullCheck(state, "state");
    }

    private static ArgsInspector.ArgCheck storyCheck(final UiStory story) {
        return ArgsInspector.nonNullCheck(story, "story");
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
    public boolean isPopulatedWithTasks() {
        return tasks != null;
    }

    @Override
    public void showTasks(ManageStoryUi ui, List<UiTask> tasks) {
        if (ui != null) {
            ui.showTasks(tasks);
        }
        this.tasks = tasks;
    }

    @Override
    public void showManageLayout(ManageStoryUi ui) {
        if (ui != null) {
            ui.showEditLayout(story);
        }
        state = State.MANAGE;
    }

    @Override
    public void showManageLayout(ManageStoryUi ui, UiStory story) {
        ArgsInspector.inspect(storyCheck(story));
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
    public void showManageTask(ManageStoryUi ui, int i) {
        final UiTask task = tasks.get(i);
        task.setInEditMode(false);
        if (ui != null) {
            ui.showManageTask(task, i);
        }
    }

    @Override
    public void showManageTask(ManageStoryUi ui, UiTask task, int i) {
        tasks.set(i, task);
        task.setInEditMode(false);
        if (ui != null) {
            ui.showManageTask(task, i);
        }
    }

    @Override
    public void showEditTask(ManageStoryUi ui, int i) {
        final UiTask task = tasks.get(i);
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

    @Override
    public UUID storyId() {
        return story.getId();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state.ordinal());
        dest.writeParcelable(this.story, flags);
        dest.writeTypedList(this.tasks);
        dest.writeParcelable(this.saveError, flags);
    }

    private ManageStoryUiModelImpl(Parcel in) {
        this.state = State.values()[in.readInt()];
        this.story = in.readParcelable(UiStory.class.getClassLoader());
        this.tasks = in.createTypedArrayList(UiTask.CREATOR);
        this.saveError = in.readParcelable(SaveError.class.getClassLoader());
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
}
