package com.aprendiz.ragp.proyectopsp2.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aprendiz.ragp.proyectopsp2.R;
import com.aprendiz.ragp.proyectopsp2.models.CTimeLog;
import com.aprendiz.ragp.proyectopsp2.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLog extends AppCompatActivity implements View.OnClickListener{
    TextView txtStart, txtInterrupcion, txtStop, txtDelta, txtComments;
    Button btnStart, btnStop, btnInsert, btnUpdate, btnDelete, btnGuardar, btnAtras, btnAdelante;
    Spinner spPhase;
    int modo=1;
    List<CTimeLog> cTimeLogs = new ArrayList<>();
    Date dateStart, dateStop;
    int delta =0;
    int interrupcion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);
        modo=1;
        delta =0;
        interrupcion=0;
        inizialite();
        disable();
        inputPhase();
        selectTimeLog();
    }

    private void disable() {
        txtStart.setEnabled(false);
        txtStop.setEnabled(false);
        txtDelta.setEnabled(false);
    }

    private void selectTimeLog() {
        ManagerDB managerDB = new ManagerDB(this);
        cTimeLogs = managerDB.selectTimeLog(MainActivity.cProyecto.getId());

    }

    private void inputPhase() {
        List<String> phaseList = new ArrayList<>();
        phaseList.add("Planning");
        phaseList.add("Design");
        phaseList.add("Code");
        phaseList.add("Compile");
        phaseList.add("UT");
        phaseList.add("PM");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phaseList);
        spPhase.setAdapter(adapter);


    }

    private void inizialite() {
        txtStart = findViewById(R.id.txtStart);
        txtInterrupcion = findViewById(R.id.txtInterrupcion);
        txtStop = findViewById(R.id.txtStop);
        txtDelta = findViewById(R.id.txtDelta);
        txtComments = findViewById(R.id.txtCommentsT);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnInsert = findViewById(R.id.btnInsertT);
        btnUpdate = findViewById(R.id.btnUpdateT);
        btnDelete = findViewById(R.id.btnDeleteT);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnAtras = findViewById(R.id.btnAtras);
        btnAdelante = findViewById(R.id.btnAdelante);



        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
        btnAdelante.setOnClickListener(this);

        btnGuardar.setEnabled(true);

        spPhase = findViewById(R.id.spPhase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInsertT:
                btnGuardar.setText("Guardar");
                btnGuardar.setEnabled(true);
                btnAtras.setEnabled(false);
                btnAdelante.setEnabled(false);
                modo=1;

                break;

            case R.id.btnUpdateT:
                btnGuardar.setText("Editar");
                btnGuardar.setEnabled(true);
                btnAtras.setEnabled(true);
                btnAdelante.setEnabled(true);
                modo=2;
                updateData();
                break;

            case R.id.btnDeleteT:
                btnGuardar.setText("Eliminar");
                btnGuardar.setEnabled(false);
                btnAtras.setEnabled(true);
                btnAdelante.setEnabled(true);
                modo=3;

                break;

            case R.id.btnGuardar:
                switch (modo){
                    case 1:
                        inputData();
                        break;

                    case 2:
                        updateData();
                        break;

                    case 3:
                        deleteData();
                        break;
                }
                break;

            case R.id.btnStart:
                inputDateStart();
                break;

            case R.id.btnStop:
                inputDateStop();
                break;
        }
    }

    private void inputDateStart() {
        dateStart= new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(dateStart);
        txtStart.setText(fecha);
    }

    private void obtenerInterrupcion (){
        try{
            String tmp1 = txtInterrupcion.getText().toString();
            int tmp2 = Integer.parseInt(tmp1);
            interrupcion = tmp2;

        }catch (Exception e){
            interrupcion=0;
        }

    }

    private void inputDateStop() {
        dateStop = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(dateStop);
        txtStop.setText(fecha);
    }


    public void getDiferencia(){
        long diferencia = dateStop.getTime() - dateStart.getTime();

        //long miliSeg = 1000;
        //long minsSeg = miliSeg*60;
        //long horasSeg = minsSeg*60;
        //long diasMil = horasSeg*60;

        float tmp = (int) (((diferencia /60)/60)- interrupcion);
        obtenerInterrupcion();

        delta = (int) tmp;
        txtDelta.setText(Integer.toString(delta));


    }

    private void inputData() {
        CTimeLog ctimeLog = new CTimeLog();
        try {
            ctimeLog.setPhase(spPhase.getSelectedItem().toString());
            ctimeLog.setStart(txtStart.getText().toString());
            ctimeLog.setStop(txtStop.getText().toString());
            ctimeLog.setInterrupcion(txtInterrupcion.getText().toString());
            ctimeLog.setDelta(txtDelta.getText().toString());
            ctimeLog.setComments(txtComments.getText().toString());
            ctimeLog.setProyecto(MainActivity.cProyecto.getId());
            ManagerDB managerDB = new ManagerDB(this);
            managerDB.inputTimeLog(ctimeLog);
            selectTimeLog();


        }catch (Exception e){
            Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        if (cTimeLogs.size()>0) {
            CTimeLog ctimeLog = new CTimeLog();
            try {
                ctimeLog.setPhase(spPhase.getSelectedItem().toString());
                ctimeLog.setStart(txtStart.getText().toString());
                ctimeLog.setStop(txtStop.getText().toString());
                ctimeLog.setInterrupcion(txtInterrupcion.getText().toString());
                ctimeLog.setDelta(txtDelta.getText().toString());
                ctimeLog.setComments(txtComments.getText().toString());
                ctimeLog.setProyecto(MainActivity.cProyecto.getId());
                ManagerDB managerDB = new ManagerDB(this);
                managerDB.inputTimeLog(ctimeLog);
                selectTimeLog();


            } catch (Exception e) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "No hay TimeLogs para actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        if (cTimeLogs.size()>0) {
            ManagerDB managerDB = new ManagerDB(this);


        }else {
            Toast.makeText(this, "No hay TimeLogs para eliminar", Toast.LENGTH_SHORT).show();
        }
    }


}
