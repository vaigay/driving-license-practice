package com.example.afinal.Model;

public class Category {
    private int id;
    private String content;
    private String image;

    public String getImage() {
        return image;
    }

    public Category(int id) {
    }

    public Category(int id, String content, String image) {
        this.id = id;
        this.content = content;
        this.image = image;
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
}
