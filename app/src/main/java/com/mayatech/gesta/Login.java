package com.mayatech.gesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText txtUsuario;
    EditText txtPassword;
    String nombre = "Marlon Zelada";

    String miUsuario, miPassword, miDueño;


    Button btningresar;
    RadioButton radioIngresar;
    private boolean radioActivado;


    //Button bPrueba;
    boolean mEstado = true;



    //GUARDAR SESION
    private static final String STRING_PREFERENCES = "prueba3";
    private static final String PREFERENCE_ESTADO_BOTON = "estado.button.sesion";
    DatabaseReference usuarioDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText) findViewById(R.id.edtUsuario);
        txtPassword = (EditText) findViewById(R.id.edtPassword);

        btningresar = (Button) findViewById(R.id.btnIngresar);
        radioIngresar = (RadioButton) findViewById(R.id.radioGuardar);


        //bPrueba = (Button) findViewById(R.id.prueba);



        radioActivado = radioIngresar.isChecked();

        radioSesion();
        botonSesion();

        //      botonPrueba();




        usuarioDatos = FirebaseDatabase.getInstance().getReference("usuario");



    }


    private void botonSesion() {
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                /*if(obtenerEstadoButton()){
                    Toast.makeText(Login.this, "Estado de boton: true", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "Estado de boton: false", Toast.LENGTH_SHORT).show();
                }*/

                final String usuario = txtUsuario.getText().toString();
                final String password = txtPassword.getText().toString();
                if(!TextUtils.isEmpty(usuario)){
                    if(!TextUtils.isEmpty(password)){
                        Query qUsuario = usuarioDatos.orderByChild("usuario").equalTo(usuario);
                        qUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int cont=0;
                                //Cuenta doc =dataSnapshot.getValue(Cuenta.class);
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    cont++;
                                    String clave = ds.getKey();
                                    //miCliente = ds.child(getString(R.string.nodo_cliente)).getValue().toString();
                                    //miEstado = ds.child(getString(R.string.nodo_estado)).getValue().toString();
                                    //miCuenta = palabra1;
                                    miUsuario = ds.child("usuario").getValue().toString();
                                    miPassword = ds.child("password").getValue().toString();
                                    miDueño = ds.child("nombre").getValue().toString();
                                    //Toast.makeText(Login.this, "Encontrados " + cont, Toast.LENGTH_SHORT).show();
                                }
                                if(cont<1){
                                    Toast.makeText(Login.this, "Usuario o Password incorrectos", Toast.LENGTH_SHORT).show();

                                }else{
                                    //Toast.makeText(Login.this, "Si existe", Toast.LENGTH_SHORT).show();
                                    if(usuario.equals(miUsuario) && password.equals(miPassword)){
                                        //Toast.makeText(Login.this, "Bienvenido " + miDueño, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Login.this, "Bienvenido " + miDueño, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent (view.getContext(), Buscar.class);

                                        SharedPreferences miPreferenciaCompartida = getSharedPreferences("DatosLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = miPreferenciaCompartida.edit();

                                        editor.putString("nombre", miDueño);
                                        editor.putBoolean("mEstado", mEstado);
                                        editor.putString("usuario", miUsuario);
                                        editor.putBoolean("sesion", radioActivado);
                                        editor.commit();

                                        startActivityForResult(intent, 0);
                                        finish();


                                    }else{
                                        Toast.makeText(Login.this, "Usuario o Password incorrectos", Toast.LENGTH_SHORT).show();
                                    }





                                    //btn_Modificar.setVisibility(View.VISIBLE);
                                }
                                //text_Vercliente.setText(miCliente);
                                //text_Verestado.setText(miEstado);*/
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                       /*if(usuario.equals(miUsuario) && password.equals(miPassword)){
                            Toast.makeText(Login.this, "Bienvenido " + miDueño, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (view.getContext(), MainActivity.class);

                           SharedPreferences miPreferenciaCompartida = getSharedPreferences("DatosLogin", MODE_PRIVATE);
                           SharedPreferences.Editor editor = miPreferenciaCompartida.edit();

                           editor.putString("nombre", miDueño);
                           editor.putBoolean("mEstado", mEstado);
                           editor.putString("usuario", miUsuario);
                           editor.putBoolean("sesion", radioActivado);
                           editor.commit();

                            startActivityForResult(intent, 0);
                            finish();


                        }else{
                            Toast.makeText(Login.this, "Usuario o Password incorrectos", Toast.LENGTH_SHORT).show();
                        }*/

                        Query qPassword = usuarioDatos.orderByChild("password").equalTo(password);



                    }else{

                    }
                }else{

                }

            }
        });
    }

    private void radioSesion() {
        radioIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(radioActivado){
                    radioIngresar.setChecked(false);
                    //Toast.makeText(Login.this, "Desactivado " + radioActivado, Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(Login.this, "Activado " + radioActivado, Toast.LENGTH_SHORT).show();
                }


                radioActivado = radioIngresar.isChecked();
            }
        });

    }




}
