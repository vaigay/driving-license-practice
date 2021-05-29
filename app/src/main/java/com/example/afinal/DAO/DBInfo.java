package com.example.afinal.DAO;

import android.app.Application;

public class DBInfo extends Application {
    public static final String dbName = "QuizApp";
    public static final int version = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        DBConnect dbConnect = new DBConnect(this,dbName,null,version);
    }
}
