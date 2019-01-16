package com.heylo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import java.util.Random;

public class PinSmsActivity extends AppCompatActivity {

    PinEntryEditText pinEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_sms);

        pinEntry = findViewById(R.id.txt_pin_entry);

        //Genere un nombre aleatoire
        Random random = new Random();
        int number = 1000 + random.nextInt(8999);
        final String pin = Integer.toString(number);

        Toast.makeText(PinSmsActivity.this,"PIN = "+number,Toast.LENGTH_LONG).show();

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {

                    if (str.toString().equals(pin)) {
                        //TODO
                        /*
                        Ici la comparaison du pin du sms a reuissi
                         */
                        Toast.makeText(PinSmsActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        
                    } else {
                        //TODO
                        /*
                        traitement des echec
                         */
                        Toast.makeText(PinSmsActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });
        }

    }
}
