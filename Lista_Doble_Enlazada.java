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
public class ListaAprobados {

    NodoAprobado Inicio;
    NodoAprobado Fin;

    public ListaAprobados() {
        Inicio = null;
        Fin = null;
    }

    public boolean InsertarAprobado(String Codigo, String Descripcion, String Nota, String Fecha) {
        boolean Valido = true;
        if (Inicio == null) {
            Inicio = new NodoAprobado(Codigo, Descripcion, Nota, Fecha);
            Fin = Inicio;
            Valido = true;
        } else {
            NodoAprobado Aux = Inicio;
            while (Aux != Inicio) {
                if (Aux.Codigo.equals(Codigo)) {
                    Valido = false;
                }
            }

            if (Valido) {
                NodoAprobado Nuevo = new NodoAprobado(Codigo, Descripcion, Nota, Fecha);
                Fin.Siguiente = Nuevo;
                Nuevo.Anterior = Fin;
                Fin = Nuevo;
            }
        }
        return Valido;
    }

    public String GApro(String Ini) {
        String Cuerpo = "";
        if (Inicio != null) {
            Cuerpo += Ini + " -> " + Inicio.Codigo + ";\n";
            NodoAprobado Aux = Inicio;
            while (Aux != null) {
                Cuerpo += Aux.Codigo + ";\n";
                if (Aux.Siguiente != null) {
                    Cuerpo += Aux.Codigo + " -> " + Aux.Siguiente.Codigo + ";\n";
                    Cuerpo += Aux.Siguiente.Codigo + " -> " + Aux.Codigo + ";\n";
                }
                Aux = Aux.Siguiente;
            }
        }
        return Cuerpo;
    }

    public String TxtApro() {
        String Cuerpo = "";
        if (Inicio != null) {
            NodoAprobado Aux = Inicio;
            while (Aux != null) {
                Cuerpo += "Curso" + Aux.Codigo + " Descripcion" + Aux.Descripcion + " Nota" + Aux.Nota + " Fecha" + Aux.Fecha + "\n";
                Aux = Aux.Siguiente;
            }
        }
        return Cuerpo;
    }
}
