package com.example.duolingo;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonParser {

    //TODO nacitat json z webu

    Context context;

    public JsonParser(Context context) {
        this.context = context;
    }

    public ArrayList<Lesson> loadLessons() {

        ArrayList<Lesson> lessons = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        InputStream input;
        try {
            input = assetManager.open("data.json");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String jsonString = new String(buffer);

            JSONObject obj = new JSONObject(jsonString);
            JSONArray lessonsArr = obj.getJSONArray("lessons");
            for (int i = 0; i < lessonsArr.length(); i++) {
                Lesson lesson = new Lesson();
                lesson.id = lessonsArr.getJSONObject(i).getInt("lesson_id");
                lesson.language = lessonsArr.getJSONObject(i).getString("language");
                lesson.name = lessonsArr.getJSONObject(i).getString("name");
                lesson.description = lessonsArr.getJSONObject(i).getString("description");
                lesson.difficulty = lessonsArr.getJSONObject(i).getInt("difficulty");
                JSONArray levelsArr = lessonsArr.getJSONObject(i).getJSONArray("levels");

                for(int j = 0; j < levelsArr.length(); j++) {
                    Level level = new Level();
                    level.id = levelsArr.getJSONObject(j).getInt("level_id");
                    level.type = levelsArr.getJSONObject(j).getString("type");
                    level.correctAnswer = levelsArr.getJSONObject(j).getString("correct_answer");
                    JSONArray dataArr = levelsArr.getJSONObject(j).getJSONArray("data");

                    for(int k = 0; k < dataArr.length(); k++) {
                        level.data.add(dataArr.getString(k));
                    }
                    lesson.levels.add(level);
                }

                lessons.add(lesson);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return lessons;
    }
}
