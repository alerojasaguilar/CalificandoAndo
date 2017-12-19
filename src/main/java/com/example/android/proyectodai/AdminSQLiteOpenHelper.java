package com.example.android.proyectodai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 01/12/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int i) {
        super(context, name, factory, i);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creamos todas las tablas
        sqLiteDatabase.execSQL("create table profesores(cu integer primary key autoincrement, nombre text, materia text)");
        sqLiteDatabase.execSQL("create table alumnos(cu integer primary key,nombre text, correo text, contraseña text, carrera text)");
        sqLiteDatabase.execSQL("create table comentarios(cu integer primary key autoincrement, comentario text, aprobado boolean, cuProf text references profesores)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {
        //Eliminamos las tablas si es necesario
        sqLiteDatabase.execSQL("drop table if exists alumnos");
        sqLiteDatabase.execSQL("drop table if exists profesores");
        sqLiteDatabase.execSQL("drop table if exists comentarios");
        //Las volvemos a crear
        sqLiteDatabase.execSQL("create table profesores(cu integer primary key autoincrement, nombre text, materia text)");
        sqLiteDatabase.execSQL("create table alumnos(cu integer primary key,nombre text, correo text, contraseña text, carrera text)");
        sqLiteDatabase.execSQL("create table comentarios(cu integer primary key autoincrement, comentario text, cuProf text references profesores)");
    }

    //Metodo para llenar la tabla de profesores
    public void llenarProfesores() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        //Agregamos algunos profesores
        valores.put("nombre", "Marco Chacon");
        valores.put("materia", "Calculo III");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Gonzalo Zamarripa");
        valores.put("materia", "Probabilidad");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Montserrat Tenorio");
        valores.put("materia", "Probabilidad");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Luis Gomez");
        valores.put("materia", "Estructura de datos");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Mariana Martinez");
        valores.put("materia", "Calculo II");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Manuel Verdin");
        valores.put("materia", "Derecho");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Emiliano Ramirez");
        valores.put("materia", "Economia I");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Ximena Jimenez");
        valores.put("materia", "Economia III");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Angel Rojas");
        valores.put("materia", "Economia II");
        db.insert("profesores", null, valores);
        valores.clear();

        valores.put("nombre", "Vicente Pacheco");
        valores.put("materia", "Economia I");
        db.insert("profesores", null, valores);
        valores.clear();
        //Cerramos la base de datos
        db.close();
    }

    public void llenarComentarios() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        //Agregamos algunos comentarios
        valores.put("comentario", "Excelente profesor");
        valores.put("cuProf", "1");
        db.insert("comentarios", null, valores);
        valores.clear();

        valores.put("comentario", "Pesimo profesor");
        valores.put("cuProf", "2");
        db.insert("comentarios", null, valores);
        valores.clear();

        valores.put("comentario", "Buen maestro.");
        valores.put("cuProf", "5");
        db.insert("comentarios", null, valores);
        valores.clear();

        valores.put("comentario", "Deja demasiada tarea");
        valores.put("cuProf", "3");
        db.insert("comentarios", null, valores);
        valores.clear();

        valores.put("comentario", "Super favoritista");
        valores.put("cuProf", "6");
        db.insert("comentarios", null, valores);
        valores.clear();

        valores.put("comentario", "Extremadamente barco");
        valores.put("cuProf", "4");
        db.insert("comentarios", null, valores);
        valores.clear();
        //Cerramos la base de datos
        db.close();
    }
}
