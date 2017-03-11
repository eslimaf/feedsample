package com.eslimaf.feedsample.application;

import android.app.Application;

import com.eslimaf.feedsample.application.ioc.AppComponent;
import com.eslimaf.feedsample.application.ioc.AppModule;
import com.eslimaf.feedsample.application.ioc.DaggerAppComponent;


public class FeedApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
