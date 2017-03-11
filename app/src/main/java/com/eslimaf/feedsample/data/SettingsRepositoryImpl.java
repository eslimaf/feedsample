package com.eslimaf.feedsample.data;


import android.content.SharedPreferences;

public class SettingsRepositoryImpl implements SettingsRepository {
    private static final String AUTO_REFRESH_PREF = "auto_refresh_pref";
    private SharedPreferences mSharedPreferences;

    public SettingsRepositoryImpl(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public boolean getAutoRefresh() {
        return mSharedPreferences.getBoolean(AUTO_REFRESH_PREF, false);
    }

    @Override
    public void setAutoRefresh(boolean enable) {
        mSharedPreferences.edit().putBoolean(AUTO_REFRESH_PREF, enable).apply();
    }
}
