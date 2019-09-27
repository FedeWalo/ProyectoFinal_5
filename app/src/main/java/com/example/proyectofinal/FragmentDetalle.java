package com.example.proyectofinal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class  FragmentDetalle extends Fragment implements View.OnClickListener{
    Button BotonVolver;
    Button BotonConsumir;
    ImageView ImagenComida;
    TextView TituloComida;
    TextView Categoria;
    TextView FitPoints;
    TextView Detalles;
    //TextView Bueno;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_detalle_comida_dieta,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ImagenComida = VistaAdevolver.findViewById(R.id.ImagenComida);
        TituloComida = VistaAdevolver.findViewById(R.id.TituloComida);
        FitPoints = VistaAdevolver.findViewById(R.id.FitPoint);
        Detalles=VistaAdevolver.findViewById(R.id.DetalleComida);
        //Bueno = VistaAdevolver.findViewById(R.id.Bueno);
        BotonConsumir = VistaAdevolver.findViewById(R.id.Boton_FrDetalleComida);
        BotonVolver = VistaAdevolver.findViewById(R.id.BotonVolverAInicio);


        String PosMemoriaImagenActual;
        PosMemoriaImagenActual = ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida.getImagen();
        int imageResource = getResources().getIdentifier(PosMemoriaImagenActual, "drawable", getActivity().getPackageName());
        //la asigno a ese iamgeview su valor
        ImagenComida.setImageResource(imageResource);
        TituloComida.setText(ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida.getNombre());
        FitPoints.setText(""+ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida.getFitPoints());
        Detalles.setText(ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida.getDetalle());
        /*if(ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida.getBueno()==1){
            Bueno.setText("Bueno");
        }
        else{
            Bueno.setText("Malo");
        }
*/
        BotonVolver.setOnClickListener(this);
        BotonConsumir.setOnClickListener(this);
        return  VistaAdevolver;
    }

    @Override
    public void onClick(View v) {
        Button BotonPresionado;
        BotonPresionado= (Button)v;
        if(BotonPresionado.getId() ==R.id.Boton_FrDetalleComida){
            ClaseComidaConsumida MisComidas;
            MisComidas = new ClaseComidaConsumida();
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
            MisComidas.InsertarComidaConsumida(ActividadAnfitriona._ComidaSeleccionadaEnFragmentListaDeComida, getActivity());
            Toast toast1 = Toast.makeText(ActividadAnfitriona.getApplicationContext(), "Su comida fue anotada con exito!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER,0,450);

            toast1.show();

        }else{
            FragmentManager adminFragments;
            FragmentTransaction transacFragments;
            FragmentHomeDieta FrInicioDieta;
            adminFragments = getFragmentManager();
            FrInicioDieta = new FragmentHomeDieta();
            transacFragments = adminFragments.beginTransaction();
            transacFragments.replace(R.id.AlojadorDeFragments,FrInicioDieta);
            transacFragments.commit();
        }
    }
}
