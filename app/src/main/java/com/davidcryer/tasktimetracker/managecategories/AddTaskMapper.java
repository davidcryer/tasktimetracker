package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.domain.Category;

public class AddTaskMapper {

    private AddTaskMapper() {}

    static AddTask from(final Category category) {
        return new AddTask(category.id());
    }
}
