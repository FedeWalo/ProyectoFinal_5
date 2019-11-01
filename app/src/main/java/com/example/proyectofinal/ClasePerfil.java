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
    private String Sexo;
    private Float Peso;
    private String NivelDeActividad;
    private Float Altura;
    private Date Fecha;
    private String FechaBusquedad;

    public ClasePerfil(){
        super();
    }

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClasePerfil (int idPerfilRecibido,String SexoPerfilRecibido,Float PesoRecibido, String NivelDeActividadRecibido,Float AlturaRecibida, Date FechaRecibida,String FechaBusquedadRecivida) {
        this.idPerfil = idPerfilRecibido;
        this.Sexo = SexoPerfilRecibido;
        this.Peso = PesoRecibido;
        this.NivelDeActividad = NivelDeActividadRecibido;
        this.Altura = AlturaRecibida;
        this.Fecha = FechaRecibida;
        this.FechaBusquedad = FechaBusquedadRecivida;
    }

    public int getIdPerfil(){return idPerfil;}

    public String getSexo(){return Sexo;}

    public Float getPeso(){return Peso;}

    public String getNivelDeActividad(){return NivelDeActividad;}

    public Float getAltura(){return Altura;}

    public Date getFecha(){return Fecha;}

    public String getFechaBusquedad(){return FechaBusquedad;}

    public void InsertarNuevoPerfil(ClasePerfil PerfilEnviado, Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getWritableDatabase();

        ContentValues NuevoDatoPerfil;
        NuevoDatoPerfil= new ContentValues();

        NuevoDatoPerfil.put("Peso",PerfilEnviado.getPeso());
        NuevoDatoPerfil.put("Sexo",PerfilEnviado.getSexo());
        NuevoDatoPerfil.put("NivelDeActividad",PerfilEnviado.getNivelDeActividad());
        NuevoDatoPerfil.put("Altura",PerfilEnviado.getAltura());
        NuevoDatoPerfil.put("Fecha",PerfilEnviado.Fecha.toString());
        NuevoDatoPerfil.put("FechaParaBusq",FechaBusquedad);

        BaseDeDatos.insert("Perfil", null, NuevoDatoPerfil); //Inserto el registro
    }

    public ClasePerfil TraerUltimosDatosPerfil(Context context){
        AccesoALaBase = new ManejadorBaseDeDatos(context,"BDFitLife",null,1);
        BaseDeDatos = AccesoALaBase.getReadableDatabase();
        ClasePerfil UltimosDatos = new ClasePerfil();
        Cursor RegistrosLeidos;
        String SQLLectura;
        SQLLectura="SELECT * FROM Perfil ORDER BY idPerfil DESC LIMIT 1"; //entre las commilas va lo mismo que podria ir en un stored
        Log.d("prueba","la consulta es:" + SQLLectura);
        RegistrosLeidos =AccesoALaBase.EjecutarConsultaLeer(SQLLectura);
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistrosLeidos.getCount(); PunteroRegistro++){
                RegistrosLeidos.moveToPosition(PunteroRegistro);
                Log.d("prueba","entro a leer");

                UltimosDatos.idPerfil = RegistrosLeidos.getInt(0);
                UltimosDatos.Sexo =RegistrosLeidos.getString(1);
                UltimosDatos.Peso = RegistrosLeidos.getFloat(2);
                UltimosDatos.NivelDeActividad = RegistrosLeidos.getString(3);
                UltimosDatos.Altura = RegistrosLeidos.getFloat(4);
                try {
                    UltimosDatos.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(5));
                }catch (Exception e){}
                UltimosDatos.FechaBusquedad= RegistrosLeidos.getString(6);
            }
        }else{
            Log.d("Prueba","No hay perfiles");
            //NO HAY REGISTROS
        }

        return UltimosDatos;
    }

}
