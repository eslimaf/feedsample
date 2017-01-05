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

package com.eslimaf.feedsample.feed.model;


import com.eslimaf.feedsample.NasaApiService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotosInteractorImpl implements PhotosInteractor {

    // Retrofit Service
    private NasaApiService mService;

    public PhotosInteractorImpl() {
        //Setup an interceptor to add the API key to every request
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder()
                                .addQueryParameter(NasaApiService.API_PARAM
                                        , NasaApiService.NASA_API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                }).build();
        //Setup our Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NasaApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(NasaApiService.class);
    }

    @Override
    public Call<FeedItem> requestPhoto(String date) {
        return mService.requestPhoto(date);
    }
}
