package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentModificarObjetivoAgregado extends Fragment implements View.OnClickListener {
    Button Boton;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;

        //falta hacer todo esto

        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_modificar_objetivo_agregado, GrupoDeLaVista, false);
        Boton= VistaAdevolver.findViewById(R.id.Boton_FrModificarObjetivos_Confirmar);
        Boton.setOnClickListener(this);
        return VistaAdevolver;
    }

    @Override
    public void onClick(View v) {
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ActividadAnfitriona.HomeObjetivos();
    }
}
