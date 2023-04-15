package Modelo;

import java.io.Serializable;

public class Album implements Serializable {

    private int id;
    private String nombre;
    private String ruta;
    private byte[] fotico;

    public Album() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public byte[] getFotico() {
        return fotico;
    }

    public void setFotico(byte[] fotico) {
        this.fotico = fotico;
    }
}
