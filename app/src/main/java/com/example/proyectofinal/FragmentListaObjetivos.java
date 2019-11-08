package com.example.proyectofinal;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class FragmentListaObjetivos extends Fragment implements View.OnClickListener{
    ListView ListViewListaCategorias;
    ArrayList<ClaseObjetivo> ListObjetivos;
    ArrayList<ClaseObjetivosUsuario> ListObjetivoUsuario;
    ArrayList<ClaseObjetivo> ListObjetivoAMostrar;
    Button BotonVolver;
    public View onCreateView(LayoutInflater Inflador, ViewGroup Grupo, Bundle DatosRecibidos){
        ListObjetivoAMostrar = new ArrayList<>();
        View VistaADevolver;
        VistaADevolver = Inflador.inflate(R.layout.layout_lista_objetivos,Grupo,false);
        //instancio el boton
        BotonVolver = VistaADevolver.findViewById(R.id.VolverAHomeObjetivos);
        ClaseObjetivo MisObjetivos;
        MisObjetivos = new ClaseObjetivo();
        ListObjetivos = MisObjetivos.ObtenerObjetivos(getActivity());

        // valido que no aparezca en la lista un objetivo si ya hay uno creado
        ClaseObjetivosUsuario MisObjetivosUsuario;
        MisObjetivosUsuario = new ClaseObjetivosUsuario();
        ListObjetivoUsuario =MisObjetivosUsuario.ObtenerObjetivos(getActivity());

        for (ClaseObjetivosUsuario objetivo: ListObjetivoUsuario) {

            if (objetivo.getTipoDeObjetivo()== 0){

                for (ClaseObjetivo _objetivo: ListObjetivos) {
                    if (_objetivo.getTipoDeObjetivo() == 1){
                        ListObjetivoAMostrar.add(_objetivo);
                    }
                }

            }
            else if (objetivo.getTipoDeObjetivo()== 1){

                for (ClaseObjetivo _objetivo: ListObjetivos) {
                    if (_objetivo.getTipoDeObjetivo() == 0){
                        ListObjetivoAMostrar.add(_objetivo);
                    }
                }

            }
        }

        if (ListObjetivoAMostrar.size() == 0){
            ListObjetivoAMostrar = ListObjetivos;
        }

        AdaptadorListaaObjetivos adaptador;
        adaptador = new AdaptadorListaaObjetivos(ListObjetivoAMostrar,VistaADevolver.getContext());

        ListViewListaCategorias = VistaADevolver.findViewById(R.id.ListaObjetivos);
        //le asigno el adaptador al listview
        ListViewListaCategorias.setAdapter(adaptador);

        ListViewListaCategorias.setOnItemClickListener(EscuchadorParaListView);
        BotonVolver.setOnClickListener(this);
        return VistaADevolver;
    }

    public class AdaptadorListaaObjetivos extends BaseAdapter {
        //declaro la lista que va  contener la lista de categorias
        private ArrayList<ClaseObjetivo> _MiListaDeObjetivos;
        private Context _MiContexto;

        public AdaptadorListaaObjetivos(ArrayList<ClaseObjetivo> ListaDeObjetivos, Context ContextoAUsar) {
            //almaceno la lista que recibo como parametro en una  variable privada
            _MiListaDeObjetivos = ListaDeObjetivos;
            //almaceno el contexto de la activity que me invoco
            _MiContexto= ContextoAUsar;
        }
        public  int getCount (){
            return _MiListaDeObjetivos.size();
        }
        public ClaseObjetivo getItem(int PoscicionAObtener){
            ClaseObjetivo ComidaADevolver;
            ComidaADevolver = _MiListaDeObjetivos.get(PoscicionAObtener);
            return ComidaADevolver;
        }
        public long getItemId(int PoscicionAObtener){
            return PoscicionAObtener;
        }

        public View getView(int PosicionActual, View VistaActual, ViewGroup GrupoActual) {
            View VistaADevovler;
            VistaADevovler = null;
            //declaro el inflador de layouts
            LayoutInflater InfladorDeLayouts;
            //inicializo el inflador, por medio de una llamada a un servicio del S.O.
            InfladorDeLayouts = (LayoutInflater) _MiContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //le digo al inflador que infle la view, en el grupo actual de vistas, y No en la raiz de la view
            VistaADevovler = InfladorDeLayouts.inflate(R.layout.diseno_ltem_listview_categoria_dieta,GrupoActual,false);

            TextView textViewNombre;
            textViewNombre= VistaADevovler.findViewById(R.id.NombreCategoria);

            //obtengo el nombre de la categoria que va en la poscicion actual
            String TextoDeLaPoscicionActual;
            TextoDeLaPoscicionActual = getItem(PosicionActual).getNombreObjetivo();
            //la asigno a ese textview su valor
            textViewNombre.setText(TextoDeLaPoscicionActual);

            return VistaADevovler;
        }
    }

    AdapterView.OnItemClickListener EscuchadorParaListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Date fecha = new Date();
            ClaseObjetivosUsuario U = new ClaseObjetivosUsuario(-1,0,0,0,fecha,fecha,0);
            if(ListObjetivos.get(position).getTipoDeObjetivo()== 0) {
                ActividadPrincipal ActividadAnfitriona;
                ActividadAnfitriona = (ActividadPrincipal) getActivity();
                ActividadAnfitriona.IngresarDatosPeso(U);
            }
            else{
                ActividadPrincipal ActividadAnfitriona;
                ActividadAnfitriona = (ActividadPrincipal) getActivity();
                ActividadAnfitriona.IngresarDatosComerSaludable(U);
            }
        }
    };


    @Override
    public void onClick(View view){
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        Button BotonPresionado;
        BotonPresionado= (Button)view;
        if(BotonPresionado.getId() ==R.id.VolverAHomeObjetivos) {
            ActividadAnfitriona.HomeObjetivos();
        }
    }
}
