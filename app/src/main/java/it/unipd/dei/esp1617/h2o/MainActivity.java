package it.unipd.dei.esp1617.h2o;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private ImageView glass;
    private ImageView stylizedImage;
    private boolean s;

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



        glass = (ImageView) findViewById(R.id.imageGlass);
        glass.setImageResource(R.drawable.bicchiere);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean s = preferences.getBoolean("SexBln", true);


        stylizedImage = (ImageView) findViewById(R.id.stylized);
        if(s) {  // bisognerà sistemare il fatto che l'omino si riempie in base a quanta acqua necessita a seconda dell'algortimo
            if(drunkGlasses == 0){
                stylizedImage.setImageResource(R.drawable.man1);
            }
            if(drunkGlasses == 1){
                stylizedImage.setImageResource(R.drawable.man2);
            }
            if(drunkGlasses == 2){
                stylizedImage.setImageResource(R.drawable.man3);
            }
            if(drunkGlasses == 3){
                stylizedImage.setImageResource(R.drawable.man4);
            }
            if(drunkGlasses == 4){
                stylizedImage.setImageResource(R.drawable.man5);
            }
            if(drunkGlasses == 5){
                stylizedImage.setImageResource(R.drawable.man6);
            }
            if(drunkGlasses == 6){
                stylizedImage.setImageResource(R.drawable.man7);
            }
            if(drunkGlasses == 7){
                stylizedImage.setImageResource(R.drawable.man8);
            }
            if(drunkGlasses == 9){
                stylizedImage.setImageResource(R.drawable.man10);
            }
            if(drunkGlasses == 10){
                stylizedImage.setImageResource(R.drawable.man11);
            }
        }
        else {
            if (drunkGlasses == 0) {
                stylizedImage.setImageResource(R.drawable.woman1);
            }
            if (drunkGlasses == 1) {
                stylizedImage.setImageResource(R.drawable.woman2);
            }
            if (drunkGlasses == 2) {
                stylizedImage.setImageResource(R.drawable.woman3);
            }
            if (drunkGlasses == 3) {
                stylizedImage.setImageResource(R.drawable.woman4);
            }
            if (drunkGlasses == 4) {
                stylizedImage.setImageResource(R.drawable.woman5);
            }
            if (drunkGlasses == 5) {
                stylizedImage.setImageResource(R.drawable.woman6);
            }
            if (drunkGlasses == 6) {
                stylizedImage.setImageResource(R.drawable.woman7);
            }
            if (drunkGlasses == 7) {
                stylizedImage.setImageResource(R.drawable.woman8);
            }
            if (drunkGlasses == 9) {
                stylizedImage.setImageResource(R.drawable.woman10);

            }


        }

    }
}
