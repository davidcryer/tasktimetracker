package com.davidcryer.tasktimetracker.managestories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiFragment;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.framework.activities.DialogFragmentFactory;
import com.davidcryer.tasktimetracker.common.framework.uiwrapper.UiWrapperRepository;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public class ManageStoriesFragment extends UiFragment<ManageStoriesUi.Listener, UiWrapperRepository> implements ManageStoriesUi, ManageStoriesNavigator.Callback, RemoveStoryListener {
    private final StoriesAdapter storiesAdapter;
    @Nullable private ManageStoriesNavigator navigator;

    public ManageStoriesFragment() {
        storiesAdapter = new StoriesAdapter();
        storiesAdapter.onClickStoryListener(new StoriesAdapter.OnClickStoryListener() {
            @Override
            public void onClick(UiStory story, int pos) {
                if (hasListener()) {
                    listener().onClickStory(ManageStoriesFragment.this, story, pos);
                }
            }

            @Override
            public void onLongClick(UiStory story) {
                if (hasListener()) {
                    listener().onLongClickStory(ManageStoriesFragment.this, story);
                }
            }

            @Override
            public void onClick(UiTask task, UiStory story) {
                if (hasListener()) {
                    listener().onClickTask(ManageStoriesFragment.this, task, story);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manage_stories, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.stories);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(storiesAdapter);
        return view;
    }

    @Override
    public void showStories(final List<UiStory> stories) {
        storiesAdapter.stories(stories);
    }

    @Override
    public void addStory(final UiStory story) {
        storiesAdapter.add(story);
    }

    @Override
    public void insertStory(final UiStory story, final int i) {
        storiesAdapter.insert(story, i);
    }

    @Override
    public void setStory(UiStory story, int i) {
        storiesAdapter.set(story, i);
    }

    @Override
    public void removeStory(int i) {
        storiesAdapter.remove(i);
    }

    @Override
    public void expandStory(int i, int pos) {
        storiesAdapter.expandStory(i, pos);
    }

    @Override
    public void shrinkStory(final int i, final int pos) {
        storiesAdapter.shrinkStory(i, pos);
    }

    @Override
    public void showAddStoryPrompt() {
        if (navigator != null) {
            navigator.showAddStoryPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return new AddStoryDialogFragment();
                }
            });
        }
    }

    @Override
    public void showRemoveStoryPrompt(final UiStory story) {
        if (navigator != null) {
            navigator.showRemoveStoryPrompt(new DialogFragmentFactory() {
                @Override
                public DialogFragment create() {
                    return RemoveStoryDialogFragment.newInstance(story);
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
    public void onAddStory(InputStoryPrompt prompt, String title, String note) {
        if (hasListener()) {
            listener().onAddStory(prompt, title, note);
        }
    }

    @Override
    public void onClickDelete(UiStory story) {
        if (hasListener()) {
            listener().onRemoveStory(this, story);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (ManageStoriesNavigator) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
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
