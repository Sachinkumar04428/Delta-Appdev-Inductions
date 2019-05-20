package com.example.deathtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuessingActivity extends AppCompatActivity {

    public static String AGE="age";
    public static int SAVED_AGE;
    public int chances ;

    //constants
    String total_try_key;
    String total_correct_key;
    String total_wrong_key;
    int total_try;
    int total_correct;
    int total_wrong;

    //Views
    View layout;
    EditText guessAge;
    Button checkGuess;
    TextView chancesLeft;

    //result
    int result = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);

        chances = 7;

        Intent intent = getIntent();
        SAVED_AGE = intent.getIntExtra(AGE,-1);

        if(SAVED_AGE<0){
            Toast.makeText(GuessingActivity.this,
                                   getResources().getString(R.string.instruct_save_age_port),
                                    Toast.LENGTH_SHORT).show();
            finish();
        }

        guessAge     =  findViewById(R.id.guessAge);
        checkGuess   =  findViewById(R.id.buttonCheck);
        layout       =  findViewById(R.id.layout);
        chancesLeft  =  findViewById(R.id.chancesLeft);
    }
    
    
    public void onCheckGuessClick(View view)
    {
        if(result<0){
            allowGuessing();
        }else{
            sendIntent(result);
            finish();
        }

    }

    public void allowGuessing()
    {
        int age ;
        int error ;
        String stringAge = null;

        try {
            stringAge = guessAge.getText().toString();
        }catch(Exception e) {
            Toast.makeText(GuessingActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
        }


        if(stringAge!=null & stringAge!="") {

            chances--;
            chancesLeft.setText("You have " + chances + " chances left");

            age = Integer.parseInt(stringAge);

            if (age > 0 && age <= 100) {

                error = calculateError(age);

                if (error == 0) {
                    chancesLeft.setText(getResources().getString(R.string.won));
                    checkGuess.setText(getResources().getString(R.string.home_page));
                    result = 1; //correct guess
                    sendIntent(result);

                } else if (chances == 0 && error != 0) {
                    chancesLeft.setText(getResources().getString(R.string.lost));
                    checkGuess.setText(getResources().getString(R.string.home_page));
                    result = 0; //wrong guess
                    sendIntent(result);
                }

            }
        }
    }

    public int calculateError(int age){

        int error = age - SAVED_AGE;
        int maxError = getMaxError();
        int color = getBgColor(maxError, error);

        //setting background colour
        layout.setBackgroundColor(color);

        return error;
    }

    public int getMaxError()
    {
        int a = 100-SAVED_AGE;
        int b = SAVED_AGE - 1;
        if(a>b)
            return a;
        else
            return b;
    }

    public int getBgColor(int maxError, int error)
    {
        int ratio = Math.abs((error*255)/maxError);
        int red = ratio;
        int green = 255 - ratio;
        int color = Color.rgb(red,green,0);
        return color;
    }

    public void sendIntent(int result){

        Intent intent = new Intent(GuessingActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.RESULT, result);
        setResult(RESULT_OK, intent);
    }
}
