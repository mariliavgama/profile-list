package com.example.mariliavgama.profilelist.data.source;


import android.support.annotation.NonNull;

import com.example.mariliavgama.profilelist.data.Item;

import java.util.List;

/**
 * Main entry point for accessing items data.
 */

public interface ItemsDataSource {

    interface LoadItemsCallback {

        void onItemsLoaded(List<Item> items);

        void onDataNotAvailable();
    }

    void getItems(@NonNull LoadItemsCallback callback);

    void saveItem(@NonNull Item item);

    void refreshItems();

    void deleteAllItems();

}