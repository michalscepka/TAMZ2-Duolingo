package com.example.duolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private DBHelper database;
    private ArrayList<UserDB> users;
    private Spinner spinner;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setTitle("Můj profil");
        database = new DBHelper(this);
        users = database.getUsersList();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        TextView activeProfileText = findViewById(R.id.textActiveProfile);
        TextView nameEditText = findViewById(R.id.editTextName);
        Button createProfileButton = findViewById(R.id.buttonCreateProfile);
        Button changeProfileButton = findViewById(R.id.buttonChangeProfile);
        Button deleteProfileButton = findViewById(R.id.buttonDeleteProfile);

        activeProfileText.setText(String.format("Aktivní profil: %s", users.get(sharedPref.getInt("activeProfile", 1) - 1).name));

        //zmena profilu
        spinner = findViewById(R.id.spinner);
        updateSpinner();

        //TODO zkusit nezobrazovat furt default
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MyProfileActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                selectedIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        changeProfileButton.setOnClickListener(v -> {
            sharedPref.edit().putInt("activeProfile", users.get(selectedIndex).id).apply();
            backToMainActivity();
        });

        //smazani vybraneho profilu
        deleteProfileButton.setOnClickListener(v -> {
            if(selectedIndex > 0) {
                database.deleteUser(users.get(selectedIndex).id);
                sharedPref.edit().putInt("activeProfile", 1).apply();
                Toast.makeText(this, String.format("Profil '%s' byl smazán", users.get(selectedIndex).name), Toast.LENGTH_LONG).show();
            } else {
                database.deleteLessonsForUser(users.get(selectedIndex).id);
                Toast.makeText(this, String.format("Profil '%s' byl resetován", users.get(selectedIndex).name), Toast.LENGTH_LONG).show();
            }

            restartActivity();
        });

        //vytvoreni noveho profilu
        createProfileButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString();
            if(!newName.equals("")) {
                int newUserId = database.insertUser(newName);
                sharedPref.edit().putInt("activeProfile", newUserId).apply();
                //Toast.makeText(this, String.valueOf(sharedPref.getInt("activeProfile", 1)), Toast.LENGTH_SHORT).show();
                restartActivity();
            }
        });
    }

    private void updateSpinner() {
        ArrayAdapter<UserDB> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void restartActivity() {
        finish();
        startActivity(getIntent());
    }

    private void backToMainActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        backToMainActivity();
    }
}
