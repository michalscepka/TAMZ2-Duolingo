package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayLessonActivity extends AppCompatActivity {

    private DBHelper database;
    private TextView descriptionTextView;
    private TextView scoreText;
    private ArrayList<ImageView> imageViews;
    private ArrayList<TextView> textViews;
    private ImageButton playButton;
    private TextView answerText;
    private ArrayList<Button> answerButtons;
    private Button checkButton;
    private Button clearButton;
    private Lesson lesson;
    private int currentLevel = 0;
    private int score;
    private int userId;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        database = new DBHelper(this);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        scoreText = findViewById(R.id.textScore);
        scoreText.setText("");

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

        playButton = findViewById(R.id.buttonPlay);
        answerText = findViewById(R.id.textAnswer);
        answerButtons = new ArrayList<>();
        answerButtons.add(findViewById(R.id.buttonOption1));
        answerButtons.add(findViewById(R.id.buttonOption2));
        answerButtons.add(findViewById(R.id.buttonOption3));
        answerButtons.add(findViewById(R.id.buttonOption4));
        answerButtons.add(findViewById(R.id.buttonOption5));
        checkButton = findViewById(R.id.buttonCheck);
        clearButton = findViewById(R.id.buttonClear);

        for(ImageView item : imageViews) {
            item.setVisibility(View.GONE);
        }
        for(TextView item : textViews) {
            item.setVisibility(View.GONE);
        }
        playButton.setVisibility(View.GONE);
        answerText.setVisibility(View.GONE);
        for(Button item : answerButtons) {
            item.setVisibility(View.GONE);
        }
        checkButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        Intent intent = getIntent();
        if(intent !=null) {
            lesson = (Lesson) intent.getSerializableExtra("lesson");
            userId = intent.getIntExtra("userId", 1);

            setLevel(lesson.levels.get(currentLevel));
            setTitle("Lekce " + lesson.id + " - " + lesson.name);
        }
    }

    private void setLevel(Level level) {
        descriptionTextView.setText(level.description);

        if(level.type.equals("pickFrom4")) {
            for (int i = 0; i < imageViews.size(); i++) {
                imageViews.get(i).setImageResource(getImage(level, i));
                imageViews.get(i).setTag(level.data.get(i));
                textViews.get(i).setText(level.data.get(i));
            }

            //zakryt/odkryt potrebne prvky
            for(ImageView item : imageViews) {
                item.setVisibility(View.VISIBLE);
            }
            for(TextView item : textViews) {
                item.setVisibility(View.VISIBLE);
            }
            playButton.setVisibility(View.GONE);
            answerText.setVisibility(View.GONE);
            for(Button item : answerButtons) {
                item.setVisibility(View.GONE);
            }
            checkButton.setVisibility(View.GONE);
            clearButton.setVisibility(View.GONE);
        }
        else if(level.type.equals("sound")) {
            int soundId = getResources().getIdentifier(lesson.levels.get(currentLevel).soundFile, "raw", getPackageName());
            mediaPlayer = MediaPlayer.create(this, soundId);

            for(int i = 0; i < answerButtons.size(); i++) {
                answerButtons.get(i).setText(level.data.get(i));
            }

            //zakryt/odkryt potrebne prvky
            for(ImageView item : imageViews) {
                item.setVisibility(View.GONE);
            }
            for(TextView item : textViews) {
                item.setVisibility(View.GONE);
            }
            playButton.setVisibility(View.VISIBLE);
            answerText.setVisibility(View.VISIBLE);
            for(Button item : answerButtons) {
                item.setVisibility(View.VISIBLE);
            }
            checkButton.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
        }
    }

    private int getImage(Level level, int index) {
        return getResources().getIdentifier(level.data.get(index), "drawable", getPackageName());
    }

    //TODO zobrazovat pocet prictenych a odectenych bodu
    public void onImageClick(View view) {
        if(String.valueOf(view.getTag()).equals(lesson.levels.get(currentLevel).correctAnswer)) {
            setNextLevel();
        } else {
            score -= 5;
            //showScore();
        }
    }

    public void onButtonPlayClick(View view) {
        mediaPlayer.start();
    }

    public void onButtonOptionClick(View view) {
        Button button = (Button)view;
        String newAnswerText = String.format("%s%s ", answerText.getText(), button.getText());
        newAnswerText = newAnswerText.substring(0, 1).toUpperCase() + newAnswerText.substring(1);
        answerText.setText(newAnswerText);
    }

    public void onButtonCheckClick(View view) {
        String userAnswer;
        try {
            userAnswer = answerText.getText().subSequence(0, answerText.length()-1).toString().toLowerCase();
        } catch (Exception e) {
            return;
        }

        if (userAnswer.equals(lesson.levels.get(currentLevel).correctAnswer.toLowerCase())) {
            setNextLevel();
            //showScore();
        } else {
            score -= 5;
            //scoreText.setText("-5");
            //showScore();
        }
    }

    public void onButtonClearClick(View view) {
        answerText.setText("");
    }

    private void setNextLevel() {
        if (!(++currentLevel >= lesson.levels.size())) {
            setLevel(lesson.levels.get(currentLevel));
            score += 10;
            //scoreText.setText("+10");
            //showScore();
            answerText.setText("");
        } else {
            score += 10;
            //scoreText.setText("+10");
            //showScore();
            database.updateLesson(lesson.id, userId, score);
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    /*private void showScore() {

    }*/

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
