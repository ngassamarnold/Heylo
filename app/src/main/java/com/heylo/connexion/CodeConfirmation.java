package com.heylo.connexion;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goodiebag.pinview.Pinview;
import com.heylo.BuildConfig;
import com.heylo.R;
import com.heylo.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CodeConfirmation extends Activity {
    private String telephone;
    private TextView tel, textcode, textCount, textSendAgainSms;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_confirmation);
        Pinview pin = (Pinview) findViewById(R.id.pinview);
        pin.setPinBackgroundRes(R.drawable.sample_background);

        countTime();

        requestQueue= Volley.newRequestQueue(this);

        Intent intent= getIntent();
        final String codeGenere= getIntent().getExtras().getString("code");
        telephone= getIntent().getExtras().getString("telephone");
        Toast.makeText(CodeConfirmation.this, codeGenere, Toast.LENGTH_SHORT).show();
        // on affiche le numéro de l'utilisateur dans la vue

        tel =(TextView)findViewById(R.id.my_number);
        textcode=(TextView)findViewById(R.id.text);
        tel.setText(getString(R.string.your_tel)+ telephone);
        textcode.setText(getString(R.string.code_confirmation));
        textCount=(TextView)findViewById(R.id.textCount);
        textSendAgainSms=(TextView)findViewById(R.id.send_code_agent);

        textSendAgainSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSendAgainSms.setText(null);
                countTime();
            }
        });


        /*
        *Lorsque l'utilisateur entre le code à 5 chiffres
         */
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
               final String code= pinview.getValue();
                //Toast.makeText(CodeConfirmation.this, code, Toast.LENGTH_SHORT).show();

                if (code.compareToIgnoreCase(codeGenere)==0){
                    Toast.makeText(CodeConfirmation.this,getString(R.string.succes_code), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                }
                else{
                    Toast.makeText(CodeConfirmation.this,getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void countTime(){
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textCount.setText(getString(R.string.TimeCount)+":"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                textSendAgainSms.setText(getString(R.string.send_code_agent));
            }
        }.start();
    }
    public void CheckNumber(final String telephone){
        stringRequest= new StringRequest(Request.Method.POST, BuildConfig.server + "valider-telephone", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String objetJson= jsonObject.getString("success");
                    //Toast.makeText(getApplicationContext(),"response-> : "+jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                    if(objetJson.equals("true")){
                        /*Toast.makeText(getApplicationContext(), "code-> "+jsonObject.getString("code"), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "telephone-> "+jsonObject.getString("telephone"), Toast.LENGTH_LONG).show();*/

                        //startActivity(new Intent(getApplicationContext(),CodeConfirmation.class));
                    }
                    else if(objetJson.equals("false")){
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Aucune connexion internet  " ,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("telephone", telephone);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
