package com.example.afinal.Model;

import java.util.List;

public class Quiz {
    private int id;
    private String content;
    private String image;
    private int chooseAnswer;
    private boolean isDeadPoint;
    private int result = 0;
    private String hint;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private List<Answer> answerList;

    public boolean isDeadPoint() {
        return isDeadPoint;
    }

    public void setDeadPoint(boolean deadPoint) {
        isDeadPoint = deadPoint;
    }

    public Quiz(int id, String content, String hint) {
        this.id = id;
        this.content = content;
        this.hint = hint;
    }

    public Quiz(int id, String content, int chooseAnswer, String hint) {
        this.id = id;
        this.content = content;
        this.chooseAnswer = chooseAnswer;
        this.hint = hint;
    }


    public Quiz() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Quiz(int id, String content, String image, int chooseAnswer, String hint, List<Answer> answerList) {
        this.id = id;
        this.image = image;
        this.content = content;
        this.chooseAnswer = chooseAnswer;
        this.hint = hint;
        this.answerList = answerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChooseAnswer() {
        return chooseAnswer;
    }

    public void setChooseAnswer(int chooseAnswer) {
        this.chooseAnswer = chooseAnswer;
    }


    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
