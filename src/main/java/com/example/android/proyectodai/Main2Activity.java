package com.example.android.proyectodai;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {
    private Spinner materia;
    private GridView profesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        materia = (Spinner) findViewById(R.id.spMateria);
        llenarSpinnerMateria(); //Llamamos al metodo para llenar el spinner con las materias disponibles
        profesores = (GridView) findViewById(R.id.gvProfesores);
    }

    //Metodo para llenar el spinner con las materias de la base de datos
    public void llenarSpinnerMateria() {
        ArrayList<String> materias = getMaterias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materia.setAdapter(adapter);
    }

    public void buscar(View v) {
        String materiaStr, nombre;
        List<String> lista = new ArrayList<>();
        materiaStr = materia.getSelectedItem().toString();

        //Checamos que si haya profesores que cumplan con esos criterios
        String query = "select distinct nombre from profesores where materia ='" + materiaStr + "'";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "profesores", null, 1);
        admin.llenarProfesores();
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(query, null);
        try {
            if (fila != null) {
                //Mientras haya profesores, los agregamos a la lista
                while (fila.moveToNext()) {
                    nombre = fila.getString(0);
                    lista.add(nombre);
                }
                //Usamos el adapter para poner la informacion en la GridView
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista);
                profesores.setAdapter(dataAdapter);
                //Creamos un ActionListener para cuando seleccionen al profesor
                profesores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String profe = parent.getItemAtPosition(position).toString();
                        seleccionProfe(profe);
                    }
                });
            } else {
                Toast.makeText(this, "No hay profesores que cumplan ese criterio", Toast.LENGTH_SHORT).show();
            }
            fila.close();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getCause() + ". " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Mandamos al usuario a la ventana del profesor que selecciona
    public void seleccionProfe(String profesor) {
        Intent intent = new Intent(this, Main3Activity.class);
        Bundle b = new Bundle();
        //Mandamos el nombre del profesor para poder identificarlo
        b.putString("nombre", profesor);
        intent.putExtras(b);
        startActivity(intent);
    }

    //Metodo para obtener las materias de la base de datos
    public ArrayList<String> getMaterias() {
        ArrayList<String> materias = new ArrayList<>();
        String query = "select distinct materia from profesores";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "profesores", null, 1);
        admin.llenarProfesores();
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(query, null);
        //Mientras haya materias, las agregamos a la lista
        if (fila.moveToFirst()) {
            for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
                materias.add(fila.getString(fila.getColumnIndex("materia")));
            }
        } else {
            materias.add("Vacia");
        }
        return materias;
    }
}

