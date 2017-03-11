package com.eslimaf.feedsample.application.ioc;

import com.eslimaf.feedsample.data.NasaApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    NasaApiService getNasaApiService();
}