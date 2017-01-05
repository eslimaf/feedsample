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

package com.eslimaf.feedsample.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eslimaf.feedsample.AboutActivity;
import com.eslimaf.feedsample.feed.model.FeedItem;
import com.eslimaf.feedsample.R;
import com.eslimaf.feedsample.feed.model.PhotosInteractor;
import com.eslimaf.feedsample.feed.model.PhotosInteractorImpl;
import com.eslimaf.feedsample.feed.presenter.FeedPresenter;
import com.eslimaf.feedsample.feed.presenter.FeedPresenterImpl;
import com.eslimaf.feedsample.feed.view.FeedView;

import java.util.ArrayList;


public class FeedActivity extends AppCompatActivity implements FeedView {

    private FeedAdapter mFeedAdapter;
    private ArrayList<FeedItem> mItemList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Snackbar mSnackbar;

    // Model
    private PhotosInteractor mPhotosInteractor;
    // Presenter
    private FeedPresenter mFeedPresenter;

    private boolean mLoadingItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mSnackbar = Snackbar.make(findViewById(android.R.id.content),
                R.string.snackbar_loading, Snackbar.LENGTH_INDEFINITE);
        mItemList = new ArrayList<>();
        mPhotosInteractor = new PhotosInteractorImpl();
        mFeedPresenter = new FeedPresenterImpl(mPhotosInteractor);


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
                if (!isLoading() && totalItemCount == getLastVisibleItemPosition() + 1) {
                    mFeedPresenter.requestPhoto();
                }
            }
        });
        mRecyclerView.setAdapter(mFeedAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFeedPresenter.onAttachView(this);
        if (mItemList.size() == 0) {
            mFeedPresenter.requestPhoto();
        }
    }

    @Override
    protected void onStop() {
        mFeedPresenter.onDetachView();
        super.onStop();
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

    // 3
    @Override
    public void addNewItemToFeed(final FeedItem item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mItemList.add(item);
                mFeedAdapter.notifyItemInserted(mItemList.size());
            }
        });
    }

    @Override
    public void showLoading() {
        mSnackbar.show();
        mLoadingItem = true;
    }

    @Override
    public void hideLoading() {
        mSnackbar.dismiss();
        mLoadingItem = false;
    }

    private boolean isLoading() {
        return mLoadingItem;
    }
  
    private int getLastVisibleItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }

}
