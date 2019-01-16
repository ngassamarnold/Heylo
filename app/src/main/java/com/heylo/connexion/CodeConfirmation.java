package com.heylo.connexion;

import android.os.Bundle;
import android.app.Activity;

import com.goodiebag.pinview.Pinview;
import com.heylo.R;

public class CodeConfirmation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_confirmation);
        Pinview pin = (Pinview) findViewById(R.id.pinview);
        pin.setPinBackgroundRes(R.drawable.sample_background);
    }

}
