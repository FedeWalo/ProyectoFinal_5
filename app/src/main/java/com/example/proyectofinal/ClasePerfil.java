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
    private int NivelDeActividad;
    private Float Altura;
    private Date Fecha;
    private String FechaBusquedad;
    private int Edad;

    public ClasePerfil(){
        super();
    }

    ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
    SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    public ClasePerfil (int idPerfilRecibido,String SexoPerfilRecibido,Float PesoRecibido, int NivelDeActividadRecibido,Float AlturaRecibida, Date FechaRecibida,String FechaBusquedadRecibida, int EdadRecibida) {
        this.idPerfil = idPerfilRecibido;
        this.Sexo = SexoPerfilRecibido;
        this.Peso = PesoRecibido;
        this.NivelDeActividad = NivelDeActividadRecibido;
        this.Altura = AlturaRecibida;
        this.Fecha = FechaRecibida;
        this.FechaBusquedad = FechaBusquedadRecibida;
        this.Edad = EdadRecibida;
    }

    public int getIdPerfil(){return idPerfil;}

    public String getSexo(){return Sexo;}

    public Float getPeso(){return Peso;}

    public int getNivelDeActividad(){return NivelDeActividad;}

    public Float getAltura(){return Altura;}

    public Date getFecha(){return Fecha;}

    public String getFechaBusquedad(){return FechaBusquedad;}

    public int getEdad(){return Edad;}

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
        NuevoDatoPerfil.put("Edad",PerfilEnviado.getEdad());

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
                UltimosDatos.NivelDeActividad = RegistrosLeidos.getInt(3);
                UltimosDatos.Altura = RegistrosLeidos.getFloat(4);
                try {
                    UltimosDatos.Fecha = Timestamp.valueOf(RegistrosLeidos.getString(5));
                }catch (Exception e){}
                UltimosDatos.FechaBusquedad= RegistrosLeidos.getString(6);
                UltimosDatos.Edad = RegistrosLeidos.getInt(7);
            }
        }else{
            Log.d("Prueba","No hay perfiles");
            //NO HAY REGISTROS
        }

        return UltimosDatos;
    }


    int FitPointsAlDia(Context context){
        float CaloriasAConsumir;
        int FPAConsumir;
        ArrayList<ClaseObjetivosUsuario> objetivos;
        ClasePerfil DatosPerfil;
        ClaseObjetivosUsuario Objetivo = new ClaseObjetivosUsuario();
        ClaseObjetivosUsuario objetivosUsuario;
        objetivosUsuario = null;
        objetivos = Objetivo.ObtenerObjetivos(context);

        for (ClaseObjetivosUsuario objetivo: objetivos) {

            if (objetivo.getTipoDeObjetivo()== 0){

                objetivosUsuario = objetivo;

            }
        }
        ClasePerfil perfil = new ClasePerfil();
        DatosPerfil = perfil.TraerUltimosDatosPerfil(context);
        if(DatosPerfil.Sexo ==  "Mujer"){
            CaloriasAConsumir = 655 + (9.6f*DatosPerfil.Peso)+(1.8f*DatosPerfil.Altura)-(4.7f*DatosPerfil.Edad);
        }else{
            CaloriasAConsumir = 66 + (13.7f*DatosPerfil.Peso)+(5*DatosPerfil.Altura)-(6.8f*DatosPerfil.Edad);
        }

        if(DatosPerfil.NivelDeActividad==0){
            CaloriasAConsumir = CaloriasAConsumir*1.2f;
        }
        else if((DatosPerfil.NivelDeActividad>=1)&&(DatosPerfil.NivelDeActividad<=2)){
            CaloriasAConsumir = CaloriasAConsumir*1.375f;
        }
        else if((DatosPerfil.NivelDeActividad>=3)&&(DatosPerfil.NivelDeActividad<=5)){
            CaloriasAConsumir = CaloriasAConsumir*1.2f;
        }
        else if((DatosPerfil.NivelDeActividad>=6)&&(DatosPerfil.NivelDeActividad<=7)){
            CaloriasAConsumir = CaloriasAConsumir*1.2f;
        }
        else{
            CaloriasAConsumir = CaloriasAConsumir*1.2f;
        }

        if(objetivosUsuario != null){
            //Logica si quiere aumentar o bajar de peso
            float objetivo = objetivosUsuario.getMeta();
            if(Float.compare(objetivo,DatosPerfil.Peso) > 0){
                float kilosAAumentar = objetivosUsuario.getMeta()-DatosPerfil.Peso;
                float caloriasExtras = (kilosAAumentar*7000)/(Integer.parseInt(Objetivo.ObtenerDiferenciaDeSemanas(objetivosUsuario.getFechaDeInicio(),objetivosUsuario.getFechaDeFin()))*7);
                CaloriasAConsumir = CaloriasAConsumir + caloriasExtras;
            }
            else if (Float.compare(objetivo,DatosPerfil.Peso) < 0){
                float kilosABajar = DatosPerfil.Peso - objetivosUsuario.getMeta();
                float caloriasMenos = (kilosABajar*7000)/(Integer.parseInt(Objetivo.ObtenerDiferenciaDeSemanas(objetivosUsuario.getFechaDeInicio(),objetivosUsuario.getFechaDeFin()))*7);
                CaloriasAConsumir = CaloriasAConsumir - caloriasMenos;
            }

        }

        FPAConsumir = (int)CaloriasAConsumir/30;
        return FPAConsumir;

    }
}
