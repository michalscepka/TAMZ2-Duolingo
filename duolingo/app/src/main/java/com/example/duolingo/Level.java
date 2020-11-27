package com.example.duolingo;

import java.util.ArrayList;

public class Level {

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
