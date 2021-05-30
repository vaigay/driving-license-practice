package com.example.afinal.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.DAO.ExamDAO;
import com.example.afinal.Model.Exam;

import java.util.List;

public class ExamAdapter extends  RecyclerView.Adapter<ExamAdapter.ViewHolder>{

    private List<Exam> examList;
    private IExamClicked iExamClicked;
    private ExamDAO examDAO;

    public ExamAdapter(Context context, List<Exam> examList, IExamClicked iExamClicked) {
        this.examList = examList;
        this.iExamClicked = iExamClicked;
        examDAO = new ExamDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        if(viewType == R.layout.exam_row)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_row,parent,false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_exam,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == examList.size()){
            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Exam exam = examDAO.createNewExam();
                    examList.add(exam);
                    notifyDataSetChanged();
                }
            });
            return;
        }
        Exam exam = examList.get(position);
        holder.itemView.setTag(exam.getId()-1);
        holder.exam_number.setText(String.valueOf("ĐỀ " + exam.getId()));
        holder.exam_status.setText(exam.getStatus());
        if(!exam.getStatus().equals("no")){
            if(exam.getStatus().equals("Đỗ"))
                holder.exam_status.setTextColor(Color.GREEN);
            else if(exam.getStatus().equals("Trượt"))
                holder.exam_status.setTextColor(Color.RED);
            holder.exam_result_number.setText((String.valueOf(exam.getResult())));
        }
        else{
            holder.exam_result_number.setVisibility(View.GONE);
            holder.exam_status.setText("Làm bài");
            holder.exam_status.setTypeface(null, Typeface.BOLD);
            holder.exam_status.setTextSize(20);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iExamClicked.clickedExam((int)v.getTag());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return (position == examList.size()) ? R.layout.create_exam : R.layout.exam_row;
    }

    @Override
    public int getItemCount() {
        return examList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView exam_number, exam_result_number, exam_status;
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exam_number = itemView.findViewById(R.id.exam_number);
            exam_result_number = itemView.findViewById(R.id.exam_result_number);
            exam_status = itemView.findViewById(R.id.exam_status);
            frameLayout = itemView.findViewById(R.id.create_exam);
        }
    }

}
