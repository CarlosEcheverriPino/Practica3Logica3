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
import java.util.stream.Collectors;

/**
 *
 * @author Esneider
 */
public class GrafoLLAdyacencia {

    static LinkedList<Nodo> nodosCabeza;
    static List<String> verticesNoRepetidos = new ArrayList<>();

    public GrafoLLAdyacencia() {
        nodosCabeza = new LinkedList<>();
    }

    public static boolean Insertar(Nodo partida, Nodo llegada) {

        boolean agregar = false;
//---------------------------------------------------------------------------
//Si el nodo de partida ya existe en la LinkedList
        if (verticesNoRepetidos.contains(partida.getNombreVertice())) {

            //me ubico en el nodo de partida
            Nodo recorrido = nodosCabeza.get(verticesNoRepetidos.indexOf(partida.getNombreVertice()));

            //Verifico que el nodo de partida sea diferente al nodo de llegada
            if (!partida.getNombreVertice().equals(llegada.getNombreVertice())) {
                //recorro la lista de adyacencia para verificar que no este agregado el nodo de llegada anteriormente
                while (recorrido.getLiga() != null) {
                    //aqui se verifica que no exista
                    if (!recorrido.getNombreVertice().equals(llegada.getNombreVertice())) {
                        if (recorrido.getLiga() != null) {
                            recorrido = recorrido.getLiga();
                        }
                    } // en caso de que la adyacencia sea hacia el mismo nodo, no hacer nada
                    else {
                        return agregar;
                    }
                }
                //aqui ya estoy ubicado en la posicion para agregar la adyacencia
                recorrido.setLiga(llegada);
                agregar = true;
            }

        } //---------------------------------------------------------------------------
        //Si el nodo de partida no existe en la LinkedList        
        else {
            //Verifico que la adyacencia no sea hacia el mismo nodo
            if (!partida.getNombreVertice().equals(llegada.getNombreVertice())) {
                //agrego el nodo a la LinkedList
                nodosCabeza.addLast(partida);
                partida.setLiga(llegada);
            }
        }

//----------------------------------------------------------------------------------------
        Nodo llegadaSinLiga = new Nodo(llegada.getNombreVertice());
        Nodo partidaSinLiga = new Nodo(partida.getNombreVertice(), llegada.getCosto());
        //verifico que no exista el nodo de llegada en la LinkedList
        if (!verticesNoRepetidos.contains(llegadaSinLiga.getNombreVertice())) {
            //Confirmo que sean diferentes 
            if (!partida.getNombreVertice().equals(llegada.getNombreVertice())) {
                nodosCabeza.addLast(llegadaSinLiga);
                llegadaSinLiga.setLiga(partidaSinLiga);
            }

        } else if (agregar) {
            Nodo recorridoAux = nodosCabeza.get(verticesNoRepetidos.indexOf(llegadaSinLiga.getNombreVertice()));
            while (recorridoAux.getLiga() != null) {
                recorridoAux = recorridoAux.getLiga();
            }

            recorridoAux.setLiga(partidaSinLiga);
        }

        Collections.sort(nodosCabeza);

        return agregar;
    }

    @SuppressWarnings("null")
    public static int leerArchivo(String ruta) throws FileNotFoundException, IOException {

        String text1;
        String text2;

        FileReader archivo = new FileReader(ruta);

        BufferedReader bufferArchivo = new BufferedReader(archivo);

        /*
        Esta parte sirve para leer el numero de lineas del archivo
        la idea es pensar si se puede usar para la matriz de costos o no
         
        ---int nLineas = (int) bufferArchivo.lines().count();--
         */
        text1 = bufferArchivo.readLine();

        StringTokenizer tokenizadorDePalabras = new StringTokenizer(text1, ":");

        int cont = 0;
        Nodo partida = null;
        Nodo llegada = null;
        int cost = 0;
        String fin = null, inicio = null;

        List<String> vertices = new ArrayList<>();

        while (tokenizadorDePalabras.hasMoreTokens()) {

            text2 = tokenizadorDePalabras.nextToken();

            if (cont == 0) {
                partida = new Nodo(text2);
                inicio = text2;
            }
            if (cont == 1) {
                cost = Integer.parseInt(text2);
            }
            if (cont == 2) {
                llegada = new Nodo(text2, cost);
                fin = text2;
            }
            cont++;

            if (!tokenizadorDePalabras.hasMoreTokens()) {
                text1 = bufferArchivo.readLine();
                cont = 0;
                Insertar(partida, llegada);

                if (!inicio.equals(fin)) {
                    vertices.add(inicio);
                    vertices.add(fin);
                    numeroVertices(vertices);
                }

                fin = null;
                inicio = null;

                if (text1 != null) {
                    tokenizadorDePalabras = new StringTokenizer(text1, ":");
                    cont = 0;
                }
            }
        }
        int msm = numeroVertices(vertices);
        return msm;
    }

