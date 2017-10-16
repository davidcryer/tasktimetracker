package com.davidcryer.tasktimetracker.common.domain;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.UUID;

public class PrePopulatedStorySharedPreferences extends StorySharedPreferences {

    public PrePopulatedStorySharedPreferences(Context context, Gson gson) {
        super(context, gson);
        save(Arrays.asList(
                new Story(UUID.randomUUID(), "Story_1", "This the the first story", null),
                new Story(UUID.randomUUID(), "Story_2", "This the the second story", Arrays.asList(
                        new Task("Task_1", "This the the first task"),
                        new Task("Task_2", "This the the second task"),
                        new Task("Task_3", "This the the third task")
                )),
                new Story(UUID.randomUUID(), "Story_3", "This the the third story", Arrays.asList(
                        new Task("Task_1", "This the the first task"),
                        new Task("Task_2", "This the the second task")
                )),
                new Story(UUID.randomUUID(), "Story_4", "This the the fourth story", null)
        ));
    }
}
