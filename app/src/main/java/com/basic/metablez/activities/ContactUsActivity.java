package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basic.metablez.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtCallUs, txtWhatsApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initMain();
    }

    public void initMain(){
        txtWhatsApp = findViewById(R.id.txt_whatsapp_us);
        txtCallUs = findViewById(R.id.txt_call_us);

        txtWhatsApp.setOnClickListener(this);
        txtCallUs.setOnClickListener(this);
    }

    //Intent to send data over whatsapp
    public void whatsAppIntent(){
        Uri uri = Uri.parse("smsto:" + "+918171626533");
        Intent iWhatsapp = new Intent(Intent.ACTION_SENDTO, uri);
        iWhatsapp.putExtra("sms_body", "Hello, Team Meatblez!");
        iWhatsapp.setPackage("com.whatsapp");
        startActivity(iWhatsapp);
    }

    //To to open default calling app with specific no.
    public void callIntent(){
        Intent icall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+918171626533"));
        startActivity(icall);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_whatsapp_us:
                whatsAppIntent();
                break;
            case R.id.txt_call_us:
                callIntent();
                break;
        }
    }
}