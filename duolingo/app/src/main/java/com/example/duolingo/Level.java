package com.example.duolingo;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {

    public int id;
    public String type;
    public String description;
    public String correctAnswer;
    public ArrayList<String> data = new ArrayList<>();
    public String soundFile;

    public Level(){}

    public Level(int id, String type, String description, String correctAnswer, ArrayList<String> data, String soundFile) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.data = data;
        this.soundFile = soundFile;
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
