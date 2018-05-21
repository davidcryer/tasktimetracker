package com.davidcryer.tasktimetracker.common.domain;

interface CategoryStore {
    void save(Category category);
    void delete(Category category);
}
