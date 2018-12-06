package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.Timer;

import java.util.UUID;

public class UiTaskWrapper extends UiListItemWrapper {
    private final UUID categoryId;
    private boolean isActive;
    private final Timer timer = new Timer();
    private Long timeOfLastTick = System.currentTimeMillis();

    public UiTaskWrapper(UUID categoryId, boolean isActive) {
        this.categoryId = categoryId;
        this.isActive = isActive;
    }
}
