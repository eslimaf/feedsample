package com.eslimaf.feedsample.data;


public interface SettingsRepository {
    boolean getAutoRefresh();

    void setAutoRefresh(boolean enable);
}
