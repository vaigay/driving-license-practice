package com.example.afinal.DAO;

import android.content.Context;
import android.database.Cursor;

import com.example.afinal.Model.Category;

import java.util.ArrayList;

public class CategoryDAO {
    private DBConnect dbConnect;
    public CategoryDAO(Context context){
        dbConnect = new DBConnect(context,DBInfo.dbName,null,DBInfo.version);
    }

    public ArrayList<Category > getAllCategory(){
        String sql = "SELECT * FROM category ORDER BY id";
        Cursor c = dbConnect.QueryData(sql);
        ArrayList< Category> categories = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String content = c.getString(c.getColumnIndex("content"));
            String image = c.getString(c.getColumnIndex("image"));
            categories.add(new Category(id,content,image));
        }
        c.close();
        return categories;
    }








}
