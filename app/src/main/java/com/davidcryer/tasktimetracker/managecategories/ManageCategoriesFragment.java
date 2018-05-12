package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.davidc.uiwrapper.UiWrapper;
import com.davidc.uiwrapper.UiWrapperFactoryFragment;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.framework.FabListener;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperFactory;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public class ManageCategoriesFragment extends UiWrapperFactoryFragment<ManageCategoriesUi, ManageCategoriesUi.Listener, UiWrapperFactory>
        implements ManageCategoriesNavigator.Callback, RemoveCategoryListener, RemoveTaskListener, FabListener {
    private final CategoriesAdapter categoriesAdapter;
    private CategoriesFilter categoriesFilter;
    private Spinner filterSpinner;
    @Nullable private ManageCategoriesNavigator navigator;

    public ManageCategoriesFragment() {
        categoriesAdapter = new CategoriesAdapter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (ManageCategoriesNavigator) context;
        categoriesFilter = new CategoriesFilter(context, new CategoriesFilter.OnFilterChangeListener() {
            @Override
            public void onFilterRemoved() {
                listener().onFilterRemoved(ui());
            }

            @Override
            public void onFilterSelected(int i) {
                listener().onFilterSelected(ui(), i);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
        categoriesFilter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manage_categories, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.items);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(categoriesAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manage_categories, menu);
        filterSpinner = (Spinner) menu.findItem(R.id.spinner).getActionView();
        categoriesFilter.setup(filterSpinner);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        filterSpinner = null;
    }

    @Override
    public void onAddCategory(ManageCategoriesUi.InputPrompt prompt, String title, String note) {
        listener().onAddCategory(prompt, title, note);
    }

    @Override
    public void onAddTask(ManageCategoriesUi.InputPrompt prompt, String title, String note, UUID categoryId) {
        listener().onAddTask(prompt, title, note, categoryId);
    }

    @Override
    public void onClickDelete(UiCategory category) {
        listener().onRemoveCategory(ui(), category);
    }

    @Override
    public void onClickDelete(UiTask task, UiCategory category) {
        listener().onRemoveTask(ui(), task, category);
    }

    @Override
    public boolean onFabClicked() {
        listener().onClickAddCategory(ui());
        return true;
    }

    @Override
    protected ManageCategoriesUi ui() {
        return new ManageCategoriesUi() {
            @Override
            public void show(List<UiListItem> items) {
                categoriesAdapter.items(items);
            }

            @Override
            public void add(final UiListItem item, final int i) {
                categoriesAdapter.add(item, i);
            }

            @Override
            public void add(List<UiListItem> items, int i) {
                categoriesAdapter.add(items, i);
            }

            @Override
            public void remove(int i) {
                categoriesAdapter.remove(i);
            }

            @Override
            public void remove(int i, int count) {
                categoriesAdapter.remove(i, count);
            }

            @Override
            public void showFilterOptions(List<String> options, int selected) {
                categoriesFilter.populate(options, selected, filterSpinner);
            }

            @Override
            public void showAddCategoryPrompt() {
                if (navigator != null) {
                    navigator.showAddCategoryPrompt(AddCategoryDialogFragment::new);
                }
            }

            @Override
            public void showAddTaskPrompt(final UUID categoryId) {
                if (navigator != null) {
                    navigator.showAddTaskPrompt(() -> AddTaskDialogFragment.newInstance(categoryId));
                }
            }

            @Override
            public void showManageTaskScreen(final ManageTaskIntentModel intentModel) {
                if (navigator != null) {
                    navigator.toManageTaskScreen(intentModel);
                }
            }
        };
    }

    @Override
    protected UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ?> uiWrapper(UiWrapperFactory uiWrapperFactory, @Nullable Bundle savedState) {
        return uiWrapperFactory.createManageCategoriesUiWrapper(savedState);
    }
}
