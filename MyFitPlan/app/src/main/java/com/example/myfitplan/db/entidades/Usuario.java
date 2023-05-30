package com.example.myfitplan.db.entidades;

//POJO de la clase Usuario
public class Usuario {
    private String username, fnac, estado;
    private  byte[] imagen;
    private int altura, porcentaje;
    private double peso;

    public Usuario(){
    }

    public Usuario(String username, byte[] imagen, String fnac, int altura, double peso, int porcentaje, String estado) {
        this.username = username;
        this.fnac = fnac;
        this.estado = estado;
        this.imagen = imagen;
        this.altura = altura;
        this.porcentaje = porcentaje;
        this.peso = peso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
