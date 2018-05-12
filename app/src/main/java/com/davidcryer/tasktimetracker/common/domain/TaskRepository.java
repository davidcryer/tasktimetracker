package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {
    ObservedTask get(UUID id);
    ObservedTask get(UUID id, UUID categoryId);
    List<ObservedTask> getAll(UUID categoryId);
    ObservedTask create(String title, String note, UUID categoryId);
}
