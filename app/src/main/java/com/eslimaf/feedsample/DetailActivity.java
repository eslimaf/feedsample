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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslimaf.feedsample.model.FeedItem;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String ITEM_KEY = "ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FeedItem item = (FeedItem) getIntent().getParcelableExtra(ITEM_KEY);
        ImageView image = (ImageView) findViewById(R.id.detail_image);
        TextView description = (TextView) findViewById(R.id.detail_description);

        if(item != null) {
            Picasso.with(this).load(item.getUrl()).into(image);
            description.setText(item.getDescription());
        }
    }
}
