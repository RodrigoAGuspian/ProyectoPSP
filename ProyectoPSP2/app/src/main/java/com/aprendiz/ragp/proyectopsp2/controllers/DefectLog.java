package com.aprendiz.ragp.proyectopsp2.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp2.R;
import com.aprendiz.ragp.proyectopsp2.models.CDefectLog;
import com.aprendiz.ragp.proyectopsp2.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectLog extends AppCompatActivity implements View.OnClickListener{
    TextView txtDate, txtFixTime, txtComments;
    ImageButton btnPlay, btnPause, btnStop;
    Spinner spType, spPhaseI, spPhaseR;
    Button btnGuardar, btnAtras, btnAdelante, btnInsert, btnUpdate, btnDelete, btnDate;
    Thread thread;
    boolean bandera = false;
    List<CDefectLog> cDefectLogs = new ArrayList<>();
    int []tiempo = {0,0};
    int modo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);
        bandera = false;
        tiempo = new int []{0,0};
        modo=0;
        inizialite();
        onClickThis();
        disable();
        inputList();
        chronometer();
    }

    private void chronometer() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera==true){
                    try {
                        {
                            Thread.sleep(1000);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tiempo[0]++;

                                if (tiempo[0]==60){
                                    tiempo[1]++;
                                    tiempo[0]=0;
                                }


                                if (tiempo[0]>=0 && tiempo[0]<10){
                                    if (tiempo[0]>=0 && tiempo[0]<10){
                                        txtFixTime.setText("0"+tiempo[1]+":"+"0"+tiempo[0]);

                                    }else {
                                        txtFixTime.setText(tiempo[1]+":"+"0"+tiempo[0]);
                                    }

                                }

                                if (tiempo[0]>=10 && tiempo[0]<60){
                                    if (tiempo[0]>=0 && tiempo[0]<10){
                                        txtFixTime.setText("0"+tiempo[1]+":"+tiempo[0]);

                                    }else {
                                        txtFixTime.setText(tiempo[1]+":"+tiempo[0]);
                                    }
                                }



                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    private void inputList() {
        List<String> phaseList = new ArrayList<>();
        phaseList.add("Planning");
        phaseList.add("Design");
        phaseList.add("Code");
        phaseList.add("Compile");
        phaseList.add("UT");
        phaseList.add("PM");

        List<String> typeList = new ArrayList<>();
        typeList.add("Documentation");
        typeList.add("Syntax");
        typeList.add("Build");
        typeList.add("Package");
        typeList.add("Assigment");
        typeList.add("Interface");
        typeList.add("Checking");
        typeList.add("Data");
        typeList.add("Fuction");
        typeList.add("System");
        typeList.add("Environment");

        ArrayAdapter<String> adapterPhase= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,phaseList);
        ArrayAdapter<String> adapterType= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,typeList);

        spType.setAdapter(adapterType);
        spPhaseI.setAdapter(adapterPhase);
        spPhaseR.setAdapter(adapterPhase);

    }

    private void disable() {
        txtDate.setEnabled(false);
        txtFixTime.setEnabled(false);

    }


    private void inizialite() {
        txtDate = findViewById(R.id.txtDateD);
        txtFixTime = findViewById(R.id.txtFixTimeD);
        txtComments = findViewById(R.id.txtCommentsD);

        btnPlay = findViewById(R.id.imgPlayD);
        btnPause = findViewById(R.id.imgPauseD);
        btnStop = findViewById(R.id.imgStopD);

        spType = findViewById(R.id.spTypeD);
        spPhaseI = findViewById(R.id.spPhaseI);
        spPhaseR = findViewById(R.id.spPhaseR);

        btnGuardar = findViewById(R.id.btnGuardar2);
        btnAtras = findViewById(R.id.btnAtras2);
        btnAdelante = findViewById(R.id.btnAdelante2);

        btnInsert = findViewById(R.id.btnInsertD);
        btnUpdate = findViewById(R.id.btnUpdateD);
        btnDelete = findViewById(R.id.btnDeleteD);
        btnDate = findViewById(R.id.btnDateD);

    }

    public void go_chronometer(){
        bandera=true;
    }

    public void pause_chronometer(){
        bandera=false;
    }

    public void stop_chronometer(){
        bandera=false;

    }


    private void onClickThis() {
        btnDate.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDateD:
                obtenerHora();
                break;

            case R.id.imgPlayD:
                go_chronometer();
                break;

            case R.id.imgPauseD:
                pause_chronometer();
                break;

            case R.id.imgStopD:
                stop_chronometer();
                break;


            case R.id.btnInsertD:
                btnGuardar.setText("Guardar");
                btnGuardar.setEnabled(true);
                btnAtras.setEnabled(false);
                btnAdelante.setEnabled(false);
                modo=1;

                break;

            case R.id.btnUpdateD:
                btnGuardar.setText("Editar");
                btnGuardar.setEnabled(true);
                btnAtras.setEnabled(true);
                btnAdelante.setEnabled(true);
                modo=2;

                break;

            case R.id.btnDeleteD:
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


        }
    }


    private void obtenerHora() {
        Date date = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(date);
        txtDate.setText(fecha);
    }

    private void selectDefectLog() {
        ManagerDB managerDB = new ManagerDB(this);
        cDefectLogs = managerDB.selectDefectLog(MainActivity.cProyecto.getId());

    }

    private void inputData() {
        ManagerDB managerDB = new ManagerDB(this);
        CDefectLog cDefectLog = new CDefectLog();
        cDefectLog.setDate(txtDate.getText().toString());
        cDefectLog.setType(spType.getSelectedItem().toString());
        cDefectLog.setFixtime(txtFixTime.getText().toString());
        cDefectLog.setComments(txtComments.getText().toString());
        cDefectLog.setPhaseI(spPhaseI.getSelectedItem().toString());
        cDefectLog.setPhaseR(spPhaseR.getSelectedItem().toString());
        cDefectLog.setProyecto(MainActivity.cProyecto.getId());
        managerDB.inputDefectLog(cDefectLog);
    }

    private void updateData() {
        ManagerDB managerDB = new ManagerDB(this);
        CDefectLog cDefectLog = new CDefectLog();
        cDefectLog.setDate(txtDate.getText().toString());
        cDefectLog.setType(spType.getSelectedItem().toString());
        cDefectLog.setFixtime(txtFixTime.getText().toString());
        cDefectLog.setComments(txtComments.getText().toString());
        cDefectLog.setPhaseI(spPhaseI.getSelectedItem().toString());
        cDefectLog.setPhaseR(spPhaseR.getSelectedItem().toString());
        cDefectLog.setProyecto(MainActivity.cProyecto.getId());
        managerDB.updateDefectLog(cDefectLog);
    }

    private void deleteData() {
    }

}
