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

public class ClasePerfil {
    private int idPerfil;
    private String NombreUsuario;
    private Float Peso;
    private String NivelDeActividad;
    private Float Altura;
    private Date Fecha;

    public ClasePerfil(){
        super();
    }

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClasePerfil (int idPerfilRecibido, String NombreUsuarioRecibido,Float PesoRecibido, String NivelDeActividadRecibido,Float AlturaRecibida, Date FechaRecibida) {
        this.idPerfil = idPerfilRecibido;
        this.NombreUsuario = NombreUsuarioRecibido;
        this.Peso = PesoRecibido;
        this.NivelDeActividad = NivelDeActividadRecibido;
        this.Altura = AlturaRecibida;
        this.Fecha = FechaRecibida;
    }

    public int getIdPerfil(){return idPerfil;}

    public String getNombreUsuario(){return NombreUsuario;}

    public Float getPeso(){return Peso;}

    public String getNivelDeActividad(){return NivelDeActividad;}

    public Float getAltura(){return Altura;}

    public Date getFecha(){return Fecha;}

    public void InsertarNuevoPerfil(ClasePerfil PerfilEnviado, Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();

        ContentValues NuevoDatoPerfil;
        NuevoDatoPerfil= new ContentValues();

        NuevoDatoPerfil.put("idPerfil",PerfilEnviado.getIdPerfil());
        NuevoDatoPerfil.put("NombreUsuario",PerfilEnviado.getNombreUsuario());
        NuevoDatoPerfil.put("Peso",PerfilEnviado.getPeso());
        NuevoDatoPerfil.put("NivelDeActividad",PerfilEnviado.getNivelDeActividad());
        NuevoDatoPerfil.put("Altura",PerfilEnviado.getAltura());

        //obtengo la fecha actual y la inserto
        Calendar calendar =Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        Timestamp FechaActual = new Timestamp(FechaHoy.getTime());
        NuevoDatoPerfil.put("Fecha",FechaActual.toString());

        BaseDeDatos.insert("Perfil", null, NuevoDatoPerfil); //Inserto el registro
    }

    public ClasePerfil TraerUltimosDatosPerfil(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLfife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();

        ClasePerfil UltimosDatos = new ClasePerfil();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="SELECT * FROM Perfil ORDER BY idPerfil DESC LIMIT 1"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                ClaseComidaConsumida ObjetoComida = new ClaseComidaConsumida();
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                UltimosDatos.idPerfil = RegistrosLeidos.getInt(0);
                UltimosDatos.NombreUsuario = RegistrosLeidos.getString(1);
                UltimosDatos.Peso = RegistrosLeidos.getFloat(2);
                UltimosDatos.NivelDeActividad = RegistrosLeidos.getString(3);
                UltimosDatos.Altura = RegistrosLeidos.getFloat(4);
                try {
                    UltimosDatos.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(5));
                }catch (Exception e){}
            }
        }else{
            Log.d("Prueba","No hay comidas");
            //NO HAY REGISTROS
        }

        return UltimosDatos;
    }

}
