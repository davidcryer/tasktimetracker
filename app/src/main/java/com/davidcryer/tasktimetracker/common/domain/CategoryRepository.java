package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {
    Category get(UUID id);
    List<Category> getAll();
    Category create(String title, String note) throws CategoryArgResults.Exception;
}
