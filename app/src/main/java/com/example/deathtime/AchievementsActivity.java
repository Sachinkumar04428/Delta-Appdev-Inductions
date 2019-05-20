package com.example.deathtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AchievementsActivity extends AppCompatActivity {

    TextView text_try;
    TextView text_correct;
    TextView text_lost;
    Button reset_btn;

    Boolean reset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Intent intent = getIntent();

        text_try = findViewById(R.id.textTotalTry);
        text_correct = findViewById(R.id.textTotalCorrect);
        text_lost = findViewById(R.id.textTotalWrong);
        reset_btn = findViewById(R.id.reset_btn);

        //getting the keys
        String total_try_key = getResources().getString(R.string.total_try_key);
        String total_correct_key = getResources().getString(R.string.total_correct_key);
        String total_wrong_key = getResources().getString(R.string.total_wrong_key);

        int total_try = intent.getIntExtra(total_try_key, 0);
        int total_correct = intent.getIntExtra(total_correct_key, 0);
        int total_wrong = intent.getIntExtra(total_wrong_key, 0);

        //updating
        text_try.setText(getResources().getString(R.string.total_try_title) + " : " + total_try);
        text_correct.setText(getResources().getString(R.string.total_correct_title) + " : " + total_correct);
        text_lost.setText(getResources().getString(R.string.total_wrong_title) + " : " + total_wrong);

    }

    public void reset(View view) {

        if(!reset) {
            reset = true;
            text_try.setText(getResources().getString(R.string.total_try_title) + " : 0");
            text_correct.setText(getResources().getString(R.string.total_correct_title) + " : 0");
            text_lost.setText(getResources().getString(R.string.total_wrong_title) + " : 0");

            reset_btn.setText(getResources().getString(R.string.save));
        }else{
            Intent intent = new Intent(AchievementsActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.RESET, true);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
