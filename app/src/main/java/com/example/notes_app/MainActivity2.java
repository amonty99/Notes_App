package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;
//    private MenuView menuview;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        menuview = findViewById(R.id.actionMenu);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes_app", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        Log.i("MA2 username", username);
        textView = (TextView) findViewById(R.id.displayName);
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("message");
        textView.setText("Welcome " + username);

        Context context = getApplicationContext();
        SQLiteDatabase db = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        DBHelper helper = new DBHelper(db);
        notes = helper.readNotes(username);

        Log.i("notes to display: ", notes.toString());

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.listNotes);
        listView.setAdapter(adapter);
        Log.i("adapter set?", ": mabye");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
//        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.Logout:
                SharedPreferences sharedpreferences = getSharedPreferences("com.example.notes_app", Context.MODE_PRIVATE);
                sharedpreferences.edit().remove(MainActivity.usernameKey).apply();
                goToActivity1();
                break;
            case R.id.addNote:
                Intent intent = new Intent(this, MainActivity3.class);
//                intent.putExtra("noteid", noteid);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    public void goToActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("message", nameStr);
        startActivity(intent);
    }
//    public void goToActivity3(int noteid) {
//        Intent intent = new Intent(this, MainActivity3.class);
//        intent.putExtra("noteid", noteid);
//        startActivity(intent);
//    }
}