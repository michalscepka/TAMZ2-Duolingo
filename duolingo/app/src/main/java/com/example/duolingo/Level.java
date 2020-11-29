package com.example.duolingo;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {

    public int id;
    public String type;
    public String description;
    public String correctAnswer;
    ArrayList<String> data = new ArrayList<>();

    public Level(){}

    public Level(int id, String type, String description, String correctAnswer) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", data=" + data +
                '}';
    }
}
