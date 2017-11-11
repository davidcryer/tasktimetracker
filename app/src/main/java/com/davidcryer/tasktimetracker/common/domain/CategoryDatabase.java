package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

public interface CategoryDatabase {
    void save(Category category);
    void save(List<Category> categories);
    List<Category> findAll();
    Category find(UUID id);
    void delete(UUID categoryId);
}
