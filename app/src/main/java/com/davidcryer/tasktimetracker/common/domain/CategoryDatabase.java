package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface CategoryDatabase {
    void save(Category category);
    void save(List<Category> categories);
    List<Category> findAll(Task.OngoingStatusListener ongoingStatusListener);
    Category find(UUID id, Task.OngoingStatusListener ongoingStatusListener);
    void delete(UUID categoryId);
}
