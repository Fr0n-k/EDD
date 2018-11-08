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
public class GrafoPensum {

    NodoPensum Inicio;
    NodoPensum Fin;
    int Elementos;

    public GrafoPensum() {
        super();
        Inicio = null;
        Fin = null;
        Elementos = 0;
    }

    public boolean InsertarPensum(String Nombre, String Codigo, String Creditos, String Area) {
        NodoPensum Nuevo = new NodoPensum(Nombre, Codigo, Creditos, Area);
        boolean Valido = true;
        if (Inicio == null) {
            Inicio = new NodoPensum(Nuevo.Nombre, Nuevo.Codigo, Nuevo.Creditos, Nuevo.Area);
            Fin = Inicio;
            Inicio.Siguiente = Fin;
            Inicio.Anterior = Fin;
            Fin.Anterior = Inicio;
            Fin.Siguiente = Inicio;
            this.Elementos++;
        } else {
            NodoPensum Aux = Inicio;
            do {
                if (Aux.Codigo.equals(Nuevo.Codigo)) {
                    Valido = false;
                    break;
                }
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);

            if (Valido) {
                NodoPensum AX = null;
                do {
                    if (Aux.Area.equals(Nuevo.Area)) {
                        AX = Aux;
                        break;
                    }
                    Aux = Aux.Siguiente;
                } while (Aux != Inicio);

                if (AX == Fin || AX == null) {
                    Fin.Siguiente = Nuevo;
                    Nuevo.Anterior = Fin;
                    Nuevo.Siguiente = Inicio;
                    Inicio.Anterior = Nuevo;
                    Fin = Nuevo;
                    this.Elementos++;
                } else {
                    Nuevo.Siguiente = AX.Siguiente;
                    AX.Siguiente.Anterior = Nuevo;
                    AX.Siguiente = Nuevo;
                    Nuevo.Anterior = AX;
                    this.Elementos++;
                }
            }
        }
        return Valido;
    }

    public boolean EliminarPensum(String ValEl) {
        boolean Valido = false;
        NodoPensum Aux = Inicio;
        do {
            if (Aux.Codigo.equals(ValEl)) {
                if (Aux == Inicio && Aux == Fin) {
                    Fin = null;
                    Inicio = null;
                } else if (Aux == Inicio) {
                    Fin.Siguiente = Aux.Siguiente;
                    Aux.Siguiente.Anterior = Fin;
                    Inicio = Aux.Siguiente;
                } else if (Aux == Fin) {
                    Aux.Anterior.Siguiente = Inicio;
                    Inicio.Anterior = Aux.Anterior;
                    Fin = Aux.Anterior;
                } else {
                    Aux.Anterior.Siguiente = Aux.Siguiente;
                    Aux.Siguiente.Anterior = Aux.Anterior;
                }
                Valido = true;
                break;
            }
            Aux = Aux.Siguiente;
        } while (Aux != Inicio);
        return Valido;
    }

    public NodoPensum VisualizarPensum(String Codigo) {
        NodoPensum Aux = Inicio;
        boolean Valido = true;
        do {
            if (Aux.Codigo.equals(Codigo)) {
                Valido = false;
                break;
            }
            Aux = Aux.Siguiente;
        } while (Aux != Inicio);

        if (Valido) {
            Aux = null;
        }
        return Aux;
    }

    public String GraficarPensum() {
        String Grafo = "";
        NodoPensum Aux = Inicio;
        int Contador = 1;
        if (Inicio != null) {
            do {
                Grafo += "subgraph cluster" + Contador + "{\n";
                do {
                    Grafo += Aux.Codigo + "[shape = record, label = \"" + Aux.Nombre + "\"];\n";
                    Aux = Aux.Siguiente;
                } while (Aux.Area.equals(Aux.Anterior.Area));
                Grafo += "label = \"" + Aux.Anterior.Area + "\";\n";
                Grafo += "}\n";
                Contador++;
            } while (Aux != Inicio);

            do {

                if (Aux.Prerequisitos.Inicio != null) {
                    NodoPre AX = Aux.Prerequisitos.Inicio;
                    while (AX != null) {
                        NodoPensum Au = VisualizarPensum(AX.Codigo);
                        if(Au != null){
                            Grafo += AX.Codigo + " -> " + Aux.Codigo + ";\n";
                        }
                        AX = AX.Siguiente;
                    }
                }
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);
        }
        return Grafo;
    }

    public String MAdyacencia() {
        String Matriz = "";
        NodoPensum Aux = Inicio;
        if (Aux != null) {
            String[][] Elementos = new String[this.Elementos + 1][this.Elementos + 1];
            Elementos[0][0] = "Elementos";
            for (int i = 1; i < this.Elementos + 1; i++) {
                Elementos[0][i] = Aux.Codigo;
                Aux = Aux.Siguiente;
            }

            for (int i = 1; i < this.Elementos + 1; i++) {
                Elementos[i][0] = Aux.Codigo;
                Aux = Aux.Siguiente;
            }

            for (int i = 1; i < this.Elementos + 1; i++) {
                for (int j = 1; j < this.Elementos + 1; j++) {
                    Elementos[i][j] = "0";
                }
            }

            do {
                if (Aux.Prerequisitos.Inicio != null) {
                    NodoPre AX = Aux.Prerequisitos.Inicio;
                    while (AX != null) {
                        for (int i = 1; i < this.Elementos + 1; i++) {
                            for (int j = 1; j < this.Elementos + 1; j++) {
                                if(AX != null){
                                    if (Elementos[0][j].equals(AX.Codigo)) {
                                        Elementos[i][j] = "1";
                                         AX = AX.Siguiente;
                                    }
                                } else{
                                    break;
                                }
                            }
                        }
                    }
                }
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);

            for (int i = 0; i < this.Elementos + 1; i++) {
                for (int j = 0; j < this.Elementos + 1; j++) {
                    Matriz += Elementos[i][j] + " | ";
                }
                Matriz += "\n";
            }
        }
        return Matriz;
    }
}
