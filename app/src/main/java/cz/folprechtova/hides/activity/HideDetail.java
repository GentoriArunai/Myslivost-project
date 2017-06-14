package cz.folprechtova.hides.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cz.folprechtova.hides.R;
import cz.folprechtova.hides.dto.Hide;

public class HideDetail extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_hide_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //zobrazení šipky zpět - ale ňák to nefachá? :(

        Hide hide = (Hide) getIntent().getExtras().getSerializable("HIDE"); //natažení serializable z intentu

        TextView titleStand = (TextView) findViewById (R.id.titleStand);
        titleStand.setText(hide.getName());

        titleStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HideDetail.this, MapsActivity.class);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); //zavírání na button zpět
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonShowMapClick(View v){

        Hide hide = (Hide) getIntent().getExtras().getSerializable("HIDE");

        float lat = hide.getLatitude();
        float lon = hide.getLongitude();
        //SharedPreferences sharedPreferences =
        //        getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE);
        //ve strings.xml do toho stringu dát jméno projektu, cz.uhk.folprle1.mapa

        startActivity(new Intent(this, MapsActivity.class));
    }
}
