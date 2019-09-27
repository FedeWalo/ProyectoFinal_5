package com.example.proyectofinal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentListaDeCategoriasComida extends Fragment  {
    ArrayList<ClaseCategoriasDieta> listCategorias;
    ArrayAdapter<String> Adaptador;
    ListView ListViewListaCategorias;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_lista_categorias_dieta,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();

        //declaro e instancio las categorias
        ClaseCategoriasDieta MisCategorias;
        MisCategorias = new ClaseCategoriasDieta();
        //uso el objeto miscategorias para llenar la lista
        listCategorias =MisCategorias.ObtenerTodas(getActivity());

        //declaro y relaciono mi objeto listview
        ListViewListaCategorias = VistaAdevolver.findViewById(R.id.ListaCategorias);
        //defino e instancio el adaptador, mandandole el Arraylist y el contexto
        AdaptadorCategoriaDieta adaptador;
        adaptador= new AdaptadorCategoriaDieta(listCategorias, VistaAdevolver.getContext());

        //le asigno el adaptador al listview
        ListViewListaCategorias.setAdapter(adaptador);

        ListViewListaCategorias.setOnItemClickListener(EscuchadorParaListView);
        return VistaAdevolver;
    }

    public class AdaptadorCategoriaDieta extends BaseAdapter {

        //declaro la lista que va  contener la lista de categorias
        private ArrayList<ClaseCategoriasDieta> _MiListaCategoriasDieta;
        private Context _MiContexto;

        public AdaptadorCategoriaDieta (ArrayList<ClaseCategoriasDieta> ListaDeCategorias,Context ContextoAUsar) {
            //almaceno la lista que recibo como parametro en una  variable privada
            _MiListaCategoriasDieta= ListaDeCategorias;
            //almaceno el contexto de la activity que me invoco
            _MiContexto= ContextoAUsar;
        }
        public  int getCount (){
            return _MiListaCategoriasDieta.size();
        }
        public ClaseCategoriasDieta getItem(int PoscicionAObtener){
            ClaseCategoriasDieta CategoriaADevovler;
            CategoriaADevovler = _MiListaCategoriasDieta.get(PoscicionAObtener);
            return CategoriaADevovler;
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
            TextoDeLaPoscicionActual = getItem(PosicionActual).GetNombreEnivado();
            //la asigno a ese textview su valor
            textViewNombre.setText(TextoDeLaPoscicionActual);

            return VistaADevovler;
        }
    }

    AdapterView.OnItemClickListener EscuchadorParaListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int CategoriaElegida = listCategorias.get(position).GetidCategoria();
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
            ActividadAnfitriona.ProcesarDatosActListaCat(CategoriaElegida);

        }
    };




}
