package com.davidcryer.tasktimetracker.managestories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;

class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
    private final static int VIEW_TYPE_STORY = 1;
    private final static int VIEW_TYPE_TASK = 2;
    private List<UiStory> stories;
    private OnClickStoryListener onClickStoryListener;
    private int cachedItemCount;
    private boolean invalidatedCachedItemCount = true;

    StoriesAdapter() {
        this.stories = new ArrayList<>();
    }

    void stories(final List<UiStory> stories) {
        invalidateCachedItemCount();
        this.stories = ListUtils.newList(stories);
        notifyDataSetChanged();
    }

    void onClickStoryListener(final OnClickStoryListener onClickStoryListener) {
        this.onClickStoryListener = onClickStoryListener;
    }

    void add(final UiStory story) {
        invalidateCachedItemCount();
        stories.add(story);
        notifyItemInserted(getItemCount() - 1);
    }

    void insert(final UiStory story, final int i) {
        invalidateCachedItemCount();
        stories.add(i, story);
        notifyItemInserted(position(i));
    }

    void set(final UiStory story, final int i) {
        stories.set(i, story);
        notifyItemChanged(position(i));
    }

    void remove(final int i) {
        invalidateCachedItemCount();
        stories.remove(i);
        notifyItemRemoved(position(i));
    }

    private int position(final int index) {
        int pos = 0;
        for (int i = 0; i < stories.size(); i++) {
            if (i == index) {
                return pos;
            }
            pos += 1 + stories.get(i).expandedTaskCount();
        }
        return -1;
    }

    void expandStory(final int i, final int pos) {
        invalidateCachedItemCount();
        notifyItemRangeInserted(pos + 1, stories.get(i).taskCount());
    }


    void shrinkStory(final int i, final int pos) {
        invalidateCachedItemCount();
        notifyItemRangeRemoved(pos + 1, stories.get(i).taskCount());
    }

    private void invalidateCachedItemCount() {
        invalidatedCachedItemCount = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_STORY: {
                return new StoryViewHolder(storyLayout(parent));
            }
            case VIEW_TYPE_TASK: {
                return new TaskViewHolder(taskLayout(parent));
            }
        }
        throw new IllegalStateException(String.format("viewType is not recognised: %1$s", viewType));
    }

    private static StoryLayout storyLayout(final ViewGroup group) {
        return (StoryLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_story, group, false);
    }

    private static TaskLayout taskLayout(final ViewGroup group) {
        return (TaskLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.holder_task, group, false);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        for (int i = 0, posItr = 0; i < stories.size(); i++) {
            final UiStory story = stories.get(i);
            if (bindStory(holder, position, story, posItr)) {
                break;
            }
            posItr++;
            if (bindStoryTask(holder, position, story, posItr)) {
                break;
            }
            posItr += story.expandedTaskCount();
        }
    }

    private boolean bindStory(final ViewHolder holder, final int holderPos, final UiStory story, final int storyPos) {
        if (holderPos == storyPos) {
            holder.story(story);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickStory(story, holder.getAdapterPosition());
                }
            });
            return true;
        }
        return false;
    }

    private void onClickStory(final UiStory story, final int pos) {
        if (onClickStoryListener != null) {
            onClickStoryListener.onClick(story, pos);
        }
    }

    private boolean bindStoryTask(final ViewHolder holder, final int holderPos, final UiStory story, final int firstTaskPos) {
        if (holderPos < firstTaskPos + story.expandedTaskCount()) {
            final UiTask task = story.task(holderPos - firstTaskPos);
            holder.task(task);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickTask(task, story);
                }
            });
            return true;
        }
        return false;
    }

    private void onClickTask(final UiTask task, final UiStory story) {
        if (onClickStoryListener != null) {
            onClickStoryListener.onClick(task, story);
        }
    }

    @Override
    public int getItemCount() {
        if (invalidatedCachedItemCount) {
            cachedItemCount = 0;
            for (final UiStory story : stories) {
                cachedItemCount += 1 + story.expandedTaskCount();
            }
            invalidatedCachedItemCount = false;
        }
        return cachedItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0, posItr = 0; i < stories.size(); i++) {
            final UiStory story = stories.get(i);
            if (position == posItr) {
                return VIEW_TYPE_STORY;
            }
            posItr++;
            if (position < posItr + story.expandedTaskCount()) {
                return VIEW_TYPE_TASK;
            }
            posItr += story.expandedTaskCount();
        }
        throw new IllegalStateException("Item neither story nor task");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewHolder(View itemView) {
            super(itemView);
        }

        void story(final UiStory story) {
            throw new UnsupportedOperationException("Stories.ViewHolder.story() method should not be used");
        }

        void task(final UiTask task) {
            throw new UnsupportedOperationException("Stories.ViewHolder.task() method should not be used");
        }
    }

    private static class StoryViewHolder extends ViewHolder {
        private final StoryLayout layout;

        private StoryViewHolder(StoryLayout itemView) {
            super(itemView);
            layout = itemView;
        }

        @Override
        void story(UiStory story) {
            layout.story(story);
        }
    }

    private static class TaskViewHolder extends ViewHolder {
        private final TaskLayout layout;

        private TaskViewHolder(TaskLayout itemView) {
            super(itemView);
            layout = itemView;
        }

        @Override
        void task(UiTask task) {
            layout.task(task);
        }
    }

    interface OnClickStoryListener {
        void onClick(UiStory story, int pos);
        void onClick(UiTask task, UiStory story);
    }
}
