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
public class ListaCortos {

    NodoCorto Inicio;
    NodoCorto Fin;

    public ListaCortos() {
        super();
        Inicio = null;
        Fin = null;
    }

    public boolean InsertarCorto(String Nombre, String Tutor_Academico, String Room) {
        NodoCorto Nuevo = new NodoCorto(Nombre, Tutor_Academico, Room);
        boolean Valido = true;
        if (Inicio == null) {
            Inicio = new NodoCorto(Nuevo.Nombre, Nuevo.Tutor_Academico, Nuevo.Room);
            Fin = Inicio;
            Inicio.Siguiente = Fin;
            Inicio.Anterior = Fin;
            Fin.Anterior = Inicio;
            Fin.Siguiente = Inicio;
        } else {
            NodoCorto Aux = Inicio;
            do {
                if (Aux.Room.equals(Nuevo.Room)) {
                    Valido = false;
                    break;
                }
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);

            if (Valido) {
                Fin.Siguiente = Nuevo;
                Nuevo.Anterior = Fin;
                Nuevo.Siguiente = Inicio;
                Inicio.Anterior = Nuevo;
                Fin = Nuevo;
            }
        }
        return Valido;
    }

    public boolean EliminarCorto(String ValEl) {
        boolean Valido = false;
        NodoCorto Aux = Inicio;
        do {
            if (Aux.Room.equals(ValEl)) {
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

    public NodoCorto VisualizarCorto(String Room) {
        NodoCorto Aux = Inicio;
        boolean Valido = true;
        do {
            if (Aux.Room.equals(Room)) {
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

    public String GraficarCortos() {
        String Cuerpo = "";
        NodoCorto Aux = Inicio;
        if (Aux != null) {
            do {
                Cuerpo += Aux.Room + ";\n";
                if (Aux.PreguntasCorto.Inicio != null) {
                    Cuerpo+= Aux.Room + "->" +Aux.Room +"Pregunta" + Aux.PreguntasCorto.Inicio.Numero +";\n";
                    NodoPregunta AX = Aux.PreguntasCorto.Inicio;
                    do{
                        Cuerpo+= Aux.Room + "Pregunta" + AX.Numero + "[label = \"" + AX.Pregunta + "\"];\n";
                        if (AX.Opciones.Inicio != null) {
                            NodoOpcion AX2 = AX.Opciones.Inicio;
                            Cuerpo += Aux.Room + "Pregunta" + AX.Numero + " ->" + Aux.Room + "Pregunta" + AX.Numero + "Opcion" + AX2.Numero + ";\n";
                            do{
                                Cuerpo +=  Aux.Room + "Pregunta" + AX.Numero + "Opcion" + AX2.Numero  + "[label = \"" + AX2.Opcion + "\"];\n";
                                if (AX2.Siguiente != null) {
                                    Cuerpo += Aux.Room + "Pregunta" + AX.Numero + "Opcion" + AX2.Numero + "->" + Aux.Room + "Pregunta" + AX.Numero + "Opcion" + AX2.Siguiente.Numero + ";\n";
                                }
                                AX2 = AX2.Siguiente;
                            }while (AX2 != AX.Opciones.Inicio);
                            
                            Cuerpo+= "{rank=same;" + Aux.Room + "Pregunta" + AX.Numero + ";";
                            do{
                                Cuerpo+=  Aux.Room + "Pregunta" + AX.Numero + "Opcion" + AX2.Numero + ";";
                                AX2 = AX2.Siguiente;
                            }while(AX2 != AX.Opciones.Inicio);
                            Cuerpo+="};\n";
                        }
                        if (AX.Siguiente != null) {
                            Cuerpo+= Aux.Room + "Pregunta" + AX.Numero + "->" + Aux.Room + "Pregunta" + AX.Siguiente.Numero +";\n";
                        }
                        AX = AX.Siguiente;
                    }while (AX != Aux.PreguntasCorto.Inicio); 
                }
                Cuerpo += Aux.Room + " -> " + Aux.Siguiente.Room + ";\n";
                Cuerpo += Aux.Siguiente.Room + " -> " + Aux.Room + ";\n";
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);

            Cuerpo += "{rank=same;";
            do {
                Cuerpo += Aux.Room + ";";
                Aux = Aux.Siguiente;
            } while (Aux != Inicio);
            Cuerpo += "}\n";
        }
        return Cuerpo;
    }

    public boolean VerVacio() {
        boolean Vacio = false;
        if (Inicio == null) {
            Vacio = true;
        }
        return Vacio;
    }

    public NodoCorto getInicio() {
        return Inicio;
    }

    public void setInicio(NodoCorto inicio) {
        Inicio = inicio;
    }

    public NodoCorto getFin() {
        return Fin;
    }

    public void setFin(NodoCorto fin) {
        Fin = fin;
    }
}
