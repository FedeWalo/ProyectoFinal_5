package com.example.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentEditarDatosPerfil extends Fragment implements View.OnClickListener{

    Button BotonComfirmar;

    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_editar_datos_perfil, GrupoDeLaVista, false);
        BotonComfirmar = VistaAdevolver.findViewById(R.id.BotonModificarDatos);

        BotonComfirmar.setOnClickListener(this);

        return VistaAdevolver;
    }



    @Override
    public void onClick(View view){
        Button BotonPresionado;
        BotonPresionado= (Button)view;
        if(BotonPresionado.getId() ==R.id.BotonModificarDatos) {
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
        }
    }

}

