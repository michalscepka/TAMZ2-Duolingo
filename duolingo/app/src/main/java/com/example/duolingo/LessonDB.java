package com.example.duolingo;

public class LessonDB {

    public int lessonId;
    public int score;

    public LessonDB(int lessonId, int score) {
        this.lessonId = lessonId;
        this.score = score;
    }

    @Override
    public String toString() {
        return "LessonDB{" +
                "lessonId=" + lessonId +
                ", score=" + score +
                '}';
    }
}
