package com.mayatech.gesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.mayatech.gesta.Models.RecuperarComentario;
import com.mayatech.gesta.Models.RecuperarComentario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Buscar extends AppCompatActivity {

    //Listar comentarios;
    private List<RecuperarComentario> listCuenta = new ArrayList<>();
    ArrayAdapter<RecuperarComentario> arrayAdapterCuenta;

    private List<RecuperarComentario> listVacia = new ArrayList<>();
    ArrayAdapter<RecuperarComentario> arrayVacio;

    ListView listV_comentarios;


    ImageButton cerrarSesion;
    ImageButton btn_limpiarCampos;



    //TextView titulo de los Comentario
    TextView comentario;




    /*tarjeta*/
    EditText text_cuenta;
    EditText text_cliente;
    EditText text_estado;
    EditText text_comentario;
    TextView text_Vercliente;
    TextView text_Verestado;
    TextView text_VerTipoCuenta;
    //Button btn_GuardarTarjeta;
    ImageButton btn_Modificar;
    ImageButton btn_buscar;
    Spinner sp_EstadosTarjeta;

    String estadoNuevo;
    String miCliente, miEstado, miCuenta, miTipo;

    Date fecha = new Date();
    DateFormat fechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    String convertirFecha = fechaHora.format(fecha);

    Boolean radioActivado = false;




    //Referencia a la Base de Datos
    DatabaseReference mData, mEstados, mComentarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        //*tarjeta*/
        text_cuenta = (EditText) findViewById(R.id.edtCuenta);
        //text_cliente = (EditText) findViewById(R.id.edtCliente);
        //text_estado = (EditText) findViewById(R.id.edtEstado);
        //text_comentario = (EditText) findViewById(R.id.edtComentario);
        text_Vercliente = (TextView) findViewById(R.id.txtVerNombre);
        text_Verestado = (TextView) findViewById(R.id.txtVerEstado);
        text_VerTipoCuenta = (TextView) findViewById(R.id.txtVerTipoTarjeta);
        //btn_GuardarTarjeta = (Button) findViewById(R.id.btnGuardarTarjeta);
        btn_Modificar = (ImageButton) findViewById(R.id.btnModificar);
        btn_buscar = (ImageButton) findViewById(R.id.btnBuscar);
        btn_limpiarCampos = (ImageButton) findViewById(R.id.btnLimpiarCampos);
        cerrarSesion = (ImageButton) findViewById(R.id.btnCerrarSesion);
        //sp_EstadosTarjeta = (Spinner) findViewById(R.id.spEstadosTarjeta);

        //Listar Comentarios
        listV_comentarios = (ListView) findViewById(R.id.listaComentarios);

        comentario = (TextView) findViewById(R.id.etiqueComentarios);




        mData = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_cliente));
        mEstados = FirebaseDatabase.getInstance().getReference();
        mComentarios = FirebaseDatabase.getInstance().getReference("comentarios");



        //guardarNuevo();
        //modificarEstado();
        //cargarEstados();
        buscarDatos();
        pasarModificar();
        cerrarSesionActual();
        limpiarCampos();

        //listarDatos();





    }

    private void limpiarCampos() {
        btn_limpiarCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*listCuenta.clear();
                text_Vercliente.setText("");
                text_Verestado.setText("");
                text_VerTipoCuenta.setText("");
                btn_Modificar.setVisibility(View.INVISIBLE);
                listV_comentarios.setVisibility(View.INVISIBLE);*/
                //String vacio="";

                //listVacia.clear();
                //arrayVacio = new ArrayAdapter<RecuperarComentario>(Buscar.this, android.R.layout.simple_list_item_1, listCuenta);

                //listV_comentarios.setAdapter(arrayVacio);

                Intent intent1 = new Intent(Buscar.this, Buscar.class);
                startActivity(intent1);
                finish();



            }
        });
    }

    private void cerrarSesionActual() {

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences miPreferenciaCompartida = getSharedPreferences("DatosLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = miPreferenciaCompartida.edit();

                editor.putBoolean("sesion", radioActivado);
                editor.commit();
                Intent intent1 = new Intent(Buscar.this, Login.class);
                startActivity(intent1);
                finish();



            }
        });

    }

    private void buscarDatos() {
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String palabra1 = text_cuenta.getText().toString().trim();
                Query q = mData.orderByChild(getString(R.string.campo_cuenta)).equalTo(palabra1);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont=0;
                        //Cuenta doc =dataSnapshot.getValue(Cuenta.class);
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            cont++;
                            String clave = ds.getKey();
                            miCliente = ds.child("cliente").getValue().toString();
                            miEstado = ds.child("estado").getValue().toString();
                            miTipo = ds.child("tipo_tarjeta").getValue().toString();
                            miCuenta = palabra1;
                            //Toast.makeText(MainActivity.this, "Encontrados " + cont, Toast.LENGTH_SHORT).show();
                        }
                        text_Vercliente.setText(miCliente);
                        text_Verestado.setText(miEstado);
                        text_VerTipoCuenta.setText(miTipo);
                        if(cont<1){
                            Toast.makeText(Buscar.this, "No se encontro", Toast.LENGTH_SHORT).show();
                        }else{
                            listarDatos(palabra1);
                            comentario.setVisibility(View.VISIBLE);
                            btn_Modificar.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private void listarDatos(String cuentaListar) {
        //mEstados.child("padre").child("hijo").child("nieto").addListenerForSingleValueEvent(new ValueEventListener(){
        //mEstados.child("padre").child("hijo").child("nieto").orderByChild("usuario").equalTo("Jose").addListenerForSingleValueEvent(new ValueEventListener(){
        mEstados.child("comentario").orderByChild("cuenta").equalTo(cuentaListar).addListenerForSingleValueEvent(new ValueEventListener(){
            //mEstados.child("cliente").orderByChild("comentario").equalTo("123").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCuenta.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    RecuperarComentario rC = ds.getValue(RecuperarComentario.class);
                    listCuenta.add(rC);
                    arrayAdapterCuenta = new ArrayAdapter<RecuperarComentario>(Buscar.this, android.R.layout.simple_list_item_1, listCuenta);

                    listV_comentarios.setAdapter(arrayAdapterCuenta);
                }
                Collections.reverse(listCuenta);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void pasarModificar() {
        btn_Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Modificar.class);
                intent.putExtra("clienteDatos", miCliente);
                intent.putExtra("clienteCuenta", miCuenta);
                intent.putExtra("estadoActual", miEstado);
                startActivityForResult(intent, 0);
                btn_Modificar.setVisibility(View.INVISIBLE);
                finish();

            }
        });
    }



}
