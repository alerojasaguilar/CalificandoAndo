package com.example.android.proyectodai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    private String profesor;
    private Integer cuProf;
    private GridView gvComent;
    private TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String comentario;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        gvComent = (GridView) findViewById(R.id.gvComentarios);
        nombre = (TextView) findViewById(R.id.tvNombre);
        Bundle bundle = this.getIntent().getExtras();
        profesor = bundle.get("nombre").toString();
        nombre.setText(profesor);
        //Primero buscamos al profesor para obtener su clave
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "profesores", null, 1);
        admin.llenarProfesores();
        admin.llenarComentarios();
        SQLiteDatabase bd = admin.getWritableDatabase();
        String query1 = "select cu from profesores where nombre='" + profesor.toString() + "'";
        Cursor fila1 = bd.rawQuery(query1, null);
        try {
            if (fila1 != null) {
                if (fila1.moveToNext()) {
                    //Obtenemos la clave del profesor
                    cuProf = Integer.parseInt(fila1.getString(0));
                }
                //Luego creamos la lista para guardar todos los comentarios
                ArrayList<String> lista = new ArrayList<>();
                //Obtenemos los comentarios del profesor
                String query = "select distinct comentario from comentarios where cuProf=" + cuProf + "";
                Cursor fila = bd.rawQuery(query, null);
                if (fila != null) {
                    while (fila.moveToNext()) {
                        comentario = fila.getString(0);
                        lista.add(comentario);
                        //Vamos agregando los comentarios a la lista
                    }
                    //Usamos el adapter para poner la informacion en la GridView
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista);
                    gvComent.setAdapter(dataAdapter);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(this, "Ese profesor no existe", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getCause() + ". " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void agregarCom(View v) {
        //Creamos el intent
        Intent intent = new Intent(this, Main5Activity.class);
        //Creamos el bundle y guardamos la clave del profesor
        Bundle b = new Bundle();
        b.putInt("cuProf", cuProf);
        intent.putExtras(b);
        //Lo mandamos a la siguiente ventana
        startActivity(intent);
    }
}
