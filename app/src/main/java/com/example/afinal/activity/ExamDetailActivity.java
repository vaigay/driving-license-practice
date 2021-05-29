package com.example.afinal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.Adapter.IQuizClicked;
import com.example.afinal.Adapter.QuizAdapter;
import com.example.afinal.Adapter.QuizResultAdapter;
import com.example.afinal.DAO.ExamDAO;
import com.example.afinal.DAO.QuizDAO;
import com.example.afinal.Model.Answer;
import com.example.afinal.Model.Exam;
import com.example.afinal.Model.Quiz;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ExamDetailActivity extends AppCompatActivity {

    QuizDAO quizDAO;
    ExamDAO examDAO;
    ArrayList<Quiz> quizzes;
    ArrayList<RadioButton> radioButtons;
    ImageView image_quiz_practice;
    RadioGroup radioGroup;
    TextView hint_practice, hint_explain_practice,quiz_content_practice,deadpoint_practice;
    QuizBottomSheet quizBottomSheet;
    Quiz currentQuiz;
    ArrayList<Answer> currentAnswers;
    int trueAnswser = -10;
    boolean changeAnswer = false;
    int previous_index = 1;
    int index = 1;
    BottomNavigationView bottomNavigationView;
    private IQuizClicked iQuizClicked;
    int[] chooseAnwsers;
    Exam exam;


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
        examDAO = new ExamDAO(this);

        chooseAnwsers = new int[25];
        int id = getIntent().getIntExtra("id",0);
        int result = getIntent().getIntExtra("result",0);
        String status = getIntent().getStringExtra("status");
        exam = new Exam(id,result,status);

        image_quiz_practice = findViewById(R.id.image_quiz_practice);
        deadpoint_practice = findViewById(R.id.deadpoint_practice_check);
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
        ActionBar actionBar = getSupportActionBar();

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

        RecyclerView.Adapter adapter;
        if(!exam.getStatus().equals("no")){                         // Exam has done
            actionBar.setTitle("Đề " + exam.getId());
            quizzes = quizDAO.getQuizzesHasDoneByIdExam(exam.getId());
            adapter = new QuizResultAdapter(quizzes,iQuizClicked);
            actionBar.setDisplayHomeAsUpEnabled(true);
            hint_practice.setVisibility(View.VISIBLE);
            hint_explain_practice.setVisibility(View.VISIBLE);
        }

        else {                                               // Do Exam
            actionBar.setTitle("Làm bài thi");
            quizzes = quizDAO.getQuizzesByIdExam(exam.getId());
            adapter = new QuizAdapter(quizzes,iQuizClicked);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        quizBottomSheet = new QuizBottomSheet(adapter);

        openQuizzed();
        quizBottomSheet.dismiss();

        bottomNavigationView = findViewById(R.id.bottom_navigation_practice);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void openQuizzed() {
        quizBottomSheet.show(getSupportFragmentManager(),quizBottomSheet.getTag());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.end_exam){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if(exam.getStatus().equals("no")){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Kết thúc!")
                    .setMessage("Bạn có chắc muốn kết thúc bài thi?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishExam();
                            Intent i=new Intent(ExamDetailActivity.this,ExamActivity.class);
                            startActivity(i);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(exam.getStatus().equals("no"))
            getMenuInflater().inflate(R.menu.exam_action_bar,menu);
        return super.onCreateOptionsMenu(menu);
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
        if(!exam.getStatus().equals("no")){
            for(int i = 0; i < radioButtons.size(); i++)
                radioButtons.get(i).setClickable(false);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
                    if( radioGroup.indexOfChild(rbSelected) != -1){
                        if(radioGroup.indexOfChild(rbSelected) == trueAnswser){
                            radioButtons.get(trueAnswser).setTextColor(Color.GREEN);
                        }
                        else{
                            radioButtons.get(radioGroup.indexOfChild(rbSelected)).setTextColor(Color.RED);
                        }
                    }
                }
            });
        }
        else{
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
                    if( radioGroup.indexOfChild(rbSelected) != -1){
                        if(radioGroup.indexOfChild(rbSelected) == trueAnswser){
                            currentQuiz.setResult(1);
                        }
                        else{
                            currentQuiz.setResult(2);
                        }
                        chooseAnwsers[index-1] = currentAnswers.get(radioGroup.indexOfChild(rbSelected)).getId();
                        currentQuiz.setChooseAnswer(chooseAnwsers[index-1]);
                    }
                }
            });
        }
    }

    private void goQuiz() {
        uncheckAll();
        bottomNavigationView.getMenu().findItem(R.id.current).setTitle(String.valueOf(index +"/" + quizzes.size()) );
        currentQuiz = quizzes.get(index-1);
        currentAnswers = quizDAO.getAnswerByIdQuiz(currentQuiz.getId());
        if(currentQuiz.isDeadPoint())
            deadpoint_practice.setVisibility(View.VISIBLE);
        else
            deadpoint_practice.setVisibility(View.GONE);
        String quizContent = "Câu " + index +": " + currentQuiz.getContent();
        quiz_content_practice.setText(quizContent);
        hint_explain_practice.setText(currentQuiz.getHint());
        image_quiz_practice.setVisibility(View.GONE);
        if(currentQuiz.getImage() != null){
            int id = getResources().getIdentifier(currentQuiz.getImage(),"drawable",getPackageName()); //image
            image_quiz_practice.setImageResource(id);
            image_quiz_practice.setVisibility(View.VISIBLE);
        }
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
        if(!exam.getStatus().equals("no")){
            for(int i = 0; i < 5; i++){
                if(radioButtons.get(i).isChecked()){
                    currentQuiz.setChooseAnswer(currentAnswers.get(i).getId());
                }
            }
        }
//        quizBottomSheet.notifyChanged(previous_index -1);
        radioGroup.clearCheck();
    }

    public void finishExam(){
        int result = 0;
        for(int i = 0; i < quizzes.size(); i++){
            if(quizzes.get(i).getResult() != 1 && quizzes.get(i).isDeadPoint())
                exam.setStatus("Trượt");
            if(quizzes.get(i).getResult() == 1)
                result += quizzes.get(i).getResult();
            quizDAO.updateResultQuizOfExam(exam.getId(),quizzes.get(i).getId(),quizzes.get(i).getResult(),chooseAnwsers[i]);
        }
        if(exam.getStatus().equals("no")){
            if(result >= 20)
                exam.setStatus("Đỗ");
            else
                exam.setStatus("Trượt");
        }
        exam.setResult(result);
        examDAO.updateResultExam(exam.getId(),exam.getResult(),exam.getStatus());
    }
}
