package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayLessonActivity extends AppCompatActivity {

    private DBHelper mydb;
    private TextView descriptionTextView;
    private TextView difficultyTextView;
    private TextView languageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lesson);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        languageTextView = findViewById(R.id.languageTextView);

        mydb = new DBHelper(this);
        Intent intent = getIntent();
        if(intent !=null) {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z hlavni aktivity
            int lessonId = intent.getIntExtra("id", 0);
            if (lessonId > 0) {
                //z db vytahnu zaznam pod hledanym ID a ulozim do lessonId
                Cursor rs = mydb.getLessonData(lessonId);
                rs.moveToFirst();

                //z DB vytahnu data
                String languageDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_LANGUAGE));
                String descriptionDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_DESCRIPTION));
                String difficultyDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_DIFFICULTY));
                String isDone = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_IS_DONE));

                if (!rs.isClosed()) {
                    rs.close();
                }

                descriptionTextView.setText(descriptionDB + "; is_done: " + isDone);
                difficultyTextView.setText(String.format("Obtížnost: %s/5", difficultyDB));
                languageTextView.setText(String.format("Jazyk: %s", languageDB));
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
