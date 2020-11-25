package com.example.duolingo;

import java.util.ArrayList;

public class Level {

    public int id;
    public int lessonId;
    public String type;
    public String correctAnswer;
    ArrayList<Data> data = new ArrayList<>();

    public Level(){}

    public Level(int id, int lessonId, String type, String correctAnswer) {
        this.id = id;
        this.lessonId = lessonId;
        this.type = type;
        this.correctAnswer = correctAnswer;
    }
}
