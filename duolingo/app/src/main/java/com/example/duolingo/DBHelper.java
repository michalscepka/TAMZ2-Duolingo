package com.example.duolingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBDuolingo.db";
    public static final String ITEM_COLUMN_LESSON_ID = "lesson_id";
    public static final String ITEM_COLUMN_LANGUAGE = "language";
    public static final String ITEM_COLUMN_DESCRIPTION = "description";
    public static final String ITEM_COLUMN_DIFFICULTY = "difficulty";
    public static final String ITEM_COLUMN_USER_ID = "user_id";
    public static final String ITEM_COLUMN_USERNAME = "username";
    public static final String ITEM_COLUMN_IS_DONE = "is_done";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lesson " +
                "(lesson_id INTEGER PRIMARY KEY, " +
                "language TEXT, " +
                "description TEXT," +
                "difficulty INTEGER)");
        db.execSQL("CREATE TABLE user " +
                "(user_id INTEGER PRIMARY KEY, " +
                "username TEXT)");
        db.execSQL("CREATE TABLE progress " +
                "(user_id INTEGER," +
                "lesson_id INTEGER," +
                "is_done BOOLEAN," +
                "FOREIGN KEY (user_id) REFERENCES user(user_id)," +
                "FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lesson");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
    }

    // LESSON
    public boolean insertLesson(String language, String description, int difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("language", language);
        contentValues.put("description", description);
        contentValues.put("difficulty", difficulty);

        long insertedId = db.insert("lesson", null, contentValues);
        return insertedId != -1;
    }

    /*public boolean deleteItem (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("DELETE FROM items WHERE id=" + id + "");
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }*/

    //Cursor representuje vracena data
    public Cursor getLessonData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT le.lesson_id, language, description, difficulty, p.is_done FROM lesson le LEFT JOIN progress p ON le.lesson_id = p.lesson_id WHERE le.lesson_id=" + id + "", null);
    }

    /*public boolean updateItem (Integer id, String name, String cost) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("UPDATE items SET name ='" + name + "', cost =" + cost + " WHERE id=" + id + "");
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }*/

    public ArrayList<Lesson> getLessonsList(int user_id) {
        ArrayList<Lesson> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery("SELECT le.lesson_id, language, description, difficulty, p.is_done " +
                "FROM lesson le JOIN progress p ON le.lesson_id = p.lesson_id " +
                "WHERE p.user_id = " + user_id, null);
        res.moveToFirst();

        while(!res.isAfterLast()) {
            int id = res.getInt(res.getColumnIndex(ITEM_COLUMN_LESSON_ID));
            String language = res.getString(res.getColumnIndex(ITEM_COLUMN_LANGUAGE));
            String description = res.getString(res.getColumnIndex(ITEM_COLUMN_DESCRIPTION));
            int difficulty = res.getInt(res.getColumnIndex(ITEM_COLUMN_DIFFICULTY));
            Integer isDone = res.getInt(res.getColumnIndex(ITEM_COLUMN_IS_DONE));
            arrayList.add(new Lesson(id, language, description, difficulty, isDone));
            res.moveToNext();
        }

        res.close();
        return arrayList;
    }

    /*public int removeAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM items", null);
        c.moveToFirst();

        db.execSQL("DELETE FROM items");

        return c.getInt(0);
    }*/

    // USER
    public boolean insertUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);

        long insertedId = db.insert("user", null, contentValues);
        return insertedId != -1;
    }

    public Cursor getUserData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from user where user_id=" + id + "", null);
    }

    // PROGRESS
    public boolean insertProgress(int user_id, int lesson_id, boolean is_done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user_id);
        contentValues.put("lesson_id", lesson_id);
        contentValues.put("is_done", is_done);

        long insertedId = db.insert("progress", null, contentValues);
        return insertedId != -1;
    }
}
