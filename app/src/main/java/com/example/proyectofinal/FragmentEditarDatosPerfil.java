package com.example.proyectofinal;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentEditarDatosPerfil extends Fragment implements View.OnClickListener{

    Button BotonComfirmar;
    Button BotonVolver;
    EditText DatoPesoIngresado;
    EditText DatoActividadIngresado;
    EditText DatoAlturaIngresado;
    float DatoPeso;
    String DatoActividad;
    float DatoAltura;
    int DatoIntActividad;


    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_editar_datos_perfil, GrupoDeLaVista, false);
        BotonComfirmar = VistaAdevolver.findViewById(R.id.ConfirmarEdicionDatosPerfil);
        BotonVolver = VistaAdevolver.findViewById(R.id.VolverAPerfil);
        BotonComfirmar.setOnClickListener(this);
        BotonVolver.setOnClickListener(this);

        DatoPesoIngresado = VistaAdevolver.findViewById(R.id.IngresoPeso);
        DatoPeso = Integer.parseInt(DatoPesoIngresado.toString());

        DatoActividadIngresado = VistaAdevolver.findViewById(R.id.IngresoVecesPorSemana);
        DatoIntActividad = Integer.parseInt(DatoActividadIngresado.toString());

        if( DatoIntActividad <= 1){
            DatoActividad = "bajo";
        }
        else if ((DatoIntActividad > 1)&&(DatoIntActividad<=3)){
            DatoActividad = "medio";

        }else if(DatoIntActividad>3){
            DatoActividad = "alto";
        }

        DatoAlturaIngresado = VistaAdevolver.findViewById(R.id.IngresoAltura);
        DatoAltura = Integer.parseInt(DatoAlturaIngresado.toString());


        return VistaAdevolver;
    }



    @Override
    public void onClick(View view){
        Button BotonPresionado;
        BotonPresionado= (Button)view;
        if(BotonPresionado.getId() ==R.id.VolverAPerfil) {
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
            ActividadAnfitriona.HomePerfil();
        }
        if(BotonPresionado.getId() ==R.id.ConfirmarEdicionDatosPerfil) {
            ClasePerfil NuevoPerfil;
            NuevoPerfil = new ClasePerfil();

            Calendar calendar =Calendar.getInstance();
            Date FechaHoy = calendar.getTime();
            Timestamp FechaActual = new Timestamp(FechaHoy.getTime());

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(FechaHoy);
            ClasePerfil Objeto = new ClasePerfil(0,"Abc",DatoPeso,DatoActividad,DatoAltura,strDate);

            NuevoPerfil.InsertarNuevoPerfil(Objeto,getContext());
        }
    }

}

