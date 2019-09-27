package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClaseObjetivosUsuario {

    private int idObjetivo;
    private int TipoDeObjetivo;
    private int Meta;
    private int Progreso;
    private Date FechaDeInicio;
    private Date FechaDeFin;
    private int PesoInicial;

    public ClaseObjetivosUsuario() {
        super();
    }

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClaseObjetivosUsuario(int idObjetivoRecibida,int TipodeObjetivoRecibido, int MetaRecibida, int ProgresoRecibido, Date FechaDeInicioRecibido,Date FechaDeFinRecibido, int PesoInicialRecibido) {
        this.idObjetivo = idObjetivoRecibida;
        this.TipoDeObjetivo = TipodeObjetivoRecibido;
        this.Meta = MetaRecibida;
        this.Progreso = ProgresoRecibido;
        this.FechaDeInicio = FechaDeInicioRecibido;
        this.FechaDeFin = FechaDeFinRecibido;
        this.PesoInicial = PesoInicialRecibido;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public int getTipoDeObjetivo() {
        return TipoDeObjetivo;
    }

    public int getMeta() {
        return Meta;
    }

    public Date getFechaDeInicio(){
        return FechaDeInicio;
    }

    public Date getFechaDeFin(){
        return FechaDeFin;
    }

    public int getProgreso(){
        return Progreso;
    }

    public int getPesoInicial(){
        return PesoInicial;
    }

    public void AgregarNuevoObjetivo(ClaseObjetivosUsuario ObjetivoAAgregar, Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();
        Timestamp FechaInicio = new Timestamp(ObjetivoAAgregar.getFechaDeInicio().getTime());
        Timestamp Fechafin  = new Timestamp(ObjetivoAAgregar.getFechaDeFin().getTime());
        ContentValues NuevoObjetivo; //Creo un registro en memoria donde pondre los parametros
        NuevoObjetivo= new ContentValues();

        NuevoObjetivo.put("TipoDeObjetivo",ObjetivoAAgregar.getTipoDeObjetivo());
        NuevoObjetivo.put("Meta",ObjetivoAAgregar.getMeta());
        NuevoObjetivo.put("Progreso",ObjetivoAAgregar.getProgreso());
        NuevoObjetivo.put("FechaDeInicio",FechaInicio.toString());
        NuevoObjetivo.put("FechaDeFin",Fechafin.toString());

        BaseDeDatos.insert("ObjetivosUsuario", null, NuevoObjetivo); //Inserto el registro
    }

    public ArrayList<ClaseObjetivosUsuario> ObtenerObjetivos(Context context){
        //REVISAR
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ArrayList<ClaseObjetivosUsuario> ListaADevolver;
        ListaADevolver = new ArrayList<>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="select * from ObjetivosUsuario"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos = AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseObjetivosUsuario ObjetoObjetivo = new ClaseObjetivosUsuario();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");
                ObjetoObjetivo.idObjetivo = RegistrosLeidos.getInt(0);
                ObjetoObjetivo.TipoDeObjetivo = RegistrosLeidos.getInt(1);
                ObjetoObjetivo.Meta = RegistrosLeidos.getInt(2);
                ObjetoObjetivo.Progreso = RegistrosLeidos.getInt(3);
               try {
                   ObjetoObjetivo.FechaDeInicio = Timestamp.valueOf(RegistrosLeidos.getString(4));
               }catch (Exception e){}
               try {
                   ObjetoObjetivo.FechaDeFin = Timestamp.valueOf(RegistrosLeidos.getString(5));
               }catch (Exception e){}


                ListaADevolver.add(ObjetoObjetivo);
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return ListaADevolver;

    }

    public void progresoObjetivoPeso(ClaseObjetivosUsuario ObjetivoAModificar, int PesoRecibido,Context context){
        //recibe el peso actualizado cuando lo modifica en la pantalla EditarPesoDelPerfil
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();


        ContentValues UpdatePeso; //Creo un registro en memoria donde pondre los parametros

        UpdatePeso= new ContentValues();
        int objetivoPeso = ObjetivoAModificar.PesoInicial- ObjetivoAModificar.Meta;
        int KilosQueBaje_subi=ObjetivoAModificar.PesoInicial- PesoRecibido;
        int ProgresoNuevo = 0;
        int PesoADevolver=0;
        ProgresoNuevo = objetivoPeso - KilosQueBaje_subi;

        //si su objetivo es subir de peso
        if(objetivoPeso<0){

            //le faltan kilos a subir
            if (ProgresoNuevo > 0){
                PesoADevolver =ProgresoNuevo;
            }
            //cumplio su objetivo
            else if (ProgresoNuevo == 0){
                PesoADevolver =0;
            }
            //sobrecumplio su objetivo
            else if (ProgresoNuevo < 0){
                PesoADevolver=ProgresoNuevo;
            }
        }
        //si su objetivo es mantenerse
        else if (objetivoPeso == 0){

            //esta por debajo de su objetivo
            if (ProgresoNuevo > 0){
                PesoADevolver =ProgresoNuevo;
            }
            //cumplio su objetivo
            else if (ProgresoNuevo == 0){
                PesoADevolver =0;
            }
            //esta por arriba de su objetivo
            else if (ProgresoNuevo < 0){
                PesoADevolver=ProgresoNuevo;
            }
        }
        //si su objetivo es bajar de peso
        else if(objetivoPeso>0){
            //sobrecumplio sus objetivos
            if (ProgresoNuevo > 0){
                PesoADevolver =ProgresoNuevo;
            }
            //cumplio su objetivo
            else if (ProgresoNuevo == 0){
                PesoADevolver =0;
            }
            //le faltan kilos a bajar
            else if (ProgresoNuevo < 0){
                PesoADevolver=ProgresoNuevo;
            }
        }

        UpdatePeso.put("Progreso",ProgresoNuevo);

        BaseDeDatos.insert("ObjetivosUsuario", null, UpdatePeso); //Inserto el registro

    }

    public void UpdateObjetivo(ClaseObjetivosUsuario ObjetoRecibido, Context context){
        //recive el peso actualizado cuando lo modifica en la pantalla EditarPesoDelPerfil
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();
        Timestamp FechaInicio = new Timestamp(ObjetoRecibido.getFechaDeInicio().getTime());
        Timestamp Fechafin  = new Timestamp(ObjetoRecibido.getFechaDeFin().getTime());
        ContentValues Update;
        Update = new ContentValues();
        Update.put("TipoDeObjetivo",ObjetoRecibido.getTipoDeObjetivo());
        Update.put("Meta", ObjetoRecibido.getMeta());
        Update.put("Progreso",ObjetoRecibido.getProgreso());
        Update.put("FechaDeInicio",FechaInicio.toString());
        Update.put("FechaDeFin",Fechafin.toString());

        String idConvertido = Integer.toString(ObjetoRecibido.idObjetivo);
        BaseDeDatos.update("ObjetivosUsuario", Update, "idObjetivo = ?", new String[]{idConvertido});

    }

    public void EliminarObjetivoUsuario(int IdRecibido,Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();

        String idConvertido = Integer.toString(IdRecibido);

        String whereClause = "idObjetivo=?";
        String whereArgs[] = {idConvertido};
        BaseDeDatos.delete("ObjetivosUsuario", whereClause, whereArgs);
    }

    public Date SumarRestarDias (Date Fecha, int Dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Fecha);
        calendar.add(Calendar.DAY_OF_YEAR, Dias);
        return calendar.getTime();
    }


    public String ObtenerDiferenciaDeSemanas(Date fechaDeInicio,Date fechaDeFin) {
        //los milisegundos
        long diferenciaMils = fechaDeFin.getTime() - fechaDeInicio.getTime();

        //obtenemos los segundos
        long segundos = diferenciaMils / 1000;

        //obtenemos las horas
        long horas = segundos / 3600;

        //obtenemos los dias
        long dias = horas / 24;

        //obtenemos los dias
        long semanas = dias / 7;


        return Long.toString(semanas);
    }


    public int ObtenerIdealPorcentajeFitPointsBuenos(Context context) {
        //REVISAR
        AccesoALaBase = new ManejadorBaseDeDatos(context, "BDFitLife", null, 1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();
        int ProporcionBuenoIdeal=0;
        ArrayList<ClaseObjetivosUsuario> ListaADevolver;
        ListaADevolver = new ArrayList<>();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura = "select * from ObjetivosUsuario"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba", "la consulta es:" + SQLLectura);
        RegistrosLeidos = AccesoALaBase.EjecutarConsultaLeer(SQLLectura);

        //REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if (RegistrosLeidos.getCount() > 0) {
            for (int PunteroRegistro = 0; PunteroRegistro < RegistrosLeidos.getCount(); PunteroRegistro++) {
                ClaseObjetivosUsuario ObjetoObjetivo = new ClaseObjetivosUsuario();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba", "entro a leer");
                ObjetoObjetivo.idObjetivo = RegistrosLeidos.getInt(0);
                ObjetoObjetivo.TipoDeObjetivo = RegistrosLeidos.getInt(1);
                ObjetoObjetivo.Meta = RegistrosLeidos.getInt(2);
                ObjetoObjetivo.Progreso = RegistrosLeidos.getInt(3);
                try {
                    ObjetoObjetivo.FechaDeInicio = Timestamp.valueOf(RegistrosLeidos.getString(4));
                } catch (Exception e) {
                }
                try {
                    ObjetoObjetivo.FechaDeFin = Timestamp.valueOf(RegistrosLeidos.getString(5));
                } catch (Exception e) {
                }


                if(ObjetoObjetivo.TipoDeObjetivo == 1){
                    ProporcionBuenoIdeal =ObjetoObjetivo.Meta;
                }
            }
        } else {
            Log.d("Prueba", "No hay comidas");
            //NO HAY REGISTROS
        }

        return ProporcionBuenoIdeal;

    }

    }