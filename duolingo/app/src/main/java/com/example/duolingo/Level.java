package com.example.duolingo;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {

    public int id;
    public String type;
    public String correctAnswer;
    ArrayList<String> data = new ArrayList<>();

    public Level(){}

    public Level(int id, String type, String correctAnswer) {
        this.id = id;
        this.type = type;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", data=" + data +
                '}';
    }
}
