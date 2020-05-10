/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.awt.*;
import java.io.IOException;

/**
 *
 * @author Esneider
 */
public class Lienzo extends javax.swing.JPanel {

    GrafoLLAdyacencia GrafoLL;

    /**
     * Creates new form Lienzo
     *
     * @throws java.io.IOException
     */
    public Lienzo() throws IOException {
        initComponents();
        setPreferredSize(new Dimension(800, 800));
        GrafoLL = new GrafoLLAdyacencia();
        //GrafoLLAdyacencia.leerArchivo("C:/Users/Sneid/OneDrive/Escritorio/vertices.txt");

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        if (GrafoLL != null) {
            for (int i = 0; i < GrafoLL.tamaño(); i++) {
                String[] XY = GrafoLL.getCabeza(i).getNombreVertice().split(",");
                pintar(g, Integer.parseInt(XY[0]) * 40, Integer.parseInt(XY[1]) * 40, GrafoLL.getCabeza(i));
                //pain(g,  GrafoLL.getCabeza(i));
            }
        }

    }

    public static void pintar(Graphics g, int x, int y, Nodo nodo) {

        if (nodo != null) {
            g.fillOval(x, y, 30, 30);
            g.setColor(Color.red);
            g.drawString(nodo.getNombreVertice(), x + 8, y + 18);
            g.setColor(Color.black);
        }

        while (nodo.getLiga() != null) {
            nodo = nodo.getLiga();
            String[] posiciones = nodo.getNombreVertice().split(",");
            g.drawLine(x + 15, y + 15, Integer.parseInt(posiciones[0]) * 40 + 15, Integer.parseInt(posiciones[1]) * 40 + 15);
        }

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}