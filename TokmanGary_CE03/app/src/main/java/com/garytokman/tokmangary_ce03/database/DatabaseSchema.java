package com.garytokman.tokmangary_ce03.database;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE03


/*
author	(string) - The author of the article.
description	(string) - A description or preface for the article.
title	(string) - The headline or title of the article.
url	(string) - The direct URL to the content page of the article.
urlToImage	(string) - The URL to a relevant image for the article.
*/

public class DatabaseSchema {

    public static final class PersonTable {

        public static final String NAME = "article_table";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String AUTHOR = "author";
            public static final String DESC = "desc";
            public static final String TITLE = "title";
            public static final String URL = "url";
            public static final String URL_IMAGE = "url_image";
        }
    }
}