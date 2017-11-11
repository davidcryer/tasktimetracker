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

    CategorySharedPreferences(final Context context, final Gson gson) {
        sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    @Override
    public void save(final Category category) {
        sharedPreferences.edit().putString(category.id().toString(), gson.toJson(category)).apply();
    }

    @Override
    public void save(final List<Category> categories) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        for (final Category category : categories) {
            editor.putString(category.id().toString(), gson.toJson(category));
        }
        editor.apply();
    }

    @Override
    public Category find(UUID id) {//TODO optimise (probably with cache)
        final List<Category> categories = findAll();
        for (final Category category : categories) {
            if (category.id().equals(id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        final List<Category> categories = new LinkedList<>();
        for (final Object categoryJson : sharedPreferences.getAll().values()) {
            categories.add(gson.fromJson(categoryJson.toString(), Category.class));
        }
        return categories;
    }

    @Override
    public void delete(final UUID categoryId) {
        sharedPreferences.edit().remove(categoryId.toString()).apply();
    }
}
