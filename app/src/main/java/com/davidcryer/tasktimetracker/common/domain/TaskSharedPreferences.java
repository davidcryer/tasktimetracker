package com.davidcryer.tasktimetracker.common.domain;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TaskSharedPreferences implements TaskRepository {
    private final CategoryRepository categoryRepository;

    public TaskSharedPreferences(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ObservedTask get(UUID id) {
        ObservedTask result = null;
        final Iterator<Category> categoryItr = categoryRepository.getAll().iterator();
        while (result == null && categoryItr.hasNext()) {
            result = get(id, categoryItr.next().id());
        }
        return result;
    }

    @Override
    public ObservedTask get(UUID id, UUID categoryId) {
        final Category category = categoryRepository.get(categoryId);
        for (final ObservedTask task : category.tasks()) {
            if (task.id().equals(id)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public List<ObservedTask> getAll(UUID categoryId) {
        return categoryRepository.get(categoryId).tasks();
    }

    @Override
    public ObservedTask create(String title, String note, UUID categoryId) {
        final Category category = categoryRepository.get(categoryId);
        return category.newTask(title, note);
    }
}
