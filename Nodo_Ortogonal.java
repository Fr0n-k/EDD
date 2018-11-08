/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author fcarv
 */
public class NodoNota {
    double Nota;
    int Fila;
    int  Columna;
    NodoNota Arriba;
    NodoNota Abajo;
    NodoNota Izquierda;
    NodoNota Derecha;

    public NodoNota(double Nota, int Fila, int Columna) {
        this.Nota = Nota;
        this.Fila = Fila;
        this.Columna = Columna;
        this.Abajo = null;
        this.Arriba = null;
        this.Izquierda = null;
        this.Derecha = null;
    }
    
}
