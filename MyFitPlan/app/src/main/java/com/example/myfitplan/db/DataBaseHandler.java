package com.example.myfitplan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfitplan.db.entidades.Comida;
import com.example.myfitplan.db.entidades.Ejercicio;
import com.example.myfitplan.db.entidades.RutinaAlimentacion;
import com.example.myfitplan.db.entidades.RutinaEjercicio;

import java.util.ArrayList;
import java.util.List;

//Clase encargada de manejar la base de datos de la aplicación
public class DataBaseHandler extends SQLiteOpenHelper {

    //Strings con todas las sentencias SQL necesarias
    public static final String DBNAME = "MFP.db";

    final String CREAR_TABLA_USUARIO = "CREATE TABLE usuario (id INTEGER PRIMARY KEY, username TEXT, imagen BLOB, fnac TEXT, altura INTEGER, peso REAL, porcentaje INTEGER, estado TEXT)";

    final String CREAR_TABLA_RUT_EJER = "CREATE TABLE rutejer (nombre TEXT PRIMARY KEY, descripcion TEXT)";
    final String CREAR_TABLA_EJERCICIOS = "CREATE TABLE ejercicios (nombre TEXT PRIMARY KEY, series INTEGER, repeticiones INTEGER, peso INTEGER, rutina TEXT, FOREIGN KEY(rutina) REFERENCES rutejer(nombre) ON DELETE CASCADE)";

    final String CREAR_TABLA_RUT_ALIM = "CREATE TABLE rutalim (nombre TEXT PRIMARY KEY, descripcion TEXT)";
    final String CREAR_TABLA_COMIDAS = "CREATE TABLE comidas (nombre TEXT PRIMARY KEY, ingrediente_principal TEXT, peso INTEGER, rutina TEXT, FOREIGN KEY(rutina) REFERENCES rutalim(nombre) ON DELETE CASCADE)";

    final String ElIMINAR_TABLA_USUARIO = "DROP TABLE IF EXISTS usuario";

    final String ElIMINAR_TABLA_RUT_EJER = "DROP TABLE IF EXISTS rutejer";
    final String ElIMINAR_TABLA_EJERCICIOS = "DROP TABLE IF EXISTS ejercicios";

    final String ElIMINAR_TABLA_RUT_ALIM = "DROP TABLE IF EXISTS rutalim";
    final String ElIMINAR_TABLA_COMIDAS = "DROP TABLE IF EXISTS comidas";


    public DataBaseHandler(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    //Se ejecuta al crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON");
        db.execSQL(CREAR_TABLA_USUARIO);
        db.execSQL(CREAR_TABLA_RUT_EJER);
        db.execSQL(CREAR_TABLA_EJERCICIOS);
        db.execSQL(CREAR_TABLA_RUT_ALIM);
        db.execSQL(CREAR_TABLA_COMIDAS);
    }

    //Se ejecuta al actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ElIMINAR_TABLA_USUARIO);
        db.execSQL(ElIMINAR_TABLA_RUT_EJER);
        db.execSQL(ElIMINAR_TABLA_EJERCICIOS);
        db.execSQL(ElIMINAR_TABLA_RUT_ALIM);
        db.execSQL(ElIMINAR_TABLA_COMIDAS);

