package com.example.afinal.Model;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable {
    private int id;
    private int result;
    private String status;

    public Exam(int id, int result, String status) {
        this.id = id;
        this.result = result;
        this.status = status;
    }

    public Exam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
