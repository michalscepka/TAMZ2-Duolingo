package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    JsonParser myJson;
    ListView itemListView;
    ArrayList<Lesson> lessons;
    int userId = 3; //TODO brat z persistentStorage

    //TODO vymyslet kam dat audio
    //TODO odebrat obtiznost

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        /*mydb.insertUzivatel("hrac");
        mydb.insertUzivatel("hrac2");
        mydb.insertLesson(1, 1, 50);
        mydb.insertLesson(2, 1, 1);
        mydb.insertLesson(1, 2, 10);
        mydb.insertUzivatel("hrac3");*/

        String output = "";
        try {
             output = new JsonTask().execute("https://json.extendsclass.com/bin/134cf168eaf5").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d("log", output);

        myJson = new JsonParser();
        lessons = myJson.loadLessons(output);
        /*for(Lesson item : lessons) {
            Log.d("log ", item.toString());
        }*/

        ArrayList<LessonDB> lessonDBS = mydb.getLessonsList(userId);
        for(LessonDB item : lessonDBS) {
            Log.d("result", item.toString());
        }

        //priradit score k lekcim
        for(int i = 0; i < lessons.size(); i++) {
            for(int j = 0; j < lessonDBS.size(); j++) {
                if(lessons.get(i).id == lessonDBS.get(j).lessonId) {
                    lessons.get(i).score = lessonDBS.get(j).score;
                }
            }
        }

        //ArrayAdapter<Lesson> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessons);
        LessonListAdapter adapter = new LessonListAdapter(getApplicationContext(), R.layout.list_lesson_layout, lessons);

        itemListView = findViewById(R.id.listView1);
        //itemListView.setAdapter(arrayAdapter);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener((parent, view, position, id) -> {
            Lesson selectedLesson = (Lesson) (itemListView.getItemAtPosition(position));

            Intent intent = new Intent(getApplicationContext(), DisplayLessonActivity.class);
            intent.putExtra("lesson", lessons.get(selectedLesson.id - 1));
            Log.d("log", "\n\nID " + (selectedLesson.id - 1));
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.my_profile) {
            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
