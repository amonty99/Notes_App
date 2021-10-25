package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static String usernameKey = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes_app", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
//            String username = sharedPreferences.getString(usernameKey, "");
            goToActivity2();
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void clickFunction(View view) {
        Log.i("Info","Button pressed");

        EditText enterName = (EditText) findViewById(R.id.enterName);
        EditText enterPassword = (EditText) findViewById(R.id.enterPassword);

        String nameStr = enterName.getText().toString();
        String passwordStr = enterPassword.getText().toString();


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes_app", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", nameStr).apply();

        goToActivity2();
    }

    public void goToActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
//        intent.putExtra("message", nameStr);
        startActivity(intent);
    }
}