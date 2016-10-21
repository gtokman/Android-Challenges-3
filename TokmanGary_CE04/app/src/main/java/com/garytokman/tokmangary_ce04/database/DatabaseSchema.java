package com.garytokman.tokmangary_ce04.database;
// Gary Tokman
// MDF3 - 1610
// DatabaseSchema

public class DatabaseSchema {

    public static final class PhotoTable {

        public static final String NAME = "photo_table";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String LOCATION_DESC = "location_desc";
            public static final String PHOTO_NAME = "photo_name";
            public static final String PHOTO_URI = "photo_uri";
            public static final String LATITUDE = "lat_value";
            public static final String LONGITUDE = "long_value";
        }
    }

}
