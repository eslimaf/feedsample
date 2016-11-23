/*
 * Copyright (c) 2016.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eslimaf.feedsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eslimaf.feedsample.model.FeedItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FeedAdapter mFeedAdapter;
    private ArrayList<FeedItem> mItemList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private Calendar mCalendar;
    private NasaApiService service;

    private boolean mLoadingItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NasaApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NasaApiService.class);

        mCalendar = Calendar.getInstance();
        mItemList = new ArrayList<>();

        mFeedAdapter = new FeedAdapter(mItemList);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Add Swipe behavior
        ItemTouchHelper.SimpleCallback itemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = viewHolder.getAdapterPosition();
                        mItemList.remove(position);
                        mRecyclerView.getAdapter().notifyItemRemoved(position);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        //Add Scroll behavior
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
                if (!mLoadingItem && totalItemCount == getLastVisibleItemPosition() + 1) {
                    requestItem();
                }
            }
        });
        mRecyclerView.setAdapter(mFeedAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mItemList.size() == 0) {
            requestItem();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about_activity) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestItem() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = dateFormat.format(mCalendar.getTime());
        mLoadingItem = true;
        service.requestItem(date, NasaApiService.NASA_API_KEY)
                .enqueue(new retrofit2.Callback<FeedItem>() {
                    @Override
                    public void onResponse(retrofit2.Call<FeedItem> call, retrofit2.Response<FeedItem> item) {
                        mCalendar.add(Calendar.DAY_OF_YEAR, -1);
                        if (!item.body().getMediaType().equals(NasaApiService.MEDIA_TYPE_VIDEO_VALUE)){
                            addNewItemToFeed(item.body());
                        }else{
                            requestItem();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<FeedItem> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void addNewItemToFeed(final FeedItem item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mItemList.add(item);
                mFeedAdapter.notifyItemInserted(mItemList.size());
                mLoadingItem = false;
            }
        });
    }

    private int getLastVisibleItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }

}
