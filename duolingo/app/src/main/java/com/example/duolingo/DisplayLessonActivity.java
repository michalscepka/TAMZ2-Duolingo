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

    private TextView descriptionTextView;
    private TextView difficultyTextView;
    private TextView languageTextView;
    private TextView textView;
    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        languageTextView = findViewById(R.id.languageTextView);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        if(intent !=null) {
            lesson = (Lesson) intent.getSerializableExtra("lesson");

            descriptionTextView.setText(lesson.description);
            difficultyTextView.setText(String.format("Obtížnost: %s/5", lesson.difficulty));
            languageTextView.setText(String.format("Jazyk: %s", lesson.language));

            StringBuilder sb = new StringBuilder();
            for(Level level : lesson.levels) {
                sb.append(level.id).append("; ").append(level.type).append("; ").append(level.correctAnswer).append("\n");
                for(String data : level.data) {
                    sb.append("\t").append(data).append("\n");
                }
            }

            textView.setText(sb.toString());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
