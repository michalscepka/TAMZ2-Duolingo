package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    JsonParser myJson;
    ListView itemListView;
    ArrayList<Lesson> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        myJson = new JsonParser(getApplicationContext());
        lessons = myJson.loadLessons();
        for(Lesson item : lessons) {
            Log.d("log ", item.toString());
        }

        ArrayAdapter<Lesson> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessons);

        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
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

        if (id == R.id.my_profile)
        {
            /*Intent intent = new Intent(getApplicationContext(), DisplayItemActivity.class);
            intent.putExtra("id", 0);
            startActivity(intent);
            finish();*/
            Toast.makeText(this, "My profile", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.lessons)
        {
            Toast.makeText(this, "Lessons", Toast.LENGTH_LONG).show();
            //recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
    }
}
