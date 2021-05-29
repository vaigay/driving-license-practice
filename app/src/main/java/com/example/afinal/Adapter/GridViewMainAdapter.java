package com.example.afinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.R;

public class GridViewMainAdapter extends BaseAdapter {
    private Context context;
    private String[] tenicon;
    private int[] hinhicon;

    public GridViewMainAdapter(Context context, String[] tenicon, int[] hinhicon) {
        this.context = context;
        this.tenicon = tenicon;
        this.hinhicon = hinhicon;
    }

    @Override
    public int getCount() {
        return tenicon.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lo= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=lo.inflate(R.layout.gridview_row_main,null);
        TextView tv=(TextView)convertView.findViewById(R.id.textview);
        ImageView iv=(ImageView) convertView.findViewById(R.id.imageView);

        tv.setText(tenicon[position]);
        iv.setImageResource(hinhicon[position]);

        return convertView;
    }}
