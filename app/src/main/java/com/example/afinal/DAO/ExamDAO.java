package com.example.afinal.DAO;

import android.content.Context;
import android.database.Cursor;

import com.example.afinal.Model.Exam;

import java.util.ArrayList;
import java.util.Collections;

public class ExamDAO {

    DBConnect dbConnect;

    public ExamDAO(Context context) {
        dbConnect = new DBConnect(context,DBInfo.dbName,null,DBInfo.version);
    }

    public Exam insertExam(){
        int idExam = dbConnect.insertExam();
        Exam exam = new Exam(idExam,0,"no");
        return exam;
    }

    public void createNewExam(int idExam){
        ArrayList<Integer> idQuizzes = new ArrayList<>();
        String sql1 = "SELECT id_quiz FROM  category_quiz WHERE  id_category = 1 ORDER BY RANDOM() LIMIT 2";
        Cursor cursor1 = dbConnect.QueryData(sql1);
        while(cursor1.moveToNext()){
            int id = cursor1.getInt(cursor1.getColumnIndex("id_quiz"));
            idQuizzes.add(id);
        }
        cursor1.close();
        String sql2 = "SELECT DISTINCT id_quiz as id FROM  category_quiz WHERE id NOT IN (SELECT id_quiz FROM  category_quiz WHERE  id_category = 1)  ORDER BY RANDOM() LIMIT 23";
        Cursor cursor2 = dbConnect.QueryData(sql2);
        while(cursor2.moveToNext()){
            int id_quiz = cursor2.getInt(cursor2.getColumnIndex("id"));
            idQuizzes.add(id_quiz);
        }
        Collections.shuffle(idQuizzes);
        cursor2.close();
        for(int i = 0; i < idQuizzes.size(); i++){
            String sql3 = "INSERT INTO exam_quiz VALUES (" + idExam + ", " + idQuizzes.get(i) + ", 0, 0)";
            dbConnect.QueryTable(sql3);
        }
    }

    public ArrayList<Exam> getAllExam(){
        String sql = "SELECT * FROM exam";
        ArrayList<Exam> exams = new ArrayList<>();
        Cursor c = dbConnect.QueryData(sql);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            int result = c.getInt(c.getColumnIndex("result"));
            String status = c.getString(c.getColumnIndex("status"));
            Exam exam = new Exam(id,result,status);
            exams.add(exam);
        }
        return exams;
    }

    public void updateResultExam(int idExam,int result,String status){
        String sql = "UPDATE exam set result = " + result + ", status = '" + status + "' WHERE id = " + idExam;
        dbConnect.QueryTable(sql);
    }

}
