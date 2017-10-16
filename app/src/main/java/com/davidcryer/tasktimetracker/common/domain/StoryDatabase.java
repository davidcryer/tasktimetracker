package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface StoryDatabase {
    void save(Story story);
    void save(List<Story> stories);
    List<Story> findAll();
    Story find(UUID id);
    void delete(UUID storyId);
}
