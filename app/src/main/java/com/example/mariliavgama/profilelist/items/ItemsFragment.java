package com.example.mariliavgama.profilelist.items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariliavgama.profilelist.R;
import com.example.mariliavgama.profilelist.data.Item;
import com.example.mariliavgama.profilelist.details.ItemDetailActivity;
import com.example.mariliavgama.profilelist.util.LayoutUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Architecture style based on: https://github.com/googlesamples/android-architecture
 * This is a Fragment which implements the view interface.
 */

public class ItemsFragment extends Fragment implements ItemsContract.View {

    private ItemsContract.Presenter mPresenter;
    private ItemsAdapter mListAdapter;
    private ItemsActivity mWeakReferenceActivity;

    public ItemsFragment() {
        // Requires empty public constructor
    }
    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ItemsAdapter(new ArrayList<Item>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ItemsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showItemDetailsUi(Item item) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.

        if (mWeakReferenceActivity == null) {
            return;
        }
        Intent intent = new Intent(mWeakReferenceActivity, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.EXTRA_IMAGE_ID, item.getImage());
        intent.putExtra(ItemDetailActivity.EXTRA_FULL_NAME_ID, item.getRealNameNormalized());
        intent.putExtra(ItemDetailActivity.EXTRA_NAME_ID, item.getName());
        intent.putExtra(ItemDetailActivity.EXTRA_TITLE_ID, item.getTitle());
        intent.putExtra(ItemDetailActivity.EXTRA_PHONE_ID, item.getPhone());
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.items_frag, container, false);

        // Set up items view
        RecyclerView recyclerView = root.findViewById(R.id.items_list);
        // only retain a weak reference to the activity
        mWeakReferenceActivity = ItemsActivity.wrActivity.get();
        if (mWeakReferenceActivity == null) {
            return null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mWeakReferenceActivity));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mWeakReferenceActivity));
        recyclerView.setAdapter(mListAdapter);

        return root;
    }

    @Override
    public void showItems(List<Item> items) {
        mListAdapter.replaceData(items);
        /*
        Context context = getContext();
        if (context == null) {
            return;
        }
        Toast.makeText(context, R.string.items_show_success, Toast.LENGTH_SHORT).show();
        */
    }

    @Override
    public void showLoadingItemsError() {
        if (mWeakReferenceActivity == null) {
            return;
        }
        Toast.makeText(mWeakReferenceActivity, R.string.items_show_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Listener for clicks on items in the ListView.
     */
    ItemClickListener mItemListener = new ItemClickListener() {
        public void onItemClick(Item item) {
            mPresenter.openItemDetails(item);
        }
    };

    static class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Item> mItems;
        private ItemClickListener mItemClickListener;

        ItemsAdapter(List<Item> items, ItemClickListener itemClickListener) {
            setList(items);
            setItemOnClickListener(itemClickListener);
        }

        void replaceData(List<Item> items) {
            setList(items);
            notifyDataSetChanged();
        }

        private void setList(@NonNull List<Item> items) {
            mItems = items;
        }

        private void setItemOnClickListener(ItemClickListener itemClickListener) {
            mItemClickListener = itemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new ItemViewHolder(view);
        }

        //
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ItemViewHolder) holder).bindData(mItems.get(position), mItemClickListener);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public int getItemViewType(final int position) {
            return R.layout.item;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView name;
        private ImageView image;
        private TextView team;
        private TextView timezone;

        ItemViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.item_name);
            image = itemView.findViewById(R.id.item_image);
            team = itemView.findViewById(R.id.item_team);
            timezone = itemView.findViewById(R.id.item_timezone);
        }

        void bindData(final Item item, final ItemClickListener onClickListener) {
            image.setContentDescription(item.getRealName());
            // Default options set on Activity onCreate will be used in all calls to displayImage
            ImageLoader.getInstance().displayImage(item.getImage(), image);

            if (context == null) {
                return;
            }
            name.setText(LayoutUtils.formatText(item.getRealName(), item.getName(),
                    context.getString(R.string.name)));

            team.setText(String.format(context.getString(R.string.team), item.getTeam()));

            timezone.setText(LayoutUtils.formatText(item.getTz(), item.getTzLabel(),
                    context.getString(R.string.timezone)));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick(item);
                }
            });
        }
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(android.R.drawable.divider_horizontal_bright);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(Item item);
    }
}