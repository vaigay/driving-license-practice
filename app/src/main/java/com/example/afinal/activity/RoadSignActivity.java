package com.example.afinal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.Adapter.RoadSignAdapter;
import com.example.afinal.DAO.RoadSignDAO;
import com.example.afinal.Model.RoadSign;
import com.example.afinal.R;

import java.util.ArrayList;

public class RoadSignActivity extends AppCompatActivity {
    RoadSignDAO roadSignDAO;
    ListView lvRoadSign;

    ArrayList<RoadSign> roadSigns = new ArrayList<>();
    RoadSignAdapter roadSignAdapter;

    Button buttonBCD,buttonBCam,buttonBBNH,buttonBHL;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_road_sign);
        lvRoadSign = (ListView) findViewById(R.id.listviewBien);

        buttonBCD = findViewById(R.id.buttonBienCD);
        buttonBCam = findViewById(R.id.buttonBienC);
        buttonBBNH = findViewById(R.id.buttonBienBNH);
        buttonBHL = findViewById(R.id.buttonBienHL);
        roadSignDAO = new RoadSignDAO(this);

        roadSigns = roadSignDAO.getRoadSignByType(1);
        for(RoadSign r : roadSigns)
            Log.d("Road", "onCreate: " + r);
        roadSignAdapter = new RoadSignAdapter(RoadSignActivity.this,R.layout.road_sign,roadSigns);
        lvRoadSign.setAdapter(roadSignAdapter);
        roadSignAdapter.notifyDataSetChanged();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Biển báo đường bộ");


        buttonBCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roadSigns = roadSignDAO.getRoadSignByType(1);
                roadSignAdapter = new RoadSignAdapter(RoadSignActivity.this,R.layout.road_sign,roadSigns);
                lvRoadSign.setAdapter(roadSignAdapter);
                roadSignAdapter.notifyDataSetChanged();
            }
        });

        buttonBHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roadSigns = roadSignDAO.getRoadSignByType(2);
                roadSignAdapter = new RoadSignAdapter(RoadSignActivity.this,R.layout.road_sign,roadSigns);
                lvRoadSign.setAdapter(roadSignAdapter);
                roadSignAdapter.notifyDataSetChanged();
            }
        });

        buttonBCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roadSigns = roadSignDAO.getRoadSignByType(3);
                roadSignAdapter = new RoadSignAdapter(RoadSignActivity.this,R.layout.road_sign,roadSigns);
                lvRoadSign.setAdapter(roadSignAdapter);
                roadSignAdapter.notifyDataSetChanged();
            }
        });

        buttonBBNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roadSigns = roadSignDAO.getRoadSignByType(4);
                roadSignAdapter = new RoadSignAdapter(RoadSignActivity.this,R.layout.road_sign,roadSigns);
                lvRoadSign.setAdapter(roadSignAdapter);
                roadSignAdapter.notifyDataSetChanged();
            }
        });


    }
}
