package com.example.proyectofinal;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class FragmentListaObjetivos extends Fragment {
    ListView ListViewListaCategorias;
    ArrayList<ClaseObjetivo> ListObjetivo;
    ArrayList<ClaseObjetivosUsuario> ListObjetivoUsuario;
    ArrayList<ClaseObjetivo> ListObjetivoAMostrar;
    public View onCreateView(LayoutInflater Inflador, ViewGroup Grupo, Bundle DatosRecibidos){
        ListObjetivoAMostrar = new ArrayList<>();
        View VistaADevolver;
        ClaseObjetivo MisObjetivos;
        MisObjetivos = new ClaseObjetivo();
        ListObjetivo = MisObjetivos.ObtenerObjetivos(getActivity());
        VistaADevolver = Inflador.inflate(R.layout.layout_lista_objetivos,Grupo,false);

        // valido que no aparezca en la lista un objetivo si ya hay uno creado
        ClaseObjetivosUsuario MisObjetivosUsuario;
        MisObjetivosUsuario = new ClaseObjetivosUsuario();
        ListObjetivoUsuario =MisObjetivosUsuario.ObtenerObjetivos(getActivity());

        for (ClaseObjetivosUsuario objetivo: ListObjetivoUsuario) {

            if (objetivo.getTipoDeObjetivo()== 0){

                for (ClaseObjetivo _objetivo: ListObjetivo) {
                    if (_objetivo.getTipoDeObjetivo() == 0){
                        ListObjetivo.remove(_objetivo);
                    }
                }

            }
            else if (objetivo.getTipoDeObjetivo()== 1){

                for (ClaseObjetivo _objetivo: ListObjetivo) {
                    if (_objetivo.getTipoDeObjetivo() == 1){
                        ListObjetivo.remove(_objetivo);
                    }
                }

            }
        }

        AdaptadorListaaObjetivos adaptador;
        adaptador = new AdaptadorListaaObjetivos(ListObjetivo,VistaADevolver.getContext());

        ListViewListaCategorias = VistaADevolver.findViewById(R.id.ListaObjetivos);
        //le asigno el adaptador al listview
        ListViewListaCategorias.setAdapter(adaptador);

        ListViewListaCategorias.setOnItemClickListener(EscuchadorParaListView);
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
            if(ListObjetivo.get(position).getTipoDeObjetivo()== 0) {
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

}
