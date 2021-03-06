package com.davidcryer.tasktimetracker.common.domain;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GsonTests {

    @Test
    public void category_toJson_withoutTasks() {
        final Gson gson = new Gson();
        final Category category = new Category("Title", "Note");
        Assert.assertEquals(gson.toJson(category), (String.format("{\"title\":\"%1$s\",\"note\":\"%2$s\"}", category.title(), category.note())));
    }

    @Test
    public void category_fromJson_withoutTasks() {
        final Gson gson = new Gson();
        final String title = "Title";
        final String note = "Note";
        final Category category = gson.fromJson(String.format("{\"title\":\"%1$s\",\"note\":\"%2$s\"}", title, note), Category.class);
        Assert.assertEquals(category.title(), title);
        Assert.assertEquals(category.note(), note);
    }

    @Test
    public void task_toJson_withoutSessionHistoryOrOngoingSession() {
        final Gson gson = new Gson();
        final Task task = new Task("Title", "Note");
        Assert.assertEquals(gson.toJson(task), (String.format("{\"id\":\"%1$s\",\"title\":\"%2$s\",\"note\":\"%3$s\"}", task.id(), task.title(), task.note())));
    }

    @Test
    public void task_fromJson_withoutSessionHistoryOrOngoingSession() {
        final Gson gson = new Gson();
        final UUID id = UUID.randomUUID();
        final String title = "Title";
        final String note = "Note";
        final Task task = gson.fromJson(String.format("{\"id\":\"%1$s\",\"title\":\"%2$s\",\"note\":\"%3$s\"}", id, title, note), Task.class);
        Assert.assertEquals(task.id(), id);
        Assert.assertEquals(task.title(), title);
        Assert.assertEquals(task.note(), note);
    }

    @Test
    public void category_toJson_withTasks() {
        final Gson gson = new Gson();
        final UUID id = UUID.randomUUID();
        final Category category = new Category(id, "Title", "Note", Arrays.asList(new Task("Task 1", "Hello"), new Task("Task 2", "World!")));
        final List<Task> tasks = category.tasks();
        final String jsonCheck = (String.format("{\"id\":\"%1$s\",\"title\":\"%2$s\",\"note\":\"%3$s\",\"tasks\":[%4$s,%5$s]}", id, category.title(), category.note(), gson.toJson(tasks.get(0)), gson.toJson(tasks.get(1))));
        Assert.assertEquals(gson.toJson(category), jsonCheck);
    }

    @Test
    public void category_fromJson_withTasks() {
        final Gson gson = new Gson();
        final String title = "Title";
        final String note = "Note";
        final String tasks = "{\"id\":\"26c6674f-3d20-4fb2-9a31-a9dc0a5ce2fc\",\"title\":\"Task 1\",\"note\":\"Hello\"},{\"id\":\"0ec5f758-5626-4589-aa2a-5b63504d00ff\",\"title\":\"Task 2\",\"note\":\"World!\"}";
        final Category category = gson.fromJson(String.format("{\"title\":\"%1$s\",\"note\":\"%2$s\",\"tasks\":[%3$s]}", title, note, tasks), Category.class);
        Assert.assertEquals(category.title(), title);
        Assert.assertEquals(category.note(), note);
        Assert.assertEquals(category.tasks().size(), 2);
    }
}
