package com.example.myfitplan.db.entidades;

//POJO de la clase Comida
public class Comida {

    private String nombre, ingrediente_principal, rutina;
    private int peso;

    public Comida(){}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngrediente_principal() {
        return ingrediente_principal;
    }
    public void setIngrediente_principal(String ingrediente_principal) {
        this.ingrediente_principal = ingrediente_principal;
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
