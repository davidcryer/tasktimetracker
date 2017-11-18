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
import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public class ManageCategoriesFragment extends UiFragment<ManageCategoriesUi.Listener, UiWrapperRepository>
        implements ManageCategoriesUi, ManageCategoriesNavigator.Callback, RemoveCategoryListener, RemoveTaskListener {
    private final CategoriesAdapter categoriesAdapter;
    private CategoriesFilter categoriesFilter;
    private Spinner filterSpinner;
    @Nullable private ManageCategoriesNavigator navigator;

    public ManageCategoriesFragment() {
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.onClickCategoryListener(new CategoriesAdapter.OnClickCategoryListener() {
            @Override
            public void onClick(UiCategory category, int pos) {
                if (hasListener()) {
                    listener().onClickCategory(ManageCategoriesFragment.this, category, pos);
                }
            }

            @Override
            public void onLongClick(UiCategory category) {
                if (hasListener()) {
                    listener().onLongClickCategory(ManageCategoriesFragment.this, category);
                }
            }

            @Override
            public void onClick(UiTask task, UiCategory category) {
                if (hasListener()) {
                    listener().onClickTask(ManageCategoriesFragment.this, task, category);
                }
            }

            @Override
            public void onLongClick(UiTask task, UiCategory category) {
                if (hasListener()) {
                    listener().onLongClickTask(ManageCategoriesFragment.this, task, category);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manage_categories, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(categoriesAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manage_categories, menu);
        filterSpinner = (Spinner) menu.findItem(R.id.spinner).getActionView();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        filterSpinner = null;
    }

    @Override
    public void showCategories(final List<UiCategory> categories) {
        categoriesAdapter.Categories(categories);
    }

    @Override
    public void addCategory(final UiCategory category) {
        categoriesAdapter.add(category);
    }

    @Override
    public void insertCategory(final UiCategory category, final int i) {
        categoriesAdapter.insert(category, i);
    }

    @Override
    public void setCategory(UiCategory category, int i) {
        categoriesAdapter.set(category, i);
    }

    @Override
    public void removeCategory(int i) {
        categoriesAdapter.remove(i);
    }

    @Override
    public void addTask(UiTask task, int categoryInd) {
        categoriesAdapter.addTask(task, categoryInd);
    }

    @Override
    public void removeTask(int categoryInd, int taskInd) {
        categoriesAdapter.removeTask(categoryInd, taskInd);
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
    public void showRemoveCategoryPrompt(final UiCategory category) {
        if (navigator != null) {
            navigator.showRemoveCategoryPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return RemoveCategoryDialogFragment.newInstance(category);
                }
            });
        }
    }

    @Override
    public void showRemoveTaskPrompt(final UiTask task, final UiCategory category) {
        if (navigator != null) {
            navigator.showRemoveTaskPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return RemoveTaskDialogFragment.newInstance(task, category);
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
    public void onAddCategory(InputCategoryPrompt prompt, String title, String note) {
        if (hasListener()) {
            listener().onAddCategory(prompt, title, note);
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
    protected Listener bind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiBinder binder) {
        return uiWrapperRepository.bind(this, binder);
    }

    @Override
    protected void unbind(@NonNull UiWrapperRepository uiWrapperRepository, @NonNull UiUnbinder unbinder) {
        uiWrapperRepository.unbind(this, unbinder);
    }
}
