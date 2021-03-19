package com.example.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

public class DBManager {

    private Context context;
    private String DB_NAME = "todo.db";

    private SQLiteDatabase db;

    private static DBManager dbManager;

    public static DBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    private DBManager(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        createTablesIfNeedBe();
    }

    void addResult(String name, String text) {
        db.execSQL("INSERT INTO RECORDS VALUES (?, ?);", new String[]{name, text + ""});
    }



    ArrayList<Result> getAllResults() {

        ArrayList<Result> data = new ArrayList<Result>();
        Cursor cursor = db.rawQuery("SELECT * FROM RESULTS;", null);
        boolean hasMoreData = cursor.moveToFirst();

        while (hasMoreData) {
            String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
            int score = Integer.parseInt(cursor.getString(cursor
                    .getColumnIndex("SCORE")));
            data.add(new Result(name, score));
            hasMoreData = cursor.moveToNext();
        }

        return data;
    }

    ArrayList<Result> getAllResultsUserName(String username) {

        ArrayList<Result> data = new ArrayList<Result>();
        String[] args = {username};
        Cursor cursor = db.rawQuery("SELECT * FROM RESULTS WHERE USERNAME = ?;", args, null);
        boolean hasMoreData = cursor.moveToFirst();

        while (hasMoreData) {
            String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
            int score = Integer.parseInt(cursor.getString(cursor
                    .getColumnIndex("SCORE")));
            data.add(new Result(name, score));
            hasMoreData = cursor.moveToNext();
        }

        return data;
    }

    public ArrayList<Result> getPlayersScore() {
        ArrayList <Result> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT DISTINCT USERNAME, SUM(SCORE) AS S FROM  RESULTS GROUP BY USERNAME ORDER BY S DESC", null);

        boolean hasMoreData = cursor.moveToFirst();


        while (hasMoreData) {
            String name = cursor.getString(0);
            int score = cursor.getInt(1);

            result.add(new Result(name, score));

            hasMoreData = cursor.moveToNext();
        }

        return result;
    }

    public void clear() {
        db.execSQL("DELETE FROM RESULTS WHERE 1 == 1");
    }

    public int countGames() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RESULTS;", null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    private void createTablesIfNeedBe() {
        db.execSQL("CREATE TABLE IF NOT EXISTS RECORDS (NAME TEXT, BODY TEXT);");
    }

    private boolean dbExist() {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }
}
