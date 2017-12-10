package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PrePopulatedCategorySharedPreferences extends CategorySharedPreferences {

    public PrePopulatedCategorySharedPreferences(Context context, Gson gson, TaskFactory taskFactory) {
        super(context, gson, taskFactory);
        deleteAll();
        saveCategories();
    }

    private void deleteAll() {
        final List<Category> categories = findAll(null);
        for (final Category category : categories) {
            delete(category.id());
        }
    }

    private void saveCategories() {
        save(Arrays.asList(
                category("Category_1", "This the the first category", null),
                category("Category_2", "This the the second category", Arrays.asList(
                        task("Task_1", "This the the first task"),
                        task("Task_2", "This the the second task"),
                        task("Task_3", "This the the third task")
                )),
                category("Category_3", "This the the third category", Arrays.asList(
                        task("Task_1", "This the the first task"),
                        task("Task_2", "This the the second task")
                )),
                category("Category_4", "This the the fourth category", null)
        ));
    }

    private Category category(final String title, final String note, final List<Task> tasks) {
        return new Category(UUID.randomUUID(), title, note, tasks);
    }

    private Task task(final String title, final String note) {
        return new Task(title, note, null);
    }
}
