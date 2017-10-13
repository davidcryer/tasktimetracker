package com.davidcryer.tasktimetracker.managetasks;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ManageTasksUiModelTests {

    @Test
    public void notPopulated() {
        final ManageTasksUiModel uiModel = new ManageTasksUiModelImpl(null);
        Assert.assertFalse(uiModel.isPopulated());
    }

    @Test
    public void isPopulated() {
        final ManageTasksUiModel uiModel = new ManageTasksUiModelImpl(new ArrayList<UiStory>());
        Assert.assertTrue(uiModel.isPopulated());
    }

    @Test
    public void showStories() {
        final ManageTasksUiModel uiModel = new ManageTasksUiModelImpl(null);
        uiModel.showStories(new ArrayList<UiStory>(), new TestUi());
        Assert.assertTrue(uiModel.isPopulated());
    }

    private static class TestUi implements ManageTasksUi {

        @Override
        public void showStories(List<UiStory> stories) {

        }
    }
}
