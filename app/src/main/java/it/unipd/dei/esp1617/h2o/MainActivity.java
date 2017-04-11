package it.unipd.dei.esp1617.h2o;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button bu;
    private TextView tv1,tv2,tv3,tv4;
    private FloatingActionButton faplus, faminus;
    private int drunkGlasses =0;
    private ImageView miaImmagine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv2.setText((CharSequence) bu);
        tv4.setText((drunkGlasses>5)?"\"@string/c2\"":"\"@string/c1\"");


                bu = (Button) findViewById(R.id.apri_second);
        /**
         * Passaggio all'activity per l'acquisizione delle informazioni sull'utente
         */
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start InputActivity
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        miaImmagine = (ImageView) findViewById(R.id.image);
        miaImmagine.setImageResource(R.drawable.bicchiere);

        faplus = (FloatingActionButton)findViewById(R.id.plus_button);
        faminus = (FloatingActionButton)findViewById(R.id.minus_button);
        /**
         * Incremento del numero di bicchieri bevuti dall'utente
         */
        faplus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drunkGlasses++;
            }
        });
        /**
         * Decremento del numero di bicchieri bevuti dall'utente
         */
        faminus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drunkGlasses--;
            }
        });
    }
}
