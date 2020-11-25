package com.example.duolingo;

public class Lesson {

    public int id;
    public String language;
    public String description;
    public int difficulty;
    public Integer isDone;

    public Lesson(int id, String language, String description, int difficulty, Integer isDone) {
        this(id, language, description, difficulty);
        this.isDone = isDone;
    }

    public Lesson(int id, String language, String description, int difficulty) {
        this.id = id;
        this.language = language;
        this.description = description;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lekce ").append(id).append("; ").append(description).append("; obtiznost ").append(difficulty).append("/5");
        if(isDone == 1)
            sb.append("; splněno");
        return sb.toString();
    }
}
