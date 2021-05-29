package com.example.afinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.Adapter.GridViewMainAdapter;
import com.example.afinal.R;


public class MainActivity extends AppCompatActivity {
    GridView gridview;


    String[] ten = {
            "THI SÁT HẠCH","HỌC LÝ THUYẾT","BIỂN BÁO ĐƯỜNG BỘ","MẸO THI KẾT QUẢ CAO","TRA CỨU LUẬT(NĐ 100/2019)","CÁC CÂU HAY SAI"

    };
    int[] hinh={
            R.drawable.cpt, R.drawable.book, R.drawable.road_sign, R.drawable.bulb,
            R.drawable.compliant, R.drawable.ds
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview=(GridView)findViewById(R.id.gridView);

        GridViewMainAdapter gv =new GridViewMainAdapter(this,ten,hinh);
        gridview.setAdapter(gv);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(MainActivity.this, ExamActivity.class);
                    startActivity(i);
                }
                if(position == 1){
                    Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                    startActivity(i);
                }
                if(position == 2){
                    Intent i = new Intent(MainActivity.this, RoadSignActivity.class);
                    startActivity(i);
                }
                if(position == 3){
                    Intent i = new Intent(MainActivity.this, TrickActivity.class);
                    startActivity(i);
                }
                if(position == 4){
                    Intent i = new Intent(MainActivity.this, LawActivity.class);
                    startActivity(i);
                }
                if(position == 5){
                    Intent i = new Intent(MainActivity.this, QuizWrongActivity.class);
                    startActivity(i);
                }
            }
        });


    }
}