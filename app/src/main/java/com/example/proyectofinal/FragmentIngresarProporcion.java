package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentIngresarProporcion extends Fragment implements View.OnClickListener  {
    TextView ProprocionIngresada;
    TextView SemanasIngresadas;
    int ProprocionAInsertar;
    int SemanasAInsertar;
    Button boton;
    Button botonEliminar;
    Button botonVolver;

    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaADevolver;
        VistaADevolver = infladorDeLayouts.inflate(R.layout.layout_ingresar_datos_proporcion,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ProprocionIngresada = VistaADevolver.findViewById(R.id.IngresoProprocion);
        SemanasIngresadas = VistaADevolver.findViewById(R.id.IngresoSemanasProporcion);
        boton =VistaADevolver.findViewById(R.id.ConfirmarDatosProporcion);
        botonEliminar = VistaADevolver.findViewById(R.id.EliminarDatosProporcion);
        botonVolver = VistaADevolver.findViewById(R.id.Volver);

        if(ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
            ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
            ProprocionIngresada. setText(""+ActividadAnfitriona.objetivosUsuario.getMeta());
            SemanasIngresadas.setText(Static.ObtenerDiferenciaDeSemanas(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(),ActividadAnfitriona.objetivosUsuario.getFechaDeFin()));
        }else{
            botonEliminar.setEnabled(false);
            botonEliminar.setVisibility(View.INVISIBLE);
        }
        boton.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);
        botonVolver.setOnClickListener(this);
        return VistaADevolver;
    }

    public void onClick(View v) {
        Button BotonPresionado;
        BotonPresionado= (Button)v;
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        if(BotonPresionado.getId() ==R.id.ConfirmarDatosProporcion) {
            if ((ProprocionIngresada.getText().length() > 0 && SemanasIngresadas.getText().length() > 0)){
                if((Integer.parseInt(ProprocionIngresada.getText().toString())  > 20 && Integer.parseInt(ProprocionIngresada.getText().toString()) < 100) && (Integer.parseInt(SemanasIngresadas.getText().toString()) > 0 && Integer.parseInt(SemanasIngresadas.getText().toString())<104)){
                    ProprocionAInsertar = Integer.parseInt(ProprocionIngresada.getText().toString());
                    SemanasAInsertar = Integer.parseInt(SemanasIngresadas.getText().toString());
                    ArrayList<ClaseObjetivosUsuario> ListObjetivos;
                    boolean HayUnObjetivoProporcionCargado= false;
                    ClaseObjetivosUsuario MisObjetivos;
                    MisObjetivos = new ClaseObjetivosUsuario();
                    ListObjetivos =MisObjetivos.ObtenerObjetivos(getActivity());
                    for (ClaseObjetivosUsuario Objetivo : ListObjetivos) {
                        if (Objetivo.getTipoDeObjetivo() == 1){
                            HayUnObjetivoProporcionCargado = true;
                        }
                    }

                    if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
                        ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                        ClaseObjetivosUsuario U;
                        U = new ClaseObjetivosUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), ActividadAnfitriona.objetivosUsuario.getTipoDeObjetivo(), ProprocionAInsertar, 0, ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), Static.SumarRestarDias(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(),SemanasAInsertar*7), ActividadAnfitriona.objetivosUsuario.getPesoInicial());
                        U.UpdateObjetivo(U, getActivity());
                        ActividadAnfitriona.HomeObjetivos();
                    }
                    else if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() < 0 && HayUnObjetivoProporcionCargado == false) {
                        Calendar calendar =Calendar.getInstance();
                        ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                        Date FechaHoy = calendar.getTime();
                        ClaseObjetivosUsuario U;
                        U = new ClaseObjetivosUsuario(0, 1, ProprocionAInsertar, 0, FechaHoy, Static.SumarRestarDias(FechaHoy,SemanasAInsertar*7), 0);
                        U.AgregarNuevoObjetivo(U, getActivity());
                        ActividadAnfitriona.HomeObjetivos();
                    }else if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() < 0 && HayUnObjetivoProporcionCargado){
                        Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "No puede haber dos objetivos del mismo tipo", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER,0,450);

                        toast1.show();
                    }
                }
            }


            if (ProprocionIngresada.getText().length() == 0){
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Ingrese la proporcion", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);

                toast1.show();
            }

            if (ProprocionIngresada.getText().length() > 0){
                if (Integer.parseInt(ProprocionIngresada.getText().toString())  < 20){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "La proporcion no puede ser menor a 20", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);

                    toast1.show();
                }
                if (Integer.parseInt(ProprocionIngresada.getText().toString())  > 100){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "La proporcion esta fuera del limite", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);

                    toast1.show();
                }
            }

            if (SemanasIngresadas.getText().length() == 0){
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Ingrese las semanas", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);

                toast1.show();
            }

            if (SemanasIngresadas.getText().length() > 0){
                if (Integer.parseInt(SemanasIngresadas.getText().toString())  < 0){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Las semanas no pueden ser negativas", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);

                    toast1.show();
                } if (Integer.parseInt(SemanasIngresadas.getText().toString())  > 104){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "El plazo no puede ser mayor a dos años", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);

                    toast1.show();
                }
            }


        }else if(BotonPresionado.getId() ==R.id.EliminarDatosProporcion){
            ClaseObjetivosUsuario U = new ClaseObjetivosUsuario();
            U.EliminarObjetivoUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), getActivity());
            ActividadAnfitriona.HomeObjetivos();
        }else{
            ActividadAnfitriona.HomeObjetivos();
        }
    }
}
