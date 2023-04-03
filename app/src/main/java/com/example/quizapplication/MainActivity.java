package com.example.quizapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // SIT305 - Task 3.1C
    // Vanessa Josephine Mattea
    // 221458997
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the widgets by its id
        Button startBtn = findViewById(R.id.button);
        EditText username = findViewById(R.id.editTextTextPersonName);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String nameFromPref = sharedPreferences.getString("username", "");

        // Update the values to EditText
        username.setText(nameFromPref);

        // Intent to proceed to the next activity
        Intent myIntent = new Intent(MainActivity.this, QuizActivity.class);

        // Start button
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                if (!username.getText().toString().isEmpty())
                {
                    myEditor.putString("username", username.getText().toString());
                    myEditor.apply();
                    startActivity(myIntent);
                }
                else Toast.makeText(MainActivity.this, "INVALID! Your name must include at least 1 character!", Toast.LENGTH_LONG).show();
            }
        });
    }
}