package com.example.duolingo;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable {

    public int id;
    public String language;
    public String name;
    public int difficulty;
    public int score;
    public int isDone;
    public ArrayList<Level> levels = new ArrayList<>();

    public Lesson(){}

    public Lesson(int id, String language, String name, int difficulty, int score, int isDone) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lekce ").append(id).append("; obtiznost ").append(difficulty).append("/5");
        if(isDone == 1)
            sb.append("; splněno");
        return sb.toString();
    }

    /*@Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", score=" + score +
                ", isDone=" + isDone +
                ", levels=" + levels +
                '}';
    }*/
}
