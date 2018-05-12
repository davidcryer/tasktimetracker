package com.davidcryer.tasktimetracker.managecategories;

import com.davidc.uiwrapper.UiModel;
import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.List;
import java.util.UUID;

public interface ManageCategoriesUiModel extends UiModel<ManageCategoriesUi> {
    void setItems(List<UiListItem> items, ManageCategoriesUi ui);
    void setItems(List<UiListItem> items, CategoryFilters filters, ManageCategoriesUi ui);
    void addItem(UiListItem item, int i, ManageCategoriesUi ui);
    void addItems(List<UiListItem> items, int i, ManageCategoriesUi ui);
    void removeItem(int i, ManageCategoriesUi ui);
    void removeItems(int i, int count, ManageCategoriesUi ui);
    boolean isPopulated();
    CategoryFilters filters();
}
