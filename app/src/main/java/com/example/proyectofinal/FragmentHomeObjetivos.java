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
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentHomeObjetivos extends Fragment implements View.OnClickListener {
    ArrayList<ClaseObjetivo> ListObjetivos;
    ArrayList<ClaseObjetivosUsuario> ListObjetivosUsuario;
    private GridView GridViewListObjetivos;
    Button BotonAnadirObjetivo;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_pantalla_home_objetivos,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        BotonAnadirObjetivo= VistaAdevolver.findViewById(R.id.Boton_FrHomeObjetivos_AnadirObj);
        //declaro e instancio las categorias
        ClaseObjetivosUsuario MisObjetivos;
        MisObjetivos = new ClaseObjetivosUsuario();

        //uso el objeto miscategorias para llenar la lista

        ListObjetivosUsuario =MisObjetivos.ObtenerObjetivos(getActivity());

        //muestro el botor de anadir dependiendo si ya estan todos los objetivos o no
        ClaseObjetivo _MisObjetivos;
        _MisObjetivos = new ClaseObjetivo();
        ListObjetivos = _MisObjetivos.ObtenerObjetivos(getActivity());


        if (ListObjetivosUsuario.size() == ListObjetivos.size()){
            BotonAnadirObjetivo.setEnabled(false);
            BotonAnadirObjetivo.setVisibility(View.INVISIBLE);
        }
        else{
            BotonAnadirObjetivo.setOnClickListener(this);
        }

        //declaro y relaciono mi objeto listview
        GridViewListObjetivos = VistaAdevolver.findViewById(R.id.ListaObjetivos);

        //defino e instancio el adaptador, mandandole el Arraylist y el contexto

        AdaptadorGrillaObjetivos adaptador;
        adaptador= new AdaptadorGrillaObjetivos(ListObjetivosUsuario,VistaAdevolver.getContext());

        //le asigno el adaptador al listview
        GridViewListObjetivos.setAdapter(adaptador);

        GridViewListObjetivos.setOnItemClickListener(EscuchadorParaListView);


        return VistaAdevolver;
    }



    public class AdaptadorGrillaObjetivos extends BaseAdapter {

        //declaro la lista que va  contener la lista de categorias
        private ArrayList<ClaseObjetivosUsuario> _MiListaDeObjetivos;
        private Context _MiContexto;

        public AdaptadorGrillaObjetivos(ArrayList<ClaseObjetivosUsuario> ListaDeObjetivos, Context ContextoAUsar) {
            //almaceno la lista que recibo como parametro en una  variable privada
            _MiListaDeObjetivos = ListaDeObjetivos;
            //almaceno el contexto de la activity que me invoco
            _MiContexto= ContextoAUsar;
        }
        public  int getCount (){
            return _MiListaDeObjetivos.size();
        }
        public ClaseObjetivosUsuario getItem(int PoscicionAObtener){
            ClaseObjetivosUsuario ComidaADevolver;
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
            VistaADevovler = InfladorDeLayouts.inflate(R.layout.diseno_item_listview_objetivos_usuario,GrupoActual,false);

            TextView textViewNombre;
            textViewNombre= VistaADevovler.findViewById(R.id.NombreCategoria);

            //obtengo el nombre de la categoria que va en la poscicion actual
            String TextoDeLaPoscicionActual;


            //la asigno a ese textview su valor

           //me tengo que fijar si 0 es de peso y 1 es de comida o al reves
            if (getItem(PosicionActual).getTipoDeObjetivo() ==0 ){
                TextoDeLaPoscicionActual = "modificar peso";
            }
            else{
                TextoDeLaPoscicionActual = "comer saludable";
            }

            textViewNombre.setText(TextoDeLaPoscicionActual);

            return VistaADevovler;
        }
    }

    AdapterView.OnItemClickListener EscuchadorParaListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ClaseObjetivosUsuario U = new ClaseObjetivosUsuario(ListObjetivosUsuario.get(position).getIdObjetivo(), ListObjetivosUsuario.get(position).getTipoDeObjetivo(), ListObjetivosUsuario.get(position).getMeta(), ListObjetivosUsuario.get(position).getProgreso(), ListObjetivosUsuario.get(position).getFechaDeInicio(), ListObjetivosUsuario.get(position).getFechaDeFin(), ListObjetivosUsuario.get(position).getPesoInicial());
            if(ListObjetivosUsuario.get(position).getTipoDeObjetivo()== 0) {
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
    public void onClick(View v) {
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        ActividadAnfitriona.IrAFrlistaObjetivos();
    }
}
