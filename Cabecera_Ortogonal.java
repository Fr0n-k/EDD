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
public class CabeceraMatriz {

    NodoCabecera Inicio;
    NodoCabecera Fin;

    public CabeceraMatriz() {
        this.Inicio = null;
        this.Fin = null;
    }

    public void InsertaCabecera(String Nombre, String Descripcion, double Ponderacion, String Fecha_Entrega, String Tipo) {
        boolean Valido = true;
        NodoCabecera Nuevo = new NodoCabecera(Nombre, Descripcion, Ponderacion, Fecha_Entrega, Tipo);
        if (Inicio == null) {
            Inicio = new NodoCabecera(Nombre, Descripcion, Ponderacion, Fecha_Entrega, Tipo);
            Fin = Inicio;
            Inicio.idx = 1;
        } else {
            NodoCabecera Aux = Inicio;
            while (Aux != null) {
                if (Aux.Nombre.equals(Nombre)) {
                    Valido = false;
                    break;
                }
                Aux = Aux.Siguiente;
            }

            if (Valido) {
                Nuevo.idx = Fin.idx + 1;
                Fin.Siguiente = Nuevo;
                Nuevo.Anterior = Fin;
                Fin = Nuevo;
            }
        }
    }

    public NodoCabecera GetCabecera(int  index) {
        NodoCabecera Resp = null;
        NodoCabecera Actual = Inicio;
        while (Actual != null) {
            if (Actual.idx == index) {
                Resp = Actual;
            }

            Actual = Actual.Siguiente;
        }

        return Resp;
    }
    
     public int GetIndex(String valor) {
        int Resp = -1;
        NodoCabecera Actual = Inicio;
        while (Actual != null) {
            if (Actual.Nombre.equals(valor)) {
                Resp = Actual.idx;
            }

            Actual = Actual.Siguiente;
        }

        return Resp;
    }
}
