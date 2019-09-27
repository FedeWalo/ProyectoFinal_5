package com.example.proyectofinal;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActividadPrincipal extends Activity {
    FragmentManager adminFragments;
    FragmentTransaction transacFragments;
    int _IdCategoriaSelecionadaEnFragmentListaDeCategoriasComida;
    ClaseComida _ComidaSeleccionadaEnFragmentListaDeComida;
    ClaseObjetivo _ObjetivoSeleccionadoEnFragmentAgregarNuevoObjetivo;
    ClaseObjetivosUsuario objetivosUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        BottomNavigationView MenuDeNavegacion = findViewById(R.id.MenuDeNavegacion);
        MenuDeNavegacion.setOnNavigationItemSelectedListener(navListener);

        adminFragments = getFragmentManager();
        FragmentPantallaInicio FrPantallaInicio;
        FrPantallaInicio = new FragmentPantallaInicio();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrPantallaInicio);
        transacFragments.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.ItemHome:
                           selectedFragment = new FragmentPantallaInicio();
                           break;
                        case R.id.ItemDieta:
                            selectedFragment = new FragmentHomeDieta();
                            break;
                        case R.id.ItemObjetivos:
                            selectedFragment = new FragmentHomeObjetivos();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.AlojadorDeFragments,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void HomeDieta(){
        FragmentHomeDieta FrInicioDieta;
        FrInicioDieta = new FragmentHomeDieta();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrInicioDieta);
        transacFragments.commit();
    }

    public void HomeObjetivos(){
        FragmentHomeObjetivos FrInicioObjetivos;
        FrInicioObjetivos = new FragmentHomeObjetivos();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrInicioObjetivos);
        transacFragments.commit();
    }

    public void ProcesarDatosHomeDieta(){
        Fragment FrListaCat;
        FrListaCat = new FragmentListaDeCategoriasComida();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrListaCat);
        transacFragments.commit();
    }

    public void ProcesarDatosActListaCat(int IdCat){
        _IdCategoriaSelecionadaEnFragmentListaDeCategoriasComida=IdCat;
        Fragment FrListaComida;
        FrListaComida = new FragmentListaDeComida();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrListaComida);
        transacFragments.commit();
    }

   public void ProcesarDatosActListaComidas(ClaseComida NombreComida){
       _ComidaSeleccionadaEnFragmentListaDeComida = NombreComida;
       Fragment FrDetalle;
       FrDetalle = new FragmentDetalle();
       transacFragments = adminFragments.beginTransaction();
       transacFragments.replace(R.id.AlojadorDeFragments,FrDetalle);
       transacFragments.commit();
    }

    public void IrAFrlistaObjetivos(){
        Fragment FragmentListaObjetivos;
        FragmentListaObjetivos = new FragmentListaObjetivos();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FragmentListaObjetivos);
        transacFragments.commit();
    }



    //que mierda hay que hacer aca(?
    //este es el caso en el que selecciona la lista de los objetivos que ya estan en la home objetivos
    // para modificarlos

    public void IngresarDatosPeso(ClaseObjetivosUsuario ObjetoEnviado){ //si se quiere modificar va a venir lleno sino viene null
        objetivosUsuario = ObjetoEnviado;
        Fragment FrIngresoPeso;
        FrIngresoPeso = new FragmentIngresarDatosPeso();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrIngresoPeso);
        transacFragments.commit();
    }
    public void IngresarDatosComerSaludable(ClaseObjetivosUsuario ObjetoEnviado){//si se quiere modificar va a venir lleno sino viene nul
        objetivosUsuario = ObjetoEnviado;
        Fragment FrIngresoComerSaludable;
        FrIngresoComerSaludable = new FragmentIngresarProporcion();
        transacFragments = adminFragments.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragments,FrIngresoComerSaludable);
        transacFragments.commit();
    }

    }

