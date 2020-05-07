
package practica3;

import java.io.IOException;
//import java.util.LinkedList;


public class Practica3 {

    
    public static void main(String[] args) throws IOException {
       
       GrafoLLAdyacencia obj = new GrafoLLAdyacencia();
       
        System.out.println("numero vertices sin repetir: "+obj.leerArchivo("C:\\Users\\user\\Desktop\\vertices.txt"));
        obj.dijkstraRuta("1,4","1,3");
    }
    
}
