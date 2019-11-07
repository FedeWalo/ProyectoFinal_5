package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPerfil extends Fragment implements View.OnClickListener{
    Button BotonEditarDatosPerfil;
    TextView SexoLayout;
    TextView PesoLayout;
    TextView ActividadLayout;
    TextView EdadLayout;
    TextView AlturaLayout;
    ClasePerfil PerfilTraido;
    String DatoActividad;

    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_perfil, GrupoDeLaVista, false);

        SexoLayout = VistaAdevolver.findViewById(R.id.Sexo);
        PesoLayout=VistaAdevolver.findViewById(R.id.Peso);
        ActividadLayout=VistaAdevolver.findViewById(R.id.NivelDeActividad);
        AlturaLayout=VistaAdevolver.findViewById(R.id.Altura);
        EdadLayout=VistaAdevolver.findViewById(R.id.Edad);

        ClasePerfil perfil = new ClasePerfil();
        PerfilTraido = perfil.TraerUltimosDatosPerfil(VistaAdevolver.getContext());

        SexoLayout.setText(PerfilTraido.getSexo());
        PesoLayout.setText(PerfilTraido.getPeso()+"");

        if(PerfilTraido.getNivelDeActividad() == 0){
            DatoActividad = "no definido";
        }
        else if( PerfilTraido.getNivelDeActividad() <= 1){
            DatoActividad = "bajo";
        }
        else if ((PerfilTraido.getNivelDeActividad() > 1)&&(PerfilTraido.getNivelDeActividad() <=3)){
            DatoActividad = "medio";

        }else if(PerfilTraido.getNivelDeActividad() >3){
            DatoActividad = "alto";
        }

        ActividadLayout.setText(DatoActividad);
        AlturaLayout.setText(PerfilTraido.getAltura()+"");

        EdadLayout.setText(PerfilTraido.getEdad()+"");
        BotonEditarDatosPerfil = VistaAdevolver.findViewById(R.id.BotonModificarDatos);
        BotonEditarDatosPerfil.setOnClickListener(this);
        return VistaAdevolver;
    }

    @Override
    public void onClick(View v) {
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ActividadAnfitriona.IrAFrEditarDatosPerfil(PerfilTraido);
    }
}
