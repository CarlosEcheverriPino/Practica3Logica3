/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

/**
 *
 * @author CompucenterPC1
 */
public class ListaLigada {

private final Nodo cabeza;
private String posicion;

    public ListaLigada(String posicion) {
        cabeza = new Nodo(posicion);
        this.posicion = posicion;
    }
    
    public void AñadirNodo (Nodo adyacente){
        Nodo recorrido ;
        recorrido = cabeza;
        while(recorrido.getLiga()!= null){
            recorrido = recorrido.getLiga();
        }
        recorrido.setLiga(adyacente);
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    
    
}
