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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {

    private ArrayList<FeedItem> mListItems;

    public FeedAdapter(ArrayList<FeedItem> items) {
        mListItems = items;
    }

    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedItemViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(FeedItemViewHolder holder, int position) {
        FeedItem item = mListItems.get(position);
        holder.bindFeedItem(item);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}
