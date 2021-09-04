package com.basic.delivery_partner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.basic.delivery_partner.R

class LoginActivity : AppCompatActivity() {
    lateinit var btnContinue: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI();

        btnContinue.setOnClickListener(View.OnClickListener {
            var iVehicle: Intent = Intent(applicationContext,  VehicleLocalityActivity:: class.java)
            startActivity(iVehicle)
        })
    }

    //For initialize all the views
    private fun initUI(){
        btnContinue = findViewById(R.id.btn_continue)
    }
}