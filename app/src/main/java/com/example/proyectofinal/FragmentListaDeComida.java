package com.example.proyectofinal;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentListaDeComida extends Fragment implements View.OnClickListener {

    ArrayList<ClaseComida> listComidasPorCategoria;
    Button VolverAListaCategorias;
    private GridView GridViewListaComidasPorCategorias;
    @Override
    public View onCreateView(LayoutInflater infladorDeLayouts, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaAdevolver;
        VistaAdevolver = infladorDeLayouts.inflate(R.layout.layout_lista_comidas_dieta,GrupoDeLaVista,false);
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();


        //declaro e instancio las categorias
        ClaseComida MisComidas;
        MisComidas = new ClaseComida();
        //uso el objeto miscategorias para llenar la lista
        listComidasPorCategoria =MisComidas.ObtenerComidaPorCat(ActividadAnfitriona._IdCategoriaSelecionadaEnFragmentListaDeCategoriasComida, getActivity() );

        //declaro y relaciono mi objeto listview
        GridViewListaComidasPorCategorias = VistaAdevolver.findViewById(R.id.ListaComidas);

        //declaro y relaciono mi boton
        VolverAListaCategorias =VistaAdevolver.findViewById(R.id.VolverAListaCategorias);

        //defino e instancio el adaptador, mandandole el Arraylist y el contexto
        AdaptadorGrillaCategoriaDieta adaptador;
        adaptador= new AdaptadorGrillaCategoriaDieta (listComidasPorCategoria,VistaAdevolver.getContext());

        //le asigno el adaptador al listview
        GridViewListaComidasPorCategorias.setAdapter(adaptador);

        GridViewListaComidasPorCategorias.setOnItemClickListener(EscuchadorParaListView);
        VolverAListaCategorias.setOnClickListener(this);
        return VistaAdevolver;
    }



    public class AdaptadorGrillaCategoriaDieta extends BaseAdapter {

        //declaro la lista que va  contener la lista de categorias
        private ArrayList<ClaseComida> _MiListaDeComidas;
        private Context _MiContexto;

        public AdaptadorGrillaCategoriaDieta (ArrayList<ClaseComida> ListaDeComidas,Context ContextoAUsar) {
            //almaceno la lista que recibo como parametro en una  variable privada
            _MiListaDeComidas = ListaDeComidas;
            //almaceno el contexto de la activity que me invoco
            _MiContexto= ContextoAUsar;
        }
        public  int getCount (){
            return _MiListaDeComidas.size();
        }
        public ClaseComida getItem(int PoscicionAObtener){
            ClaseComida ComidaADevolver;
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
            VistaADevovler = InfladorDeLayouts.inflate(R.layout.diseno_item_cardview_comidas_dieta,GrupoActual,false);

            TextView textViewNombre;
            textViewNombre= VistaADevovler.findViewById(R.id.NombreCategoria);
            ImageView imageViewImagen;
            imageViewImagen= VistaADevovler.findViewById(R.id.DireccionImagen);

            //obtengo el nombre de la categoria que va en la poscicion actual
            String TextoDeLaPoscicionActual;
            TextoDeLaPoscicionActual = getItem(PosicionActual).getNombre();
            //la asigno a ese textview su valor
            textViewNombre.setText(TextoDeLaPoscicionActual);

            //obtengo el nombre de la categoria que va en la poscicion actual
            String PosMemoriaImagenActual;
            PosMemoriaImagenActual = getItem(PosicionActual).getImagen();
            int imageResource = getResources().getIdentifier(PosMemoriaImagenActual, "drawable", getActivity().getPackageName());
            //la asigno a ese iamgeview su valor
            imageViewImagen.setImageResource(imageResource);

            return VistaADevovler;
        }
    }

    AdapterView.OnItemClickListener EscuchadorParaListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ClaseComida ComidaElegida = listComidasPorCategoria.get(position);
            ActividadPrincipal ActividadAnfitriona;
            ActividadAnfitriona = (ActividadPrincipal) getActivity();
            ActividadAnfitriona.ProcesarDatosActListaComidas(ComidaElegida);

        }
    };

    @Override
    public void onClick(View view){
        ActividadPrincipal ActividadAnfitriona;
        ActividadAnfitriona = (ActividadPrincipal) getActivity();
        Button BotonPresionado;
        BotonPresionado= (Button)view;
        if(BotonPresionado.getId() ==R.id.VolverAListaCategorias) {
            ActividadAnfitriona.ProcesarDatosHomeDieta();
        }
    }
}
