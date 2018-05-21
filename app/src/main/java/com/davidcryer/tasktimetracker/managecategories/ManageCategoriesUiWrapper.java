package com.davidcryer.tasktimetracker.managecategories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.exceptions.CategoryArgResults;
import com.davidcryer.tasktimetracker.common.domain.exceptions.TaskArgResults;
import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyActiveException;
import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyInactiveException;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.publicinterfaces.CategoryRepository;
import com.davidcryer.tasktimetracker.common.domain.Task;
import com.davidcryer.tasktimetracker.common.domain.publicinterfaces.TaskRepository;

import java.util.List;
import java.util.UUID;

public class ManageCategoriesUiWrapper extends UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    private ManageCategoriesUiWrapper(
            @NonNull final ManageCategoriesUiModel uiModel,
            final CategoryRepository categoryRepository,
            final TaskRepository taskRepository
    ) {
        super(uiModel);
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    public static ManageCategoriesUiWrapper newInstance(
            final ManageCategoriesUiModelFactory modelFactory,
            final CategoryRepository categoryRepository,
            final TaskRepository taskRepository
    ) {
        return new ManageCategoriesUiWrapper(modelFactory.create(), categoryRepository, taskRepository);
    }

    public static ManageCategoriesUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstanceState,
            final ManageCategoriesUiModelFactory modelFactory,
            final CategoryRepository categoryRepository,
            final TaskRepository taskRepository
    ) {
        final ManageCategoriesUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null
                ? newInstance(modelFactory, categoryRepository, taskRepository)
                : new ManageCategoriesUiWrapper(savedModel, categoryRepository, taskRepository);
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
                final Task task = taskRepository.get(uiTask.getId(), uiTask.getCategoryId());
                try {
                    task.activate();
                } catch (AlreadyActiveException ignored) {
                    uiTask.setActive(true);
                }
            }

            @Override
            public void onDeactivateTask(ManageCategoriesUi ui, UiTask uiTask) {
                final Task task = taskRepository.get(uiTask.getId(), uiTask.getCategoryId());
                try {
                    task.deactivate();
                } catch (AlreadyInactiveException ignored) {
                    uiTask.setActive(false);
                }
            }

            @Override
            public void onAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
                try {
                    final Category category = categoryRepository.create(title, note);
                    uiModel().addItem(items(category), , ui());//TODO
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
                    final Category category = categoryRepository.get(categoryId);
                    if (category == null) {
                        throw new IllegalStateException(String.format("Category not found for %1$s", categoryId.toString()));
                    }
                    final Task task = category.newTask(title, note);
                    uiModel().addItem();//TODO
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
            public void onFilterRemoved(ManageCategoriesUi ui) {
                final CategoryFilters filters = uiModel().filters();
                filters.select(CategoryFilters.DELELECTED);
                uiModel().setItems(items(categoryRepository.getAll()), filters, ui);
            }

            @Override
            public void onFilterSelected(ManageCategoriesUi ui, int i) {
                final CategoryFilters filters = uiModel().filters();
                final CategoryFilter filter = filters.select(i);
                uiModel().setItems(items(categoryRepository.get(filter.getId())), filters, ui);
            }
        };
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulated()) {
            uiModel().setItems(items(categoryRepository.getAll()), ui());
        }
    }

    private List<UiListItem> items(final List<Category> categories) {
        return UiCategoryMapper.from(categories);
    }

    private List<UiListItem> items(final Category category) {
        return UiCategoryMapper.from(category);
    }
}
