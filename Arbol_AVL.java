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
public class ArbolTutores {

    NodoTutores Raiz;

    public ArbolTutores() {
        Raiz = null;
    }

    public NodoTutores InsertarTutor(NodoTutores Raiz, NodoTutores Nuevo, boolean h) {
        NodoTutores n1 = null;
        if (Raiz == null) {
            Raiz = new NodoTutores(Nuevo.Carnet, Nuevo.Curso, Nuevo.Periodo,Nuevo.Pass);
            n1 = Raiz;
            h = true;
        } else if (Nuevo.Carnet < Raiz.Carnet) {
            NodoTutores iz;
            iz = InsertarTutor((NodoTutores) Raiz.Izquierda, Nuevo, h);
            Raiz.Izquierda = iz;
            h = true;
            if (h) {
                switch (Raiz.fe) {
                    case 1:
                        Raiz.fe = 0;
                        h = false;
                        break;
                    case 0:
                        Raiz.fe = -1;
                        break;
                    case -1: // aplicar rotación a la izquierda
                        n1 = (NodoTutores) Raiz.Izquierda;
                        if (n1.fe == -1) {
                            Raiz = RotacionII(Raiz, n1);
                        } else {
                            Raiz = RotacionID(Raiz, n1);
                        }
                        h = false;
                }
            }
        } else if (Nuevo.Carnet > Raiz.Carnet) {
            NodoTutores dr;
            dr = InsertarTutor((NodoTutores) Raiz.Derecha, Nuevo, h);
            Raiz.Derecha = dr;
            h = true;
            if (h) {
                switch (Raiz.fe) {
                    case 1:
                        n1 = (NodoTutores) Raiz.Derecha;
                        if (n1.fe == +1) {
                            Raiz = RotacionDD(Raiz, n1);
                        } else {
                            Raiz = RotacionDI(Raiz, n1);
                        }
                        h = false;
                        break;
                    case 0:
                        Raiz.fe = +1;
                        break;
                    case -1:
                        Raiz.fe = 0;
                        h = false;
                }
            }
        } else {
            n1 = null;
        }
        return Raiz;
    }

    private NodoTutores RotacionII(NodoTutores n, NodoTutores n1) {
        n.Izquierda = n1.Derecha;
        n1.Derecha = n;
        if (n1.fe == -1) {
            n.fe = 0;
            n1.fe = 0;
        } else {
            n.fe = -1;
            n1.fe = 1;
        }
        return n1;
    }

    private NodoTutores RotacionDD(NodoTutores n, NodoTutores n1) {
        n.Derecha = n1.Izquierda;
        n1.Izquierda = n;
        if (n1.fe == +1) {
            n.fe = 0;
            n1.fe = 0;
        } else {
            n.fe = +1;
            n1.fe = -1;
        }
        return n1;
    }

