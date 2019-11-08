package com.example.proyectofinal;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText DatoSexoIngresado;
    EditText DatoEdadIngresa;
    float DatoPeso;
    float DatoAltura;
    int DatoIntActividad;
    int idRegistroDatosPerfil;
    String DatoSexo;
    int DatoEdad;


    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {

        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_editar_datos_perfil, GrupoDeLaVista, false);

        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();

        BotonComfirmar = VistaAdevolver.findViewById(R.id.ConfirmarEdicionDatosPerfil);
        BotonVolver = VistaAdevolver.findViewById(R.id.VolverAPerfil);
        BotonComfirmar.setOnClickListener(this);
        BotonVolver.setOnClickListener(this);

        DatoPesoIngresado = VistaAdevolver.findViewById(R.id.IngresoPeso);
        DatoActividadIngresado = VistaAdevolver.findViewById(R.id.IngresoVecesPorSemana);
        DatoAlturaIngresado = VistaAdevolver.findViewById(R.id.IngresoAltura);
        DatoSexoIngresado = VistaAdevolver.findViewById(R.id.ingresoSexo);
        DatoEdadIngresa = VistaAdevolver.findViewById(R.id.ingresoEdad);

        if (ActividadAnfitriona.PerfilActual.getIdPerfil() != 0){
            DatoPesoIngresado.setText(ActividadAnfitriona.PerfilActual.getPeso()+"");
            DatoActividadIngresado.setText(ActividadAnfitriona.PerfilActual.getNivelDeActividad()+"");
            DatoAlturaIngresado.setText(ActividadAnfitriona.PerfilActual.getAltura()+"");
            DatoSexoIngresado.setText(ActividadAnfitriona.PerfilActual.getSexo());
            DatoEdadIngresa.setText(ActividadAnfitriona.PerfilActual.getEdad()+"");
        }

        return VistaAdevolver;
    }



    @Override
    public void onClick(View view){
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        Button BotonPresionado;
        BotonPresionado= (Button)view;
        if(BotonPresionado.getId() ==R.id.VolverAPerfil) {
            ActividadAnfitriona.HomePerfil();
        }
        if(BotonPresionado.getId() ==R.id.ConfirmarEdicionDatosPerfil) {
            if (DatoPesoIngresado.getText().length() > 0 && DatoActividadIngresado.getText().length() > 0 && DatoAlturaIngresado.getText().length() > 0 && DatoSexoIngresado.getText().length() > 0 && DatoEdadIngresa.getText().length() > 0) {
                DatoSexo = DatoSexoIngresado.getText().toString();
                DatoPeso = Float.parseFloat(DatoPesoIngresado.getText().toString());
                DatoIntActividad = Integer.parseInt(DatoActividadIngresado.getText().toString());
                DatoEdad = Integer.parseInt(DatoEdadIngresa.getText().toString());

                DatoAltura = Float.parseFloat(DatoAlturaIngresado.getText().toString());
                ClasePerfil NuevoPerfil;
                NuevoPerfil = new ClasePerfil();

                Calendar calendar = Calendar.getInstance();
                Date FechaHoy = calendar.getTime();
                Timestamp FechaActual = new Timestamp(FechaHoy.getTime());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(FechaHoy);
                 ClasePerfil Objeto = new ClasePerfil(0,DatoSexo,DatoPeso,DatoIntActividad,DatoAltura,FechaActual,strDate,DatoEdad);

                   NuevoPerfil.InsertarNuevoPerfil(Objeto,ActividadAnfitriona);
            }
            else{
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Hay uno o mas campos vacios", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);
            }
        }
    }

}

