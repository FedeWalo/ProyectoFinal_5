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

public class FragmentIngresarDatosPeso extends Fragment implements View.OnClickListener {

    TextView PesoIngresado;
    TextView SemanasIngresadas;
    int PesoAInsertar;
    int SemanasAInsertar;
    Button boton;
    Button botonEliminar;
    Button BotonVolver;

    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaADevolver;
        VistaADevolver = infladorDeLayouts.inflate(R.layout.layout_ingresar_datos_peso,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        BotonVolver = VistaADevolver.findViewById(R.id.Volver);
        PesoIngresado = VistaADevolver.findViewById(R.id.IngresoPeso);
        SemanasIngresadas = VistaADevolver.findViewById(R.id.IngresoSemanasPeso);
        boton = VistaADevolver.findViewById(R.id.ConfirmarDatosPeso);
        botonEliminar = VistaADevolver.findViewById(R.id.EliminarDatosPeso);
        if(ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
            ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
            PesoIngresado.setText(""+ActividadAnfitriona.objetivosUsuario.getMeta());
            SemanasIngresadas.setText(Static.ObtenerDiferenciaDeSemanas(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(),ActividadAnfitriona.objetivosUsuario.getFechaDeFin()));
        }
        else{
            botonEliminar.setEnabled(false);
            botonEliminar.setVisibility(View.INVISIBLE);
        }
        boton.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);
        BotonVolver.setOnClickListener(this);
        return VistaADevolver;

    }
    public void onClick(View v) {
        Button BotonPresionado;
        BotonPresionado= (Button)v;
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        if(BotonPresionado.getId() ==R.id.ConfirmarDatosPeso) {
            if ((PesoIngresado.getText().length() > 0 && SemanasIngresadas.getText().length() > 0)) {
                if ((Integer.parseInt(PesoIngresado.getText().toString()) > 40 && Integer.parseInt(PesoIngresado.getText().toString()) < 150) && (Integer.parseInt(SemanasIngresadas.getText().toString()) > 0 && Integer.parseInt(SemanasIngresadas.getText().toString()) < 104)) {
                    PesoAInsertar = Integer.parseInt(PesoIngresado.getText().toString());
                    SemanasAInsertar = Integer.parseInt(SemanasIngresadas.getText().toString());
                    ArrayList<ClaseObjetivosUsuario> ListObjetivos;
                    boolean HayUnObjetivoPesoCargado = false;
                    ClaseObjetivosUsuario MisObjetivos;
                    MisObjetivos = new ClaseObjetivosUsuario();
                    ListObjetivos = MisObjetivos.ObtenerObjetivos(getActivity());
                    for (ClaseObjetivosUsuario Objetivo : ListObjetivos) {
                        if (Objetivo.getTipoDeObjetivo() == 0) {
                            HayUnObjetivoPesoCargado = true;
                        }
                    }
                    if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
                        ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                        ClaseObjetivosUsuario U;
                        U = new ClaseObjetivosUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), ActividadAnfitriona.objetivosUsuario.getTipoDeObjetivo(), PesoAInsertar, 0, ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), Static.SumarRestarDias(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), SemanasAInsertar * 7), ActividadAnfitriona.objetivosUsuario.getPesoInicial());
                        U.UpdateObjetivo(U, getActivity());
                        ActividadAnfitriona.HomeObjetivos();

                    } else if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() < 0 && HayUnObjetivoPesoCargado == false) {
                        Calendar calendar = Calendar.getInstance();
                        ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                        Date FechaHoy = calendar.getTime();
                        ClaseObjetivosUsuario U;
                        U = new ClaseObjetivosUsuario(0, 0, PesoAInsertar, 0, FechaHoy, Static.SumarRestarDias(FechaHoy, SemanasAInsertar * 7), 0);
                        U.AgregarNuevoObjetivo(U, getActivity());
                        ActividadAnfitriona.HomeObjetivos();
                    } else if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() < 0 && HayUnObjetivoPesoCargado) {
                        Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "No puede haber dos objetivos del mismo tipo", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 450);

                        toast1.show();
                    }
                }
            }

            if (PesoIngresado.getText().length() == 0){
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Ingrese un peso", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);

                toast1.show();
            }
            if (PesoIngresado.getText().length() > 0){
                if (Integer.parseInt(PesoIngresado.getText().toString())  < 40){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "El peso no puede ser menor a 40 kilos", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);

                    toast1.show();
                } if (Integer.parseInt(PesoIngresado.getText().toString())  > 150){
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "El peso esta fuera del limite", Toast.LENGTH_SHORT);
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



        }else if(BotonPresionado.getId() ==R.id.EliminarDatosPeso){
            ClaseObjetivosUsuario U = new ClaseObjetivosUsuario();
            U.EliminarObjetivoUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), getActivity());
            ActividadAnfitriona.HomeObjetivos();
        }else {
            ActividadAnfitriona.HomeObjetivos();
        }

    }
}
