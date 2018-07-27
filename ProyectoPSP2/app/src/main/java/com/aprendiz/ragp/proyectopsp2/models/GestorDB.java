package com.aprendiz.ragp.proyectopsp2.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aprendiz.ragp.proyectopsp2.utilities.Constants;

public class GestorDB extends SQLiteOpenHelper {
    public GestorDB(Context context) {
        super(context, Constants.DATABASE_NAME, null,Constants.DATABASE_VALUE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TableProject);
        db.execSQL(Constants.TableTimeLog);
        db.execSQL(Constants.TableDefectLog);
        db.execSQL(Constants.TableProjectPlanSummary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
