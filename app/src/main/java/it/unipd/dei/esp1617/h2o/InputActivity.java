package it.unipd.dei.esp1617.h2o;

//import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
/**
 * Created by boemd on 04/04/2017.
 */

public class InputActivity extends AppCompatActivity
{
    private boolean toastNameSent,toastWeightSent;
    private boolean modificationsHaveOccurred = false;

    private int hourS = -1, minS = -1, hourW = -1, minW = -1;
    static final int TIME_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        modificationsHaveOccurred=false;
        //dati persistenti salvati come SharedPeferences
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int age = preferences.getInt("age_value",0);
        int weight = preferences.getInt("weight_value",50);
        boolean lessnot = preferences.getBoolean("lessnot_value",false);
        boolean sport = preferences.getBoolean("sport_value",false);
        boolean male = preferences.getBoolean("male_value",false);  //male = true, female = false;
        String name = preferences.getString("name_value", "Al Bano Carrisi");
        int quantity = preferences.getInt("quantity", 0);
        hourW = preferences.getInt("hour_w", 7);
        minW = preferences.getInt("min_w", 0);
        hourS = preferences.getInt("hour_s", 23);
        minS = preferences.getInt("min_s", 0);

        String wake = preferences.getString("time_wake", hourW+" : "+minW);
        String sleep = preferences.getString("time_sleep", hourS+" : "+minS);



        //aggancio widget con IDs
        final EditText spaceName=(EditText) findViewById(R.id.name_space);
        EditText spaceWeight=(EditText) findViewById(R.id.weight);
        //EditText spaceSport=(EditText) findViewById(R.id.sport_time);
        Spinner spinnerSex=(Spinner) findViewById(R.id.sex_spinner);
        final Spinner spinnerAge=(Spinner) findViewById(R.id.age_spinner);
        TextView spaceSleep=(TextView) findViewById(R.id.sleep_time);
        TextView spaceWake=(TextView) findViewById(R.id.wake_time);
        CheckBox checkNot = (CheckBox) findViewById(R.id.less_notifications);
        CheckBox checkSport= (CheckBox) findViewById(R.id.sport_box);
        //CheckBox
        checkNot.setChecked(lessnot);
        checkSport.setChecked(sport);

        //EditText
        if(name.equals(""))
        {
            name="Al Bano Carrisi";
        }
        spaceName.setText(name);
        spaceWeight.setText(Integer.toString(weight)); //Non è stato messo l'Integer puro perché causava un bug nell'apertura
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

                modificationsHaveOccurred=true;
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
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modificationsHaveOccurred=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> years_adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(years_adapter);
        spinnerAge.setSelection(age);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modificationsHaveOccurred=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spaceSleep.setText(sleep);
        spaceWake.setText(wake);

        spaceSleep.setOnClickListener(new EditText.OnClickListener() {
            public void onClick(View v) {
                if (hourS == -1 || minS == -1) {
                    Calendar c = Calendar.getInstance();
                    hourS = c.get(Calendar.HOUR);
                    minS = c.get(Calendar.MINUTE);
                }
                showTimeDialogS(hourS, minS);
            }
        });

        spaceWake.setOnClickListener(new EditText.OnClickListener() {
            public void onClick(View v) {
                if (hourW == -1 || minW == -1) {
                    Calendar c = Calendar.getInstance();
                    hourW = c.get(Calendar.HOUR);
                    minW = c.get(Calendar.MINUTE);
                }

                showTimeDialogW(hourW, minW);
            }
        });

    }

    public void showTimeDialogS(int hour, int min) {
        (new TimePickerDialog(InputActivity.this, timeSetListenerS, hour, min, true)).show();
    }

    private TimePickerDialog.OnTimeSetListener timeSetListenerS = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            int hourS = hourOfDay;
            int minS = minute;
            TextView spaceSleep = (TextView) findViewById(R.id.sleep_time);
            spaceSleep.setText(new StringBuilder().append(hourS).append(" : ").append(minS));

        }
    };

    private void showTimeDialogW(int hour, int min){
        (new TimePickerDialog(InputActivity.this, timeSetListenerW, hour,min,true)).show();
    }

    private TimePickerDialog.OnTimeSetListener timeSetListenerW = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            int hourW = hourOfDay;
            int minW = minute;
            TextView spaceWake = (TextView) findViewById(R.id.wake_time);
            spaceWake.setText(new StringBuilder().append(hourW).append(" : ").append(minW));

        }
    };

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
        CheckBox checkNoti = (CheckBox)findViewById(R.id.less_notifications) ;
        boolean lessnot = checkNoti.isChecked();
        CheckBox checkSport = (CheckBox)findViewById(R.id.sport_box) ;
        boolean sport = checkSport.isChecked();
        TextView spaceWake=(TextView) findViewById(R.id.wake_time);
        TextView spaceSleep=(TextView) findViewById(R.id.sleep_time);

        /*
        int hourW = 0+Integer.parseInt(((TextView)findViewById(R.id.wake_hour)).getText().toString());
        int minW = 0+Integer.parseInt(((TextView)findViewById(R.id.wake_min)).getText().toString());
        int hourS = 0+Integer.parseInt(((TextView)findViewById(R.id.sleep_hour)).getText().toString());
        int minS = 0+Integer.parseInt(((TextView)findViewById(R.id.sleep_min)).getText().toString());
        */

        //salvataggio dello stato persistente
        editor.putInt("age_value",age);
        editor.putInt("weight_value",weight);
        editor.putBoolean("lessnot_value",lessnot);
        editor.putBoolean("male_value",male);
        editor.putString("name_value",name);
        editor.putBoolean("sport_value",sport);

        editor.putInt("hour_w",hourW);
        editor.putInt("min_w",minW);
        editor.putInt("hour_s",hourS);
        editor.putInt("min_s",minS);

        editor.putString("time_wake", spaceWake.getText().toString());
        editor.putString("time_sleep", spaceSleep.getText().toString());

        if(modificationsHaveOccurred){
            editor.putInt("quantity",getQuantity());
        }

        //salvataggio in mutua esclusione
        editor.commit();

        if(modificationsHaveOccurred){
            //scheduleNotifications();
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
        boolean male = preferences.getBoolean("male_value",false);  //male = true, female = false;
        boolean sport = preferences.getBoolean("sport_value",false);
        int quantity=0; //quantità determinata in cl
        if(age<=2) quantity=500;
        else if(age<5) quantity=900;
        else if(age<10) quantity=1100;
        else if(age<12) quantity=1300;
        else{
            if(male){
                quantity=1700;
                if(age>16)
                    quantity+=300;
                if(sport)
                    quantity+=200;
                if(weight>80)
                    quantity+=100;
            }
            else{
                quantity=1500;
                if(age>16)
                    quantity+=300;
                if(sport)
                    quantity+=200;
                if(weight>70)
                    quantity+=100;
            }
        }


        return quantity;
    }


    /*
    //questo metodo viene invocato quando l'activity viene messa in pausa se i valori di input subiscono delle modifiche
    //gli orari delle notifiche vengono schedulati utilizzando il nuovo input
    private void scheduleNotifications()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int bicchieri = (Integer) (preferences.getInt("quantity", 0))/200;
        MyTime wake, sleep;
        int hours[] = new int[24];
        int slot = sleep.getHour()-wake.getHour()+1;
        int free = slot-bicchieri; //se>0 ho più slot che bicchieri
        int extra = -free;
        if(extra>0){

        }
    }
    */

}