package com.example.proyectofinal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ClaseComida {
    private int idComida;
    private String Nombre;
    private String Imagen;
    private int FitPoints;
    private String Detalle;
    private int idCategoria;
    private int Bueno;

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si



    public ClaseComida(){
        super();
    }

    public ClaseComida (int idComidaEnviado, String NombreEnviado,String ImagenEnviada,int FitPointsEnviados,String DetalleEnviado, int idCategoriaEnviada, int BuenoEnviado) {
        this.idComida = idComidaEnviado;
        this.Nombre = NombreEnviado;
        this.Imagen = ImagenEnviada;
        this.FitPoints = FitPointsEnviados;
        this.Detalle = DetalleEnviado;
        this.idCategoria = idCategoriaEnviada;
        this.Bueno = BuenoEnviado;
    }

    public int getidComida() { return idComida; }

    public int getIdCategoria() { return idCategoria; }

    public String getNombre()
    {
        return Nombre;
    }

    public String getImagen() { return Imagen; }

    public int getFitPoints()
    {
        return FitPoints;
    }

    public String getDetalle()
    {
        return Detalle;
    }

    public int getBueno()
    {
        return Bueno;
    }

    //Seters, eliminar cuando no se requiera agregar nuevas comidas



    public ArrayList<ClaseComida> ObtenerTodas(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseComida> ListaADevolver;
        ListaADevolver = new ArrayList<ClaseComida>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="select * from Comidas"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos = AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComida ObjetoComida = new ClaseComida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoComida.idComida = RegistrosLeidos.getInt(0);
                ObjetoComida.idCategoria = RegistrosLeidos.getInt(1);
                ObjetoComida.Nombre = RegistrosLeidos.getString(2);
                ObjetoComida.FitPoints = RegistrosLeidos.getInt(3);
                ObjetoComida.Bueno = RegistrosLeidos.getInt(4);
                ObjetoComida.Imagen = RegistrosLeidos.getString(5);
                ObjetoComida.Detalle = RegistrosLeidos.getString(6);

                Log.d("prueba","El id de la cat traida es " + ObjetoComida.idComida);
                Log.d("prueba","El nombre de la cat traida es " + ObjetoComida.Nombre);

                ListaADevolver.add(ObjetoComida);
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return ListaADevolver;
    }


    public ArrayList<ClaseComida> ObtenerComidaPorCat(int idCategoria,Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseComida> ListaADevolver;
        ListaADevolver = new ArrayList<ClaseComida>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="select * from Comidas where idCategoria ="+ idCategoria; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos = AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComida ObjetoComida = new ClaseComida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoComida.idComida = RegistrosLeidos.getInt(0);
                ObjetoComida.idCategoria = RegistrosLeidos.getInt(1);
                ObjetoComida.Nombre = RegistrosLeidos.getString(2);
                ObjetoComida.FitPoints = RegistrosLeidos.getInt(3);
                ObjetoComida.Bueno = RegistrosLeidos.getInt(4);
                ObjetoComida.Imagen = RegistrosLeidos.getString(5);
                ObjetoComida.Detalle = RegistrosLeidos.getString(6);

                Log.d("prueba","El id de la cat traida es " + ObjetoComida.idComida);
                Log.d("prueba","El nombre de la cat traida es " + ObjetoComida.Nombre);

                ListaADevolver.add(ObjetoComida);
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return ListaADevolver;
    }

    public void ConsumirNuevaComida(ClaseComida ComidaRecibida,Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();
        AccesoALaBase.InsertarComida(ComidaRecibida.idCategoria,ComidaRecibida.Nombre, ComidaRecibida.FitPoints, ComidaRecibida.Bueno, ComidaRecibida.Imagen, ComidaRecibida.Detalle,BaseDeDatos);
    }

}

