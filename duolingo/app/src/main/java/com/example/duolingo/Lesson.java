package com.example.duolingo;

import java.util.ArrayList;

public class Lesson {

    public int id;
    public String language;
    public String name;
    public String description;
    public int difficulty;
    public int score;
    public int isDone;
    public ArrayList<Level> levels = new ArrayList<>();

    public Lesson(){}

    public Lesson(int id, String language, String name, String description, int difficulty, int score, int isDone) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.score = score;
        this.isDone = isDone;
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lekce ").append(id).append("; ").append(description).append("; obtiznost ").append(difficulty).append("/5");
        if(isDone == 1)
            sb.append("; splněno");
        return sb.toString();
    }*/

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", score=" + score +
                ", isDone=" + isDone +
                ", levels=" + levels +
                '}';
    }
}
