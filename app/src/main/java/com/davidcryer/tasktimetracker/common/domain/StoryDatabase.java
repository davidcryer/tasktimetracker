package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface StoryDatabase {
    void save(final Story story);
    void save(final List<Story> stories);
    List<Story> findAll();
    void delete(final UUID storyId);
}
