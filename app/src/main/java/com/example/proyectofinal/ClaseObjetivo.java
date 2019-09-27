package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ClaseObjetivo {
    private int idObjetivo;
    private String NombreObjetivo;
    private int TipoDeObjetivo;

    public ClaseObjetivo(){
        super();
    }

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClaseObjetivo (int idObjetivoRecivida, String NombreObjetvoRecivido,int TipodeObjetivoRecivido) {
        this.idObjetivo = idObjetivoRecivida;
        this.NombreObjetivo = NombreObjetvoRecivido;
        this.TipoDeObjetivo = TipodeObjetivoRecivido;
    }

    public int getIdObjetivo(){return idObjetivo;}

    public String getNombreObjetivo(){return NombreObjetivo;}

    public int getTipoDeObjetivo(){return TipoDeObjetivo;}

    public ArrayList<ClaseObjetivo> ObtenerObjetivos(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseObjetivo> ListaADevolver;
        ListaADevolver = new ArrayList<>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="select * from Objetivos";
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos = AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseObjetivo ObjetoObjetivo = new ClaseObjetivo();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoObjetivo.idObjetivo = RegistrosLeidos.getInt(0);
                ObjetoObjetivo.NombreObjetivo = RegistrosLeidos.getString(1);
                ObjetoObjetivo.TipoDeObjetivo = RegistrosLeidos.getInt(2);

                ListaADevolver.add(ObjetoObjetivo);
            }
        }else{
            Log.d("Prueba","No hay objetivos");
            //NO HAY REGISTROS
        }

        return ListaADevolver;

    }


}

