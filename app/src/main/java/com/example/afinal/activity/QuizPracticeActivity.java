package com.example.afinal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.Adapter.IQuizClicked;
import com.example.afinal.Adapter.QuizResultAdapter;
import com.example.afinal.DAO.QuizDAO;
import com.example.afinal.Model.Answer;
import com.example.afinal.Model.Quiz;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class QuizPracticeActivity extends AppCompatActivity {

    QuizDAO quizDAO;
    ArrayList<Quiz> quizzes;
    ArrayList<RadioButton> radioButtons;
    ImageView image_quiz_practice;
    RadioGroup radioGroup;
    TextView hint_practice, hint_explain_practice,quiz_content_practice,deadpoint_practice;
    QuizBottomSheet quizBottomSheet;
    Quiz currentQuiz;
    ArrayList<Answer> currentAnswers;
    int trueAnswser = -10;
    int previous_index = 1;
    int index = 1;
    BottomNavigationView bottomNavigationView;
    private IQuizClicked iQuizClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_quiz);
        setupElement();
        setupRadio();
        goQuiz();
    }

    private void setupElement() {
        quizDAO = new QuizDAO(this);
        int idCat = getIntent().getIntExtra("idCategory",0);
        String contentCategory = getIntent().getStringExtra("categoryContent");

        image_quiz_practice = findViewById(R.id.image_quiz_practice);
        deadpoint_practice = findViewById(R.id.deadpoint_practice_check);
        quizzes = quizDAO.getQuizByIdCategory(idCat);
        Log.d("idCategory","idCategory: " + idCat);
        Log.d("categoryContent","categoryContent: " + contentCategory);
        Log.d("QuizPractice","quizzes size: " + quizzes.size());
        hint_practice = findViewById(R.id.hint_practice);
        hint_explain_practice = findViewById(R.id.hint_explain_practice);
        quiz_content_practice = findViewById(R.id.quiz_content_practice);
        hint_practice.setVisibility(View.GONE);
        hint_explain_practice.setVisibility(View.GONE);
        radioButtons = new ArrayList<>();
        radioButtons.add(findViewById(R.id.answer1));
        radioButtons.add(findViewById(R.id.answer2));
        radioButtons.add(findViewById(R.id.answer3));
        radioButtons.add(findViewById(R.id.answer4));
        radioButtons.add(findViewById(R.id.answer5));

        iQuizClicked = new IQuizClicked() {
            @Override
            public void clickQuiz(int position) {
                if(position != index - 1){
                    previous_index = index;
                    index = position + 1;
                    goQuiz();
                }
                quizBottomSheet.dismiss();
            }
        };

        QuizResultAdapter adapter = new QuizResultAdapter(quizzes,iQuizClicked);
        quizBottomSheet = new QuizBottomSheet(adapter);


//        quizBottomSheet = new QuizBottomSheet(quizzes, new IQuizClicked() {
//            @Override
//            public void clickQuiz(int position) {
//                if(position != index - 1){
//                    previous_index = index;
//                    index = position + 1;
//                    goQuiz();
//                }
//                quizBottomSheet.dismiss();
//            }
//        });

        openQuizzed();
        quizBottomSheet.dismiss();
        ActionBar actionBar = getSupportActionBar();//action bar
        actionBar.setTitle(contentCategory);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bottomNavigationView = findViewById(R.id.bottom_navigation_practice);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void openQuizzed() {
        quizBottomSheet.show(getSupportFragmentManager(),quizBottomSheet.getTag());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.back:
                    if(index > 1){
                        previous_index = index;
                        index--;
                        goQuiz();
                        return true;
                    }
                    else
                        return false;
                case R.id.next:
                    if(index < quizzes.size()){
                        previous_index = index;
                        index++;
                        goQuiz();
                        return true;
                    }
                    else
                        return false;
                case R.id.current:
                    openQuizzed();
            }
            return false;
        }
    };


    private void setupRadio() {
        radioGroup = findViewById(R.id.quiz_practice_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
                if( radioGroup.indexOfChild(rbSelected) != -1){
                    if(radioGroup.indexOfChild(rbSelected) == trueAnswser){
                        hint_practice.setVisibility(View.VISIBLE);
                        currentQuiz.setResult(1);
                        hint_explain_practice.setVisibility(View.VISIBLE);
                        radioButtons.get(trueAnswser).setTextColor(Color.GREEN);
                    }
                    else{
                        currentQuiz.setResult(2);
                        hint_practice.setVisibility(View.GONE);
                        hint_explain_practice.setVisibility(View.GONE);
                        radioButtons.get(radioGroup.indexOfChild(rbSelected)).setTextColor(Color.RED);
                    }
                }
            }
        });
    }

    private void goQuiz() {
        uncheckAll();
        bottomNavigationView.getMenu().findItem(R.id.current).setTitle(String.valueOf(index +"/" + quizzes.size()) );
        currentQuiz = quizzes.get(index-1);
        currentAnswers = quizDAO.getAnswerByIdQuiz(currentQuiz.getId());
        //currentQuiz.setChooseAnswer(quizDAO.getChooseAnswer(currentQuiz.getId()));
        if(currentQuiz.isDeadPoint())
            deadpoint_practice.setVisibility(View.VISIBLE);
        else
            deadpoint_practice.setVisibility(View.GONE);
        String quizContent = "Câu " + index +": " + currentQuiz.getContent();
        quiz_content_practice.setText(quizContent);
        hint_explain_practice.setText(currentQuiz.getHint());
        image_quiz_practice.setVisibility(View.GONE);
        Log.d("idQuiz","idQuiz: " + currentQuiz.getId());
        Log.d("currentQuizImage","currentQuizImage: " + currentQuiz.getImage() );
        if(currentQuiz.getImage() != null ){
            int id = getResources().getIdentifier(currentQuiz.getImage(),"drawable",getPackageName()); //image
            Log.d("Image","xxxImage: " + currentQuiz.getImage() + " " + id);
            image_quiz_practice.setImageResource(id);
            image_quiz_practice.setVisibility(View.VISIBLE);
        }
        hint_practice.setVisibility(View.GONE);
        hint_explain_practice.setVisibility(View.GONE);
        setAnswsersRadioButtons();
    }

    private void setAnswsersRadioButtons() {
        int i = 0;
        trueAnswser = -10;
        for(; i < currentAnswers.size(); i++){
            radioButtons.get(i).setText(currentAnswers.get(i).getContent());
            radioButtons.get(i).setTextColor(Color.BLACK);
            if (currentAnswers.get(i).isTrue())
                trueAnswser = i ;
            radioButtons.get(i).setVisibility(View.VISIBLE);
            if(currentQuiz.getChooseAnswer() == currentAnswers.get(i).getId()){
                radioButtons.get(i).setChecked(true);
            }
        }
        for(;i<radioButtons.size();i++){
            radioButtons.get(i).setVisibility(View.GONE);
        };
    }

    private void uncheckAll() {
        for(int i = 0; i < 5; i++){
            if(radioButtons.get(i).isChecked()){
                quizDAO.updateQuizChooseAnswer(currentQuiz.getId(),currentAnswers.get(i).getId(),currentQuiz.getResult());
                currentQuiz.setChooseAnswer(currentAnswers.get(i).getId());
            }
        }
//        if(changeAnswer == true){
//
//            Log.d("err", "uncheckAll: " + (previous_index -1) + " currentQuiz: " + quizzes.indexOf(currentQuiz));
//            quizBottomSheet.notifyChanged(previous_index -1);
//        }
        radioGroup.clearCheck();
    }

    @Override
    protected void onDestroy() {
        for(int i = 0; i < radioButtons.size(); i++)
            if(radioButtons.get(i).isChecked()){
                quizDAO.updateQuizChooseAnswer(currentQuiz.getId(),currentAnswers.get(i).getId(),currentQuiz.getResult());
                //Log.d("change", "idQuiz: " + currentQuiz.getId() + " idAnswer: " + currentAnswers.get(i).getId());
            }
        super.onDestroy();
    }
}
