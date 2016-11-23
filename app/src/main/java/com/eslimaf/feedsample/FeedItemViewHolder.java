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


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslimaf.feedsample.model.FeedItem;
import com.squareup.picasso.Picasso;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    public static final String ITEM_KEY = "ITEM";

    private final ImageView mImage;
    private final TextView mDateView;
    private final TextView mShortTextView;
    private FeedItem mFeedItem;

    public FeedItemViewHolder(final View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.feed_item_image);
        mDateView = (TextView) itemView.findViewById(R.id.feed_item_title_text);
        mShortTextView = (TextView) itemView.findViewById(R.id.feed_item_short_text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = itemView.getContext();
                Intent showDetailIntent = new Intent(context, DetailActivity.class);
                showDetailIntent.putExtra(ITEM_KEY, mFeedItem);
                context.startActivity(showDetailIntent);
            }
        });
    }

    public void bindFeedItem(FeedItem item) {
        Picasso.with(mImage.getContext()).load(item.getUrl()).into(mImage);
        mDateView.setText(item.getHumanDate());
        mShortTextView.setText(item.getDescription());
        mFeedItem = item;
    }
}
