package com.garytokman.tokmangary_ce03.database;
// Gary Tokman
// MDF3 - 1610
// DatabaseSchema

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