package com.example.mariliavgama.profilelist.details;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariliavgama.profilelist.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the item detail screen.
 */
public class ItemDetailFragment extends Fragment implements ItemDetailContract.View {

    @NonNull
    private static final String ARGUMENT_IMAGE_ID = "ITEM_IMAGE";
    @NonNull
    private static final String ARGUMENT_FULL_NAME_ID = "ITEM_FULL_NAME";
    @NonNull
    private static final String ARGUMENT_NAME_ID = "ITEM_NAME";
    @NonNull
    private static final String ARGUMENT_TITLE_ID = "ITEM_TITLE";
    @NonNull
    private static final String ARGUMENT_PHONE_ID = "ITEM_PHONE";

    private ItemDetailContract.Presenter mPresenter;

    private ImageView mImage;
    private TextView mFullName;
    private TextView mName;
    private TextView mTitle;
    private TextView mPhone;

    public static ItemDetailFragment newInstance(@Nullable String image, @Nullable String fullName,
                                                 @Nullable String name, @Nullable String title,
                                                 @Nullable String phone) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_IMAGE_ID, image);
        arguments.putString(ARGUMENT_FULL_NAME_ID, fullName);
        arguments.putString(ARGUMENT_NAME_ID, name);
        arguments.putString(ARGUMENT_TITLE_ID, title);
        arguments.putString(ARGUMENT_PHONE_ID, phone);
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.itemdetail_frag, container, false);

        mImage = root.findViewById(R.id.details_image);
        mFullName = root.findViewById(R.id.details_full_name);
        mName = root.findViewById(R.id.details_name);
        mTitle = root.findViewById(R.id.details_title);
        mPhone = root.findViewById(R.id.details_phone);

        return root;
    }

    @Override
    public void setPresenter(@NonNull ItemDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDetails(@NonNull String image, @NonNull String fullName, @NonNull String name,
                            @NonNull String title, @NonNull String phone) {
        ImageLoader.getInstance().displayImage(image, mImage);

        mFullName.setText(fullName);
        mName.setText(name);
        mTitle.setText(title);
        mPhone.setText(phone);
    }
}