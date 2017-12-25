package com.davidcryer.tasktimetracker.common.domain;

public interface CategoryStore {
    void save(Category category);
    void delete(Category category);
}
