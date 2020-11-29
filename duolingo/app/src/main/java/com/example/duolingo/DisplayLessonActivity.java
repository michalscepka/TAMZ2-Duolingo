package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplayLessonActivity extends AppCompatActivity {

    private TextView descriptionTextView;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Lesson lesson;
    private int currentLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        Intent intent = getIntent();
        if(intent !=null) {
            lesson = (Lesson) intent.getSerializableExtra("lesson");

            setLevel(lesson.levels.get(currentLevel));
        }
    }

    private void setLevel(Level level) {
        descriptionTextView.setText(level.description);
        img1.setImageResource(getImage(level, 0));
        img1.setTag(level.data.get(0));
        img2.setImageResource(getImage(level, 1));
        img2.setTag(level.data.get(1));
        img3.setImageResource(getImage(level, 2));
        img3.setTag(level.data.get(2));
        img4.setImageResource(getImage(level, 3));
        img4.setTag(level.data.get(3));
    }

    private int getImage(Level level, int index) {
        return getResources().getIdentifier(level.data.get(index), "drawable", getPackageName());
    }

    public void onImageClick(View view) {
        if(String.valueOf(view.getTag()).equals(lesson.levels.get(currentLevel).correctAnswer)) {
            Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
            if(!(++currentLevel >= lesson.levels.size())) {
                setLevel(lesson.levels.get(currentLevel));
            }
            else {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "Bad", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
