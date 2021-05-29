package com.example.afinal.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.DAO.QuizDAO;
import com.example.afinal.R;
import com.example.afinal.Adapter.CategoryAdapter;
import com.example.afinal.DAO.CategoryDAO;

import com.example.afinal.Model.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.CategoryClicked {
    CategoryDAO categoryDAO;
    QuizDAO quizDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_category);
        categoryDAO = new CategoryDAO(this);
        quizDAO = new QuizDAO(this);
        ArrayList<Category> categories = categoryDAO.getAllCategory();
        ArrayList<Integer>  quizzesHasDone = quizDAO.countQuizHasDone();
        ArrayList<Integer> quizzesDeadAndTotal = quizDAO.countTotalQuiz();
        Log.d("quizzesDeadAndTotal","abc:" + quizzesDeadAndTotal.size());
        RecyclerView recyclerView = findViewById(R.id.category_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter myAdapter = new CategoryAdapter(this,categories,quizzesHasDone,quizzesDeadAndTotal);
        recyclerView.setAdapter(myAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Học lý thuyết");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClickCategory(int idCategory, String categoryContent) {
        Intent intent = new Intent(CategoryActivity.this,QuizPracticeActivity.class);
        intent.putExtra("idCategory",idCategory);
        intent.putExtra("categoryContent",categoryContent);
        startActivity(intent);
    }
}
