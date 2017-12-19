package com.example.android.proyectodai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText correo, contraseña;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ligamos los EditText y la WebView de la vista para poder acceder a ellos
        correo = (EditText) findViewById(R.id.etCorreo1);
        contraseña = (EditText) findViewById(R.id.etContraseña1);
        wv = (WebView) this.findViewById(R.id.webView);
        //Abrimos un navegador
        wv.setWebViewClient(new ClienteWebView());
        //Habilitamos el javascript
        wv.getSettings().setJavaScriptEnabled(true);
        //Le pasamos la direccion
        wv.loadUrl("https://www.itam.mx/");

    }

    public void entrar(View v) {
        String con, cor;
        Intent intent = new Intent(this, Main2Activity.class);
        Bundle b = new Bundle();

        //Obtenemos el correo y la contraseña con la que se quiere entrar
        cor = correo.getText().toString();
        con = contraseña.getText().toString();
        //Preparamos el bundle
        b.putString("correo", cor);
        b.putString("contraseña", con);
        intent.putExtras(b);
        //Checar que el usuario este registrado
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            Cursor fila = bd.rawQuery("select cu from alumnos where correo ='" + cor.toString() + "' and contraseña='" + con.toString() + "'", null);
            //Checamos que exista un usuario con ese correo y contraseña

            if (fila.moveToFirst()) {
                //Si existe lo mandamos a la siguiente ventana
                startActivity(intent);
            } else {
                //Si no existe avisamos al usuario que hubo un error
                Toast.makeText(this, "Correo o contraseña equivocada. Intentelo de nuevo.", Toast.LENGTH_LONG).show();
                limpiar(v);
            }
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void limpiar(View v) {
        //Despues de cada registro limpiamos los campos para dejarlo como nuevo
        correo.setText("");
        contraseña.setText("");
    }

    public void registrarse(View v) {
        //Creamos el intent y lo mandamos a la siguiente ventana
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }

    class ClienteWebView extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
