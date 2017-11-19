package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCategoriesUiModelTests {

    @Test
    public void notPopulated() {
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(null);
        Assert.assertFalse(uiModel.isPopulated());
    }

    @Test
    public void isPopulated() {
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(new ArrayList<UiCategory>());
        Assert.assertTrue(uiModel.isPopulated());
    }

    @Test
    public void showCategories() {
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(null);
        final TestUi ui = new TestUi();
        uiModel.showCategories(new ArrayList<UiCategory>(), ui);
        Assert.assertTrue(uiModel.isPopulated());
        Assert.assertTrue(ui.categories != null && ui.categories.isEmpty());
    }

    @Test
    public void showCategories_nullList() {
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(null);
        final TestUi ui = new TestUi();
        uiModel.showCategories(null, ui);
        Assert.assertFalse(uiModel.isPopulated());
        Assert.assertTrue(ui.categories != null && ui.categories.isEmpty());
    }

    @Test
    public void addCategory() {
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(null);
        final TestUi ui = new TestUi(new ArrayList<UiCategory>());
        uiModel.addCategory(uiCategory(), ui);
        Assert.assertTrue(uiModel.isPopulated());
        Assert.assertTrue(ui.categories != null && !ui.categories.isEmpty());
    }

    @Test
    public void insertCategory() {
        final List<UiCategory> initialCategories = Collections.singletonList(uiCategory());
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(initialCategories);
        final TestUi ui = new TestUi(initialCategories);
        uiModel.insertCategory(uiCategory(), 0, ui);
        Assert.assertTrue(ui.categories != null && !ui.categories.isEmpty());
    }

    @Test
    public void removeCategory() {
        final List<UiCategory> initialCategories = Collections.singletonList(uiCategory());
        final ManageCategoriesUiModel uiModel = new ManageCategoriesUiModelImpl(initialCategories);
        final TestUi ui = new TestUi(initialCategories);
        uiModel.removeCategory(initialCategories.get(0).getId(), ui);
        Assert.assertTrue(ui.categories != null && ui.categories.isEmpty());
    }

    private static UiCategory uiCategory() {
        return new UiCategory(null, null, null, false, null);
    }

    private static class TestUi implements ManageCategoriesUi {
        List<UiCategory> categories;

        private TestUi() {
        }

        private TestUi(List<UiCategory> categories) {
            this.categories = new ArrayList<>(categories);
        }

        @Override
        public void showCategories(List<UiCategory> categories) {
            this.categories = categories;
        }

        @Override
        public void add(UiCategory item) {
            this.categories.add(item);
        }

        @Override
        public void insert(UiCategory item, int i) {
            this.categories.set(i, item);
        }

        @Override
        public void remove(int pos) {
            this.categories.remove(pos);
        }

        @Override
        public void remove(int categoryInd, int taskInd) {

        }

        @Override
        public void set(UiCategory item, int i) {

        }

        @Override
        public void expandCategory(int i, int pos) {

        }

        @Override
        public void shrinkCategory(int i, int pos) {

        }

        @Override
        public void showManageTaskScreen(ManageTaskIntentModel intentModel) {

        }

        @Override
        public void showAddCategoryPrompt() {

        }

        @Override
        public void showRemoveCategoryPrompt(UiCategory category) {

        }

        @Override
        public void showRemoveTaskPrompt(UiTask task, UiCategory category) {

        }
    }
}
