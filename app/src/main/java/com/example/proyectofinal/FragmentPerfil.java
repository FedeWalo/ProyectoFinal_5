package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentPerfil extends Fragment implements View.OnClickListener{
    Button BotonEditarDatosPerfil;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_perfil, GrupoDeLaVista, false);
        BotonEditarDatosPerfil = VistaAdevolver.findViewById(R.id.BotonModificarDatos);
        BotonEditarDatosPerfil.setOnClickListener(this);
        return VistaAdevolver;
    }

    @Override
    public void onClick(View v) {
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ActividadAnfitriona.IrAFrEditarDatosPerfil();
    }
}
