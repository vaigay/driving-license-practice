package com.example.afinal.Model;

public class Answer {
    private int id;
    private int idQuiz;
    private boolean isTrue;
    private String content;

    public Answer() {
    }

    public Answer(int id, int idQuiz, boolean isTrue, String content) {
        this.id = id;
        this.idQuiz = idQuiz;
        this.isTrue = isTrue;
        this.content = content;
    }

    public Answer(int id, boolean isTrue, String content) {
        this.id = id;
        this.isTrue = isTrue;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

