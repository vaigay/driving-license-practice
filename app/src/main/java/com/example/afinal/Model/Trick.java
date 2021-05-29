package com.example.afinal.Model;

import java.util.List;

public class Trick {
    private int id;
    private String name;
    private List<TrickDetail> trickDetailList;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrickDetail> getTrickDetailList() {
        return trickDetailList;
    }

    public Trick(int id, String name, List<TrickDetail> trickDetailList) {
        this.id = id;
        this.name = name;
        this.trickDetailList = trickDetailList;
    }

    public void setTrickDetailList(List<TrickDetail> trickDetailList) {
        this.trickDetailList = trickDetailList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Trick() {
    }


}
