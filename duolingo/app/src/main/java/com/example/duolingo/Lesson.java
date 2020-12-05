package com.example.duolingo;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable {

    public int id;
    public String name;
    public int score;
    public ArrayList<Level> levels = new ArrayList<>();

    public Lesson(){}

    public Lesson(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Lekce " + id + ": " + name + "; obtížnost: " + "; Score: " + score;
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
