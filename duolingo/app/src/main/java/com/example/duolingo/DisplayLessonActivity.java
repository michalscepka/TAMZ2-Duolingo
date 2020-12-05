package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayLessonActivity extends AppCompatActivity {

    DBHelper mydb;
    private TextView descriptionTextView;
    private ArrayList<ImageView> imageViews;
    private ArrayList<TextView> textViews;
    private Lesson lesson;
    private int currentLevel = 0;
    private int score;
    private int userId = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        mydb = new DBHelper(this);

        descriptionTextView = findViewById(R.id.descriptionTextView);

        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.img1));
        imageViews.add(findViewById(R.id.img2));
        imageViews.add(findViewById(R.id.img3));
        imageViews.add(findViewById(R.id.img4));

        textViews = new ArrayList<>();
        textViews.add(findViewById(R.id.imgText1));
        textViews.add(findViewById(R.id.imgText2));
        textViews.add(findViewById(R.id.imgText3));
        textViews.add(findViewById(R.id.imgText4));

        Intent intent = getIntent();
        if(intent !=null) {
            lesson = (Lesson) intent.getSerializableExtra("lesson");

            setLevel(lesson.levels.get(currentLevel));
        }
    }

    private void setLevel(Level level) {
        descriptionTextView.setText(level.description);

        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setImageResource(getImage(level, i));
            imageViews.get(i).setTag(level.data.get(i));
            textViews.get(i).setText(level.data.get(i));
        }
    }

    private int getImage(Level level, int index) {
        return getResources().getIdentifier(level.data.get(index), "drawable", getPackageName());
    }

    public void onImageClick(View view) {
        if(String.valueOf(view.getTag()).equals(lesson.levels.get(currentLevel).correctAnswer)) {
            Toast.makeText(this, "+10", Toast.LENGTH_SHORT).show();
            if(!(++currentLevel >= lesson.levels.size())) {
                setLevel(lesson.levels.get(currentLevel));
                score += 10;
            }
            else {
                score += 10;
                mydb.updateLesson(lesson.id, userId, score);
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "-5", Toast.LENGTH_SHORT).show();
            score -= 5;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
