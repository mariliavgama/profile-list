package com.example.mariliavgama.profilelist.items;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mariliavgama.profilelist.R;
import com.example.mariliavgama.profilelist.data.source.ItemsRepository;
import com.example.mariliavgama.profilelist.data.source.local.ItemsLocalDataSource;
import com.example.mariliavgama.profilelist.data.source.local.ListAppDatabase;
import com.example.mariliavgama.profilelist.data.source.remote.ItemsRemoteDataSource;
import com.example.mariliavgama.profilelist.util.ActivityUtils;
import com.example.mariliavgama.profilelist.util.AppExecutors;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.lang.ref.WeakReference;

/**
 * Architecture style based on: https://github.com/googlesamples/android-architecture
 * This is an Activity which creates fragments and presenters.
 */

public class ItemsActivity extends AppCompatActivity {

    static WeakReference<ItemsActivity> wrActivity = new WeakReference<>(null);
    private ItemsPresenter mItemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_act);
        wrActivity = new WeakReference<>(this);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                // Cache is not available by default, so set it here.
                .cacheInMemory(true)
                // Comment this this if would like to disable cache on SD Card
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config); // Has to be done on Application start

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ItemsFragment itemsFragment =
                (ItemsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (itemsFragment == null) {
            // Create the fragment
            itemsFragment = ItemsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    itemsFragment, R.id.contentFrame);
        }

        // TODO: Use dependency injection instead of creating a new instance of repository
        ListAppDatabase database = ListAppDatabase.getInstance(getApplicationContext());

        ItemsRepository repository = ItemsRepository.getInstance(ItemsRemoteDataSource.getInstance(),
                ItemsLocalDataSource.getInstance(new AppExecutors(),
                        database.itemDao()));

        // Create the presenter
        mItemsPresenter = new ItemsPresenter(repository, itemsFragment);
    }
}