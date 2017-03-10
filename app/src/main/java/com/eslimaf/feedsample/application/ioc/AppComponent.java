package com.eslimaf.feedsample.application.ioc;

import android.app.Activity;

import dagger.Component;

@Component(modules = {})
public interface AppComponent {
    void inject(Activity activity);
}