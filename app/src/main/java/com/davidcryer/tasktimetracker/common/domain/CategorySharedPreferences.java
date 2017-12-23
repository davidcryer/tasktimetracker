package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CategorySharedPreferences implements CategoryDatabase {
    private final static String PREFS_KEY = "category";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private final TaskFactory taskFactory;

    public CategorySharedPreferences(final Context context, final Gson gson, final TaskFactory taskFactory) {
        sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        this.gson = gson;
        this.taskFactory = taskFactory;
    }

    @Override
    public void save(final Category category) {
        sharedPreferences.edit().putString(category.id().toString(), gson.toJson(dbCategory(category))).apply();
    }

    @Override
    public void save(final List<Category> categories) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        for (final Category category : categories) {
            editor.putString(category.id().toString(), gson.toJson(dbCategory(category)));
        }
        editor.apply();
    }

    private DbCategory dbCategory(final Category category) {
        return category.toDbCategory();
    }

    @Override
    public Category find(UUID id, Task.OngoingStatusListener ongoingStatusListener) {//TODO optimise (probably with cache)
        final List<Category> categories = findAll(ongoingStatusListener);
        for (final Category category : categories) {
            if (category.id().equals(id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll(Task.OngoingStatusListener ongoingStatusListener) {
        final List<Category> categories = new LinkedList<>();
        for (final Object json : sharedPreferences.getAll().values()) {
            categories.add(fromJson(json, ongoingStatusListener));
        }
        return categories;
    }

    private Category fromJson(final Object json, final Task.OngoingStatusListener ongoingStatusListener) {
        return fromJson(json).toCategory(taskFactory, ongoingStatusListener);
    }

    private DbCategory fromJson(final Object json) {
        return gson.fromJson(json.toString(), DbCategory.class);
    }

    @Override
    public void delete(final UUID categoryId) {
        sharedPreferences.edit().remove(categoryId.toString()).apply();
    }
}
