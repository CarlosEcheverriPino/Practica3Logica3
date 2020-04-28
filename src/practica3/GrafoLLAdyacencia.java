/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Esneider
 */
public class GrafoLLAdyacencia {
    
    private final LinkedList<Nodo> nodosCabeza;

    public GrafoLLAdyacencia() {
        nodosCabeza = new LinkedList<>();
    }
    
    
    
    //Metodo para insertar, aún no probado. Rezen para que funcione o se los va a llevar los negros del ataús xD
    public void Insertar (Nodo partida, Nodo llegada){
        
        
        if(nodosCabeza.contains(partida)){
            
           Nodo recorrido = nodosCabeza.get(nodosCabeza.indexOf(partida));
           while(recorrido.getLiga()!=null){
               recorrido=recorrido.getLiga();
           } 
           recorrido.setLiga(llegada);
           
            
        }else{
            nodosCabeza.addFirst(partida);
            partida.setLiga(llegada);
        }
        
    }       
    
    
    
    //Pase el metodo de leerArchivo para acá para poder usar el metodo insertar sin crear objetos. 
    //si se puede de otra manera sin necesidad de traerlo acá pueden hacerlo
    public void leerArchivo(String ruta) throws FileNotFoundException, IOException {

        String text1;
        String text2;

        
        FileReader archivo = new FileReader(ruta);

        
        BufferedReader bufferArchivo = new BufferedReader(archivo);

        /*
        Esta parte sirve para leer el numero de lineas del archivo
        la idea es pensar si se puede usar para la matriz de costos o no
        */
        int nlineas = (int) bufferArchivo.lines().count();
       
        text1 = bufferArchivo.readLine();

        StringTokenizer tokenizadorDePalabras = new StringTokenizer(text1, ":");
    
        int cont = 0;
        Nodo partida = null;
        Nodo llegada = null ;
        int cost;
        
        while (tokenizadorDePalabras.hasMoreTokens()) {

            text2 = tokenizadorDePalabras.nextToken();

            
            if (cont == 0) {
                partida = new Nodo(text2);
            }
            //gaurda el costo, aun no usado. Falta crear la matriz de costos
            if (cont == 1) {
               cost= Integer.parseInt(text2);
            }
            if (cont == 2) {
                llegada = new Nodo(text2);
            }
            cont++;

            
            if (!tokenizadorDePalabras.hasMoreTokens()) {
                text1 = bufferArchivo.readLine();
                cont = 0;
                Insertar(partida, llegada);
               
                
                if (text1 != null) {
                    tokenizadorDePalabras = new StringTokenizer(text1, ":");
                    cont = 0;
                }
            }

        }

    }
    
   
   
}
