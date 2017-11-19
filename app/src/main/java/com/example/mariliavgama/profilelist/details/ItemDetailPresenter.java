package com.example.mariliavgama.profilelist.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link ItemDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class ItemDetailPresenter implements ItemDetailContract.Presenter {

    private final ItemDetailContract.View mItemDetailView;

    @Nullable
    private String mImage;
    @Nullable
    private String mFullName;
    @Nullable
    private String mName;
    @Nullable
    private String mTitle;
    @Nullable
    private String mPhone;

    public ItemDetailPresenter(@Nullable String image, @Nullable String fullName,
                               @Nullable String name, @Nullable String title,
                               @Nullable String phone,
                               @NonNull ItemDetailContract.View itemDetailView) {
        mImage = image;
        mFullName = fullName;
        mName = name;
        mTitle = title;
        mPhone = phone;

        mItemDetailView = checkNotNull(itemDetailView, "itemDetailView cannot be null!");

        mItemDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        showDetails();
    }

    private void showDetails() {
        mItemDetailView.showDetails(mImage, mFullName, mName, mTitle, mPhone);
    }
}