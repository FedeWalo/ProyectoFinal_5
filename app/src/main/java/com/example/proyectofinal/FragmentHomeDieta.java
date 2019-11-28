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

public class FragmentHomeDieta extends Fragment implements View.OnClickListener {
    Button Boton;
    ArrayList<ClaseComidaConsumida> listComidasConsumidas;
    ArrayList<ClaseComida> ListComidas;
    ArrayList<ClaseComida> ListADevolver;
    ListView ListViewListaUltimasComidasConsumidas;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts,ViewGroup GrupoDeLaVista, Bundle Datos) {
        ListADevolver = new ArrayList<>();
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_home_dieta,GrupoDeLaVista,false);
        Boton= VistaAdevolver.findViewById(R.id.Boton_FrInicio);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();

        //declaro e instancio las categorias
        ClaseComidaConsumida MisComidasConsumidas;
        MisComidasConsumidas = new ClaseComidaConsumida();
        //uso el objeto miscategorias para llenar la lista
        listComidasConsumidas =MisComidasConsumidas.ObtenerUltimasConsumidas(getActivity());

        //declaro e instancio las categorias
        ClaseComida MisComidas;
        MisComidas = new ClaseComida();
        //uso el objeto miscategorias para llenar la lista
        ListComidas =MisComidas.ObtenerTodas(getActivity());


        for (ClaseComida comida: ListComidas) {

            for (ClaseComidaConsumida _comida: listComidasConsumidas) {

                if (comida.getNombre().equals(_comida.getNombre())) {
                   ListADevolver.add(comida);

                }
            }
        }

        //declaro y relaciono mi objeto listview
        ListViewListaUltimasComidasConsumidas = VistaAdevolver.findViewById(R.id.ListaCategorias);

        //defino e instancio el adaptador, mandandole el Arraylist y el contexto
        FragmentHomeDieta.AdaptadorListViewCategoriaDieta adaptador;
        adaptador= new FragmentHomeDieta.AdaptadorListViewCategoriaDieta(listComidasConsumidas, VistaAdevolver.getContext());

        //le asigno el adaptador al listview
        ListViewListaUltimasComidasConsumidas.setAdapter(adaptador);
        ListViewListaUltimasComidasConsumidas.setOnItemClickListener(EscuchadorParaListView);
        Boton.setOnClickListener(this);
        return VistaAdevolver;
    }

    @Override
    public void onClick(View v) {
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ActividadAnfitriona.ProcesarDatosHomeDieta();
    }

    public class AdaptadorListViewCategoriaDieta extends BaseAdapter {

        //declaro la lista que va  contener la lista de categorias
        private ArrayList<ClaseComidaConsumida> _MiListaDeComidas;
        private Context _MiContexto;

        public AdaptadorListViewCategoriaDieta (ArrayList<ClaseComidaConsumida> ListaDeComidas,Context ContextoAUsar) {
            //almaceno la lista que recibo como parametro en una  variable privada
            _MiListaDeComidas = ListaDeComidas;
            //almaceno el contexto de la activity que me invoco
            _MiContexto= ContextoAUsar;
        }
        public  int getCount (){
            return _MiListaDeComidas.size();
        }
        public ClaseComidaConsumida getItem(int PoscicionAObtener){
            ClaseComidaConsumida ComidaADevolver;
            ComidaADevolver = _MiListaDeComidas.get(PoscicionAObtener);
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
            VistaADevovler = InfladorDeLayouts.inflate(R.layout.diseno_item_listview_ultimas_comidas,GrupoActual,false);

            TextView textViewNombre;
            textViewNombre= VistaADevovler.findViewById(R.id.NombreCategoria);

            //obtengo el nombre de la categoria que va en la poscicion actual
            String TextoDeLaPoscicionActual;
            TextoDeLaPoscicionActual = getItem(PosicionActual).getNombre();
            //la asigno a ese textview su valor
            textViewNombre.setText(TextoDeLaPoscicionActual);

            return VistaADevovler;
        }
    }

    AdapterView.OnItemClickListener EscuchadorParaListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ClaseComida ComidaElegida = new ClaseComida();
           if (listComidasConsumidas.size() > 0) {
               for (ClaseComida comida : ListADevolver) {

                   if (comida.getNombre().equals(listComidasConsumidas.get(position).getNombre())) {
                       ComidaElegida = comida;
                   }

               }
           }
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
            ActividadAnfitriona.ProcesarDatosActListaComidas(ComidaElegida);
        }
    };

}