    public static int numeroVertices(List<String> listaVertices) {

        verticesNoRepetidos = listaVertices.stream().distinct().collect(Collectors.toList());
        Collections.sort(verticesNoRepetidos);
        return verticesNoRepetidos.size();
    }
    
    public static class MyComp implements Comparator<Nodo> {

        @Override
        public int compare(Nodo c1, Nodo c2) {
            switch (c1.getNombreVertice().compareTo(c2.getNombreVertice())) {
                case -1:
                    return -1;
                case 1:
                    return 1;
                default:
                    return c1.getNombreVertice().compareTo(c2.getNombreVertice());
            }
        }
    }

    /**
     *
     * @param inicio
     * @return
     */
    public static StringBuilder dijkstraRuta(String inicio) {

        if (!verticesNoRepetidos.contains(inicio)) {
            StringBuilder msm = new StringBuilder();
            msm.append("No existe el vertice ingresado");
            return msm;
        }

        //Me ubico en el nodo inicio en la LinkedList
        Nodo recorrido = nodosCabeza.get(verticesNoRepetidos.indexOf(inicio));
        Nodo anterior = recorrido;
        //Cola para las adyacencias
        Queue<Nodo> cola = new ArrayDeque<>();
        List<String> enCola2 = new ArrayList<>();

        //Arreglo que guarda Nodos con le nombre del vertice de donde vino y cual fue el costo
        Nodo[] arregloCostos = new Nodo[verticesNoRepetidos.size()];
        //Nodo pirmero con nodo de llegada -1 y costo 0
        arregloCostos[verticesNoRepetidos.indexOf(inicio)] = new Nodo("-1", 0);

        //avanzo en la lista de adyacencia
        if (recorrido.getLiga() != null) {
            recorrido = recorrido.getLiga();
        }
        while (recorrido != null) {
            //Verifico que el nodo a encolar no lo haya encolado antes
            if (!enCola2.contains(recorrido.getNombreVertice())) {
                cola.add(recorrido);
            }
            enCola2.add(recorrido.getNombreVertice());

            // posicion del nodo cabeza
            int num = verticesNoRepetidos.indexOf(anterior.getNombreVertice());
            //Costo toal de llegada al nodo actual
            int costoTotal = recorrido.getCosto() + arregloCostos[num].getCosto();
            Nodo costoRecorrido = new Nodo(anterior.getNombreVertice(), costoTotal);

            //Verifico que la posicion sea diferente de null
            if (arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] != null) {
                int actualCosto = arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())].getCosto();
                int nuevoCosto = costoRecorrido.getCosto();
                //Comparo costos para posible cambio
                if (nuevoCosto < actualCosto) {
                    arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] = costoRecorrido;
                }
                //si la posicion es null agrego el nodo en la posicion    
            } else {
                arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] = costoRecorrido;//recorrido.getCosto();
            }
            //avanzo en la lista
            recorrido = recorrido.getLiga();

            //Si la posicion es null y la pila esta vacia, desencolo
            while (recorrido == null && !cola.isEmpty()) {

                recorrido = cola.poll();
                recorrido = nodosCabeza.get(verticesNoRepetidos.indexOf(recorrido.getNombreVertice()));
                anterior = recorrido;
                recorrido = recorrido.getLiga();

            }

        }

        StringBuilder ruta = new StringBuilder();
        Stack pila = new Stack();
        //recorro todas las adyacencias de cada nodo
        for (int i = 0; i < verticesNoRepetidos.size(); i++) {
            //Verifico que no esté en el nodo de partida
            if (!arregloCostos[i].getNombreVertice().equals("-1")) {

                pila.add(verticesNoRepetidos.get(i));

                int aum = i;
                String name = arregloCostos[aum].getNombreVertice();

                while (!name.equals("-1")) {

                    pila.add(name);
                    aum = verticesNoRepetidos.indexOf(arregloCostos[aum].getNombreVertice());
                    name = arregloCostos[aum].getNombreVertice();

                }
                //desencolo la ruta y agrego al StringBuilder
                while (!pila.isEmpty()) {
                    ruta.append(pila.pop()).append("-->");
                }
                //añado el costo
                ruta.append("costo:").append(arregloCostos[i].getCosto());
                ruta.append("\n");
            }
        }
        return ruta;
    }

    public static Nodo getCabeza(int i) {
        return nodosCabeza.get(i);
    }
    
    public static int tamaño() {
        int tamaño = nodosCabeza.size();
        return tamaño;
    }
}
