package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.davidcryer.tasktimetracker.common.argvalidation.IllegalCategoryArgsException;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class CategorySharedPreferences implements DomainManager, CategoryStore {
    private final static String PREFS_KEY = "category";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private final CategoryFactory categoryFactory;

    CategorySharedPreferences(final Context context, final Gson gson, final CategoryFactory categoryFactory) {
        sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        this.gson = gson;
        this.categoryFactory = categoryFactory;
    }

    @Override
    public Category get(UUID id, Task.OngoingStatusListener ongoingStatusListener) {//TODO optimise (probably with cache)
        final List<Category> categories = getAll(ongoingStatusListener);
        for (final Category category : categories) {
            if (category.id().equals(id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> getAll(Task.OngoingStatusListener ongoingStatusListener) {
        final List<Category> categories = new LinkedList<>();
        for (final Object json : sharedPreferences.getAll().values()) {
            categories.add(fromJson(json, ongoingStatusListener));
        }
        return categories;
    }

    private Category fromJson(final Object json, final Task.OngoingStatusListener ongoingStatusListener) {
        return fromJson(json).toCategory(this, categoryFactory, ongoingStatusListener);
    }

    private DbCategory fromJson(final Object json) {
        return gson.fromJson(json.toString(), DbCategory.class);
    }

    @Override
    public Category create(String title, String note) throws IllegalCategoryArgsException {
        return categoryFactory.create(this, title, note);
    }

    @Override
    public void save(final Category category) {
        sharedPreferences.edit().putString(id(category), gson.toJson(category.toDbCategory())).apply();
    }

    @Override
    public void delete(final Category category) {
        sharedPreferences.edit().remove(id(category)).apply();
    }

    private String id(final Category category) {
        return category.id().toString();
    }
}
