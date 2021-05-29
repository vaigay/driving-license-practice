package com.example.afinal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.DAO.QuizDAO;
import com.example.afinal.Model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private ArrayList<Category> categories;
    private ArrayList<Integer> quizzesHasDone;
    private ArrayList<Integer> quizzesTotal;
    Context context;
    CategoryClicked activity;
    QuizDAO quizDAO;

    public CategoryAdapter(Context context, ArrayList<Category > listCategory, ArrayList<Integer> quizzesHasDone,ArrayList<Integer> quizzesTotalx){
        this.context = context;
        activity = (CategoryClicked) context;
        categories = listCategory;
        quizDAO = new QuizDAO(context);
        this.quizzesHasDone = quizzesHasDone;
        this.quizzesTotal = quizzesTotalx;
        Log.d("quizzesTotal", "CategoryAdapter: " + quizzesTotal.size());
        Log.d("listCategory", "CategoryAdapter: " + categories.size());
    }

    public interface CategoryClicked{
        void onClickCategory(int index,String categoryContent);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category c = categories.get(position); //get Category
        holder.itemView.setTag(c);// set Tag

        holder.content.setText(c.getContent());//content

        int countDead = quizDAO.countDeadQuizByIdCategory(c.getId());
        if(countDead != 0){
            String deadQuiz = "(" + countDead + " câu điểm liệt)";
            holder.deadpoint.setText(deadQuiz);// total deadpoint
        }
        else{
            holder.deadpoint.setVisibility(View.INVISIBLE);
        }

        int totalQuiz = quizzesTotal.get(position);
        String strTotalQuiz = totalQuiz + " câu hỏi ";
        holder.total_quiz_practice.setText(strTotalQuiz);// total quiz

        int quizDone = quizzesHasDone.get(position);// done progress
        //int quizDone = quizDAO.countQuizHasDoneByIdCategory(c.getId());
//        holder.deadpoint.setText(String.valueOf(quizDone));
        holder.progressBar.setProgress(quizDone);
        holder.progressBar.setMax(totalQuiz);

        String value = quizDone + "/" + totalQuiz;//set value progressbar
        holder.quiz_value_progressbar.setText(value);

        int id = context.getResources().getIdentifier(c.getImage(),"drawable",context.getPackageName()); //image
        holder.image.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return quizzesTotal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView content, deadpoint, total_quiz_practice, quiz_value_progressbar;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_image);
            content = itemView.findViewById(R.id.category_content);
            deadpoint = itemView.findViewById(R.id.deadpoint);
            progressBar = itemView.findViewById(R.id.progress_practice);
            total_quiz_practice = itemView.findViewById(R.id.total_quiz_practice);
            quiz_value_progressbar = itemView.findViewById(R.id.quiz_value_progressbar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category c = (Category)v.getTag();
                    int id = c.getId();
                    String categoryContent = c.getContent();
                    activity.onClickCategory(id,categoryContent);
                }
            });
        }
    }
}
