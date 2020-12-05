package com.example.duolingo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public JsonParser() {}

    public ArrayList<Lesson> loadLessons(String jsonString) {

        ArrayList<Lesson> lessons = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray lessonsArr = obj.getJSONArray("lessons");

            for (int i = 0; i < lessonsArr.length(); i++) {
                Lesson lesson = new Lesson();
                lesson.id = lessonsArr.getJSONObject(i).getInt("lesson_id");
                lesson.name = lessonsArr.getJSONObject(i).getString("name");
                JSONArray levelsArr = lessonsArr.getJSONObject(i).getJSONArray("levels");

                for(int j = 0; j < levelsArr.length(); j++) {
                    Level level = new Level();
                    level.id = levelsArr.getJSONObject(j).getInt("level_id");
                    level.type = levelsArr.getJSONObject(j).getString("type");
                    level.description = levelsArr.getJSONObject(j).getString("description");
                    level.correctAnswer = levelsArr.getJSONObject(j).getString("correct_answer");
                    JSONArray dataArr = levelsArr.getJSONObject(j).getJSONArray("data");

                    for(int k = 0; k < dataArr.length(); k++) {
                        level.data.add(dataArr.getString(k));
                    }
                    lesson.levels.add(level);
                }

                lessons.add(lesson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lessons;
    }
}
