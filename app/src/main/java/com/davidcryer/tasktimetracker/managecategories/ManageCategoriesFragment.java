package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.framework.FabListener;
import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public class ManageCategoriesFragment extends UiFragment<ManageCategoriesUi.Listener, UiWrapperRepository>
        implements ManageCategoriesUi, ManageCategoriesNavigator.Callback, RemoveCategoryListener, RemoveTaskListener, FabListener {
    private final CategoriesAdapter categoriesAdapter;
    private CategoriesFilter categoriesFilter;
    private Spinner filterSpinner;
    @Nullable private ManageCategoriesNavigator navigator;

    public ManageCategoriesFragment() {
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.onClickCategoryListener(new CategoriesAdapter.OnClickListener() {
            @Override
            public void onClick(UiCategory category, int i) {
                if (hasListener()) {
                    listener().onClickCategory(ManageCategoriesFragment.this, category, i);
                }
            }

            @Override
            public void onClick(UiTask task) {
                if (hasListener()) {
                    listener().onClickTask(ManageCategoriesFragment.this, task);
                }
            }

            @Override
            public void onClickAddTask(UUID categoryId) {
                if (hasListener()) {
                    listener().onClickAddTask(ManageCategoriesFragment.this, categoryId);
                }
            }
        });
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
                if (hasListener()) {
                    listener().onFilterRemoved(ManageCategoriesFragment.this);
                }
            }

            @Override
            public void onFilterSelected(int i) {
                if (hasListener()) {
                    listener().onFilterSelected(ManageCategoriesFragment.this, i);
                }
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void show(List<UiListItem> items) {
        categoriesAdapter.items(items);
    }

    @Override
    public void add(final UiListItem item) {
        categoriesAdapter.add(item);
    }

    @Override
    public void insert(final UiListItem item, final int i) {
        categoriesAdapter.insert(item, i);
    }

    @Override
    public void set(UiListItem item, int i) {
        categoriesAdapter.set(item, i);
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
    public void showFilterOptions(List<String> options) {
        categoriesFilter.populate(options, filterSpinner);
    }

    @Override
    public void showFilterOptions(List<String> options, int selected) {
        categoriesFilter.populate(options, selected, filterSpinner);
    }

    @Override
    public void showAddCategoryPrompt() {
        if (navigator != null) {
            navigator.showAddCategoryPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return new AddCategoryDialogFragment();
                }
            });
        }
    }

    @Override
    public void showAddTaskPrompt(final UUID categoryId) {
        if (navigator != null) {
            navigator.showAddTaskPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return AddTaskDialogFragment.newInstance(categoryId);
                }
            });
        }
    }

    @Override
    public void showManageTaskScreen(final ManageTaskIntentModel intentModel) {
        if (navigator != null) {
            navigator.toManageTaskScreen(intentModel);
        }
    }

    @Override
    public void onAddCategory(InputPrompt prompt, String title, String note) {
        if (hasListener()) {
            listener().onAddCategory(prompt, title, note);
        }
    }

    @Override
    public void onAddTask(InputPrompt prompt, String title, String note, UUID categoryId) {
        if (hasListener()) {
            listener().onAddTask(prompt, title, note, categoryId);
        }
    }

    @Override
    public void onClickDelete(UiCategory category) {
        if (hasListener()) {
            listener().onRemoveCategory(this, category);
        }
    }

    @Override
    public void onClickDelete(UiTask task, UiCategory category) {
        if (hasListener()) {
            listener().onRemoveTask(this, task, category);
        }
    }

    @Override
    public boolean onFabClicked() {
        if (hasListener()) {
            listener().onClickAddCategory(this);
        }
        return true;
    }

    @Override
    protected Listener bind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiBinder binder) {
        return uiWrapperRepository.bind(this, binder);
    }

    @Override
    protected void unbind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiUnbinder unbinder) {
        uiWrapperRepository.unbind(this, unbinder);
    }
}
