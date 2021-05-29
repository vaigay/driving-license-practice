package com.example.afinal.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class QuizBottomSheet extends BottomSheetDialogFragment {
    private RecyclerView.Adapter adapter;


    public QuizBottomSheet(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_quiz,null);
        view.setNestedScrollingEnabled(false);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.quizzes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
//        adapter = new QuizResultAdapter(quizzes, new IQuizClicked() {
//            @Override
//            public void clickQuiz(int position) {
//                iQuizClicked.clickQuiz(position);
//            }
//        });


        recyclerView.setAdapter(adapter);
        return bottomSheetDialog;
    }

//    public void notifyChanged(int position){
//        adapter.notifyItemChanged(position);
//    }
}

