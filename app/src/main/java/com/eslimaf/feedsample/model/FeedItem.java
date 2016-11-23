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

package com.eslimaf.feedsample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeedItem implements Parcelable {
    @SerializedName("date")
    private String mDate;
    @SerializedName("explanation")
    private String mDescription;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("media_type")
    private String mMediaType;


    protected FeedItem(Parcel p) {
        mDate = p.readString();
        mDescription = p.readString();
        mUrl = p.readString();
        mMediaType = p.readString();
    }

    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {

        @Override
        public FeedItem createFromParcel(Parcel parcel) {
            return new FeedItem(parcel);
        }

        @Override
        public FeedItem[] newArray(int i) {
            return new FeedItem[i];
        }
    };

    public String getHumanDate() {
        return convertDateToHumanDate(mDate);
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getMediaType() {
        return mMediaType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDate);
        parcel.writeString(mDescription);
        parcel.writeString(mUrl);
        parcel.writeString(mMediaType);
    }

    private String convertDateToHumanDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat humanDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        try {
            Date humanDate = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(humanDate);
            return humanDateFormat.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
