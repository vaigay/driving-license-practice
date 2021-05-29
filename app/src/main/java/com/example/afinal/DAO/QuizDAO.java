package com.example.afinal.DAO;

import android.content.Context;
import android.database.Cursor;

import com.example.afinal.Model.Answer;
import com.example.afinal.Model.Quiz;

import java.util.ArrayList;

public class QuizDAO {
    DBConnect dbConnect;

    public QuizDAO(Context context) {
        dbConnect = new DBConnect(context,DBInfo.dbName,null,DBInfo.version);
    }

    public ArrayList<Quiz> getQuizByIdCategory(int idCat){
        String sql = "SELECT quiz.id as idq, quiz.image, quiz.content,quiz.chooseAnswer, quiz.hint, quiz.result FROM quiz, category_quiz " +
                "WHERE category_quiz.id_category = " + idCat + " AND quiz.id = category_quiz.id_quiz";
        Cursor c = dbConnect.QueryData(sql);
        ArrayList<Quiz> quizzes = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("idq"));
            String content = c.getString(c.getColumnIndex("content"));
            String image = c.getString(c.getColumnIndex("image"));
            //int id_category = c.getInt(c.getColumnIndex("id_category"));
            int chooseAnswer = c.getInt(c.getColumnIndex("chooseAnswer"));
            String hint = c.getString(c.getColumnIndex("hint"));
            int result = c.getInt(c.getColumnIndex("result"));
            Quiz q = new Quiz(id,content,hint);
            q.setResult(result);
            String sql2 = "SELECT 1 FROM category_quiz WHERE id_category = 1 AND id_quiz = " + id;
            Cursor tmp = dbConnect.QueryData(sql2);
//            Integer check = c.getInt(c.getColumnIndex("check"));
            if(tmp.moveToNext())
                q.setDeadPoint(true);
            else
                q.setDeadPoint(false);
            q.setImage(image);
            q.setChooseAnswer(chooseAnswer);
            quizzes.add(q);
        }
        c.close();
        return quizzes;
    }

    public ArrayList<Answer> getAnswerByIdQuiz(int idQuiz){
        String sql = "SELECT * FROM answer WHERE idQuiz = " + idQuiz;
        ArrayList<Answer> answers = new ArrayList<>();
        Cursor c = dbConnect.QueryData(sql);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String content = c.getString(c.getColumnIndex("content"));
            boolean check = c.getInt(c.getColumnIndex("isTrue")) > 0;
            Answer answer = new Answer(id,check,content);
            answers.add(answer);
        }
        return answers;
    }



    public void updateQuizChooseAnswer(int idQuiz,int idAnswer, int result){
        String sql = "UPDATE quiz SET chooseAnswer = " + idAnswer + ", result = "+ result + " WHERE id = " + idQuiz;
        dbConnect.QueryTable(sql);
    };


    public int countDeadQuizByIdCategory(int idCategory){
        String sql = "SELECT COUNT(id_quiz) as countDead FROM category_quiz WHERE id_category = " + idCategory + " AND id_quiz in (SELECT id_quiz FROM category_quiz WHERE id_category =  1) ";
        Cursor c = dbConnect.QueryData(sql);
        if(c.moveToNext()){
            return c.getInt(c.getColumnIndex("countDead"));
        }
        return 0;
    }


    public ArrayList<Integer> countQuizHasDone(){
        String sql = "SELECT id_quiz, SUM(CASE WHEN chooseAnswer > 0  THEN 1 ELSE 0 END) as countQuiz FROM category_quiz, quiz WHERE   quiz.id = category_quiz.id_quiz   GROUP BY category_quiz.id_category ORDER BY category_quiz.id_category";
        Cursor c = dbConnect.QueryData(sql);
        ArrayList<Integer> res = new ArrayList<>();
        while(c.moveToNext()){
            res.add(c.getInt(c.getColumnIndex("countQuiz")));
        }
        return res;
    }

    public ArrayList<Integer> countTotalQuiz(){
        String sql = "SELECT id_quiz, COUNT(id_quiz) as countTotal FROM category_quiz " +
                "GROUP BY category_quiz.id_category ORDER BY category_quiz.id_category";
        Cursor c = dbConnect.QueryData(sql);
        ArrayList<Integer> res = new ArrayList<>();
        while(c.moveToNext()){
            int total = c.getInt(c.getColumnIndex("countTotal"));
            res.add(total);
        }
        return res;
    }

    public ArrayList<Quiz> getQuizzesByIdExam(int idExam){
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT quiz.id , quiz.image, quiz.content FROM quiz, exam_quiz WHERE exam_quiz.id_exam = " + idExam + " AND exam_quiz.id_quiz = quiz.id";
        Cursor c = dbConnect.QueryData(sql);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String content = c.getString(c.getColumnIndex("content"));
            String image = c.getString(c.getColumnIndex("image"));
            Quiz q = new Quiz();
            String sql2 = "SELECT 1 FROM category_quiz WHERE id_category = 1 AND id_quiz = " + id;
            Cursor tmp = dbConnect.QueryData(sql2);
            if(tmp.moveToNext())
                q.setDeadPoint(true);
            else
                q.setDeadPoint(false);
            if(image != null && !image.equals("") )
                q.setImage(image);
            q.setId(id);
            q.setContent(content);
            quizzes.add(q);
        }
        return quizzes;
    }

    public ArrayList<Quiz> getQuizzesHasDoneByIdExam(int idExam){
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT quiz.id , quiz.image, exam_quiz.result, quiz.content,exam_quiz.chooseAnswer as chooseAnswer, quiz.hint FROM quiz, exam_quiz WHERE exam_quiz.id_exam = " + idExam + " AND exam_quiz.id_quiz = quiz.id";
        Cursor c = dbConnect.QueryData(sql);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String content = c.getString(c.getColumnIndex("content"));
            String image = c.getString(c.getColumnIndex("image"));
            int chooseAnswer = c.getInt(c.getColumnIndex("chooseAnswer"));
            String hint = c.getString(c.getColumnIndex("hint"));
            int result = c.getInt(c.getColumnIndex("result"));
            Quiz q = new Quiz();
            String sql2 = "SELECT 1 FROM category_quiz WHERE id_category = 1 AND id_quiz = " + id;
            Cursor tmp = dbConnect.QueryData(sql2);
            q.setChooseAnswer(chooseAnswer);
            if(tmp.moveToNext())
                q.setDeadPoint(true);
            else
                q.setDeadPoint(false);
            if(image != null && !image.equals("") )
                q.setImage(image);
            q.setId(id);
            q.setContent(content);
            q.setHint(hint);
            q.setResult(result);
            quizzes.add(q);
        }
        return quizzes;
    }

    public void updateResultQuizOfExam(int idExam,int idQuiz,int result,int chooseAnswer){
        String sql = "UPDATE exam_quiz set result = " + result + ", chooseAnswer = " + chooseAnswer +" WHERE id_exam = " + idExam + " AND id_quiz = " + idQuiz;
        dbConnect.QueryTable(sql);
        if(result == 1){
            String sql3 = "DELETE FROM exam_quiz_wrong WHERE exam_quiz_wrong.id_quiz = " + idQuiz;
            dbConnect.QueryTable(sql3);
        }
        else{
            String sql2 = "INSERT OR IGNORE INTO exam_quiz_wrong VALUES (" + idQuiz + ", 0, 0 )";
            dbConnect.QueryTable(sql2);
        }
    }

    public ArrayList<Quiz> getAllExamQuizWrong(){
        String sql = "SELECT quiz.id, quiz.image, quiz.content, quiz.hint, exam_quiz_wrong.result as result, exam_quiz_wrong.chooseAnswer as chooseAnswer FROM quiz, exam_quiz_wrong WHERE quiz.id = exam_quiz_wrong.id_quiz ";
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor c = dbConnect.QueryData(sql);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String content = c.getString(c.getColumnIndex("content"));
            String image = c.getString(c.getColumnIndex("image"));
            int chooseAnswer = c.getInt(c.getColumnIndex("chooseAnswer"));
            String hint = c.getString(c.getColumnIndex("hint"));
            int result = c.getInt(c.getColumnIndex("result"));
            Quiz q = new Quiz(id,content,hint);
            q.setResult(result);
            String sql2 = "SELECT 1 FROM category_quiz WHERE id_category = 1 AND id_quiz = " + id;
            Cursor tmp = dbConnect.QueryData(sql2);
            if(tmp.moveToNext())
                q.setDeadPoint(true);
            else
                q.setDeadPoint(false);
            q.setImage(image);
            q.setChooseAnswer(chooseAnswer);
            quizzes.add(q);
        }
        return quizzes;
    }

    public void updateQuizWrongChooseAnswer(int idQuiz,int idAnswer, int result){
        String sql = "UPDATE exam_quiz_wrong SET chooseAnswer = " + idAnswer + ", result = "+ result + " WHERE id_quiz = " + idQuiz;
        dbConnect.QueryTable(sql);
    };


}
