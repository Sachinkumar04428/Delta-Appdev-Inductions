package com.example.deathtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterAgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_age);

        final EditText enterAge = (EditText)findViewById(R.id.enterAge);
        final Button saveAge = (Button)findViewById(R.id.saveAge);



        saveAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = -1;
                String stringAge = null;
                try {
                    stringAge = enterAge.getText().toString();
                }catch(Exception e){

                    Toast.makeText(EnterAgeActivity.this,
                            "Please enter age",
                            Toast.LENGTH_SHORT).show();
                }

                if(stringAge!=null) {

                    age = Integer.parseInt(stringAge);
                    if(age>0 && age<=100) {

                        Intent intent = new Intent(EnterAgeActivity.this, MainActivity.class);
                        intent.putExtra(GuessingActivity.AGE,age);
                        Toast.makeText(EnterAgeActivity.this,"Your Entered age has been saved",Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }else
                        Toast.makeText(EnterAgeActivity.this, "Please Enter a valid age between 1-100",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
