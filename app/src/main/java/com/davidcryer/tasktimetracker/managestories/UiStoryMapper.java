package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;

import java.util.ArrayList;
import java.util.List;

class UiStoryMapper {

    private UiStoryMapper() {}

    static List<UiStory> from(final List<Story> stories) {
        final List<UiStory> uiStories = new ArrayList<>(stories.size());
        for (final Story story : stories) {
            uiStories.add(from(story));
        }
        return uiStories;
    }

    private static UiStory from(final Story story) {
        return new UiStory(story.id(), story.title(), story.note(), UiTaskMapper.from(story));
    }

    static ManageStoryIntentModel toManageStoryIntentModel(final UiStory story) {
        return new ManageStoryIntentModel(story.getId(), story.getTitle(), story.getNote());
    }
}
