package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PrePopulatedCategorySharedPreferences extends CategorySharedPreferences {

    public PrePopulatedCategorySharedPreferences(Context context, Gson gson) {
        super(context, gson);
        deleteAll();
        saveCategories();
    }

    private void deleteAll() {
        final List<Category> categories = findAll();
        for (final Category category : categories) {
            delete(category.id());
        }
    }

    private void saveCategories() {
        save(Arrays.asList(
                new Category(UUID.randomUUID(), "Category_1", "This the the first category", null),
                new Category(UUID.randomUUID(), "Category_2", "This the the second category", Arrays.asList(
                        new Task("Task_1", "This the the first task"),
                        new Task("Task_2", "This the the second task"),
                        new Task("Task_3", "This the the third task")
                )),
                new Category(UUID.randomUUID(), "Category_3", "This the the third category", Arrays.asList(
                        new Task("Task_1", "This the the first task"),
                        new Task("Task_2", "This the the second task")
                )),
                new Category(UUID.randomUUID(), "Category_4", "This the the fourth category", null)
        ));
    }
}
