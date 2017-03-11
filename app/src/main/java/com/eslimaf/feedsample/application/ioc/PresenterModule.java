package com.eslimaf.feedsample.application.ioc;

import com.eslimaf.feedsample.data.NasaApiService;
import com.eslimaf.feedsample.feed.model.PhotosInteractor;
import com.eslimaf.feedsample.feed.model.PhotosInteractorImpl;
import com.eslimaf.feedsample.feed.presenter.FeedPresenter;
import com.eslimaf.feedsample.feed.presenter.FeedPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @PerActivity
    @Provides
    public PhotosInteractor providesPhotoInteractor(NasaApiService apiService) {
        return new PhotosInteractorImpl(apiService);
    }

    @PerActivity
    @Provides
    public FeedPresenter providesFeedPresenter(PhotosInteractor photosInteractor) {
        return new FeedPresenterImpl(photosInteractor);
    }
}
