package com.mayatech.gesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.gesta.Models.Comentario;
import com.mayatech.gesta.Models.Comentario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Modificar extends AppCompatActivity {

    TextView text_cuenta;
    TextView info;
    EditText text_comentario;
    Spinner estadoTarjeta;
    String estadoNuevo;
    TextView cuentaModificar;
    TextView estado_actual;

    ImageButton btn_Guardar;
    ImageButton btn_Cancelar;

    Date fecha = new Date();
    DateFormat fechaHora = new SimpleDateFormat("dd-MM-yyyy");
    String convertirFecha = fechaHora.format(fecha);

    //String[] items = {"Solicitada", "Entregada", "Rechazada"};


    DatabaseReference mData, mEstados, mComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        cuentaModificar = (TextView) findViewById(R.id.verCuentaCliente);
        info = (TextView) findViewById(R.id.verNombreCliente);
        estado_actual = (TextView) findViewById(R.id.txtVerEstadoActual);
        text_comentario = (EditText) findViewById(R.id.edtComentario);
        btn_Guardar = (ImageButton) findViewById(R.id.btnGuardar);
        btn_Cancelar = (ImageButton) findViewById(R.id.btnCancelar);
        text_cuenta = (TextView) findViewById(R.id.verCuentaCliente);

        mData = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_cliente));
        mEstados = FirebaseDatabase.getInstance().getReference();
        mComentarios = FirebaseDatabase.getInstance().getReference("comentarios");

        modificarEstado();
        cancelarCambios();
        verUsuarioNombre();






        estadoTarjeta = (Spinner) findViewById(R.id.spinnerEstadoTarjeta);
        //estadoTarjeta = getResources().getStringArray(R.array.lista_estado);

        estadoTarjeta.setSelection(-1);

        estadoTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    String item = adapterView.getItemAtPosition(i).toString();
                    estadoNuevo = item;
                }else{
                    estadoNuevo = estado_actual.getText().toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });

        Bundle bundle = getIntent().getExtras();
        Bundle bundle2 = getIntent().getExtras();
        Bundle bundle3 = getIntent().getExtras();

        String dato = bundle.getString("clienteDatos").toString();
        String dato2 = bundle2.getString("clienteCuenta").toString();
        String dato3 = bundle3.getString("estadoActual").toString();

        info.setText(dato);
        cuentaModificar.setText(dato2);
        estado_actual.setText(dato3);

        estadoTarjeta.getSelectedItem();



    }

    private void cancelarCambios() {
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Buscar.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }

    private void modificarEstado() {
        btn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ultimoEstadoT = estado_actual.getText().toString();
                final String cuenta = text_cuenta.getText().toString();
                final String comentario = text_comentario.getText().toString();
                //final String nuevoComent = convertirFecha + " \n" + estadoNuevo +" \n" + comentario;
                final String nuevoComent = comentario;
                if(!TextUtils.isEmpty(cuenta)){

                    Query q = mData.orderByChild(getString(R.string.campo_cuenta)).equalTo(cuenta);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Cuenta cu2 = new Cuenta (comentario);
                            String clave="";

                            Comentario coment = new Comentario(convertirFecha, verUsuarioNombre(), nuevoComent, ultimoEstadoT, cuenta);
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                clave= ds.getKey();

                                mData.child(clave).child(getString(R.string.nodo_estado)).setValue(estadoNuevo);
                                mData.child(clave).child("ultimo_Comentario").setValue(nuevoComent);
                                mData.child(clave).child("ultimoEstado").setValue(ultimoEstadoT);
                                mData.child(clave).child("usuario").setValue(verUsuarioNombre());
                                mData.child(clave).child("fecha").setValue(convertirFecha);
                                //mData.child(clave).child("comentario").setValue(cu2);
                                //mData.child(clave).child("comentario").setValue(cu2);
                                //mData.child(clave).child("comentario").child("comentario");
                            }
                            /*
                            //String clave2 = mData.push().getKey();
                            //mData.child(clave).child("comentario").child(clave2).setValue(coment);*/
                            String clave2 = mEstados.child("comentario").push().getKey();
                            mEstados.child("comentario").child(clave2).setValue(coment);




                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    Toast.makeText(Modificar.this, "Modificado", Toast.LENGTH_SHORT).show();;
                    Intent intent = new Intent (view.getContext(), Buscar.class);
                    startActivityForResult(intent, 0);
                    finish();

                }else {
                    Toast.makeText(Modificar.this, "Introducir Cuenta", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    public String verUsuarioNombre(){
        SharedPreferences miPreferenciaCompartida = getSharedPreferences("DatosLogin", MODE_PRIVATE);

        String nombreUsuario = miPreferenciaCompartida.getString("nombre", "No existe el nombre");
        boolean mEstado = miPreferenciaCompartida.getBoolean("mEstado", false);

        //verUsuario.setText(nombreUsuario);
        //String re = Boolean.toString(mEstado);
        //verBool.setText(re);

        return nombreUsuario;
    }




}
