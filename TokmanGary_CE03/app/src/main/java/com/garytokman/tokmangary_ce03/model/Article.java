package com.garytokman.tokmangary_ce03.model;
// Gary Tokman
// MDF3 - 1610
// Article


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
author	(string) - The author of the article.
description	(string) - A description or preface for the article.
title	(string) - The headline or title of the article.
url	(string) - The direct URL to the content page of the article (can be null).
urlToImage	(string) - The URL to a relevant image for the article.
*/

public class Article implements Parcelable {

    private String mAuthor;
    private String mDescription;
    private String mTitle;
    private String mUrl;
    private String mUrlToImage;

    public Article(JSONArray jsonArray) {
        try {
            int index = (int) Math.round(Math.random() * jsonArray.length());
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            mAuthor = jsonObject.getString("author");
            mDescription = jsonObject.getString("description");
            mTitle = jsonObject.getString("title");
            String url = jsonObject.getString("url");
            mUrl = url != null ? url: "https://techcrunch.com";
            mUrlToImage = jsonObject.getString("urlToImage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Article(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    @Override
    public String toString() {
        return mAuthor + " " + mTitle + " " + mDescription + " " + mUrl + " " + mUrlToImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mUrl);
        parcel.writeString(mUrlToImage);
    }

    public static final Creator<Article> CREATOR = new ClassLoaderCreator<Article>() {
        @Override
        public Article createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new Article(parcel);
        }

        @Override
        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int i) {
            return new Article[i];
        }
    };
}
