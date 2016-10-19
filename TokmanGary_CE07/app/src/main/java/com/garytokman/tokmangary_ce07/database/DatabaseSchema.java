package com.garytokman.tokmangary_ce07.database;
// Gary Tokman
// MDF3 - 1610
// DatabaseSchema

public class DatabaseSchema {

    public static final class CarTable {

        public static final String NAME = "car_table";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String CAR_MAKE = "car_model";
            public static final String CAR_MODEL = "car_make";
            public static final String YEAR = "year";
        }
    }
}