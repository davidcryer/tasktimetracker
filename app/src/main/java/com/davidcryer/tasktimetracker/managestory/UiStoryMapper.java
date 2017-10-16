package com.davidcryer.tasktimetracker.managestory;

class UiStoryMapper {

    private UiStoryMapper() {}

    static UiStory from(final ManageStoryIntentModel intentModel) {
        return new UiStory(intentModel.getId(), intentModel.getTitle(), intentModel.getNote());
    }
}
