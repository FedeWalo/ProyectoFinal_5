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
    TextView AlturaLayout;
    ClasePerfil PerfilTraido;

    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_perfil, GrupoDeLaVista, false);

        SexoLayout = VistaAdevolver.findViewById(R.id.Sexo);
        PesoLayout=VistaAdevolver.findViewById(R.id.Peso);
        ActividadLayout=VistaAdevolver.findViewById(R.id.NivelDeActividad);
        AlturaLayout=VistaAdevolver.findViewById(R.id.Altura);

        ClasePerfil perfil = new ClasePerfil();
        PerfilTraido = perfil.TraerUltimosDatosPerfil(VistaAdevolver.getContext());

        SexoLayout.setText(PerfilTraido.getSexo());
        PesoLayout.setText(PerfilTraido.getPeso()+"");
        ActividadLayout.setText(PerfilTraido.getNivelDeActividad());
        AlturaLayout.setText(PerfilTraido.getAltura()+"");

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
