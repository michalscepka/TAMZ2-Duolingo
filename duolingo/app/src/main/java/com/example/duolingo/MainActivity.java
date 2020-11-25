package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    ListView itemListView;
    int user_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        /*mydb.insertLesson("english", "Zvirata", 2);
        mydb.insertLesson("english", "Objekty", 3);

        mydb.insertUser("uzivatel1");

        mydb.insertProgress(1,1, true);
        mydb.insertProgress(1,2, false);*/

        ArrayList<Lesson> arrayList = mydb.getLessonsList(user_id);
        ArrayAdapter<Lesson> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
        itemListView.setOnItemClickListener((parent, view, position, id) -> {
            Lesson selectedLesson = (Lesson) (itemListView.getItemAtPosition(position));

            Intent intent = new Intent(getApplicationContext(), DisplayLessonActivity.class);
            intent.putExtra("id", selectedLesson.id);
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
