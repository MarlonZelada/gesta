package com.mayatech.gesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    DatabaseReference usuarioDatos;

    int contExtra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        usuarioDatos = FirebaseDatabase.getInstance().getReference("usuario");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(Splash.this, Login.class);
                //startActivity(intent);
                //finish();
                buscar_Datos_Login();
            }
        },1500);
    }

    private void buscar_Datos_Login() {

        SharedPreferences miPreferenciaCompartida = getSharedPreferences("DatosLogin", MODE_PRIVATE);
        final String usuario = miPreferenciaCompartida.getString("usuario", "No existe");
        final boolean mEstado = miPreferenciaCompartida.getBoolean("sesion", false);
        if (mEstado){

            Query qUsuario = usuarioDatos.orderByChild("usuario").equalTo(usuario);

            qUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int cont = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        cont++;
                        String clave = ds.getKey();
                        //Toast.makeText(Splash.this, "Encontrados " + cont, Toast.LENGTH_SHORT).show();
                    }
                    contExtra = cont;
                    //Toast.makeText(Splash.this, "Valor de contador Extra " + contExtra, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Splash.this, "Usuario " + usuario, Toast.LENGTH_SHORT).show();
                    if(cont>=1){
                        //Toast.makeText(Splash.this, "Si puede Buscar ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Splash.this, Buscar.class);
                        startActivity(intent1);
                        finish();

                    }else{
                        //Toast.makeText(Splash.this, "No puede Ingresar ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                        finish();
                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });





            /*Intent intent1 = new Intent(Splash.this, MainActivity.class);
            startActivity(intent1);
            finish();*/
        }else{
            Intent intent2 = new Intent(Splash.this, Login.class);
            startActivity(intent2);
            finish();
        }


    }




}
