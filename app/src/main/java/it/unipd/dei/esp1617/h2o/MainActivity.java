package it.unipd.dei.esp1617.h2o;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button bu;
    private TextView tv1,tv2,tv3,tv4;
    private FloatingActionButton faplus, faminus;
    private static int drunkGlasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        drunkGlasses=preferences.getInt("drunk_glasses",0);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        changeViewsText();

        bu = (Button) findViewById(R.id.apri_second);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start InputActivity
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        faplus = (FloatingActionButton)findViewById(R.id.plus_button);
        faminus = (FloatingActionButton)findViewById(R.id.minus_button);
        //Incremento del numero di bicchieri bevuti dall'utente
        faplus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                incrementGlasses();
            }
        });
        //Decremento del numero di bicchieri bevuti dall'utente
        faminus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                decrementGlasses();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("drunk_glasses",drunkGlasses);
        editor.apply();
    }

    public void incrementGlasses(){
        drunkGlasses++;
        changeViewsText();
    }
    public void decrementGlasses(){
        if(drunkGlasses>0){
            drunkGlasses--;
            changeViewsText();
        }

    }
    public void changeViewsText(){
        tv2.setText(Integer.toString(drunkGlasses));
        tv4.setText((drunkGlasses>5)?R.string.c2:R.string.c1);
    }
}
