package com.example.afinal.Model;

public class RoadSign {
    private int id;
    private String name;
    private String description;
    private String imageURL;
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

    @Override
    public String toString() {
        return "RoadSign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", type=" + type +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RoadSign() {
    }

    public RoadSign(int id, String name, String description, String imageURL, int type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.type = type;
    }
}
