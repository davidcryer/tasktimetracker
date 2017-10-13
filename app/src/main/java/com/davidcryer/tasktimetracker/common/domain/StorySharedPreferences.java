package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class StorySharedPreferences implements StoryDatabase {
    private final static String PREFS_KEY = "story";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public StorySharedPreferences(final Context context, final Gson gson) {
        sharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    @Override
    public void save(final Story story) {
        sharedPreferences.edit().putString(story.id().toString(), gson.toJson(story)).apply();
    }

    @Override
    public void save(final List<Story> stories) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        for (final Story story : stories) {
            editor.putString(story.id().toString(), gson.toJson(story));
        }
        editor.apply();
    }

    @Override
    public List<Story> findAll() {
        final List<Story> stories = new LinkedList<>();
        for (final Object storyJson : sharedPreferences.getAll().values()) {
            stories.add(gson.fromJson(storyJson.toString(), Story.class));
        }
        return stories;
    }

    @Override
    public void delete(final UUID storyId) {
        sharedPreferences.edit().remove(storyId.toString()).apply();
    }
}
