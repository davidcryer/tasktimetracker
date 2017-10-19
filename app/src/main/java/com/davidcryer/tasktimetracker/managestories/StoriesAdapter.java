package com.davidcryer.tasktimetracker.managestories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
    private List<UiStory> stories;
    private OnClickStoryListener onClickStoryListener;

    StoriesAdapter() {
        this.stories = new ArrayList<>();
    }

    void stories(final List<UiStory> stories) {
        this.stories = ListUtils.newList(stories);
        notifyDataSetChanged();
    }

    void onClickStoryListener(final OnClickStoryListener onClickStoryListener) {
        this.onClickStoryListener = onClickStoryListener;
    }

    void add(final UiStory story) {
        stories.add(story);
        notifyItemInserted(stories.size() - 1);
    }

    void insert(final UiStory story, final int i) {
        stories.set(i, story);
        notifyItemInserted(i);
    }

    void remove(int i) {
        stories.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_stories_story, parent));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UiStory story = stories.get(position);
        final StoryLayout layout = holder.layout;
        layout.story(story);
        layout.onClickTaskListener(new TasksAdapter.OnClickTaskListener() {
            @Override
            public void onClick(UiTask task) {
                if (onClickStoryListener != null) {
                    onClickStoryListener.onClick(task, story);
                }
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickStoryListener != null) {
                    onClickStoryListener.onClick(story);
                }
            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onClickStoryListener != null) {
                    onClickStoryListener.onLongClick(story);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final StoryLayout layout;

        private ViewHolder(View itemView) {
            super(itemView);
            layout = (StoryLayout) itemView;
        }
    }

    public static class StoryLayout extends LinearLayout {
        private final TextView titleView;
        private final TextView noteView;
        private final View storyClicker;
        private final TasksAdapter tasksAdapter;

        public StoryLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            titleView = findViewById(R.id.title);
            noteView = findViewById(R.id.note);
            storyClicker = findViewById(R.id.storyClicker);
            tasksAdapter = new TasksAdapter();
            ((RecyclerView) findViewById(R.id.tasks)).setAdapter(tasksAdapter);
        }

        private void story(final UiStory story) {
            title(story.getTitle());
            note(story.getNote());
            tasks(story.getTasks());
        }

        private void title(final String title) {
            titleView.setText(title);
        }

        private void note(final String note) {
            noteView.setText(note);
        }

        private void tasks(final List<UiTask> tasks) {
            tasksAdapter.tasks(tasks);
        }

        private void onClickTaskListener(final TasksAdapter.OnClickTaskListener onClickTaskListener) {
            tasksAdapter.onClickTaskListener(onClickTaskListener);
        }

        @Override
        public void setOnClickListener(@Nullable OnClickListener l) {
            storyClicker.setOnClickListener(l);
        }

        @Override
        public void setOnLongClickListener(@Nullable OnLongClickListener l) {
            storyClicker.setOnLongClickListener(l);
        }
    }

    interface OnClickStoryListener {
        void onClick(UiStory story);
        void onClick(UiTask task, UiStory story);
        void onLongClick(UiStory story);
    }
}
