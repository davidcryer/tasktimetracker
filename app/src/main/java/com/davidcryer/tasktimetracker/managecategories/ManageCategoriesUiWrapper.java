package com.davidcryer.tasktimetracker.managecategories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.CategoryArgRules;
import com.davidcryer.tasktimetracker.common.domain.TaskArgRules;
import com.davidcryer.tasktimetracker.common.domain.AlreadyStartedException;
import com.davidcryer.tasktimetracker.common.domain.AlreadyStoppedException;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.DomainManager;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.UUID;

public class ManageCategoriesUiWrapper extends UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel>
        implements Task.OngoingStatusListener {
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
            public void onToggleActiveStatus(ManageCategoriesUi ui, UiTask uiTask, boolean isActive) {
                final Task task = uiModel().task(uiTask.getId());
                if (isActive) {
                    try {
                        task.start();
                    } catch (AlreadyStartedException e) {
                        onStart(task);
                    }
                } else {
                    try {
                        task.stop();
                    } catch (AlreadyStoppedException e) {
                        onStop(task);
                    }
                }
            }

            @Override
            public void onAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
                try {
                    final Category category = domainManager.create(title, note);
                    uiModel().addCategory(category, ui());
                    prompt.dismiss();
                } catch (CategoryArgRules.Exception e) {
                    showErrors(prompt, e.args());
                }
            }

            private void showErrors(final ManageCategoriesUi.InputPrompt prompt, final CategoryArgRules args) {
                if (args.titleIsIllegal()) {
                    prompt.showTitleError(args.titleError());
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
                } catch (TaskArgRules.Exception e) {
                    showErrors(prompt, e.args());
                }
            }

            private void showErrors(final ManageCategoriesUi.InputPrompt prompt, final TaskArgRules args) {
                if (args.titleIsIllegal()) {
                    prompt.showTitleError(args.titleError());
                }
            }

//            @Override
//            public void onEditCategory(ManageCategoriesUi.InputPrompt prompt, UUID categoryId, String title, String note) {
//                try {
//                    final Category category = domainManager.get(categoryId);
//                    if (category != null) {
//                        category.writer().title(title).note(note).commit();
//                        domainManager.save(category);
//                        uiModel().updateCategory(category, ui());
//                    }
//                    prompt.dismiss();
//                } catch (CategoryArgRules iae) {
//                    showErrors(prompt, iae.args());
//                }
//            }

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
    public void onStart(Task task) {
        uiModel().activate(task, ui());
    }

    @Override
    public void onStop(Task task) {
        uiModel().deactivate(task, ui());
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulated()) {
            uiModel().showCategories(domainManager.getAll(this), ui());
        }
    }
}
