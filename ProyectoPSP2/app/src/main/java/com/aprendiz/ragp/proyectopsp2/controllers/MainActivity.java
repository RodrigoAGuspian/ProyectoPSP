package com.aprendiz.ragp.proyectopsp2.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp2.R;
import com.aprendiz.ragp.proyectopsp2.models.AdapterP;
import com.aprendiz.ragp.proyectopsp2.models.ManagerDB;
import com.aprendiz.ragp.proyectopsp2.models.Proyecto;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static Proyecto cProyecto = new Proyecto();
    RecyclerView recyclerView;
    FloatingActionButton btnAgregarP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        btnAgregarP = findViewById(R.id.btnAgregarP);
        cProyecto = new Proyecto();
        inputAdapter();

        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Agregar Proyecto");
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.item_proyectoa,null);
                final EditText txtProyecto = view.findViewById(R.id.txtNombreP);
                builder.setView(view);
                builder.setPositiveButton("Agreagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ManagerDB managerDB = new ManagerDB(MainActivity.this);
                            Proyecto proyecto = new Proyecto();
                            proyecto.setProyecto(txtProyecto.getText().toString());
                            managerDB.inputProyecto(proyecto);
                            dialog.cancel();
                            inputAdapter();
                        }catch (Exception e){
                            Snackbar.make(v,"Por favor ingrese un nombre",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });
    }

    private void inputAdapter() {
        ManagerDB managerDB = new ManagerDB(this);
        final List<Proyecto> proyectoList = managerDB.selectProject();
        AdapterP adapterP = new AdapterP(proyectoList);
        recyclerView.setAdapter(adapterP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapterP.setMlistener(new AdapterP.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                cProyecto = proyectoList.get(position);
                Intent intent = new Intent(MainActivity.this,MenuProyecto.class);
                startActivity(intent);
            }
        });
    }

}
