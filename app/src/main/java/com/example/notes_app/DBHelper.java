package com.example.notes_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase sqldb;

    public DBHelper(SQLiteDatabase sqldb) {
        this.sqldb = sqldb;
    }

    public void createTable() {
//        sqldb.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");

        sqldb.execSQL("CREATE TABLE IF NOT EXISTS notes" + "(id INTEGER PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");
    }
// , src TEXT
    public ArrayList<Note> readNotes(String username) {
        createTable();
        Cursor c = sqldb.rawQuery(String.format("SELECT * from notes where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        Log.i("DBH username", username);
        ArrayList<Note> notesList = new ArrayList<>();
        Log.i("DBHelper", "before while");
        while (!c.isAfterLast()) {
            Log.i("DBHelper", "inside while");
            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date, username, title, content);
            notesList.add(note);
            Log.i("note read", note.getContent());
            Log.i("note added", String.valueOf(notesList.get(notesList.size() - 1)));
            c.moveToNext();
        }
        c.close();
        sqldb.close();

        return notesList;
    }

    public void saveNotes(String username, String title, String content, String date) {
        createTable();
        Log.i("saveNotes", username + " " + date + " " + title);
        sqldb.execSQL(String.format("INSERT INTO notes (username, date, title, content) VALUES ('%s', '%s', '%s', '%s')", username, date, title, content));
    }

    public void updateNote(String title, String date, String content, String username) {
        createTable();
        sqldb.execSQL(String.format("UPDATE notes set content = '%s', date = '%s' where title = '%s' and username = '%s'", content, date, title, username));
    }
}
