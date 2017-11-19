package com.example.mariliavgama.profilelist.details;

import android.support.annotation.NonNull;

import com.example.mariliavgama.profilelist.BasePresenter;
import com.example.mariliavgama.profilelist.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ItemDetailContract {

    interface View extends BaseView<Presenter> {

        void showDetails(@NonNull String image, @NonNull String fullName, @NonNull String name,
                         @NonNull String title, @NonNull String phone);
    }

    interface Presenter extends BasePresenter {
    }
}