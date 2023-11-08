package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //creamos las variables para recoger lo que el usuario teclee en
    //la pantalla, de tipo edittext porque los campos son edittext
    EditText edn, eda, ede, eds, edt;

    Button guard, buspn, buspt,elim, act;

    //creamos la busqueda por medio de volley, tomando la informacion de android y
    //la pasamos al php
    RequestQueue requestQueue;
    //creamos las variables de tipo cadena para los datos de los edittext
    String nombre, apellido, edad, sexo, telefono;

    //creamos un cuadro de dialogo para indicar al usuario lo que se esta haciendo
    ProgressDialog progressDialog;

    //creamos la variable de tipo cadena que contendra la url de nuestra base de datos
    String HttpUrl="https://semana3ejemplo2.000webhostapp.com/ejemplo/conecta.php";



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //le asignamos los valores que se ingresen a los campos
        //a las variables que creamos
        edn=findViewById(R.id.edn);
        eda=findViewById(R.id.eda);
        ede=findViewById(R.id.ede);
        eds=findViewById(R.id.eds);
        edt=findViewById(R.id.edt);
        //creamos las conexiones
        nombre= String.valueOf(edn);
        apellido=String.valueOf(eda);
        edad=String.valueOf(ede);
        sexo=String.valueOf(eds);
        telefono=String.valueOf(edt);
        //conectamos el boton guardar
        guard=findViewById(R.id.btnguardar);
        //le decimos que el requestqueue es un codigo que nos permitira hacer una nueva cola
        //en la activiad que se llama mainactivity
        requestQueue= Volley.newRequestQueue(MainActivity.this);
        //y se establece una barra de progreso
        progressDialog=new ProgressDialog(MainActivity.this);

        guard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("por favor espere, se esta cargando la base de datos");
                progressDialog.show();

                //obtener los valores del edittext
                GetValuesFromEditText();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,

                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String ServerResponse) {
                                progressDialog.dismiss();

                                Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            }
                        },
                             new Response.ErrorListener() {
                                 private VolleyError error;

                                 @Override
                                 public void onErrorResponse(VolleyError volleyError) {

                                     progressDialog.dismiss();

                                     Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                 }
                             }){
                    @Override
                    protected Map<String, String> getParams(){

                        Map<String, String> params=new HashMap<String, String>();

                        params.put("nombre", nombre);
                        params.put("apellido", apellido);
                        params.put("edad", edad);
                        params.put("sexo", sexo);
                        params.put("telefono", telefono);

                        return params;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);

                requestQueue.add(stringRequest);
            }
        });

    }

    public void GetValuesFromEditText(){
        nombre=edn.getText().toString();
        apellido=eda.getText().toString();
        edad=ede.getText().toString();
        sexo=eds.getText().toString();
        telefono=edt.getText().toString();
    }
}