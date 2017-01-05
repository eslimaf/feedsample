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

package com.eslimaf.feedsample.feed.presenter;


import com.eslimaf.feedsample.feed.model.FeedItem;
import com.eslimaf.feedsample.NasaApiService;
import com.eslimaf.feedsample.feed.model.PhotosInteractor;
import com.eslimaf.feedsample.feed.view.FeedView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;

public class FeedPresenterImpl implements FeedPresenter {
    private FeedView mFeedView;
    private PhotosInteractor mPhotosInteractor;
    private Calendar mCalendar;

    public FeedPresenterImpl(PhotosInteractor interactor) {
        mPhotosInteractor = interactor;
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onAttachView(FeedView view) {
        mFeedView = view;
    }

    @Override
    public void onDetachView() {
        mFeedView = null;
    }

    @Override
    public void requestPhoto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = dateFormat.format(mCalendar.getTime());
        mFeedView.showLoading();
        mPhotosInteractor.requestPhoto(date)
                .enqueue(new retrofit2.Callback<FeedItem>() {
                    @Override
                    public void onResponse(retrofit2.Call<FeedItem> call, retrofit2.Response<FeedItem> item) {
                        mCalendar.add(Calendar.DAY_OF_YEAR, -1);
                        if (item.body() != null
                                && !item.body().getMediaType()
                                .equals(NasaApiService.MEDIA_TYPE_VIDEO_VALUE)) {
                            mFeedView.addNewItemToFeed(item.body());
                            mFeedView.hideLoading();
                        } else {
                            requestPhoto();
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedItem> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
