package com.example.proyectofinal;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentEditarDatosPerfil extends Fragment implements View.OnClickListener{

    Button BotonComfirmar;
    Button BotonVolver;
    EditText DatoPesoIngresado;
    EditText DatoActividadIngresado;
    EditText DatoAlturaIngresado;
    RadioButton RadioFemenino;
    RadioButton RadioMasculino;
    EditText DatoEdadIngresa;
    float DatoPeso;
    float DatoAltura;
    String DatoSexo;
    int DatoIntActividad;
    int idRegistroDatosPerfil;
    int DatoEdad;
    Spinner DatoSexoIngresadoSpinner;
    ActividadPrincipal ActividadAnfitriona;

    RadioGroup GrupoSexo;



    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        ArrayList<String> array_spinner;
        ArrayAdapter<String> adapter;

        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_editar_datos_perfil, GrupoDeLaVista, false);

        ActividadAnfitriona = (ActividadPrincipal) getActivity();

        BotonComfirmar = VistaAdevolver.findViewById(R.id.ConfirmarEdicionDatosPerfil);
        BotonVolver = VistaAdevolver.findViewById(R.id.VolverAPerfil);
        BotonComfirmar.setOnClickListener(this);
        BotonVolver.setOnClickListener(this);

        DatoPesoIngresado = VistaAdevolver.findViewById(R.id.IngresoPeso);
        DatoActividadIngresado = VistaAdevolver.findViewById(R.id.IngresoVecesPorSemana);
        DatoAlturaIngresado = VistaAdevolver.findViewById(R.id.IngresoAltura);
        RadioFemenino = VistaAdevolver.findViewById(R.id.IngresoFemenino);
        RadioMasculino = VistaAdevolver.findViewById(R.id.IngresoMasculino);

        GrupoSexo = VistaAdevolver.findViewById(R.id.ingresoSexo);

        DatoEdadIngresa = VistaAdevolver.findViewById(R.id.ingresoEdad);


        if (ActividadAnfitriona.PerfilActual.getIdPerfil() != 0){
            DatoPesoIngresado.setText(ActividadAnfitriona.PerfilActual.getPeso()+"");
            DatoActividadIngresado.setText(ActividadAnfitriona.PerfilActual.getNivelDeActividad()+"");
            DatoAlturaIngresado.setText(ActividadAnfitriona.PerfilActual.getAltura()+"");
                if(ActividadAnfitriona.PerfilActual.getSexo().equals("Masculino")) {
                    GrupoSexo.check(R.id.IngresoMasculino);

                }
                else{
                    GrupoSexo.check(R.id.IngresoFemenino);
                }
            }

            DatoEdadIngresa.setText(ActividadAnfitriona.PerfilActual.getEdad()+"");
            return VistaAdevolver;
        }


        //aca van las opciones que queremos que aparezcan
        //array_spinner.add("Masculino");
        // array_spinner.add("Femenino");
       // adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array_spinner);
       // DatoSexoIngresadoSpinner.setdrop(adapter);






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
            if (DatoPesoIngresado.getText().length() > 0 && DatoActividadIngresado.getText().length() > 0 && DatoAlturaIngresado.getText().length() > 0 && DatoEdadIngresa.getText().length() > 0){

                if(Float.parseFloat(DatoAlturaIngresado.getText().toString()) < 250f && Float.parseFloat(DatoAlturaIngresado.getText().toString())> 130f && Float.parseFloat(DatoPesoIngresado.getText().toString()) > 40f && Float.parseFloat(DatoPesoIngresado.getText().toString()) < 200f && Float.parseFloat(DatoActividadIngresado.getText().toString()) > 0 && Float.parseFloat(DatoActividadIngresado.getText().toString()) < 14f && Float.parseFloat(DatoEdadIngresa.getText().toString())>17f && Float.parseFloat(DatoEdadIngresa.getText().toString())<120f) {
                    if (GrupoSexo.getCheckedRadioButtonId() == R.id.IngresoFemenino) {
                        DatoSexo = "Femenino";
                    } else {
                        DatoSexo = "Masculino";
                    }

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
                    ClasePerfil Objeto = new ClasePerfil(0, DatoSexo, DatoPeso, DatoIntActividad, DatoAltura, FechaActual, strDate, DatoEdad);
                    NuevoPerfil.InsertarNuevoPerfil(Objeto, ActividadAnfitriona);

                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Se a modificado correctamente sus datos!", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 450);
                    toast1.show();
                }
                //se pueden hacer else if aca para cada caso que haya escrito algo mal
                else{
                    Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Hay uno o mas campos fuera de rango", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,450);
                    toast1.show();
                }
            }
            else{
                Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Hay uno o mas campos vacios", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0,450);
                toast1.show();
            }
        }
    }

}

