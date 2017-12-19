package com.example.android.proyectodai;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {
    private EditText comentario;
    private int cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        comentario = (EditText) findViewById(R.id.etCom);
        Bundle bundle = this.getIntent().getExtras();
        //Obtenemos la clave unica
        cu=bundle.getInt("cu");
    }

    public void agregarComentario(View v) {
        String com;
        try {
            //Creamos las variables necesarias
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "comentarios", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //Obtenemos el comentario
            com = comentario.getText().toString();
            //Creamos el registro
            ContentValues registro = new ContentValues();
            //Metemos la informacion en el registro
            if (checarCampo()) { //Llamamos este metodo para checar que el comentario no este vacio
                registro.put("comentario", com);
                registro.put("aprobado", false);
                registro.put("cuProf", cu);
                //Agregamos a la tabla alumnos el nuevo registro
                bd.insert("comentarios", null, registro);
                bd.close();
                //Avisamos que se realizo de manera correcta el alta
                Toast.makeText(this, "El comentario ha sido agregado. Cuando sea aprobado, será agregado.", Toast.LENGTH_LONG).show();
            } else {
                //Si el campo del comentario esta vacio, se lo avisamos al usuario
                Toast.makeText(this, "Error. Porfavor escribe tu comentario", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getCause() + ", " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //Usamos este metodo para checar que si haya un comentario escrito
    private boolean checarCampo() {
        boolean res = true;
        //Si alguno esta vacio, regresamos falso
        if (comentario.equals("")) {
            res = false;
        }
        return res;
    }
}
