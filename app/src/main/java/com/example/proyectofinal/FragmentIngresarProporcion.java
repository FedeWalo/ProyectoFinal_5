package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
            ProprocionAInsertar = Integer.parseInt(ProprocionIngresada.getText().toString());
            SemanasAInsertar = Integer.parseInt(SemanasIngresadas.getText().toString());
            if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
                ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                ClaseObjetivosUsuario U;
                U = new ClaseObjetivosUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), ActividadAnfitriona.objetivosUsuario.getTipoDeObjetivo(), ProprocionAInsertar, 0, ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), Static.SumarRestarDias(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(),SemanasAInsertar*7), ActividadAnfitriona.objetivosUsuario.getPesoInicial());
                U.UpdateObjetivo(U, getActivity());
                ActividadAnfitriona.HomeObjetivos();
            } else {
                Calendar calendar =Calendar.getInstance();
                ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                Date FechaHoy = calendar.getTime();
                ClaseObjetivosUsuario U;
                U = new ClaseObjetivosUsuario(0, 1, ProprocionAInsertar, 0, FechaHoy, Static.SumarRestarDias(FechaHoy,SemanasAInsertar*7), 0);
                U.AgregarNuevoObjetivo(U, getActivity());
                ActividadAnfitriona.HomeObjetivos();
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
