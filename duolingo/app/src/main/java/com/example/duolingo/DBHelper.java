package com.example.duolingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBDuolingo.db";
    public static final String ITEM_COLUMN_USER_ID = "user_id";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_LESSON_ID = "lesson_id";
    public static final String ITEM_COLUMN_SCORE = "score";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user " +
                "(user_id INTEGER PRIMARY KEY, " +
                "name TEXT)");
        db.execSQL("CREATE TABLE lesson " +
                "(lesson_id INTEGER, " +
                "user_id TEXT, " +
                "score INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES uzivatel(user_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lesson");
        db.execSQL("DROP TABLE IF EXISTS uzivatel");
        onCreate(db);
    }

    // USER
    public boolean insertUzivatel(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        long insertedId = db.insert("user", null, contentValues);
        return insertedId != -1;
    }

    public Cursor getUserData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * " +
                "FROM user " +
                "WHERE user_id=" + id + "", null);
    }

    // LESSON
    public boolean insertLesson(int lessonId, int userId, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("lesson_id", lessonId);
        contentValues.put("score", score);

        long insertedId = db.insert("lesson", null, contentValues);
        return insertedId != -1;
    }

    public void updateLesson(int lessonId, int userId, int newScore) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("lesson_id", lessonId);
        contentValues.put("score", newScore);

        if(existsLessonRecord(lessonId, userId)) {
            db.update("lesson", contentValues, "lesson_id =" + lessonId + " AND user_id=" + userId + " AND score < '" + newScore + "'", null);
        }
        else {
            insertLesson(lessonId, userId, newScore);
        }
    }

    public boolean existsLessonRecord(int lessonId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM lesson WHERE user_id=" + userId + " AND lesson_id=" + lessonId, null);
        if(c.getCount() <= 0) {
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public ArrayList<LessonDB> getLessonsList(int userId) {
        ArrayList<LessonDB> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT le.lesson_id, le.score FROM user u JOIN lesson le ON u.user_id = le.user_id WHERE u.user_id =" + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            int lessonId = res.getInt(res.getColumnIndex(ITEM_COLUMN_LESSON_ID));
            int score = res.getInt(res.getColumnIndex(ITEM_COLUMN_SCORE));
            arrayList.add(new LessonDB(lessonId, score));
            res.moveToNext();
        }
        res.close();

        return arrayList;
    }
}
