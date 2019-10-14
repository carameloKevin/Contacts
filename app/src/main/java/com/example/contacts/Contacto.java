package com.example.contacts;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

class Contacto implements Serializable {
    private static int idCounter = 0;
    private int id;
    private String nombre;
    private String numero;
    private String cumple;

    public Contacto(){

    }

    public Contacto(String nombre, String numeroTel, String unCumple)
    {
        /*
        No utilizar este metodo para agregar contactos. El idCounter lo tiene que sacar de la DB
        porque este idCounter parece que se resetea siempre que se abre o reinstala la aplicacion
        por lo tanto, cuando quiere agregar un contacto se cruza que ya ahi uno con la id N (por ej: 0)
        y no puede agregar dos objetos con la misma id.
        Para usar en queries y parecidos funciona bien porque se descarta despues.
        * */
        this.id = idCounter;
        idCounter += 1;
        this.nombre = nombre;
        this.numero = numeroTel;
        this.cumple = unCumple;
    }

    public Contacto( int id ,String nombre, String numeroTel, String unCumple)
    {
        //Este metodo esta para el DBHandler
        this.id = id;
        this.nombre = nombre;
        this.numero = numeroTel;
        this.cumple = unCumple;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getNumero() {

        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String toString()
    {
        return  nombre + " | " + numero + " | " + this.cumple.toString();
    }

    public String getCumple() {
        return this.cumple;
    }

    public int getId(){
        return this.id;
    }

    public void setCumple(String unCumple){
        this.cumple = unCumple;
    }
}
