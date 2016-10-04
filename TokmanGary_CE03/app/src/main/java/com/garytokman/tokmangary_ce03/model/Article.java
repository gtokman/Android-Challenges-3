package com.garytokman.tokmangary_ce03.model;
// Gary Tokman
// MDF3 - 1610
// Article

/*
author	(string) - The author of the article.
description	(string) - A description or preface for the article.
title	(string) - The headline or title of the article.
url	(string) - The direct URL to the content page of the article.
urlToImage	(string) - The URL to a relevant image for the article.
*/

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private String mAuthor;
    private String mDescription;
    private String mTitle;
    private String mUrl;
    private String mUrlToImage;

    public Article(JSONObject jsonObject) {
        try {
            mAuthor = jsonObject.getString("author");
            mDescription = jsonObject.getString("description");
            mTitle = jsonObject.getString("title");
            mUrl = jsonObject.getString("url");
            mUrlToImage = jsonObject.getString("urlToImage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
