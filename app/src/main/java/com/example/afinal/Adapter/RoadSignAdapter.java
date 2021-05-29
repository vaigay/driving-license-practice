package com.example.afinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.Model.RoadSign;
import com.example.afinal.R;

import java.util.ArrayList;

public class RoadSignAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<RoadSign> roadSigns;

    public RoadSignAdapter(Context context, int layout, ArrayList<RoadSign> roadSigns) {
        this.context = context;
        this.layout = layout;
        this.roadSigns = roadSigns;
    }

    @Override
    public int getCount() {
        return roadSigns.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen , txtMota;
        ImageView image;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RoadSignAdapter.ViewHolder holder;
        if (view== null){
            holder = new  RoadSignAdapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtMota = (TextView) view.findViewById(R.id.textViewRoadSignDes);
            holder.txtTen = (TextView) view.findViewById(R.id.textViewRoadSignName);
            holder.image = (ImageView) view.findViewById(R.id.imageRoadSign);
            view.setTag(holder);
        }else {
            holder = ( RoadSignAdapter.ViewHolder) view.getTag();
        }
        RoadSign roadSign = roadSigns.get(i);
        holder.txtMota.setText(roadSign.getDescription());
        holder.txtTen.setText(roadSign.getName());
        int id = context.getResources().getIdentifier(roadSign.getImageURL(),"drawable",context.getPackageName()); //image
        holder.image.setImageResource(id);
        return view;
    }
}
