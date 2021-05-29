package com.example.afinal.DAO;

import android.content.Context;
import android.database.Cursor;

import com.example.afinal.Model.RoadSign;

import java.util.ArrayList;

public class RoadSignDAO {

    private DBConnect db;

    public RoadSignDAO(Context context) {
        db = new DBConnect(context,DBInfo.dbName,null,DBInfo.version);
    }

    public ArrayList<RoadSign> getRoadSignByType(int t){
        String sql = "SELECT * FROM road_sign WHERE type = " + t;
        Cursor c = db.QueryData(sql);
        ArrayList<RoadSign> roadSigns = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String description = c.getString(c.getColumnIndex("description"));
            String imageURL = c.getString(c.getColumnIndex("imageURL"));
            int type = c.getInt(c.getColumnIndex("type"));
            RoadSign roadSign = new RoadSign(id,name,description,imageURL,type);
            roadSigns.add(roadSign);
        }
        return roadSigns;
    }
}
