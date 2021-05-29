package com.example.afinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;

public class LawDetailActivity extends AppCompatActivity {

    TextView tvdesciption;
    TextView tvphuong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.law_click_item);


        tvdesciption=findViewById(R.id.tvdes);
        tvphuong=findViewById(R.id.phuong);


        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tra cứu luật");
        tvdesciption.setText(name);
        if ( name.equals("Hiệu lệnh,chỉ dẫn") )
        {
            tvphuong.setText(getResources().getString(R.string.HieuLenhChiDan));

        }
        else if(name.equals("Chuyển hướng,nhường đường"))
        {
            tvphuong.setText(getResources().getString(R.string.ChuyenHuongNhuongDuong));
        }
        else if(name.equals("Dừng xe,đỗ xe"))
        {
            tvphuong.setText(getResources().getString(R.string.DungXeDoXe));
        }
        else if(name.equals("Thiết bị ưu tiên,còi"))
        {
            tvphuong.setText(getResources().getString(R.string.ThietBiUuTien));
        }
        else if(name.equals("Tốc độ,khoảng cách an toàn"))
        {
            tvphuong.setText(getResources().getString(R.string.TocDoKhoangCach));
        }
        else if(name.equals("Vận chuyển người,hàng hóa"))
        {
            tvphuong.setText(getResources().getString(R.string.VanChuyen));
        }
        else if(name.equals("Trang thiết bị phương tiện"))
        {
            tvphuong.setText(getResources().getString(R.string.TrangThietBi));
        }
        else if(name.equals("Đường cấm,đường một chiều"))
        {
            tvphuong.setText(getResources().getString(R.string.DuongCam));
        }
        else if(name.equals("Nồng độ cồn,chất kích thích"))
        {
            tvphuong.setText(getResources().getString(R.string.NongDoCon));
        }
//        else (name.equals("Giấy tờ xe"))
        else
        {
            tvphuong.setText(getResources().getString(R.string.GiayPhep));
        }


    }


}

