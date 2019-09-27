package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ClaseComidaConsumida {
    private int idComida;
    private String Nombre;
    private String Imagen;
    private int FitPoints;
    private String Detalle;
    private int idCategoria;
    private int Bueno;
    private Date Fecha;
    private String FechaParaBusq;

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClaseComidaConsumida(){
        super();
    }

    public ClaseComidaConsumida (int idComidaEnviado, String NombreEnviado,String ImagenEnviada,int FitPointsEnviados,String DetalleEnviado, int idCategoriaEnviada, int BuenoEnviado, Date fechaEnviada, String FechaParaBusq) {
        this.idComida = idComidaEnviado;
        this.Nombre = NombreEnviado;
        this.Imagen = ImagenEnviada;
        this.FitPoints = FitPointsEnviados;
        this.Detalle = DetalleEnviado;
        this.idCategoria = idCategoriaEnviada;
        this.Bueno = BuenoEnviado;
        this.Fecha = fechaEnviada;
        this.FechaParaBusq = FechaParaBusq;

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

    public Date getFecha(){ return Fecha; }

    public String getFechaParaBusq() { return FechaParaBusq; }


    public void InsertarComidaConsumida(ClaseComida ComidaEnviada, Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();

        ContentValues NuevaComidaConsumida; //Creo un registro en memoria donde pondre los parametros
        NuevaComidaConsumida= new ContentValues();

        NuevaComidaConsumida.put("idCategoria",ComidaEnviada.getIdCategoria());
        NuevaComidaConsumida.put("Nombre",ComidaEnviada.getNombre());
        NuevaComidaConsumida.put("FitPoints",ComidaEnviada.getFitPoints());
        NuevaComidaConsumida.put("Bueno",ComidaEnviada.getBueno());
        NuevaComidaConsumida.put("Imagen",ComidaEnviada.getImagen());
        NuevaComidaConsumida.put("Detalle",ComidaEnviada.getDetalle());

        //obtengo la fecha actual y la pongo
        Calendar calendar =Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        Timestamp FechaActual = new Timestamp(FechaHoy.getTime());
        NuevaComidaConsumida.put("Fecha",FechaActual.toString());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(FechaHoy);
        NuevaComidaConsumida.put("FechaParaBusq",strDate);

        BaseDeDatos.insert("ComidasConsumidas", null, NuevaComidaConsumida); //Inserto el registro
    }

    public ArrayList<ClaseComidaConsumida> ObtenerUltimasConsumidas(Context context){

        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseComidaConsumida> ListaADevolver;
        ListaADevolver = new ArrayList<ClaseComidaConsumida>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="SELECT * FROM ComidasConsumidas ORDER BY idComida DESC LIMIT 2"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComidaConsumida ObjetoComida = new ClaseComidaConsumida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoComida.idComida = RegistrosLeidos.getInt(0);
                ObjetoComida.idCategoria = RegistrosLeidos.getInt(1);
                ObjetoComida.Nombre = RegistrosLeidos.getString(2);
                ObjetoComida.FitPoints = RegistrosLeidos.getInt(3);
                ObjetoComida.Bueno = RegistrosLeidos.getInt(4);
                ObjetoComida.Imagen = RegistrosLeidos.getString(5);
                ObjetoComida.Detalle = RegistrosLeidos.getString(6);
                try {
                    ObjetoComida.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(7));
                }catch (Exception e){}
                ObjetoComida.FechaParaBusq = RegistrosLeidos.getString(8);
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

    public int ConsultarFitPointDelDia(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        int CantFPConsumidos = 0;
        Cursor RegistrosLeidos;
        String SQLLectura;

        Calendar calendar = Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FECHAHOYQUERY = dateFormat.format(FechaHoy);


        SQLLectura="SELECT * FROM ComidasConsumidas WHERE FechaParaBusq = '"+ FECHAHOYQUERY + "'";
       // SQLLectura="select * from ComidasConsumidas";
       // SQLLectura="SELECT * FROM ComidasConsumidas";
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComidaConsumida ObjetoComidaConsumida = new ClaseComidaConsumida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoComidaConsumida.idComida = RegistrosLeidos.getInt(0);
                ObjetoComidaConsumida.idCategoria = RegistrosLeidos.getInt(1);
                ObjetoComidaConsumida.Nombre = RegistrosLeidos.getString(2);
                ObjetoComidaConsumida.FitPoints = RegistrosLeidos.getInt(3);
                ObjetoComidaConsumida.Bueno = RegistrosLeidos.getInt(4);
                ObjetoComidaConsumida.Imagen = RegistrosLeidos.getString(5);
                ObjetoComidaConsumida.Detalle = RegistrosLeidos.getString(6);
                try {
                    ObjetoComidaConsumida.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(7));
                }catch (Exception e){}
                ObjetoComidaConsumida.FechaParaBusq = RegistrosLeidos.getString(8);
                CantFPConsumidos+=ObjetoComidaConsumida.FitPoints;
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return CantFPConsumidos;
    }

    public int ConsultarProporcionBuenosDelDia(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        int CantFPConsumidos = 0;
        Cursor RegistrosLeidos;
        String SQLLectura;
        int FitPointsBuenos=0;
        int PorcentajeBuenos=0;
        Calendar calendar = Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FECHAHOYQUERY = dateFormat.format(FechaHoy);


        SQLLectura="SELECT * FROM ComidasConsumidas WHERE FechaParaBusq = '"+ FECHAHOYQUERY + "'";
        // SQLLectura="select * from ComidasConsumidas";
        // SQLLectura="SELECT * FROM ComidasConsumidas";
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){

            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComidaConsumida ObjetoComidaConsumida = new ClaseComidaConsumida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                ObjetoComidaConsumida.idComida = RegistrosLeidos.getInt(0);
                ObjetoComidaConsumida.idCategoria = RegistrosLeidos.getInt(1);
                ObjetoComidaConsumida.Nombre = RegistrosLeidos.getString(2);
                ObjetoComidaConsumida.FitPoints = RegistrosLeidos.getInt(3);
                ObjetoComidaConsumida.Bueno = RegistrosLeidos.getInt(4);
                ObjetoComidaConsumida.Imagen = RegistrosLeidos.getString(5);
                ObjetoComidaConsumida.Detalle = RegistrosLeidos.getString(6);
                try {
                    ObjetoComidaConsumida.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(7));
                }catch (Exception e){}
                ObjetoComidaConsumida.FechaParaBusq = RegistrosLeidos.getString(8);
                CantFPConsumidos+=ObjetoComidaConsumida.FitPoints;
                if (RegistrosLeidos.getInt(4) == 1){
                    FitPointsBuenos +=ObjetoComidaConsumida.FitPoints;
                }
                PorcentajeBuenos = (FitPointsBuenos * 100)/CantFPConsumidos;
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return PorcentajeBuenos;
    }


    public int[] ConsultarProporcionBuenosDeLosUltimos7Dias(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();
        int[]PorcentajeBuenos=new int[7];
        int CantFPConsumidos = 0;
        Cursor RegistrosLeidos;
        String SQLLectura;
        int FitPointsBuenos=0;
        Calendar calendar = Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        FechaHoy = calendar.getTime();
        for(int i=0; i<7; i++){
            String FECHAHOYQUERY = dateFormat.format(FechaHoy);
            SQLLectura="SELECT * FROM ComidasConsumidas WHERE FechaParaBusq = '"+ FECHAHOYQUERY + "'";
            //   FechaHoy - 1 dia
            calendar.setTime(FechaHoy);
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            FechaHoy = calendar.getTime();
            Log.d("prueba","la consulta es:" + SQLLectura);
            RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

            //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
            if(RegistrosLeidos.getCount()>0){

                for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                    ClaseComidaConsumida ObjetoComidaConsumida = new ClaseComidaConsumida();
                    RegistrosLeidos.moveToPosition(PunteroRegistro);
                    Log.d("prueba","entro a leer");

                    ObjetoComidaConsumida.idComida = RegistrosLeidos.getInt(0);
                    ObjetoComidaConsumida.idCategoria = RegistrosLeidos.getInt(1);
                    ObjetoComidaConsumida.Nombre = RegistrosLeidos.getString(2);
                    ObjetoComidaConsumida.FitPoints = RegistrosLeidos.getInt(3);
                    ObjetoComidaConsumida.Bueno = RegistrosLeidos.getInt(4);
                    ObjetoComidaConsumida.Imagen = RegistrosLeidos.getString(5);
                    ObjetoComidaConsumida.Detalle = RegistrosLeidos.getString(6);
                    try {
                        ObjetoComidaConsumida.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(7));
                    }catch (Exception e){}
                    ObjetoComidaConsumida.FechaParaBusq = RegistrosLeidos.getString(8);
                    CantFPConsumidos+=ObjetoComidaConsumida.FitPoints;
                    if (RegistrosLeidos.getInt(4) == 1){
                        FitPointsBuenos +=ObjetoComidaConsumida.FitPoints;
                    }
                }
                if (FitPointsBuenos> 0){
                    PorcentajeBuenos[i]= (FitPointsBuenos * 100)/CantFPConsumidos;
                }
                else{
                    PorcentajeBuenos[i] = 0;
                }
            }else{
                Log.d("Prueba","No hay comidas");
                //NO HAY REGISTROS
                PorcentajeBuenos[i] =0;
            }
        }
        return PorcentajeBuenos;
    }

}
