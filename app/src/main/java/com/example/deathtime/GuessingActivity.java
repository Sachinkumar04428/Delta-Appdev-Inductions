package com.example.deathtime;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuessingActivity extends AppCompatActivity {

    public static String AGE="age";
    private final int MAX_ERROR=99;
    public static int SAVED_AGE;
    public int chances ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);

        chances = 7;

        Intent intent = getIntent();
        SAVED_AGE = intent.getIntExtra(AGE,-1);

        if(SAVED_AGE<0){
            Toast.makeText(GuessingActivity.this,
                            "No Age has been saved for you to guess!. " +
                                    "Please go and save Age first.",
                                    Toast.LENGTH_SHORT).show();
            finish();
        }

        final EditText guessAge = (EditText)findViewById(R.id.guessAge);
        final Button checkGuess = (Button)findViewById(R.id.buttonCheck);
        final View layout = (View)findViewById(R.id.layout);
        final TextView chancesLeft  = (TextView)findViewById(R.id.chancesLeft);


        checkGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age ;
                int error = -1;
                String stringAge = null;
                try {
                    stringAge = guessAge.getText().toString();
                }catch(Exception e){

                    Toast.makeText(GuessingActivity.this,
                            "Please enter age",
                            Toast.LENGTH_SHORT).show();
                }


                if(stringAge!=null) {

                    chances--;
                    chancesLeft.setText("You have "+chances+" chances left");

                    age = Integer.parseInt(stringAge);
                    if (age > 0 && age <= 100)
                        error = calculateError(age, layout, chancesLeft);

                    if(chances==0 && error!=0) {
                        chancesLeft.setText(getResources().getString(R.string.lost));
                        checkGuess.setEnabled(false);
                    }
                }
            }
        });
    }


    public int calculateError(int age, View layout, TextView chancesLeft) {

        int error = age - SAVED_AGE;
        if (error == 0) {

            Toast.makeText(GuessingActivity.this,
                    "Hooray",
                    Toast.LENGTH_SHORT).show();
            chancesLeft.setText("You guessed it right! \n Hell yeah!");
        }

        int maxError = getMaxError();
        int color = getBgColor(maxError, error);
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
        Log.i("info","red "+red+" green "+green+" ratio "+ ratio+" error"+error+" maxError "+maxError);
        int color = Color.rgb(red,green,0);
        return color;
    }
}
