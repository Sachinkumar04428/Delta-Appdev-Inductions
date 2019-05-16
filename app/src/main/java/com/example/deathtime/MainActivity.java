package com.example.deathtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean age_saved = false;
    Button dod ;
    Button death;
    TextView label ;
    int age = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dod = (Button)findViewById(R.id.btn_dod);
        death = (Button)findViewById(R.id.btn_death);
        label = (TextView)findViewById(R.id.label);

        dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, GuessingActivity.class);
                intent.putExtra(GuessingActivity.AGE, age);
                startActivity(intent);
            }
        });

        death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, EnterAgeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected  void onResume(){
        super.onResume();

        Intent intent=getIntent();
        age = intent.getIntExtra(GuessingActivity.AGE, -1);

        if(age>0){
            age_saved = true;
            label.setText(getResources().getText(R.string.instruct_guess_age));
        }
    }
}
