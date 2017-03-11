package com.eslimaf.feedsample.data;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NasaApiServiceImpl implements NasaApiService {

    private NasaApi mNasaApi;

    public NasaApiServiceImpl() {
        //Setup an interceptor to add the API key to every request
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder()
                                .addQueryParameter(NasaApi.API_PARAM
                                        , NasaApi.NASA_API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                }).build();
        //Setup our Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NasaApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNasaApi = retrofit.create(NasaApi.class);
    }

    @Override
    public NasaApi getApi() {
        return mNasaApi;
    }
}
