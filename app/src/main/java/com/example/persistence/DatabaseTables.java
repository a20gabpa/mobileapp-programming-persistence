package com.example.persistence;

public class DatabaseTables {
    static class Country  {
        static final String TABLE_NAME = "country";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_TYPE = "NAME";
        static final String COLUMN_NAME_ACRONYM = "acronym";
    }

    static final String SQL_CREATE_TABLE_COUNTRY =
            // Table --> "CREATE TABLE country(id INTEGER PRIMARY KEY, name TEXT, type TEXT, acronym TEXT)"
            "CREATE TABLE " + Country.TABLE_NAME + " (" +
                    Country.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    Country.COLUMN_NAME_TYPE + " TEXT," +
                    Country.COLUMN_NAME_ACRONYM + " TEXT)";

    static final String SQL_DELETE_TABLE_COUNTRY =
            // Table --> "DROP TABLE IF EXISTS country"
            "DROP TABLE IF EXISTS " + Country.TABLE_NAME;
}
