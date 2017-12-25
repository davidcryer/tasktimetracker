package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

public class PrePopulatedCategorySharedPreferences extends CategorySharedPreferences {

    public PrePopulatedCategorySharedPreferences(Context context, Gson gson, CategoryFactory categoryFactory) {
        super(context, gson, categoryFactory);
        deleteAll();
        saveCategories();
    }

    private void deleteAll() {
        final List<Category> categories = getAll(null);
        for (final Category category : categories) {
            category.delete();
        }
    }

    private void saveCategories() {
        create("Category_1", "This the the first category");
        final Category cat2 = create("Category_2", "This the the second category");
        cat2.newTask("Task_1", "This the the first task");
        cat2.newTask("Task_2", "This the the second task");
        cat2.newTask("Task_3", "This the the third task");
        final Category cat3 = create("Category_3", "This the the third category");
        cat3.newTask("Task_1", "This the the first task");
        cat3.newTask("Task_2", "This the the second task");
        create("Category_4", "This the the fourth category");
    }
}
