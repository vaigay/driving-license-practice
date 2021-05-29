package com.example.afinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.Adapter.GridViewLawAdapter;
import com.example.afinal.R;


public class LawActivity extends AppCompatActivity {
    GridView gridview;



    String[] ten = {
            "Hiệu lệnh,chỉ dẫn","Chuyển hướng,nhường đường","Dừng xe,đỗ xe","Thiết bị ưu tiên,còi",
            "Tốc độ,khoảng cách an toàn","Vận chuyển người,hàng hóa","Trang thiết bị phương tiện","Đường cấm,đường một chiều",
            "Nồng độ cồn,chất kích thích","Giấy tờ xe"

    };
    int[] hinh={
            R.drawable.tf1, R.drawable.traffic, R.drawable.parking, R.drawable.horn,
            R.drawable.td, R.drawable.shipment, R.drawable.sx, R.drawable.no,
            R.drawable.alc, R.drawable.id
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.law_main_activity);
        gridview=(GridView)findViewById(R.id.gridView_law);

        GridViewLawAdapter gv =new GridViewLawAdapter(this,ten,hinh);
        gridview.setAdapter(gv);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tra cứu luật");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                String selectedItem = ten[i];
                int selectedIMG=hinh[i];
                startActivity(new Intent(LawActivity.this, LawDetailActivity.class).putExtra("name",selectedItem).putExtra("hinh",selectedIMG));


            }
        });

    }
}
