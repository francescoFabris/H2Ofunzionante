package it.unipd.dei.esp1617.h2o;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
    }
}
