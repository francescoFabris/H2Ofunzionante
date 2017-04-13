package it.unipd.dei.esp1617.h2o;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by boemd on 04/04/2017.
 */

public class InputActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //dati persistenti salvati come SharedpPeferences
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int age = preferences.getInt("age_value",0);
        int weight = preferences.getInt("weight_value",50);
        boolean lessnot = preferences.getBoolean("lessnot_value",false);
        boolean male = preferences.getBoolean("male_value",false);  //male = true, female = false;
        String name = preferences.getString("name_value", "Al Bano Carrisi");
        //mancano gli orari!!

        EditText spaceName=(EditText) findViewById(R.id.name_space);
        EditText spaceWeight=(EditText) findViewById(R.id.weight);
        EditText spaceSport=(EditText) findViewById(R.id.sport_time);
        Spinner spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        Spinner spinnerAge=(Spinner) findViewById(R.id.age_spinner);
        EditText spaceSleep=(EditText) findViewById(R.id.sleep_time);
        EditText spaceWake=(EditText) findViewById(R.id.wake_time);
        CheckBox checkNot = (CheckBox) findViewById(R.id.less_notifications);

        //EditText
        spaceName.setText(name);
        spaceWeight.setText(""+weight);
        //spaceSport.setText();
        //spaceSleep.setText();
        //spaceSleep.setText();

        //CheckBox
        checkNot.setChecked(lessnot);

        //Spinner
        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(sex_adapter);
        //inutile per il momento
        if(spinnerSex.getSelectedItemPosition()==1)
            male=true;


        ArrayAdapter<CharSequence> years_adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(years_adapter);
        //inutile per il momento
        age = spinnerAge.getSelectedItemPosition();


    }

    @SuppressLint("CommitPrefsEdit")
    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //references dei widgets per leggerne lo stato
        Spinner spinnerAge=(Spinner) findViewById(R.id.age_spinner);
        int age = spinnerAge.getSelectedItemPosition();
        EditText spaceWeight=(EditText) findViewById(R.id.weight);
        int weight = Integer.parseInt(spaceWeight.getText().toString());
        CheckBox checkNot = (CheckBox) findViewById(R.id.less_notifications);
        boolean lessnot = checkNot.isChecked();
        Spinner spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        boolean male = (spinnerSex.getSelectedItemPosition()==1)?true:false;
        EditText spaceName=(EditText) findViewById(R.id.name_space);
        String name = spaceName.getText().toString();

        //salvataggio dello stato persistente
        editor.putInt("age_value",age);
        editor.putInt("weight_value",weight);
        editor.putBoolean("lessnot_value",lessnot);
        editor.putBoolean("male_value",male);
        editor.putString("name_value",name);
        //salvataggio in mutua esclusione
        editor.commit();
    }


}
