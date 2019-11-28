package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


        //Creacion tabla Usuarios
        SQLCrearTabla = "create table Perfil(idPerfil integer primary key, Sexo text, Peso integer, NivelDeActividad interger, Altura integer,Fecha DATE DEFAULT CURRENT_DATE, FechaParaBusq text, Edad interger)";
        BaseDeDatos.execSQL(SQLCrearTabla);

        /*
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
        InsertarComida(1,"MANZANA",1,1,"manzana","La fruta mas famosa de todas, inigulable y colorida",BaseDeDatos);
        InsertarComida(1,"BANANA",1,1,"banana","Amarilla con mucho potacio, sirve para los gimnastas",BaseDeDatos);
        InsertarComida(1,"NARANJA",1,1,"naranja","Naranja nomralmente de color naranja, mejor exrimirlas y hacer jugo",BaseDeDatos);
        InsertarComida(1,"TOMATE",1,1,"tomate","fruta tipicamente confundida con verdura",BaseDeDatos);
        InsertarComida(1,"KIWI",1,1,"kiwi","Es originaria de una gran área de China, sobre todo de los bosques del valle del río Yangtsé. Introducida en Nueva Zelanda en 1904.",BaseDeDatos);
        InsertarComida(1,"ZANAHORIA",1,1,"zanahoria", "una roseta de hojas en primavera y verano, mientras desarrolla la gruesa raíz napiforme.",BaseDeDatos);
        InsertarComida(1,"ANANA",2,1,"anana", "es una planta perenne de la familia de las bromeliáceas, nativa de América del Sur. Esta especie, de escaso porte y con hojas duras y lanceoladas desde la cola hasta 1 m de largo",BaseDeDatos);
<<<<<<< HEAD
        InsertarComida(1,"ARANDANOS",1,1,"arandano", "son blancos al principio y a medida que van madurando se tornan rojizo-purpúreos para convertirse en azules cuando están completamente maduros. ",BaseDeDatos);
=======
        InsertarComida(1,"ARANDANOS",1,1,"arandano", "son blancos al principio y a medida que van madurando se tornan rojizo-purpúreos para convertirse en azules cuando están completamente maduros. Por su dulce sabor se utilizan para elaborar, mermeladas, vinos, pasteles y diversos platos dulces.",BaseDeDatos);
>>>>>>> 6ef25befcb2e257b905027390e37a6833fb831ac
        InsertarComida(1,"CIRUELA",1,1,"ciruela", "El principal componente de las ciruelas es el agua, seguido de los hidratos de carbono, entre los que destaca la presencia de sorbitol, de leve acción laxante.",BaseDeDatos);
        InsertarComida(1,"LIMON",1,1,"limon", " se emplean fundamentalmente para aderezar o realizar el sabor de otras frutas o platos y preparaciones culinarias.",BaseDeDatos);
        InsertarComida(1,"MANDARINA",1,1,"mandarina", "La mandarina es el fruto del mandarino, árbol que pertenece a la familia de las Rutáceas, con características similares al naranjo, aunque más pequeño y delicado. E",BaseDeDatos);


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


        Calendar calendar = Calendar.getInstance();
        Date FechaHoy = calendar.getTime();
        Timestamp FechaActual = new Timestamp(FechaHoy.getTime());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(FechaHoy);

        ContentValues PrimerDatoPerfil; //Creo un registro en memoria donde pondre los parametros
        PrimerDatoPerfil = new ContentValues();
        PrimerDatoPerfil.put("Sexo","No Definido");
        PrimerDatoPerfil.put("Peso",0);
        PrimerDatoPerfil.put("NivelDeActividad",0);
        PrimerDatoPerfil.put("Altura",0);
        PrimerDatoPerfil.put("Fecha",FechaActual.toString());
        PrimerDatoPerfil.put("FechaParaBusq",strDate);
        PrimerDatoPerfil.put("Edad", 0);
        BaseDeDatos.insert("Perfil", null, PrimerDatoPerfil); //Inserto el registro


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

