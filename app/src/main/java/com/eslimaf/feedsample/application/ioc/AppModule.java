package com.eslimaf.feedsample.application.ioc;


import android.content.Context;
import android.content.SharedPreferences;

import com.eslimaf.feedsample.application.FeedApplication;
import com.eslimaf.feedsample.data.NasaApiService;
import com.eslimaf.feedsample.data.NasaApiServiceImpl;
import com.eslimaf.feedsample.data.SettingsRepository;
import com.eslimaf.feedsample.data.SettingsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private static final String SHARED_PREFS_NAME = "feed_app_prefs";
    private FeedApplication mFeedApplication;

    public AppModule(FeedApplication feedApplication) {
        mFeedApplication = feedApplication;
    }

    @Singleton
    @Provides
    public NasaApiService providesNasaApiService() {
        return new NasaApiServiceImpl();
    }

    @Singleton
    @Provides
    public SettingsRepository providesSettingsRepository() {
        SharedPreferences sharedPreferences = mFeedApplication
                .getSharedPreferences(SHARED_PREFS_NAME
                        , Context.MODE_PRIVATE);
        return new SettingsRepositoryImpl(sharedPreferences);
    }
}
