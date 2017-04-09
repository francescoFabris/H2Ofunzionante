package it.unipd.dei.esp1617.h2o;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

/**
 * Created by boemd on 04/04/2017.
 */

public class InputActivity extends AppCompatActivity
{
    private EditText spaceName, spaceWeight, spaceSport, spaceSleep, spaceWake;
    private Spinner spinnerSex,spinnerAge;
    private RadioButton lessButton;

    private String name;
    private boolean lessnotif, male; //male = true, female = false;
    private int age, weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        spaceName=(EditText) findViewById(R.id.name_space);
        spaceWeight=(EditText) findViewById(R.id.weight);
        spaceSport=(EditText) findViewById(R.id.sport_time);
        spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        spinnerAge=(Spinner) findViewById(R.id.age_spinner);
        spaceSleep=(EditText) findViewById(R.id.sleep_time);
        spaceWake=(EditText) findViewById(R.id.wake_time);
        lessButton=(RadioButton) findViewById(R.id.less_notify);


        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(sex_adapter);

        ArrayAdapter<CharSequence> years_adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(years_adapter);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                age = 0;
            }
        });

        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                male = (position==0)? false:true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                male = false;
            }
        });

        spaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                spaceName = null;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                spaceName = (EditText) s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                spaceName = (EditText) s;
            }
        });

    }


}
