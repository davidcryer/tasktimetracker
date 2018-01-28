package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface DomainManager {
    Category get(UUID id, Task.OngoingStatusListener ongoingStatusListener);
    List<Category> getAll(Task.OngoingStatusListener ongoingStatusListener);
    Category create(String title, String note) throws CategoryArgRules.Exception;
}
