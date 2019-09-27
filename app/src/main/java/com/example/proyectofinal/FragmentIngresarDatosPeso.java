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
            if(PesoAInsertar  > 0 && PesoAInsertar < 150) {
                PesoAInsertar = Integer.parseInt(PesoIngresado.getText().toString());
                SemanasAInsertar = Integer.parseInt(SemanasIngresadas.getText().toString());
                if (ActividadAnfitriona.objetivosUsuario.getIdObjetivo() >= 0) {
                    ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                    ClaseObjetivosUsuario U;
                    U = new ClaseObjetivosUsuario(ActividadAnfitriona.objetivosUsuario.getIdObjetivo(), ActividadAnfitriona.objetivosUsuario.getTipoDeObjetivo(), PesoAInsertar, 0, ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), Static.SumarRestarDias(ActividadAnfitriona.objetivosUsuario.getFechaDeInicio(), SemanasAInsertar * 7), ActividadAnfitriona.objetivosUsuario.getPesoInicial());
                    U.UpdateObjetivo(U, getActivity());
                    ActividadAnfitriona.HomeObjetivos();

                } else {
                    Calendar calendar = Calendar.getInstance();
                    ClaseObjetivosUsuario Static = new ClaseObjetivosUsuario();
                    Date FechaHoy = calendar.getTime();
                    ClaseObjetivosUsuario U;
                    U = new ClaseObjetivosUsuario(0, 0, PesoAInsertar, 0, FechaHoy, Static.SumarRestarDias(FechaHoy, SemanasAInsertar * 7), 0);
                    U.AgregarNuevoObjetivo(U, getActivity());
                    ActividadAnfitriona.HomeObjetivos();
                }
            }else{
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Ingrese los campos correctamente!", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);

                toast1.show();
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
