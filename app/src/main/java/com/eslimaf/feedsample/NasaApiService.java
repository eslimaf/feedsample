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


import com.eslimaf.feedsample.feed.model.FeedItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApiService {
    String NASA_API_KEY = "P1teEzaIAyk9JednPwE96ojFmHILVc2hFJtGtBHy";
    String BASE_URL = "https://api.nasa.gov";
    String DATE_PARAM = "date";
    String API_PARAM = "api_key";
    String MEDIA_TYPE_VIDEO_VALUE = "video";

    @GET("planetary/apod")
    Call<FeedItem> requestItem(@Query(DATE_PARAM) String date, @Query(API_PARAM) String apiKey);
}
