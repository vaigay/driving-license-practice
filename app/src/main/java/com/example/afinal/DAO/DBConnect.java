package com.example.afinal.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.afinal.R;

import java.util.Scanner;

public class DBConnect extends SQLiteOpenHelper {
    Context context;



    public DBConnect(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Resources res = context.getResources();
        Scanner in = new Scanner(res.openRawResource(R.raw.db2));
        while(in.hasNextLine()){
            String sql = in.nextLine();
            db.execSQL(sql);
        }
    }

    public void QueryTable(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }

    public Cursor QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(sql,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        db.execSQL("DROP TABLE IF EXISTS answer");
        db.execSQL("DROP TABLE IF EXISTS category_quiz");
        db.execSQL("DROP TABLE IF EXISTS exam_quiz");
        db.execSQL("DROP TABLE IF EXISTS exam");
        db.execSQL("DROP TABLE IF EXISTS exam_quiz_wrong");
        db.execSQL("DROP TABLE IF EXISTS road_sign");
        db.execSQL("DROP TABLE IF EXISTS trick");
        db.execSQL("DROP TABLE IF EXISTS trick_detail");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        db.execSQL("DROP TABLE IF EXISTS answer");
        db.execSQL("DROP TABLE IF EXISTS category_quiz");
        db.execSQL("DROP TABLE IF EXISTS exam_quiz");
        db.execSQL("DROP TABLE IF EXISTS exam");
        db.execSQL("DROP TABLE IF EXISTS exam_quiz_wrong");
        db.execSQL("DROP TABLE IF EXISTS road_sign");
        db.execSQL("DROP TABLE IF EXISTS trick");
        db.execSQL("DROP TABLE IF EXISTS trick_detail");
        onCreate(db);
    }

    public int insertExam(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.putNull("id");
        cv.put("result",0);
        cv.put("status","no");
        return (int) db.insert("exam",null,cv);
    }

    public SQLiteDatabase getWriteDB(){
        return this.getWritableDatabase();
    }
}
