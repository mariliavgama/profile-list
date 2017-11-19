package com.example.mariliavgama.profilelist.data.source.remote;
import android.support.annotation.NonNull;

import com.example.mariliavgama.profilelist.data.Item;
import com.example.mariliavgama.profilelist.data.source.ItemsDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class ItemsRemoteDataSource implements ItemsDataSource {

    private static ItemsRemoteDataSource INSTANCE;

    private final static Map<String, Item> ITEMS_SERVICE_DATA;

    static {
        ITEMS_SERVICE_DATA = new LinkedHashMap<>(2);
    }

    public static ItemsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemsRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private ItemsRemoteDataSource() {}

    private static void addItem(String id, String realName, String name, String tz, String tzLabel,
                                String image, String realNameNormalized, String title, String team,
                                String phone) {
        Item newItem = new Item(id, realName, name, tz, tzLabel, image, realNameNormalized, title,
                                team, phone);
        ITEMS_SERVICE_DATA.put(newItem.getId(), newItem);
    }

    @Override
    public void getItems(final @NonNull LoadItemsCallback callback) {
        ItemsService service = ItemsService.retrofit.create(ItemsService.class);
        Call<Result> call = service.repoItems();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    callback.onDataNotAvailable();
                    return;
                }

                List<Member> results = response.body().getMembers();
                if (results == null) {
                    callback.onDataNotAvailable();
                } else {
                    List<Item> items = new ArrayList<>();

                    for (int i = 0; i < results.size(); i++) {
                        Member m = results.get(i);
                        Profile p = m.getProfile();
                        if (p != null) {
                            addItem(m.getId(), m.getRealName(), m.getName(),
                                    m.getTz(), m.getTzLabel(), p.getImage192(),
                                    p.getRealNameNormalized(), p.getTitle(),
                                    p.getTeam(), p.getPhone());
                        }
                    }
                    items.addAll(ITEMS_SERVICE_DATA.values());
                    callback.onItemsLoaded(items);
                }
            }

            @Override
            public void onFailure (Call<Result> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveItem(@NonNull Item item) {
        ITEMS_SERVICE_DATA.put(item.getId(), item);
    }

    @Override
    public void refreshItems() {

    }

    @Override
    public void deleteAllItems() {
        ITEMS_SERVICE_DATA.clear();
    }

    public interface ItemsService {
        String baseUrl = "https://slack.com/";
        String getUrl =  "api/users.list?token=xoxp-5048173296-5048487710-19045732087-b5427e3b46";

        @GET(getUrl)
        Call<Result> repoItems();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}