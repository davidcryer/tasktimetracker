package com.davidcryer.tasktimetracker.managestories;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ManageStoriesUiModelTests {

    @Test
    public void notPopulated() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        Assert.assertFalse(uiModel.isPopulated());
    }

    @Test
    public void isPopulated() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(new ArrayList<UiStory>());
        Assert.assertTrue(uiModel.isPopulated());
    }

    @Test
    public void showStories() {
        final ManageStoriesUiModel uiModel = new ManageStoriesUiModelImpl(null);
        uiModel.showStories(new ArrayList<UiStory>(), new TestUi());
        Assert.assertTrue(uiModel.isPopulated());
    }

    private static class TestUi implements ManageStoriesUi {

        @Override
        public void showStories(List<UiStory> stories) {

        }
    }
}
