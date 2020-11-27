package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    JsonHelper myJson;
    ListView itemListView;
    ArrayList<Lesson> lessons;
    int user_id = 1;
    String[] inputText;
    ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        //initDB();

        myJson = new JsonHelper();
        lessons = myJson.loadLessons(getAssets());
        for(Lesson item : lessons) {
            Log.d("log ", item.toString());
        }

        /*myImage = findViewById(R.id.imageView);
        String mDrawableName = "dog";
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        myImage.setImageResource(resID);*/


        //ArrayList<Lesson> arrayList = mydb.getLessonsList();
        //ArrayAdapter<Lesson> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        ArrayAdapter<Lesson> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessons);

        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
        itemListView.setOnItemClickListener((parent, view, position, id) -> {
            Lesson selectedLesson = (Lesson) (itemListView.getItemAtPosition(position));

            Intent intent = new Intent(getApplicationContext(), DisplayLessonActivity.class);
            intent.putExtra("id", selectedLesson.id);
            Log.d("log", "\n\nID " + selectedLesson.id);
            startActivity(intent);
            finish();
        });
    }

    /*public void initDB() {
        mydb.insertLesson("english", "Zvirata", 2, 55, 1);
        mydb.insertLesson("english", "Objekty", 3, 0, 0);

        mydb.insertLevel(1, "pickFrom4", "cat");
        mydb.insertLevel(1, "listening", "I am not a dog");
        mydb.insertLevel(1, "whatIsOnPicture", "dog");
        mydb.insertLevel(2, "pickFrom4", "tiger");
        mydb.insertLevel(2, "listening", "I love dogs");
        mydb.insertLevel(2, "whatIsOnPicture", "turtle");

        mydb.insertData(1, "cat", "cat");
        mydb.insertData(1, "dog", "dog");
        mydb.insertData(1, "bird", "bird");
        mydb.insertData(1, "turtle", "turtle");

        mydb.insertData(2, "cat", "cat");
        mydb.insertData(2, "dog", "dog");
        mydb.insertData(2, "bird", "bird");
        mydb.insertData(2, "turtle", "turtle");

        mydb.insertData(3, "cat", "cat");
        mydb.insertData(3, "dog", "dog");
        mydb.insertData(3, "bird", "bird");
        mydb.insertData(3, "turtle", "turtle");
    }*/

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
