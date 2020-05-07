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

    private final LinkedList<Nodo> nodosCabeza;
    List<String> verticesNoRepetidos = new ArrayList<>();

    public GrafoLLAdyacencia() {
        nodosCabeza = new LinkedList<>();
    }

    //Metodo para insertar, aún no probado. Rezen para que funcione o se los va a llevar los negros del ataúd xD
    public boolean Insertar(Nodo partida, Nodo llegada) {

        boolean agregar = false;

        if (!verticesNoRepetidos.isEmpty() && verticesNoRepetidos.contains(partida.getNombreVertice())) {

            int numNodo = verticesNoRepetidos.indexOf(partida.getNombreVertice());
            Nodo recorrido = nodosCabeza.get(numNodo);

            if (recorrido.getLiga() != null) {
                recorrido = recorrido.getLiga();
            }
            do {

                if (!recorrido.getNombreVertice().equals(llegada.getNombreVertice())) {

                    if (recorrido.getLiga() != null) {
                        recorrido = recorrido.getLiga();
                    }

                } else {
                    return agregar;
                }

            } while (recorrido.getLiga() != null);

            Nodo llegadaSinLiga = new Nodo(llegada.getNombreVertice());
            Nodo partidaSinLiga = new Nodo(partida.getNombreVertice(), llegada.getCosto());
            recorrido.setLiga(llegada);
            if (!verticesNoRepetidos.contains(llegadaSinLiga.getNombreVertice())) {
                nodosCabeza.addLast(llegadaSinLiga);
                llegadaSinLiga.setLiga(partidaSinLiga);
            } else {

                Nodo recorridoAux = nodosCabeza.get(verticesNoRepetidos.indexOf(llegadaSinLiga.getNombreVertice()));
                while (recorridoAux.getLiga() != null) {
                    recorridoAux = recorridoAux.getLiga();
                }
                recorridoAux.setLiga(partidaSinLiga);

            }

            agregar = true;

        } else {

            partida.setLiga(llegada);
            nodosCabeza.addLast(partida);
            Nodo llegadaSinLiga = new Nodo(llegada.getNombreVertice());
            Nodo partidaSinLiga = new Nodo(partida.getNombreVertice(), llegada.getCosto());

            if (!verticesNoRepetidos.contains(llegadaSinLiga.getNombreVertice())) {
                nodosCabeza.addLast(llegadaSinLiga);
                llegadaSinLiga.setLiga(partidaSinLiga);
            } else {
                Nodo recorridoAux = nodosCabeza.get(verticesNoRepetidos.indexOf(llegadaSinLiga.getNombreVertice()));
                while (recorridoAux.getLiga() != null) {
                    recorridoAux = recorridoAux.getLiga();
                }
                recorridoAux.setLiga(partidaSinLiga);
            }

            agregar = true;

        }

        Collections.sort(nodosCabeza);

        return agregar;
    }

    public int leerArchivo(String ruta) throws FileNotFoundException, IOException {

        String text1;
        String text2;

        FileReader archivo = new FileReader(ruta);

        BufferedReader bufferArchivo = new BufferedReader(archivo);

        /*
        Esta parte sirve para leer el numero de lineas del archivo
        la idea es pensar si se puede usar para la matriz de costos o no
         */
        //int nLineas = (int) bufferArchivo.lines().count();
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
                vertices.add(inicio);
                vertices.add(fin);
                numeroVertices(vertices);
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

    public int numeroVertices(List<String> listaVertices) {

        verticesNoRepetidos = listaVertices.stream().distinct().collect(Collectors.toList());
        Collections.sort(verticesNoRepetidos);
        return verticesNoRepetidos.size();
    }

    public class MyComp implements Comparator<Nodo> {

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

    public void dijkstraRuta(String inicio, String fin) {

        int posInicio = verticesNoRepetidos.indexOf(inicio);

        Nodo recorrido = nodosCabeza.get(posInicio);
        Nodo anterior = recorrido;
        Queue<Nodo> cola = new ArrayDeque<>();
        List<String> enCola2 = new ArrayList<>();
        Nodo[] arregloCostos = new Nodo[verticesNoRepetidos.size()];
        Nodo primero = new Nodo("-1", 0);
        arregloCostos[verticesNoRepetidos.indexOf(inicio)] = primero;

        if (recorrido.getLiga() != null) {
            recorrido = recorrido.getLiga();
        }
        while (recorrido != null) {

            if (!enCola2.contains(recorrido.getNombreVertice())) {
                cola.add(recorrido);
            }
            enCola2.add(recorrido.getNombreVertice());

            // posicion del nodo cabeza
            int num = verticesNoRepetidos.indexOf(anterior.getNombreVertice());

            int costoTotal = recorrido.getCosto() + arregloCostos[num].getCosto();
            Nodo costoRecorrido = new Nodo(anterior.getNombreVertice(), costoTotal);

            if (arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] != null) {
                int actualCosto = arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())].getCosto();
                int nuevoCosto = costoRecorrido.getCosto();
                if (nuevoCosto < actualCosto) {
                    arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] = costoRecorrido;
                }

            } else {
                arregloCostos[verticesNoRepetidos.indexOf(recorrido.getNombreVertice())] = costoRecorrido;//recorrido.getCosto();
            }

            recorrido = recorrido.getLiga();

            while (recorrido == null && !cola.isEmpty()) {

                recorrido = cola.poll();
                recorrido = nodosCabeza.get(verticesNoRepetidos.indexOf(recorrido.getNombreVertice()));
                anterior = recorrido;
                recorrido = recorrido.getLiga();

            }

        }

        StringBuilder ruta = new StringBuilder();

        ruta.append(fin).append(" <-- ");
        int pos = verticesNoRepetidos.indexOf(fin);
        String nameRutas = arregloCostos[pos].getNombreVertice();

        do {

            ruta.append(nameRutas).append(" <-- ");
            pos = verticesNoRepetidos.indexOf(arregloCostos[pos].getNombreVertice());
            nameRutas = arregloCostos[pos].getNombreVertice();

        } while (!nameRutas.equals("-1"));
        System.out.println(ruta);

        System.out.println("valor: " + arregloCostos[verticesNoRepetidos.indexOf(fin)].getCosto());
    }

    public Nodo getCabeza(int i) {
        return nodosCabeza.get(i);
    }

    public int tamaño() {
        return nodosCabeza.size();
    }

//1,1:2:1,2
//1,1:2:1,4
//1,1:2:1,6
//1,5:2:1,7
//1,6:2:1,3
//1,8:2:1,7
//1,9:2:1,11
//1,1:2:1,2
//1,3:3:1,8
//1,3:5:1,9
//1,1:2:1,8
//1,1:2:1,9
}
