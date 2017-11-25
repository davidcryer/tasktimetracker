package com.davidcryer.tasktimetracker.managecategories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalCategoryArgsException;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.CategoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.UUID;

public class ManageCategoriesUiWrapper extends UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> {
    private final CategoryDatabase categoryDatabase;

    private ManageCategoriesUiWrapper(@NonNull final ManageCategoriesUiModel uiModel, final CategoryDatabase categoryDatabase) {
        super(uiModel);
        this.categoryDatabase = categoryDatabase;
    }

    public static ManageCategoriesUiWrapper newInstance(final ManageCategoriesUiModelFactory modelFactory, final CategoryDatabase categoryDatabase) {
        return new ManageCategoriesUiWrapper(modelFactory.create(), categoryDatabase);
    }

    public static ManageCategoriesUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstanceState,
            final ManageCategoriesUiModelFactory modelFactory,
            final CategoryDatabase categoryDatabase
    ) {
        final ManageCategoriesUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory, categoryDatabase) : new ManageCategoriesUiWrapper(savedModel, categoryDatabase);
    }

    @Override
    protected ManageCategoriesUi.Listener uiListener() {
        return new ManageCategoriesUi.Listener() {
            @Override
            public void onClickCategory(ManageCategoriesUi ui, UiCategory category, int pos) {
                //TODO go to category screen
            }

            @Override
            public void onClickTask(ManageCategoriesUi ui, UiTask task) {
                ui.showManageTaskScreen(UiTaskMapper.toManageTaskIntentModel(task));
            }

            @Override
            public void onClickAddCategory(ManageCategoriesUi ui) {
                ui.showAddCategoryPrompt();
            }

            @Override
            public void onClickAddTask(ManageCategoriesUi ui, UUID categoryId) {
                ui.showAddTaskPrompt(categoryId);
            }

            @Override
            public void onAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
                try {
                    final Category category = new Category(title, note);
                    categoryDatabase.save(category);
                    uiModel().addCategory(category, ui());//TODO add in alphabetical order
                    prompt.dismiss();
                } catch (IllegalCategoryArgsException iae) {
                    showErrors(prompt, iae.args());
                }
            }

            @Override
            public void onAddTask(ManageCategoriesUi.InputPrompt prompt, String title, String note, UUID categoryId) {
                try {
                    final Category category = categoryDatabase.find(categoryId);
                    final Task task = new Task(title, note);
                    category.addTask(task);
                    categoryDatabase.save(category);
                    uiModel().addTask(task, categoryId, ui());
                    prompt.dismiss();
                } catch (IllegalCategoryArgsException iae) {
                    showErrors(prompt, iae.args());
                }
            }

            private void showErrors(final ManageCategoriesUi.InputPrompt prompt, final IllegalCategoryArgsException.Args args) {
                if (args.titleIsIllegal()) {
                    prompt.showTitleError(args.titleError());
                }
            }

//            @Override
//            public void onEditCategory(ManageCategoriesUi.InputPrompt prompt, UUID categoryId, String title, String note) {
//                try {
//                    final Category category = categoryDatabase.find(categoryId);
//                    if (category != null) {
//                        category.writer().title(title).note(note).commit();
//                        categoryDatabase.save(category);
//                        uiModel().updateCategory(category, ui());
//                    }
//                    prompt.dismiss();
//                } catch (IllegalCategoryArgsException iae) {
//                    showErrors(prompt, iae.args());
//                }
//            }

            @Override
            public void onRemoveCategory(ManageCategoriesUi ui, UiCategory category) {
                categoryDatabase.delete(category.getId());
                uiModel().removeCategory(category.getId(), ui);
            }

            @Override
            public void onRemoveTask(ManageCategoriesUi ui, UiTask task, UiCategory category) {
                final Category domainCategory = categoryDatabase.find(category.getId());
                if (domainCategory.deleteTask(task.getId())) {
                    categoryDatabase.save(domainCategory);
                    uiModel().removeTask(task.getId(), category.getId(), ui);
                }
            }

            @Override
            public void onFilterRemoved(ManageCategoriesUi ui) {
                uiModel().removeFilter(ui);
            }

            @Override
            public void onFilterSelected(ManageCategoriesUi ui, int i) {
                uiModel().updateFilter(i, ui);
            }
        };
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulated()) {
            uiModel().showCategories(categoryDatabase.findAll(), ui());
        }
    }
}
