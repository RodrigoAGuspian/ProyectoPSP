package com.aprendiz.ragp.proyectopsp2.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aprendiz.ragp.proyectopsp2.R;

public class MenuProyecto extends AppCompatActivity implements View.OnClickListener{
    Button btnTime, btnDefect, btnPPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proyecto);
        btnTime = findViewById(R.id.btnTime);
        btnDefect = findViewById(R.id.btnDefect);
        btnPPS = findViewById(R.id.btnPPS);

        btnTime.setOnClickListener(this);
        btnDefect.setOnClickListener(this);
        btnPPS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnTime:
                intent = new Intent(MenuProyecto.this, TimeLog.class);
                startActivity(intent);
                break;

            case R.id.btnDefect:
                intent = new Intent(MenuProyecto.this, DefectLog.class);
                startActivity(intent);
                break;

            case R.id.btnPPS:

                break;



        }
    }
}
