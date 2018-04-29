package com.davidcryer.tasktimetracker.managecategories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.CategoryArgResults;
import com.davidcryer.tasktimetracker.common.domain.TaskArgResults;
import com.davidcryer.tasktimetracker.common.domain.AlreadyActiveException;
import com.davidcryer.tasktimetracker.common.domain.AlreadyInactiveException;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.DomainManager;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.UUID;

public class ManageCategoriesUiWrapper extends UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> {
    private final DomainManager domainManager;

    private ManageCategoriesUiWrapper(@NonNull final ManageCategoriesUiModel uiModel, final DomainManager domainManager) {
        super(uiModel);
        this.domainManager = domainManager;
    }

    public static ManageCategoriesUiWrapper newInstance(final ManageCategoriesUiModelFactory modelFactory, final DomainManager domainManager) {
        return new ManageCategoriesUiWrapper(modelFactory.create(), domainManager);
    }

    public static ManageCategoriesUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstanceState,
            final ManageCategoriesUiModelFactory modelFactory,
            final DomainManager domainManager
    ) {
        final ManageCategoriesUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory, domainManager) : new ManageCategoriesUiWrapper(savedModel, domainManager);
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
            public void onActivateTask(ManageCategoriesUi ui, UiTask uiTask) {
                final Task task = uiModel().task(uiTask.getId());
                try {
                    task.activate();
                } catch (AlreadyActiveException ignored) {
                    uiTask.setActive(true);
                }
            }

            @Override
            public void onDeactivateTask(ManageCategoriesUi ui, UiTask uiTask) {
                final Task task = uiModel().task(uiTask.getId());
                try {
                    task.deactivate();
                } catch (AlreadyInactiveException ignored) {
                    uiTask.setActive(false);
                }
            }

            @Override
            public void onAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
                try {
                    final Category category = domainManager.create(title, note);
                    uiModel().addCategory(category, ui());
                    prompt.dismiss();
                } catch (CategoryArgResults.Exception e) {
                    showErrors(prompt, e.results());
                }
            }

            private void showErrors(final ManageCategoriesUi.InputPrompt prompt, final CategoryArgResults results) {
                if (results.hasTitleError()) {
                    prompt.showTitleError(results.title().note());
                }
            }

            @Override
            public void onAddTask(ManageCategoriesUi.InputPrompt prompt, String title, String note, UUID categoryId) {
                try {
                    final Category category = uiModel().category(categoryId);
                    if (category == null) {
                        throw new IllegalStateException(String.format("Category not found for %1$s", categoryId.toString()));
                    }
                    uiModel().addTask(category.newTask(title, note), category, ui());
                    prompt.dismiss();
                } catch (TaskArgResults.Exception e) {
                    showErrors(prompt, e.results());
                }
            }

            private void showErrors(final ManageCategoriesUi.InputPrompt prompt, final TaskArgResults args) {
                if (args.hasTitleError()) {
                    prompt.showTitleError(args.title().note());
                }
            }

            @Override
            public void onRemoveCategory(ManageCategoriesUi ui, UiCategory category) {
                uiModel().removeCategory(category.getId(), ui);
            }

            @Override
            public void onRemoveTask(ManageCategoriesUi ui, UiTask task, UiCategory uiCategory) {
                final Category category = uiModel().category(uiCategory.getId());
                if (category == null) {
                    throw new IllegalStateException(String.format("Category not found for %1$s", uiCategory.getId().toString()));
                }
                if (category.deleteTask(task.getId())) {
                    uiModel().removeTask(task.getId(), uiCategory.getId(), ui);
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
            uiModel().showCategories(domainManager.getAll(), ui());
        }
    }
}