        onCreate(db);
    }

    //Función para crear un perfil de usuario
    public boolean insertUsuario(String username, byte[] imagen, String fnac, String altura, String peso, String porcentaje, String estado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 1);
        contentValues.put("username", username);
        contentValues.put("imagen", imagen);
        contentValues.put("fnac", fnac);
        contentValues.put("altura", altura);
        contentValues.put("peso", peso);
        contentValues.put("porcentaje", porcentaje);
        contentValues.put("estado", estado);
        long result = db.insert("usuario", null, contentValues);
        
        return result != -1;
    }

    //Función para actualizar el perfil de usuario
    public boolean updateUsuario(String username, byte[] imagen, String fnac, String altura, String peso, String porcentaje, String estado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("imagen", imagen);
        contentValues.put("fnac", fnac);
        contentValues.put("altura", altura);
        contentValues.put("peso", peso);
        contentValues.put("porcentaje", porcentaje);
        contentValues.put("estado", estado);
        int result = db.update("usuario", contentValues, "id=1", null);

        return result == 1;
    }


    //Función para crear una nueva rutina de entrenamiento
    public boolean insertRutEjer(String nombre, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("descripcion", descripcion);
        long result = db.insert("rutejer", null, contentValues);

        return result != -1;
    }

    //Función para actualizar una rutina de entrenamiento
    public boolean updateRutEjer(String nombre, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("descripcion", descripcion);
        int result = db.update("rutejer", contentValues, "nombre='" + nombre + "'", null);

        return result == 1;
    }

    //Función para crear una lista con todas las rutinas de entrenamiento
    public List<RutinaEjercicio> getRutinasEjer() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.query("rutejer", null, null, null, null, null, null);

        List<RutinaEjercicio> rutinasEjer = new ArrayList<>();
        while (cursor.moveToNext()) {
            RutinaEjercicio rutEjer = new RutinaEjercicio();
            rutEjer.setNombre(cursor.getString(0));
            rutEjer.setDescripcion(cursor.getString(1));
            rutinasEjer.add(rutEjer);
        }
        cursor.close();
        return rutinasEjer;
    }

    //Función para crear un nuevo ejercicio
    public boolean insertEjercicio(String nombre, int series, int repeticiones, int peso, String rutina){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("series", series);
        contentValues.put("repeticiones", repeticiones);
        contentValues.put("peso", peso);
        contentValues.put("rutina", rutina);
        long result = db.insert("ejercicios", null, contentValues);

        return result != -1;
    }

    //Función para actualizar un ejercicio
    public boolean updateEjercicio(String nombre, int series, int repeticiones, int peso){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("series", series);
        contentValues.put("repeticiones", repeticiones);
        contentValues.put("peso", peso);
        int result = db.update("ejercicios", contentValues, "nombre='" + nombre + "'", null);

        return result == 1;
    }

    //Función para crear una lista con todos los ejercicios de una determinada rutina
    public List<Ejercicio> getEjercicios(String nombreRutina) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM ejercicios WHERE rutina='" + nombreRutina +"'", null);

        List<Ejercicio> ejercicios = new ArrayList<>();
        while (cursor.moveToNext()) {
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNombre(cursor.getString(0));
            ejercicio.setSeries(cursor.getInt(1));
            ejercicio.setRepeticiones(cursor.getInt(2));
            ejercicio.setPeso(cursor.getInt(3));
            ejercicio.setRutina(cursor.getString(4));
            ejercicios.add(ejercicio);
        }
        cursor.close();
        return ejercicios;
    }


    //Función para crear una nueva rutina de alimentación
    public boolean insertRutAlim(String nombre, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("descripcion", descripcion);
        long result = db.insert("rutalim", null, contentValues);

        return result != -1;
    }

    //Función para actualizar una rutina de alimentación
    public boolean updateRutAlim(String nombre, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("descripcion", descripcion);
        int result = db.update("rutalim", contentValues, "nombre='" + nombre + "'", null);

        return result == 1;
    }

    //Función para crear una lista con todas las rutinas de alimentación
    public List<RutinaAlimentacion> getRutinasAlim() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.query("rutalim", null, null, null, null, null, null);

        List<RutinaAlimentacion> rutinasAlim = new ArrayList<>();
        while (cursor.moveToNext()) {
            RutinaAlimentacion rutAlim = new RutinaAlimentacion();
            rutAlim.setNombre(cursor.getString(0));
            rutAlim.setDescripcion(cursor.getString(1));
            rutinasAlim.add(rutAlim);
        }
        cursor.close();
        return rutinasAlim;
    }

    //Función para crear una nueva comida
    public boolean insertComida(String nombre, String ingrediente_principal, int peso, String rutina){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("ingrediente_principal", ingrediente_principal);
        contentValues.put("peso", peso);
        contentValues.put("rutina", rutina);
        long result = db.insert("comidas", null, contentValues);

        return result != -1;
    }

    //Función para actualizar una comida
    public boolean updateComida(String nombre, String ingrediente_principal, int peso){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ingrediente_principal", ingrediente_principal);
        contentValues.put("peso", peso);
        int result = db.update("comidas", contentValues, "nombre='" + nombre + "'", null);

        return result == 1;
    }

    //Función para crear una lista con las comidas de una determinada rutina
    public List<Comida> getComidas(String rutina) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM comidas WHERE rutina='" + rutina + "'", null);

        List<Comida> comidas = new ArrayList<>();
        while (cursor.moveToNext()) {
            Comida comida = new Comida();
            comida.setNombre(cursor.getString(0));
            comida.setIngrediente_principal(cursor.getString(1));
            comida.setPeso(cursor.getInt(2));
            comida.setRutina(cursor.getString(3));
            comidas.add(comida);
        }
        cursor.close();
        return comidas;
    }
}
