
package practica3;

import java.io.IOException;
//import java.util.LinkedList;


public class Practica3 {

    
    public static void main(String[] args) throws IOException {
        
        GrafoLLAdyacencia obj = new GrafoLLAdyacencia();
        System.out.println("numero vertices sin repetir: "+obj.leerArchivo("C:/Users/Sneid/OneDrive/Escritorio/vertices.txt"));   

        System.out.println("ruta: "+obj.dijkstraRuta("1,1","8,4"));
      
    }
    
}
