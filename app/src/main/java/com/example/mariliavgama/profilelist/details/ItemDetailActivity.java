package com.example.mariliavgama.profilelist.details;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mariliavgama.profilelist.R;
import com.example.mariliavgama.profilelist.util.ActivityUtils;

/**
 * Displays item details screen.
 */
public class ItemDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_ID = "ITEM_IMAGE";
    public static final String EXTRA_FULL_NAME_ID = "ITEM_FULL_NAME";
    public static final String EXTRA_NAME_ID = "ITEM_NAME";
    public static final String EXTRA_TITLE_ID = "ITEM_TITLE";
    public static final String EXTRA_PHONE_ID = "ITEM_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.itemdetail_act);

        // Get the extras
        String image = getIntent().getStringExtra(EXTRA_IMAGE_ID);
        String fullName = getIntent().getStringExtra(EXTRA_FULL_NAME_ID);
        String name = getIntent().getStringExtra(EXTRA_NAME_ID);
        String title = getIntent().getStringExtra(EXTRA_TITLE_ID);
        String phone = getIntent().getStringExtra(EXTRA_PHONE_ID);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setTitle(fullName);
        }

        ItemDetailFragment itemDetailFragment = (ItemDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (itemDetailFragment == null) {
            itemDetailFragment = ItemDetailFragment.newInstance(image, fullName, name, title, phone);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    itemDetailFragment, R.id.contentFrame);
        }

        // Create the presenter
        new ItemDetailPresenter(image, fullName, name, title, phone, itemDetailFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}