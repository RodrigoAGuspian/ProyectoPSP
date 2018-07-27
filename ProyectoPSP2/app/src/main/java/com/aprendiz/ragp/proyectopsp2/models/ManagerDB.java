package com.aprendiz.ragp.proyectopsp2.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {

    GestorDB gestorDB;
    SQLiteDatabase db;
    public ManagerDB(Context context) {
        gestorDB=new GestorDB(context);
    }

    public void openWriteDB(){
        db = gestorDB.getWritableDatabase();

    }

    public void openReadDB(){
        db = gestorDB.getReadableDatabase();

    }

    public void closeDB(){
        if (db!=null) {
            db.close();
        }
    }


    public void inputProyecto(Proyecto proyecto){
        openWriteDB();
        ContentValues values = new ContentValues();
        values.put("NOMBRE",proyecto.getProyecto());
        db.insert("PROYECTO",null,values);
        closeDB();

    }



    public List<Proyecto> selectProject(){
        openReadDB();
        List<Proyecto> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM PROYECTO",null);
        if (cursor.moveToFirst()){
            do {
                Proyecto cProyecto = new Proyecto();
                cProyecto.setId(cursor.getInt(0));
                cProyecto.setProyecto(cursor.getString(1));
                results.add(cProyecto);

            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();
        return results;
    }


    public List<CTimeLog> selectTimeLog(int proyecto){
        openReadDB();
        List<CTimeLog> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM TIMELOG WHERE PROYECTO="+proyecto+";",null);
        if (cursor.moveToFirst()){
            do {
                CTimeLog ctimeLog = new CTimeLog();
                ctimeLog.setId(cursor.getInt(0));
                ctimeLog.setPhase(cursor.getString(1));
                ctimeLog.setStart(cursor.getString(2));
                ctimeLog.setInterrupcion(cursor.getString(3));
                ctimeLog.setStop(cursor.getString(4));
                ctimeLog.setDelta(cursor.getString(5));
                ctimeLog.setComments(cursor.getString(6));
                ctimeLog.setProyecto(cursor.getInt(7));

                results.add(ctimeLog);

            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();

        return results;
    }


    public void inputTimeLog(CTimeLog cTimeLog){
        openWriteDB();
        ContentValues values = new ContentValues();
        values.put("PHASE",cTimeLog.getPhase());
        values.put("START",cTimeLog.getStart());
        values.put("INTERRUPCION",cTimeLog.getInterrupcion());
        values.put("STOP",cTimeLog.getStop());
        values.put("DELTA",cTimeLog.getDelta());
        values.put("COMMENTS",cTimeLog.getComments());
        values.put("PROYECTO",cTimeLog.getProyecto());

        try {
            db.insert("TIMELOG", null, values);
        }catch (Exception e){
        }

        closeDB();

    }

    public List<CDefectLog> selectDefectLog(int proyecto){
        List<CDefectLog> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DEFECTLOG WHERE PROYECTO="+proyecto+";",null);
        if (cursor.moveToFirst()){
            do {
                CDefectLog cDefectLog = new CDefectLog();
                cDefectLog.setId(cursor.getInt(0));
                cDefectLog.setDate(cursor.getString(1));
                cDefectLog.setFixtime(cursor.getString(2));
                cDefectLog.setFixtime(cursor.getString(3));
                cDefectLog.setPhaseI(cursor.getString(4));
                cDefectLog.setPhaseR(cursor.getString(5));
                cDefectLog.setComments(cursor.getString(6));
                cDefectLog.setProyecto(cursor.getInt(7));

                results.add(cDefectLog);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return results;
    }

    public void inputDefectLog(CDefectLog cDefectLog){
        ContentValues values = new ContentValues();
        values.put("DATE",cDefectLog.getDate());
        values.put("TYPE",cDefectLog.getType());
        values.put("FIXTIME",cDefectLog.getFixtime());
        values.put("PHASEINJECTED",cDefectLog.getPhaseI());
        values.put("PHASEREMOVED",cDefectLog.getPhaseR());
        values.put("COMMENTS",cDefectLog.getComments());
        values.put("PROYECTO",cDefectLog.getProyecto());
        try {
            db.insert("DEFECTLOG", null, values);
        }catch (Exception e){
        }

    }

    public void updateDefectLog(CDefectLog cDefectLog ){
        ContentValues values = new ContentValues();
        values.put("DATE",cDefectLog.getDate());
        values.put("TYPE",cDefectLog.getType());
        values.put("FIXTIME",cDefectLog.getFixtime());
        values.put("PHASEINJECTED",cDefectLog.getPhaseI());
        values.put("PHASEREMOVED",cDefectLog.getPhaseR());
        values.put("COMMENTS",cDefectLog.getComments());
        String [] parameters = {Integer.toString(cDefectLog.getId())};

        try {
            db.update("DEFECTLOG", values,"IDDEFECTLOG=?",parameters);
        }catch (Exception e){
            Log.e("Error Actualizar: ",e.getMessage());
        }

    }


}
