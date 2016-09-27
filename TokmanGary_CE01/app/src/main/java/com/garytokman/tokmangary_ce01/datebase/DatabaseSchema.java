package com.garytokman.tokmangary_ce01.datebase;
// Gary Tokman
// MDF3 - 1610
// DatabaseSchema

public class DatabaseSchema {

    public static final class PersonTable {

        public static final String NAME = "person_table";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String AGE = "age";
        }
    }
}
