package com.example.deathtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GuessingActivity extends AppCompatActivity {

    public static String AGE="age";
    public static int SAVED_AGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);

        Intent intent = getIntent();
        SAVED_AGE = intent.getIntExtra(AGE,-1);

        if(SAVED_AGE<0){
            Toast.makeText(GuessingActivity.this, "No Age has been saved for you to guess!. Please go and save Age first.",Toast.LENGTH_SHORT).show();
            finish();
        }

        final EditText guessAge = (EditText)findViewById(R.id.guessAge);
        Button checkGuess = (Button)findViewById(R.id.buttonCheck);

        checkGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = -1;
                String string_age = guessAge.getText().toString();
                if(string_age!=null) {

                    age = Integer.parseInt(string_age);
                    if(age>0 && age<=100) {

                        int error = age - SAVED_AGE;
                        if(error>0){

                            Toast.makeText(GuessingActivity.this, "You have entered age higher than actual age", Toast.LENGTH_SHORT).show();
                        }else if(error<0){

                            Toast.makeText(GuessingActivity.this, "You have entered age lower than actual age", Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(GuessingActivity.this, "You have got it CORRECT! Hell yeah!", Toast.LENGTH_SHORT).show();

                        }

                    }else
                        Toast.makeText(GuessingActivity.this, "Please Enter a valid age between 1-100",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
