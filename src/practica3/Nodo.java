/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

/**
 *
 * @author Esneider
 */
public class Nodo {

    private String nombreVertice;
    private Nodo liga;
    private int costo;

    public Nodo(String nombreVertice) {
        this.nombreVertice = nombreVertice;
        liga = null;
    }

    public Nodo(String nombreVertice, int costo) {
        this.costo = costo;
        this.nombreVertice = nombreVertice;
        liga = null;
    }
    
    public String getNombreVertice() {
        return nombreVertice;
    }

    public void setNombreVertice(String posicion) {
        this.nombreVertice = posicion;
    }

    public Nodo getLiga() {
        return liga;
    }

    public void setLiga(Nodo liga) {
        this.liga = liga;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

}
