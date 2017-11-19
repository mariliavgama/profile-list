package com.example.mariliavgama.profilelist.items;

import com.example.mariliavgama.profilelist.BasePresenter;
import com.example.mariliavgama.profilelist.BaseView;
import com.example.mariliavgama.profilelist.data.Item;

import java.util.List;

/**
 * Architecture style based on: https://github.com/googlesamples/android-architecture
 * This is a contract class which defines the connection between the view and the presenter.
 */

public interface ItemsContract {

    interface View extends BaseView<Presenter> {
        void showItems(List<Item> items);

        void showLoadingItemsError();

        void showItemDetailsUi(Item item);
    }

    interface Presenter extends BasePresenter {
        void openItemDetails(Item item);

        void refreshItems();

        void takeView(ItemsContract.View view);
    }
}