package com.example.android.proyectodai;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {
    //Esta es la ventana para registrarse. Creamos todas las variables
    private EditText cu, nom, cor, con, car;
    String nombre, carrera, correo, contraseña;
    int clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //Ligamos todos los EditText para tener acceso a ellos
        nom = (EditText) findViewById(R.id.etNombre);
        car = (EditText) findViewById(R.id.etCarrera);
        cu = (EditText) findViewById(R.id.etCU);
        cor = (EditText) findViewById(R.id.etCorreo);
        con = (EditText) findViewById(R.id.etContraseña);
    }

    public void limpiar(View v) {
        //Despues de cada registro limpiamos los campos para dejarlo como nuevo
        cu.setText("");
        nom.setText("");
        cor.setText("");
        con.setText("");
        car.setText("");
    }

    public void registro(View v) {
        try {
            //Creamos las variables necesarias
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //Obtenemos la informacion
            clave = Integer.parseInt(cu.getText().toString());
            nombre = nom.getText().toString();
            carrera = car.getText().toString();
            correo = cor.getText().toString();
            contraseña = con.getText().toString();
            //Creamos el registro
            ContentValues registro = new ContentValues();
            //Metemos toda la informacion en el registro
            if (checarCampos()) { //Llamamos este metodo para checar que no haya campos vacios
                registro.put("cu", clave);
                registro.put("nombre", nombre);
                registro.put("correo", correo);
                registro.put("contraseña", contraseña);
                registro.put("carrera", carrera);
                //Agregamos a la tabla alumnos el nuevo registro
                bd.insert("alumnos", null, registro);
                bd.close();
                //Limpiamos los campos
                limpiar(v);
                //Avisamos que se realizo de manera correcta el registro
                Toast.makeText(this, "Has sido registrado. Ahora puedes ingresar.", Toast.LENGTH_LONG).show();

            } else {
                //Si falta algun campo se lo avisamos al usuario
                Toast.makeText(this, "Error. Porfavor llena todos los campos para registrarte", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) { //Si la clave unica no es un numero mandamos esta exception
            Toast.makeText(this, "Clave unica no valida", Toast.LENGTH_LONG).show();
        } catch (Exception e) { //Si es otro tipo de exception, mandamos la causa y el mensaje
            Toast.makeText(this, "error " + e.getCause() + ", " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Usamos este metodo para checar que todos los campos se llenen para hacer el registro correctmente
    private boolean checarCampos() {
        boolean res = true;
        //Si alguno esta vacio, regresamos falso
        if (nombre.equals("") || carrera.equals("") || correo.equals("") || contraseña.equals("")) {
            res = false;
        }
        return res;
    }
}
