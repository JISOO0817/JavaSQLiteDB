package com.example.javasqlitedb.DB;

public class Constants {

    //DB 이름
    public static final String DB_NAME = "FOOD_DB";

    //DB 버전
    public static final int DB_VERSION = 1;

    //TABLE 이름
    public static final String TABLE_NAME = "FOOD_TABLE";

    //컬럼
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_PRICE = "PRICE";
    public static final String C_DESCRIPTION = "DESCRIPTION";
    public static final String C_ADD_STAMP = "ADD_STAMP";
    public static final String C_UPDATE_STAMP = "UPDATE_STAMP";

    //CREATE TABLE
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_PRICE + " TEXT,"
            + C_DESCRIPTION + " TEXT,"
            + C_ADD_STAMP + " TEXT,"
            + C_UPDATE_STAMP + " TEXT"
            + ");";



}