    private NodoTutores RotacionID(NodoTutores n, NodoTutores n1) {
        NodoTutores n2;
        n2 = (NodoTutores) n1.Derecha;
        n.Izquierda = n2.Derecha;
        n2.Derecha = n;
        n1.Derecha = n2.Izquierda;
        n2.Izquierda = n1;

        if (n2.fe == +1) {
            n1.fe = -1;
        } else {
            n1.fe = 0;
        }
        if (n2.fe == -1) {
            n.fe = 1;
        } else {
            n.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    private NodoTutores RotacionDI(NodoTutores n, NodoTutores n1) {
        NodoTutores n2;
        n2 = (NodoTutores) n1.Izquierda;
        n.Derecha = n2.Izquierda;
        n2.Izquierda = n;
        n1.Izquierda = n2.Derecha;
        n2.Derecha = n1;

        if (n2.fe == +1) {
            n.fe = -1;
        } else {
            n.fe = 0;
        }
        if (n2.fe == -1) {
            n1.fe = 1;
        } else {
            n1.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    public NodoTutores EliminarTutor(NodoTutores Inicio, int ValEl, boolean altura) {
        if (Inicio != null) {
            if (ValEl < Inicio.Carnet) {
                Inicio.Izquierda = EliminarTutor(Inicio.Izquierda, ValEl, altura);
                if (altura) {
                    Inicio = equilibrar1(Inicio, altura);
                }
            } else if (ValEl > Inicio.Carnet) {
                Inicio.Derecha = EliminarTutor(Inicio.Derecha, ValEl, altura);
                if (altura) {
                    Inicio = equilibrar2(Inicio, altura);
                }
            } else // Nodo encontrado
            {
                NodoTutores q;
                q = Inicio; // nodo a quitar del árbol
                if (q.Izquierda == null) {
                    Inicio = q.Derecha;
                    altura = true;
                } else if (q.Derecha == null) {
                    Inicio = q.Izquierda;
                    altura = true;
                } else {
                    NodoTutores iz;
                    iz = reemplazar(Inicio, Inicio.Izquierda, altura);
                    altura = true;
                    Inicio.Izquierda = iz;
                    if (altura) {
                        Inicio = equilibrar1(Inicio, altura);
                    }
                }
                q = null;
            }
        }
        return Inicio;
    }

    private NodoTutores reemplazar(NodoTutores n, NodoTutores act, boolean cambiaAltura) {
        if (act.Derecha != null) {
            NodoTutores d;
            d = reemplazar(n, act.Derecha, cambiaAltura);
            act.Derecha = d;
            if (cambiaAltura) {
                act = equilibrar2(act, cambiaAltura);
            }
        } else {
            n.Carnet = act.Carnet;
            n.Curso = act.Curso;
            n.Periodo = act.Periodo;
            n = act;
            act = act.Izquierda;
            n = null;
            cambiaAltura = true;
        }
        return act;
    }

    private NodoTutores equilibrar1(NodoTutores n, boolean cambiaAltura) {
        NodoTutores n1;
        switch (n.fe) {
            case -1:
                n.fe = 0;
                break;
            case 0:
                n.fe = 1;
                cambiaAltura = false;
                break;
            case +1: //se aplicar un tipo de rotación derecha
                n1 = n.Derecha;
                if (n1.fe >= 0) {
                    if (n1.fe == 0) //la altura no vuelve a disminuir
                    {
                        cambiaAltura = false;
                    }
                    n = RotacionDD(n, n1);
                } else {
                    n = RotacionDI(n, n1);
                }
                break;
        }
        return n;
    }

    private NodoTutores equilibrar2(NodoTutores n, boolean cambiaAltura) {
        NodoTutores n1;
        switch (n.fe) {
            case -1: // Se aplica un tipo de rotación izquierda
                n1 = n.Izquierda;
                if (n1.fe <= 0) {
                    if (n1.fe == 0) {
                        cambiaAltura = false;
                    }
                    n = RotacionII(n, n1);
                } else {
                    n = RotacionID(n, n1);
                }
                break;
            case 0:
                n.fe = -1;
                cambiaAltura = false;
                break;
            case +1:
                n.fe = 0;
                break;
        }
        return n;
    }

    public NodoTutores VisualizarTutor(NodoTutores Inicio, int ValBus) {
        NodoTutores Resp = null;
        if (Inicio != null) {
            if (ValBus < Inicio.Carnet) {
                Resp = VisualizarTutor(Inicio.Izquierda, ValBus);
            } else if (ValBus > Inicio.Carnet) {
                Resp = VisualizarTutor(Inicio.Derecha, ValBus);
            } else {
                Resp = Inicio;
            }
        }
        return Resp;
    }

    public String GraficarTutores(NodoTutores Inicio, String Grafo) {
        String Cuerpo = "";
        if (Inicio != null) {
            Cuerpo += Inicio.Carnet + "[shape = record,label = \"Carnet:" + Inicio.Carnet + "\\n Curso: " + Inicio.Curso + "\\n Periodo: " + Inicio.Periodo + "\"];\n";
            if (Inicio.Izquierda != null) {
                Cuerpo += Inicio.Carnet + " -> " + Inicio.Izquierda.Carnet + ";\n";
            }
            if (Inicio.Derecha != null) {
                Cuerpo += Inicio.Carnet + " -> " + Inicio.Derecha.Carnet + ";\n";
            }
            Cuerpo += GraficarTutores(Inicio.Izquierda, "");
            Cuerpo += GraficarTutores(Inicio.Derecha, "");
        }
        return Cuerpo;
    }
}
