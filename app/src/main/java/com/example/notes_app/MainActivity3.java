package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    EditText editText;

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = (EditText) findViewById(R.id.writeNote);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }

    public void onClick(View view) {
        editText = (EditText) findViewById(R.id.writeNote);
        String content = String.valueOf(editText.getText());
        Context context = getApplicationContext();
        SQLiteDatabase db = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper helper = new DBHelper(db);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes_app", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("note info", date + " " + content);
        if (noteid == -1) {
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            helper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title,date,content,username);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}