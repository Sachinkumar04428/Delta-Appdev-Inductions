package com.example.deathtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterAgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_age);

        final EditText enterAge = findViewById(R.id.enterAge);
        final Button saveAge    = findViewById(R.id.saveAge);

        saveAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age;
                String stringAge = null;

                try {
                    stringAge = enterAge.getText().toString();

                    if(stringAge!=null & stringAge!="") {

                        age = Integer.parseInt(stringAge);

                        if (age >= 0 && age <= 100) {

                            Intent data = new Intent(EnterAgeActivity.this, MainActivity.class);
                            data.putExtra(GuessingActivity.AGE, age);
                            setResult(RESULT_OK, data);

                            Toast.makeText(EnterAgeActivity.this, "Your Entered age has been saved", Toast.LENGTH_SHORT).show();

                            finish();

                        }else
                            Toast.makeText(EnterAgeActivity.this, getResources().getString(R.string.valid_age), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(EnterAgeActivity.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
