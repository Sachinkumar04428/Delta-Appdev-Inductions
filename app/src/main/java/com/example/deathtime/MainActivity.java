package com.example.deathtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //constants
    final int requestCodeAge = 0;
    final int requestCodeResult = 1;
    final int requestCodeReset = 2;

    final int CORRECT_GUESS = 1;

    public final static String RESULT="result";
    public final static String CONTEXT="context";
    public final static String RESET="reset";

    String total_try_key ;
    String total_correct_key ;
    String total_wrong_key ;

    final String AGE_SAVED="age_saved";
    final String AGE = "age";
    boolean age_saved;
    int age = -1;

    //views
    Button dod ;
    Button death;
    TextView label ;

    //sharedpreferences
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState!=null)
        {
            age_saved = savedInstanceState.getBoolean(AGE_SAVED, false);
            age = savedInstanceState.getInt(AGE, -1);
        }

        dod = (Button)findViewById(R.id.btn_dod);
        death = (Button)findViewById(R.id.btn_death);
        label = (TextView)findViewById(R.id.label);

        if(age_saved)
            label.setText(getResources().getString(R.string.instruct_guess_age));

        //getting default SharedPreferences
        Context context = MainActivity.this;
        sharedPref = ((MainActivity) context).getPreferences(Context.MODE_PRIVATE);

        //setting up constants
        total_try_key     = getString(R.string.total_try_key);
        total_correct_key = getString(R.string.total_correct_key);
        total_wrong_key   = getString(R.string.total_wrong_key);

        editor = sharedPref.edit();

        dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(age>=0) {
                    Intent intent = new Intent(MainActivity.this, GuessingActivity.class);
                    intent.putExtra(GuessingActivity.AGE, age);
                    startActivityForResult(intent, requestCodeResult);
                }else{
                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.instruct_save_age_port),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, EnterAgeActivity.class);
                startActivityForResult(intent, requestCodeAge);
            }
        });
    }


    public void updateSavedData(String result_key, SharedPreferences.Editor editor) {

        //getting saved values
        int total_try = sharedPref.getInt(total_try_key, 0);
        int total_result = sharedPref.getInt(result_key, 0);

        //updating saved values
        editor.putInt(result_key, total_result+1);
        editor.putInt(total_try_key,total_try+1);
        editor.commit();  //saves the changes

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == requestCodeAge & resultCode == Activity.RESULT_OK) {

            age = data.getIntExtra(GuessingActivity.AGE, -1);
            if (age > 0) {
                age_saved = true;
                label.setText(getResources().getText(R.string.instruct_guess_age));
            }

        }else if(requestCode == requestCodeResult & resultCode == Activity.RESULT_OK){

            String result_key = getResultKey(data.getIntExtra(MainActivity.RESULT, 0));
            label.setText(getResources().getString(R.string.instruct_save_age_land));
            age = -1;
            age_saved = false;
            updateSavedData(result_key, editor);

        }else if(requestCode == requestCodeReset & resultCode == Activity.RESULT_OK){

            Boolean reset = data.getBooleanExtra(MainActivity.RESET, false);
            if(reset)
                resetSavedData();

        }

    }

    public void resetSavedData(){

        editor.putInt(total_try_key,0);
        editor.putInt(total_correct_key,0);
        editor.putInt(total_wrong_key,0);
        editor.commit();

    }

    public String getResultKey(int result){

        if(result==CORRECT_GUESS)
            return total_correct_key;
        else
            return total_wrong_key;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AGE, age);
        outState.putBoolean(AGE_SAVED, age_saved);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.achievements){
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);

            //getting latest values
            int total_try = sharedPref.getInt(total_try_key, 0);
            int total_correct = sharedPref.getInt(total_correct_key, 0);
            int total_wrong = sharedPref.getInt(total_wrong_key, 0);

            //put values
            intent.putExtra(total_try_key,total_try);
            intent.putExtra(total_correct_key, total_correct);
            intent.putExtra(total_wrong_key, total_wrong);

            startActivityForResult(intent, requestCodeReset);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
