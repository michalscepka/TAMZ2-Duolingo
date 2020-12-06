package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private ListView itemListView;
    private ArrayList<Lesson> lessons;
    private SharedPreferences sharedPref;
    private UserDB activeProfile;

    //TODO vymyslet kam dat audio
    //TODO mozna udelat lekci s akcelerometrem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHelper(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //default uzivatel
        Cursor c = database.getUserData(sharedPref.getInt("activeProfile", 1));
        if(c.getCount() == 0) {
            createDefaultUser();
        } else {
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex(DBHelper.ITEM_COLUMN_USER_ID));
            String name = c.getString(c.getColumnIndex(DBHelper.ITEM_COLUMN_NAME));
            activeProfile = new UserDB(id, name);
        }
        c.close();

        setTitle(String.format("Lekce uživatele '%s'", activeProfile.name));

        Toast.makeText(this, String.valueOf(sharedPref.getInt("activeProfile", 100)), Toast.LENGTH_SHORT).show();

        String output = "";
        try {
             output = new JsonTask().execute("https://json.extendsclass.com/bin/134cf168eaf5").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d("log", output);

        JsonParser json = new JsonParser();
        lessons = json.loadLessons(output);
        /*for(Lesson item : lessons) {
            Log.d("log ", item.toString());
        }*/

        assignScore();

        LessonListAdapter adapter = new LessonListAdapter(getApplicationContext(), R.layout.list_lesson_layout, lessons);
        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener((parent, view, position, id) -> {
            Lesson selectedLesson = (Lesson) (itemListView.getItemAtPosition(position));

            Intent intent = new Intent(getApplicationContext(), DisplayLessonActivity.class);
            intent.putExtra("lesson", lessons.get(selectedLesson.id - 1));
            intent.putExtra("userId", activeProfile.id);
            //Log.d("log", "\n\nID " + (selectedLesson.id - 1));
            startActivity(intent);
            finish();
        });
    }

    private void createDefaultUser() {
        String defaultName = "Default";
        database.insertUser(defaultName);
        sharedPref.edit().putInt("activeProfile", 1).apply();
        activeProfile = new UserDB(1, defaultName);
    }

    private void assignScore() {
        //vytahnout score aktualniho uzivatele z DB
        ArrayList<LessonDB> lessonDBS = database.getLessonsList(activeProfile.id);

        //priradit score k lekcim
        for(int i = 0; i < lessons.size(); i++) {
            for(int j = 0; j < lessonDBS.size(); j++) {
                if(lessons.get(i).id == lessonDBS.get(j).lessonId) {
                    lessons.get(i).score = lessonDBS.get(j).score;
                }
            }
        }
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
