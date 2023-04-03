package com.example.quizapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private int score = 0;
    private int correctBtn = 0;
    private int totalQuestion = Question.questions.length;
    private int currentQuestion = 0;
    private String selectedOption = "";
    private TextView title;
    private TextView question;
    private TextView quizNumber;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button nextBtn;
    private ProgressBar progressBar;
    private boolean flag = false;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        // findViewById
        TextView welcome = findViewById(R.id.textView5);
        quizNumber = findViewById(R.id.textView2);
        title = findViewById(R.id.textViewTitle);
        question = findViewById(R.id.textViewQuestion);
        progressBar = findViewById(R.id.progressBar);
        option1 = findViewById(R.id.answer1);
        option2 = findViewById(R.id.answer2);
        option3 = findViewById(R.id.answer3);
        nextBtn = findViewById(R.id.nextBtn);

        // Getting username from EditText on Main via SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Welcome the user
        if (!username.isEmpty() || username != "")
            welcome.setText("Welcome, " + username);
        else
            welcome.setText("Welcome!");

        // Sets all the button to the onClick listener
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        newQuestion();
    }
    // Disable the back button
    @Override
    public void onBackPressed() { }

    @Override
    public void onClick(View view)
    {
        // Resets the button background color
        option1.setBackgroundColor(Color.rgb(98,0,238));
        option2.setBackgroundColor(Color.rgb(98,0,238));
        option3.setBackgroundColor(Color.rgb(98,0,238));

        flag = false;
        Button clickedButton = (Button) view;

        // Finds the ID of the correct button
        if (option1.getText().toString() == Question.correctOption[currentQuestion])
            correctBtn = option1.getId();
        if (option2.getText().toString() == Question.correctOption[currentQuestion])
            correctBtn = option2.getId();
        if (option3.getText().toString() == Question.correctOption[currentQuestion])
            correctBtn = option3.getId();
        Log.v("correctBtn ID", Integer.toString(correctBtn));

        // When the submit button is clicked
        if (clickedButton.getId() == R.id.nextBtn)
        {
            // When the correct option is clicked
            if (selectedOption.equals(Question.correctOption[currentQuestion]))
            {
                // Updating the score by 1 point and sets flag as true (indicates the correct option is clicked)
                score++;
                flag = true;
            }
            // Change the index of the current question
            currentQuestion++;
            Log.v("currentQuestion Counter", Integer.toString(currentQuestion));

            // Change the background color of the selected button. Green means correct. Red means wrong.
            option1.setBackgroundColor(Color.RED);
            option2.setBackgroundColor(Color.RED);
            option3.setBackgroundColor(Color.RED);
            if (R.id.answer1 == correctBtn && flag == true) option1.setBackgroundColor(Color.GREEN);
            if (R.id.answer2 == correctBtn && flag == true) option2.setBackgroundColor(Color.GREEN);
            if (R.id.answer3 == correctBtn && flag == true) option3.setBackgroundColor(Color.GREEN);

            // Add a delay to show the user's answer and go to the next question
            if (currentQuestion < totalQuestion)
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newQuestion();
                    }
                }, 3000);
            }
            // If all the questions have been shown, go to the last activity
            if (currentQuestion == totalQuestion)
            {
                Intent myIntent2 = new Intent(getApplicationContext(), Finish.class);
                // Send the value of score and total of questions to be displayed at the last activity
                myIntent2.putExtra("score", Integer.toString(score));
                myIntent2.putExtra("totalQuestion", Integer.toString(totalQuestion));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(myIntent2);
                    }
                }, 3000);
            }
        }
        else
        {
            // Change the color of the selected button
            selectedOption = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }
    void newQuestion()
    {
        // Resets the button background color
        option1.setBackgroundColor(Color.rgb(98,0,238));
        option2.setBackgroundColor(Color.rgb(98,0,238));
        option3.setBackgroundColor(Color.rgb(98,0,238));

        question.setText((Question.questions[currentQuestion]));
        option1.setText(Question.options[currentQuestion][0]);
        option2.setText(Question.options[currentQuestion][1]);
        option3.setText(Question.options[currentQuestion][2]);
        progressBar.setProgress(progressBar.getProgress()+20, true);
        quizNumber.setText("Question " + (currentQuestion+1) + "/" + totalQuestion);

    }
}