package com.example.duolingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBDuolingo.db";
    public static final String ITEM_COLUMN_LESSON_ID = "lesson_id";
    public static final String ITEM_COLUMN_LANGUAGE = "language";
    public static final String ITEM_COLUMN_DESCRIPTION = "description";
    public static final String ITEM_COLUMN_DIFFICULTY = "difficulty";
    public static final String ITEM_COLUMN_SCORE = "score";
    public static final String ITEM_COLUMN_IS_DONE = "is_done";
    public static final String ITEM_COLUMN_LEVEL_ID = "level_id";
    public static final String ITEM_COLUMN_TYPE = "type";
    public static final String ITEM_COLUMN_CORRECT_ANSWER = "correct_answer";
    public static final String ITEM_COLUMN_DATA_ID = "data_id";
    public static final String ITEM_COLUMN_SOURCE = "source";
    public static final String ITEM_COLUMN_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lesson " +
                "(lesson_id INTEGER PRIMARY KEY, " +
                "language TEXT, " +
                "description TEXT," +
                "difficulty INTEGER," +
                "score INTEGER," +
                "is_done BOOLEAN)");
        db.execSQL("CREATE TABLE level " +
                "(level_id INTEGER PRIMARY KEY, " +
                "lesson_id INTEGER, " +
                "type TEXT, " +
                "correct_answer TEXT, " +
                "FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id))");
        db.execSQL("CREATE TABLE data " +
                "(data_id INTEGER PRIMARY KEY, " +
                "level_id INT, " +
                "source TEXT, " +
                "name TEXT, " +
                "FOREIGN KEY (level_id) REFERENCES level(level_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lesson");
        db.execSQL("DROP TABLE IF EXISTS level");
        db.execSQL("DROP TABLE IF EXISTS data");
        onCreate(db);
    }

    // LESSON
    public boolean insertLesson(String language, String description, int difficulty, int score, int isDone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("language", language);
        contentValues.put("description", description);
        contentValues.put("difficulty", difficulty);
        contentValues.put("score", score);
        contentValues.put("is_done", isDone);

        long insertedId = db.insert("lesson", null, contentValues);
        return insertedId != -1;
    }

    //Cursor representuje vracena data
    public Cursor getLessonData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT le.lesson_id, language, description, difficulty, score, is_done, lvl.level_id, type, correct_answer " +
                "FROM lesson le " +
                "JOIN level lvl ON le.lesson_id = lvl.lesson_id " +
                "WHERE le.lesson_id=" + id + "", null);
    }

    public ArrayList<Lesson> getLessonsList() {
        ArrayList<Lesson> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery("SELECT lesson_id, language, description, difficulty, score, is_done FROM lesson", null);
        res.moveToFirst();

        while(!res.isAfterLast()) {
            int id = res.getInt(res.getColumnIndex(ITEM_COLUMN_LESSON_ID));
            String language = res.getString(res.getColumnIndex(ITEM_COLUMN_LANGUAGE));
            String description = res.getString(res.getColumnIndex(ITEM_COLUMN_DESCRIPTION));
            int difficulty = res.getInt(res.getColumnIndex(ITEM_COLUMN_DIFFICULTY));
            int score = res.getInt(res.getColumnIndex(ITEM_COLUMN_SCORE));
            int isDone = res.getInt(res.getColumnIndex(ITEM_COLUMN_IS_DONE));
            arrayList.add(new Lesson(id, language, description, difficulty, score, isDone));
            res.moveToNext();
        }

        res.close();
        return arrayList;
    }

    // LEVEL
    public boolean insertLevel(int lessonId, String type, String correctAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lesson_id", lessonId);
        contentValues.put("type", type);
        contentValues.put("correct_answer", correctAnswer);

        long insertedId = db.insert("level", null, contentValues);
        return insertedId != -1;
    }

    public Cursor getLevelData(int lessonId, int levelId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT d.data_id, d.level_id, d.source, d.name " +
                "FROM level le JOIN data d ON le.level_id = d.level_id " +
                "WHERE le.level_id=" + levelId + " AND le.lesson_id=" + lessonId +"", null);
    }

    // DATA
    public boolean insertData(int levelId, String source, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("level_id", levelId);
        contentValues.put("source", source);
        contentValues.put("name", name);

        long insertedId = db.insert("data", null, contentValues);
        return insertedId != -1;
    }
}
