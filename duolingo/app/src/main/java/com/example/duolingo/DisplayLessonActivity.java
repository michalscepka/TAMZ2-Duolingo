package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayLessonActivity extends AppCompatActivity {

    private DBHelper mydb;
    private JsonHelper myJson;
    private TextView descriptionTextView;
    private TextView difficultyTextView;
    private TextView languageTextView;
    private TextView textView;

    //TODO prepsat tak at si ten list muzu posilat mezi aktivitami
    private ArrayList<Lesson> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        languageTextView = findViewById(R.id.languageTextView);
        textView = findViewById(R.id.textView);

        myJson = new JsonHelper();
        lessons = myJson.loadLessons(getAssets());
        for(Lesson item : lessons) {
            Log.d("log", item.toString());
        }

        Intent intent = getIntent();
        if(intent !=null) {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z hlavni aktivity
            int lessonId = intent.getIntExtra("id", 0);
            if (lessonId > 0) {
                descriptionTextView.setText(lessons.get(lessonId-1).description);
                difficultyTextView.setText(String.format("Obtížnost: %s/5", lessons.get(lessonId-1).difficulty));
                languageTextView.setText(String.format("Jazyk: %s", lessons.get(lessonId-1).language));

                StringBuilder sb = new StringBuilder();
                for(Level level : lessons.get(lessonId-1).levels) {
                    sb.append(level.id).append("; ").append(level.type).append("; ").append(level.correctAnswer).append("\n");
                    for(String data : level.data) {
                        sb.append("\t").append(data).append("\n");
                    }
                }

                textView.setText(sb.toString());
            }
        }

        /*mydb = new DBHelper(this);
        Intent intent = getIntent();
        if(intent !=null) {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z hlavni aktivity
            int lessonId = intent.getIntExtra("id", 0);
            if (lessonId > 0) {
                getDataFromDB(lessonId);
            }
        }*/
    }

    /*private void getDataFromDB(int lessonId) {
        //z db vytahnu zaznam pod hledanym ID
        Cursor rs = mydb.getLessonData(lessonId);
        rs.moveToFirst();

        //z DB vytahnu data
        lesson.language = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_LANGUAGE));
        lesson.description = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_DESCRIPTION));
        lesson.difficulty = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_DIFFICULTY));
        lesson.isDone = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_IS_DONE));

        do {
            Level level = new Level();
            level.id = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_LEVEL_ID));
            //level.lessonId = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_LESSON_ID));
            level.type = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_TYPE));
            level.correctAnswer = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_CORRECT_ANSWER));
            lesson.levels.add(level);
        } while (rs.moveToNext());

        if (!rs.isClosed()) {
            rs.close();
        }

        for(int i = 0; i < lesson.levels.size(); i++) {
            Cursor c = mydb.getLevelData(lessonId, i+1);
            c.moveToFirst();

            Log.d("level ", String.valueOf(i));

            do {
                Data data = new Data();
                data.id = c.getInt(c.getColumnIndex(DBHelper.ITEM_COLUMN_DATA_ID));
                data.level_id = c.getInt(c.getColumnIndex(DBHelper.ITEM_COLUMN_LEVEL_ID));
                data.source = c.getString(c.getColumnIndex(DBHelper.ITEM_COLUMN_SOURCE));
                data.name = c.getString(c.getColumnIndex(DBHelper.ITEM_COLUMN_NAME));
                //lesson.levels.get(i).data.add(data);
            } while (c.moveToNext());
            c.close();
        }

        descriptionTextView.setText(lesson.description);
        difficultyTextView.setText(String.format("Obtížnost: %s/5", lesson.difficulty));
        languageTextView.setText(String.format("Jazyk: %s", lesson.language));

        StringBuilder sb = new StringBuilder();
        for(Level level : lesson.levels) {
            sb.append(level.id).append(" ").append(level.type).append(" ").append(level.correctAnswer).append('\n');

        }

        textView.setText(sb.toString());
    }*/

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
