package it.unipd.dei.esp1617.h2o;

//import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * Created by boemd on 04/04/2017.
 */

public class InputActivity extends AppCompatActivity
{
    private boolean toastNameSent,toastWeightSent;
    private boolean modificationsHaveOccurred = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //dati persistenti salvati come SharedPeferences
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int age = preferences.getInt("age_value",0);
        int weight = preferences.getInt("weight_value",50);
        boolean lessnot = preferences.getBoolean("lessnot_value",false);
        boolean male = preferences.getBoolean("male_value",false);  //male = true, female = false;
        String name = preferences.getString("name_value", "Al Bano Carrisi");
        int quantity = preferences.getInt("quantity", 0);
        //mancano gli orari!!

        //aggancio widget con IDs
        final EditText spaceName=(EditText) findViewById(R.id.name_space);
        EditText spaceWeight=(EditText) findViewById(R.id.weight);
        EditText spaceSport=(EditText) findViewById(R.id.sport_time);
        Spinner spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        final Spinner spinnerAge=(Spinner) findViewById(R.id.age_spinner);
        EditText spaceSleep=(EditText) findViewById(R.id.sleep_time);
        EditText spaceWake=(EditText) findViewById(R.id.wake_time);
        CheckBox checkNot = (CheckBox) findViewById(R.id.less_notifications);

        //CheckBox
        checkNot.setChecked(lessnot);

        //EditText
        if(name.equals(""))
        {
            name="Al Bano Carrisi";
        }
        spaceName.setText(name);
        spaceWeight.setText(Integer.toString(weight)); //Non è stato messo l'Integer puro perché causava un bug nell'apertura
        //spaceSport.setText();
        //spaceSleep.setText();
        //spaceSleep.setText();

        spaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isToastNameSent()&&s.toString().length()>20){
                    Toast.makeText(InputActivity.this, R.string.toast_name, Toast.LENGTH_SHORT).show();
                    setToastNameSent();
                }

            }
        });

        if(weight==0)
            weight=50;

        spaceWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(!isToastWeightSent()&&Double.parseDouble(0+s.toString())>199){       //parseInt da errore se s è vuota!! E' necessario aggiungere lo 0 iniziale
                    Toast.makeText(InputActivity.this, R.string.toast_weight,Toast.LENGTH_SHORT).show();
                    setToastWeightSent();
                }

            }
        });

        //Spinner
        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(sex_adapter);
        spinnerSex.setSelection(male?1:0);


        ArrayAdapter<CharSequence> years_adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(years_adapter);
        spinnerAge.setSelection(age);
        //quello che segue è inutile, ma per il momento lo lascio perché non si sa mai
        /*
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerAge.setSelection(spinnerAge.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

    }

    //@SuppressLint("CommitPrefsEdit")
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
        Double w = Double.parseDouble(0+spaceWeight.getText().toString());
        int weight = w.intValue();
        //weight = (weight==0)?50:weight;
        Spinner spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        boolean male = spinnerSex.getSelectedItemPosition()==1;
        EditText spaceName=(EditText) findViewById(R.id.name_space);
        String name = spaceName.getText().toString();
        CheckBox checkBox = (CheckBox)findViewById(R.id.less_notifications) ;
        boolean lessnot = checkBox.isChecked();

        //salvataggio dello stato persistente
        editor.putInt("age_value",age);
        editor.putInt("weight_value",weight);
        editor.putBoolean("lessnot_value",lessnot);
        editor.putBoolean("male_value",male);
        editor.putString("name_value",name);
        if(modificationsHaveOccurred){
            editor.putInt("quantity",getQuantity());
        }
        //salvataggio in mutua esclusione
        editor.commit();

        if(modificationsHaveOccurred){
            scheduleNotifications();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();           //i metodi "not-setter" del controllo dei toast vengono chiamati in onResume() così da poter venire chiamati
        setToastNameNotSent();      //ogni volta che l'InputActivity viene riaperta
        setToastWeightNotSent();
    }

    //metodi di controllo del flusso dei Toast
    private boolean isToastNameSent(){
        return toastNameSent;
    }
    private boolean isToastWeightSent(){
        return toastWeightSent;
    }
    private void setToastNameSent(){
        toastNameSent = true;
    }
    private void setToastWeightSent() {
        toastWeightSent = true;
    }
    private void setToastNameNotSent(){
        toastNameSent=false;
    }
    private void setToastWeightNotSent(){
        toastWeightSent=false;
    }

    //algoritmo che determina la quantità d'acqua da consumare
    private int getQuantity(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int age = preferences.getInt("age_value",0);
        int weight = preferences.getInt("weight_value",50);
        boolean lessnot = preferences.getBoolean("lessnot_value",false);
        boolean male = preferences.getBoolean("male_value",false);  //male = true, female = false;
        String name = preferences.getString("name_value", "Al Bano Carrisi");
        boolean sport;
        int quantity=0;

        //corpo algoritmo

        return quantity;
    }

    //questo metodo viene invocato quando l'activity viene messa in pausa se i valori di input subiscono delle modifiche
    //le gli orari delle notifiche vengono schedulati utilizzando il nuovo input
    private void scheduleNotifications()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int quantity = preferences.getInt("quantity", 0);
        MyTime wake, sleep, sport;
    }
}
