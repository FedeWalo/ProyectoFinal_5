package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ManejadorBaseDeDatos extends SQLiteOpenHelper {

    public ManejadorBaseDeDatos(Context contexto, String NombreBase, SQLiteDatabase.CursorFactory fabrica, int VersionCreacion){
        super(contexto, NombreBase, fabrica, VersionCreacion);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos){
        //declaro e inicializo el string de creacion de la tabla
        String SQLCrearTabla;
        SQLCrearTabla = "create table Comidas(idComida integer primary key autoincrement, idCategoria integer, Nombre text, FitPoints integer, Bueno integer, Imagen text, Detalle text)";
        //Ejecuto la creacion  de la tabla Comidas
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Categorias ss
        SQLCrearTabla = "create table Categorias(idCategoria integer primary key autoincrement, Nombre text)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla ComidasConsumidas
        SQLCrearTabla = "create table ComidasConsumidas(idComida integer primary key autoincrement, idCategoria integer, Nombre text, FitPoints integer, Bueno integer, Imagen text, Detalle text , Fecha DATE DEFAULT CURRENT_DATE, FechaParaBusq text )";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Objetivos
        SQLCrearTabla = "create table Objetivos(idObjetivo integer primary key autoincrement, NombreObjetivo text, TipoDeObjetivo integer)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla ObjetivosUsuarios
        SQLCrearTabla = "create table ObjetivosUsuario(idObjetivo integer primary key autoincrement, TipoDeObjetivo integer, Meta integer, Progreso integer, FechaDeInicio DATE DEFAULT CURRENT_DATE, FechaDeFin DATE)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Estadisticas
        SQLCrearTabla = "create table Estadisticas(CantFPPorDia integer)";


        /*//Creacion tabla Usuarios
        SQLCrearTabla = "create table Usuarios(NombreUsuario text primary key, Peso integer, Imagen text, NivelDeActividad integer, DiasRacha integer)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Rutinas
        SQLCrearTabla = "create table Rutinas(idRutina integer primary key autoincrement, idEnfoque integer, Nombre text, Imagen text, PorcentajeDeEfectividad integer)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla EjerciciosXRutina
        SQLCrearTabla = "create table EjerciciosXRutina(idEjercicioXRutina integer primary key autoincrement, idRutina integer, idEjercicio integer, Repeticiones integer, Sets integer, Kilos integer)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Ejercicios
        SQLCrearTabla = "create table Ejercios(idEjercicio integer primary key autoincrement, idGrupoMuscular integer, Nombre text, Imagen text)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla DiasXRutina
        SQLCrearTabla = "create table DiasXRutina(idDiasXRutina integer primary key autoincrement, idRutina integer, DiaPlaneado text)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla GruposMusculares
        SQLCrearTabla = "create table GruposMusculares(idGrupoMuscular integer primary key autoincrement, Nombre text)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla Enfoque
        SQLCrearTabla = "create table Enfoque(idEnfoque integer primary key autoincrement, Nombre text)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        //Creacion tabla HistorialRutinas
        SQLCrearTabla = "create table HistorialRutinas(idDiasxRutina integer primary key autoincrement, idEjercicioxRutina integer primary key, CumplientoPeso integer, CumplientoPeso integer, CumplientoSets integer)";
        BaseDeDatos.execSQL(SQLCrearTabla);*/

        //cargo la tabla categorias
        ContentValues UnNuevoRegistro; //Creo un registro en memoria donde pondre los parametros
        UnNuevoRegistro = new ContentValues();
        UnNuevoRegistro.put("Nombre","Verduras y Fruta");
        BaseDeDatos.insert("Categorias", null, UnNuevoRegistro); //Inserto el registro

        ContentValues UnNuevoRegistro1; //Creo un registro en memoria donde pondre los parametros
        UnNuevoRegistro1 = new ContentValues();
        UnNuevoRegistro1.put("Nombre","Grasas");
        BaseDeDatos.insert("Categorias", null, UnNuevoRegistro1); //Inserto el registro

        ContentValues UnNuevoRegistro2; //Creo un registro en memoria donde pondre los parametros
        UnNuevoRegistro2 = new ContentValues();
        UnNuevoRegistro2.put("Nombre","Cereales y Legumbres");
        BaseDeDatos.insert("Categorias", null, UnNuevoRegistro2); //Inserto el registro

        ContentValues UnNuevoRegistro3; //Creo un registro en memoria donde pondre los parametros
        UnNuevoRegistro3 = new ContentValues();
        UnNuevoRegistro3.put("Nombre","Carnes");
        BaseDeDatos.insert("Categorias", null, UnNuevoRegistro3); //Inserto el registro

        ContentValues UnNuevoRegistro4; //Creo un registro en memoria donde pondre los parametros
        UnNuevoRegistro4 = new ContentValues();
        UnNuevoRegistro4.put("Nombre","Lacteos");
        BaseDeDatos.insert("Categorias", null, UnNuevoRegistro4); //Inserto el registro


       /*
        //Cargo Objetivos del usuario
        Log.d("Prueba","cargo objetivos usuarios");
        ContentValues ObjetivoUsuario; //Creo un registro en memoria donde pondre los parametros
        ObjetivoUsuario = new ContentValues();
        ObjetivoUsuario.put("TipoDeObjetivo",0);
        ObjetivoUsuario.put("Meta",78);
        ObjetivoUsuario.put("Progreso", 14);
        ObjetivoUsuario.put("FechaDeInicio","2019-08-22");
        ObjetivoUsuario.put("FechaDeFin","2019-09-22");
        BaseDeDatos.insert("ObjetivosUsuario", null, ObjetivoUsuario); */

        //Cargo tipos de objetivos
        Log.d("Prueba","cargo objetivos");
        ContentValues RegistroObjetivo; //Creo un registro en memoria donde pondre los parametros
        RegistroObjetivo = new ContentValues();
        RegistroObjetivo.put("NombreObjetivo","SUBIR/BAJAR PESO");
        RegistroObjetivo.put("TipoDeObjetivo",0);
        BaseDeDatos.insert("Objetivos", null, RegistroObjetivo); //Inserto el registro

        ContentValues RegistroObjetivo1; //Creo un registro en memoria donde pondre los parametros
        RegistroObjetivo1 = new ContentValues();
        RegistroObjetivo1.put("NombreObjetivo","COMER SALUDABLE");
        RegistroObjetivo1.put("TipoDeObjetivo",1);
        BaseDeDatos.insert("Objetivos", null, RegistroObjetivo1); //Inserto el registro


        //comidas  frutas y verduras
        InsertarComida(1,"LECHUGA",1,1,"lechuga","Vegetal de origen natural. Normalmente consumido en ensaladas, a las tortugas les gusta mucho.",BaseDeDatos);
        InsertarComida(1,"MANZANA",1,1,"frutas","La fruta mas famosa de todas, inigulable y colorida",BaseDeDatos);
        InsertarComida(1,"BANANA",1,1,"frutas","Amarilla con mucho potacio, sirve para los gimnastas",BaseDeDatos);
        InsertarComida(1,"NARANJA",1,1,"frutas","Naranja nomralmente de color naranja, mejor exrimirlas y hacer jugo",BaseDeDatos);
        InsertarComida(1,"MANGO",1,1,"frutas","fruta tropical proveniente de paises tropical",BaseDeDatos);


        //Comidas de Categoria Grasas
        InsertarComida(2,"TORTA",5,0,"torta","porcion de torta muchas calorias but mucha alegria tho",BaseDeDatos);
        InsertarComida(2,"CARAMELO",1,0,"caramelo","Por lo general dulce y barato, tiene varias funciones como la de ser vuelto en supermercados",BaseDeDatos);
        InsertarComida(2,"CHOCOLATE",1,0,"chocolate","Rico chocolate super calorico",BaseDeDatos);


        //Comidas de Categoria Cereales y legumbres
        InsertarComida(3,"AVENA",2,1,"avena","Porcion de avena, ayuda a bajar el colesterol y el estres segun medicos de europa del norte.",BaseDeDatos);
        InsertarComida(3,"CEREALES",2,0,"cereales","La mayoria tiene altos niveles de azucar y estan procesados de maneras no convenientes",BaseDeDatos);

        //Comidas de categoria Carnes
        InsertarComida(4,"SALMON",3,1,"salmon","Bellos peces criados especialmente para ser consumidos, coloreados artificialmente en piletas de 2x2, consumido principalmente en palermo soho o recoleta.",BaseDeDatos);
        InsertarComida(4,"POLLO",3,1,"carne","Son ricos... mejor no hablemos de como se hace para hacerlos llegar a nuestros platos.",BaseDeDatos);

        //Comidas de categoria Lacteos
        InsertarComida(5,"QUESO",3,1,"queso","Alimento color amarillo con agujeros para permitir la respiracion.",BaseDeDatos);
        InsertarComida(5,"LECHE",2,1,"lacteo","Blanca como la nieve, alimento especial para niños en desarrollo por su cantidad de calcio.",BaseDeDatos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int versionAnterior, int versionNueva){

    }

    public Cursor EjecutarConsultaLeer(String Consulta) {
        SQLiteDatabase midb;
        midb = this.getReadableDatabase();
        Cursor RegistrosLeidos;
        RegistrosLeidos = midb.rawQuery(Consulta,null);
        return RegistrosLeidos;
    }

    public void InsertarComida(int idCategoria,String Nombre,int FitPoints, int Bueno ,String Imagen,String Detalle, SQLiteDatabase BaseDeDatos){
        ContentValues AgregoUnaComidaConsumida;
        AgregoUnaComidaConsumida = new ContentValues();
        AgregoUnaComidaConsumida.put("idCategoria",idCategoria);
        AgregoUnaComidaConsumida.put("Nombre",Nombre);
        AgregoUnaComidaConsumida.put("FitPoints",FitPoints);
        AgregoUnaComidaConsumida.put("Bueno",Bueno);
        AgregoUnaComidaConsumida.put("Imagen",Imagen);
        AgregoUnaComidaConsumida.put("Detalle",Detalle);
        BaseDeDatos.insert("Comidas", null, AgregoUnaComidaConsumida);
    }

/*
Hacer en cada activity que se utilize la BD

    GLOBAL:
        ManejadorBaseDeDatos AccesoALaBase; //Objetopara manejar los accesos a la base
        SQLiteDatabase BaseDeDatos; //Referencia a la base en si

    ONCREATE:
        AccesoALaBase = new Manejador(this, "NombreDeBD", null, 1); //inicializo la Base
        BaseDeDatos = AcesoALaBase.getWritableDatabase(); //Establezco un vinculo con la BD

    INSERTAR(se pone directo en donde lo queres usar, un OnClick, OnCreate, etc):
        ContentValues UnNuevoRegistro; //Creo un registro en memoria donde pondre los parametros

        UnNuevoRegistro= new ContentValues();
        UnNuevoRegistro.put("Nombre", "Monica");
        UnNuevoRegistro.put("Edad","18");

        BaseDeDatos.insert("Tabla a la cual voy a insertar", null, UnNuevoRegistro); //Inserto el registro

    LEER(se pone directo en donde lo queres usar, un OnClick, OnCreate, etc):
        Cursor RegistrosLeidos;
        StringSQLLectura;
        SQLLectura="select nombre, from NombreTabla"; //entre las commilas va lo mismo que podria ir en un stored
        RegistrosLeidos= BaseDeDatos.rawQuery(SQLLectura, null);

        REGISTROSLEIDOS SE UTIIZA COMO UN ARRAY:
        if(RegistrosLeidos.getCount()>0){
            for(int PunteroRegistro=0; PunteroRegistro<RegistosLeidos.getCount(); PunteroRegistro++){
                RegistrosLeidos.moveToPosition(PunteroRegistro);

                String NombreLeido=RegistrosLeidos.getString(0);

                int EdadLeida=RegistrosLeidos.getInt(1);
            }
        }else{
            NO HAY REGISTROS
        }

 */

}

