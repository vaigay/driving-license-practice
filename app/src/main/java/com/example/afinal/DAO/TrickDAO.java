package com.example.afinal.DAO;

import android.content.Context;
import android.database.Cursor;

import com.example.afinal.Model.Trick;
import com.example.afinal.Model.TrickDetail;

import java.util.ArrayList;
import java.util.List;

public class TrickDAO {
    private DBConnect db;

    public TrickDAO(Context context){
        db = new DBConnect(context,DBInfo.dbName,null,DBInfo.version);
    }

    public List<Trick> getTrickByType(int t){
        String sql = "SELECT * FROM trick WHERE type = " + t;
        Cursor c = db.QueryData(sql);
        List<Trick> tricks = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            List<TrickDetail> trickDetails = new ArrayList<>();
            String sql2 = "SELECT * FROM trick_detail WHERE id_trick = " + id;
            Cursor c2 = db.QueryData(sql2);
            while(c2.moveToNext()){
                String description = c2.getString(c2.getColumnIndex("description"));
                trickDetails.add(new TrickDetail(description));
            }
            Trick trick = new Trick(id,name,trickDetails);
            tricks.add(trick);
        }
        return tricks;
    }
}
