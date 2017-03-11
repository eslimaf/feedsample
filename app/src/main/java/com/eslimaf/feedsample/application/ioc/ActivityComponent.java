package com.eslimaf.feedsample.application.ioc;

import com.eslimaf.feedsample.feed.FeedActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = {AppComponent.class}
        , modules = {PresenterModule.class}
)
public interface ActivityComponent {
    void inject(FeedActivity feedActivity);
}
