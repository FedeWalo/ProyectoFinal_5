package com.example.proyectofinal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ClaseCategoriasDieta {
    private  int idCategoria;
    private String Nombre;

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClaseCategoriasDieta(){
        super();
    }

    //Eliminar el set cuando ya no debamos ingresar mas categorias
    public ClaseCategoriasDieta (int idCategoriaEnviado, String NombreEnivado) {
        this.idCategoria = idCategoriaEnviado;
        this.Nombre = NombreEnivado;
    }

    public int GetidCategoria() {
        return idCategoria;
    }
    public String GetNombreEnivado() {
        return Nombre;
    }

    public ArrayList<ClaseCategoriasDieta> ObtenerTodas(Context context){
        /*
         ERRORES:
           - EN VEZ DE TRAER EL NOMBRE DE LA CAT TRAER DOS VECES EL ID(una en donde tiene que y otra en ObjetoCategoria.Nombre)
           - LA LISTA MUESTRA SOLO EL ULTIMO REGISTRO TRAIDO(nose si es porque se carga mal el array o se muestra mal en el fragment)
        */
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseCategoriasDieta> ListaADevolver;
        ListaADevolver = new ArrayList<ClaseCategoriasDieta>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="select * from Categorias"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseCategoriasDieta ObjetoCategoria = new ClaseCategoriasDieta();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoCategoria.idCategoria = RegistrosLeidos.getInt(0);
                ObjetoCategoria.Nombre = RegistrosLeidos.getString(1);

                Log.d("prueba","El id de la cat traida es " + ObjetoCategoria.idCategoria);
                Log.d("prueba","El nombre de la cat traida es " + ObjetoCategoria.Nombre);

                ListaADevolver.add(ObjetoCategoria);
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return ListaADevolver;
    }
}

