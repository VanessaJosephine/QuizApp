package com.example.quizapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Finish extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        // findViewById
        TextView congrats = findViewById(R.id.textView3);
        TextView finalScore = findViewById(R.id.textView6);
        Button restart = findViewById(R.id.button2);
        Button end = findViewById(R.id.button3);

        // Getting String value from EditText on Main via SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Intent - Getting the score value from QuizActivity
        Intent myIntent3 = getIntent();
        Intent myIntent4 = new Intent(Finish.this, MainActivity.class);
        String score = myIntent3.getStringExtra("score");
        String totalQuestion = myIntent3.getStringExtra("totalQuestion");

        // Display correct information
        congrats.setText("Congratulations, " + username + "! :)");
        finalScore.setText(score + "/" + totalQuestion);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(myIntent4);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}