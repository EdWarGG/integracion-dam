package com.example.myfitplan.db.entidades;

//POJO de la clase Ejercicio
public class Ejercicio {

    private String nombre, rutina;
    private int series, repeticiones, peso;

    public Ejercicio(){
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeries() {
        return series;
    }
    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }
    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getPeso() {
        return peso;
    }
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getRutina() {
        return rutina;
    }
    public void setRutina(String rutina) {
        this.rutina = rutina;
    }
}
