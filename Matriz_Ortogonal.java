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
public class MatrizNotas {

    CabeceraMatriz Filas;
    CabeceraMatriz Columnas;

    public MatrizNotas() {
        Filas = new CabeceraMatriz();
        Columnas = new CabeceraMatriz();
    }

    public void Insertar(int Fila, int Columna, double Nota) {
        NodoNota Nuevo = new NodoNota(Nota, Fila, Columna);

        //INSERCION_FILAS
        NodoCabecera eFila = Filas.GetCabecera(Fila);
        if (eFila.Acceso == null) //Si no existe encabezado se crea.
        {
            eFila.Acceso = new NodoNota(Nota, Fila, Columna);
        } else {
            if (Nuevo.Columna < eFila.Acceso.Columna) //Inserción al inicio
            {
                Nuevo.Derecha = eFila.Acceso;
                eFila.Acceso.Izquierda = Nuevo;
                eFila.Acceso = Nuevo;
            } else {
                NodoNota Actual = eFila.Acceso;
                while (Actual.Derecha != null) {
                    if (Nuevo.Columna < Actual.Derecha.Columna) //Inserción en el medio
                    {
                        Nuevo.Derecha = Actual.Derecha;
                        Actual.Derecha.Izquierda = Nuevo;
                        Nuevo.Izquierda = Actual;
                        Actual.Derecha = Nuevo;
                        break;
                    }

                    Actual = Actual.Derecha;
                }

                if (Actual.Derecha == null) //Inserción al final
                {
                    Actual.Derecha = Nuevo;
                    Nuevo.Izquierda = Actual;
                }
            }
        }
        //FIN_FILAS

        //INSERCION_COLUMNAS
        NodoCabecera eColumna = Columnas.GetCabecera(Columna);
        if (eColumna.Acceso == null) //Si no existe encabezado se crea.
        {
            eColumna.Acceso = new NodoNota(Nota, Fila, Columna);
        } else {
            if (Nuevo.Fila < eColumna.Acceso.Fila) //Inserción al inicio
            {
                Nuevo.Abajo = eColumna.Acceso;
                eColumna.Acceso.Arriba = Nuevo;
                eColumna.Acceso = Nuevo;
            } else {
                NodoNota Actual = eColumna.Acceso;
                while (Actual.Abajo != null) {
                    if (Nuevo.Fila < Actual.Abajo.Fila) //Inserción en el medio
                    {
                        Nuevo.Abajo = Actual.Abajo;
                        Actual.Abajo.Arriba = Nuevo;
                        Nuevo.Arriba = Actual;
                        Actual.Abajo = Nuevo;
                        break;
                    }

                    Actual = Actual.Abajo;
                }

                if (Actual.Abajo == null) //Inserción al final
                {
                    Actual.Abajo = Nuevo;
                    Nuevo.Arriba = Actual;
                }
            }
        }
        //FIN_COLUMNAS
    }

    public NodoNota VisualizarNota(int Fila, int Columna) {
        NodoNota Resp = null;
        NodoCabecera Aux = Filas.Inicio;

        while (Aux != null) {
            if (Aux.idx == Fila) {
                Resp = Aux.Acceso;
            }
            Aux = Aux.Siguiente;
        }

        while (Resp != null) {
            if (Resp.Columna == Columna) {
                break;
            }
            Resp = Resp.Derecha;
        }
        return Resp;
    }

    public boolean EliminarNota(int Fila, int Columna) {
        NodoNota NB = VisualizarNota(Fila, Columna);
        boolean Resp = false;
        if (NB != null) {
            NodoCabecera FilaA = Filas.GetCabecera(Fila);
            NodoCabecera ColumnaA = Columnas.GetCabecera(Columna);
            if (NB.Arriba == null && NB.Izquierda == null && NB.Derecha == null && NB.Abajo == null) {
                FilaA.Acceso = null;
                ColumnaA.Acceso = null;
            } else if (NB.Arriba == null && NB.Izquierda == null && NB.Derecha == null && NB.Abajo != null) {
                FilaA.Acceso = null;
                NB.Arriba = null;
                ColumnaA.Acceso = NB.Abajo;
            } else if (NB.Arriba == null && NB.Izquierda == null && NB.Derecha != null && NB.Abajo == null) {
                NB.Derecha.Izquierda = null;
                FilaA.Acceso = NB.Derecha;
                ColumnaA.Acceso = null;
            } else if (NB.Arriba == null && NB.Izquierda == null && NB.Derecha != null && NB.Abajo != null) {
                NB.Abajo.Arriba = null;
                NB.Derecha.Izquierda = null;
                FilaA.Acceso = NB.Derecha;
                ColumnaA.Acceso = NB.Abajo;
            } else if (NB.Arriba == null && NB.Izquierda != null && NB.Derecha == null && NB.Abajo == null) {
                ColumnaA.Acceso = null;
                NB.Izquierda.Derecha = null;
            } else if (NB.Arriba == null && NB.Izquierda != null && NB.Derecha == null && NB.Abajo != null) {
                NB.Abajo.Arriba = null;
                NB.Izquierda.Derecha = null;
                ColumnaA.Acceso = NB.Abajo;
            } else if (NB.Arriba == null && NB.Izquierda != null && NB.Derecha != null && NB.Abajo == null) {
                NB.Derecha.Izquierda = NB.Izquierda;
                NB.Izquierda.Derecha = NB.Derecha;
                ColumnaA.Acceso = null;
            } else if (NB.Arriba == null && NB.Izquierda != null && NB.Derecha != null && NB.Abajo != null) {
                NB.Derecha.Izquierda = NB.Izquierda;
                NB.Izquierda.Derecha = NB.Derecha;
                NB.Abajo.Arriba = null;
                ColumnaA.Acceso = NB.Abajo;
            } else if (NB.Arriba != null && NB.Izquierda == null && NB.Derecha == null && NB.Abajo == null) {
                FilaA.Acceso = null;
                NB.Arriba.Abajo = null;
            } else if (NB.Arriba != null && NB.Izquierda == null && NB.Derecha == null && NB.Abajo != null) {
                FilaA.Acceso = null;
                NB.Arriba.Abajo = NB.Abajo;
                NB.Abajo.Arriba = NB.Arriba;
            } else if (NB.Arriba != null && NB.Izquierda == null && NB.Derecha != null && NB.Abajo == null) {
                FilaA.Acceso = NB.Derecha;
                NB.Derecha.Izquierda = null;
                NB.Arriba.Abajo = null;
            } else if (NB.Arriba != null && NB.Izquierda == null && NB.Derecha != null && NB.Abajo != null) {
                NB.Arriba.Abajo = NB.Abajo;
                NB.Abajo.Arriba = NB.Arriba;
                NB.Derecha.Izquierda = null;
                FilaA.Acceso = NB.Derecha;
            } else if (NB.Arriba != null && NB.Izquierda != null && NB.Derecha == null && NB.Abajo == null) {
                NB.Arriba.Abajo = null;
                NB.Izquierda.Derecha = null;
            } else if (NB.Arriba != null && NB.Izquierda != null && NB.Derecha == null && NB.Abajo != null) {
                NB.Arriba.Abajo = NB.Abajo;
                NB.Abajo.Arriba = NB.Arriba;
                NB.Izquierda.Derecha = null;
            } else if (NB.Arriba != null && NB.Izquierda != null && NB.Derecha != null && NB.Abajo == null) {
                NB.Arriba.Abajo = null;
                NB.Izquierda.Derecha = NB.Derecha;
                NB.Derecha.Izquierda = NB.Izquierda;
            } else {
                NB.Abajo.Arriba = NB.Arriba;
                NB.Arriba.Abajo = NB.Abajo;
                NB.Derecha.Izquierda = NB.Izquierda;
                NB.Izquierda.Derecha = NB.Izquierda;
            }
            Resp = true;
        }
        return Resp;
    }

    public boolean EliminarActividad(int Index) {
        boolean Resp = false;
        NodoCabecera Aux = Columnas.Inicio;

        if (Index == 1) {
            NodoNota AuxN = Aux.Acceso;
            while (AuxN != null) {
                NodoCabecera FActual = Filas.GetCabecera(AuxN.Fila);
                if (AuxN.Derecha != null) {
                    AuxN.Derecha.Izquierda = null;
                }
                FActual.Acceso = AuxN.Derecha;
                AuxN = AuxN.Abajo;
            }
            NodoCabecera Cambio = Columnas.Inicio.Siguiente;
            Columnas.Inicio = Columnas.Inicio.Siguiente;
            if (Columnas.Inicio.Siguiente != null) {
                Cambio.Anterior = null;
            }
            while (Cambio != null) {
                Cambio.idx = Cambio.idx - 1;
                Cambio = Cambio.Siguiente;
            }
            Resp = true;
        } else if (Columnas.Fin.idx == Index) {
            NodoNota AuxN = Columnas.Fin.Acceso;
            while (AuxN != null) {
                AuxN.Izquierda.Derecha = null;
                AuxN = AuxN.Abajo;
            }
            Resp = true;
        } else {
            while (Aux != null) {
                if (Aux.idx == Index) {
                    NodoNota AuxN = Aux.Acceso;
                    while (AuxN != null) {
                        AuxN.Izquierda.Derecha = AuxN.Derecha;
                        AuxN.Derecha.Izquierda = AuxN.Izquierda;
                        AuxN = AuxN.Abajo;
                    }

                    Aux.Siguiente.Anterior = Aux.Anterior;
                    Aux.Anterior.Siguiente = Aux.Siguiente;
                    NodoCabecera Cambio = Aux.Siguiente;
                    while (Cambio != null) {

                        Cambio.idx = Cambio.idx - 1;
                        Cambio = Cambio.Siguiente;
                    }
                    break;
                }
                Aux = Aux.Siguiente;
            }
            Resp = true;
        }
        return Resp;
    }

    public String GraficarMatriz() {
        String Cuerpo = "Inicio;\n";
        NodoCabecera Fil = Filas.Inicio;
        if (Fil != null) {
            Cuerpo += "Inicio -> F" + Fil.idx + ";\n";
        }
        while (Fil != null) {
            Cuerpo += "F" + Fil.idx + "[label= \"" + Fil.Nombre + "\"];\n";
            if (Fil.Acceso != null) {
                Cuerpo += "F" + Fil.idx + " -> N" + Fil.Acceso.Columna + Fil.Acceso.Fila + ";\n";
                NodoNota Aux = Fil.Acceso;
                while (Aux != null) {
                    Cuerpo += "N" + Aux.Columna + Aux.Fila + "[label = \"" + Fil.Acceso.Nota + "\"];\n";
                    if (Aux.Derecha != null) {
                        Cuerpo += "N" + Aux.Columna + Aux.Fila + " -> N" + Aux.Derecha.Columna + Aux.Derecha.Fila + ";\n";
                        Cuerpo += "N" + Aux.Derecha.Columna + Aux.Derecha.Fila + " -> N" + Aux.Columna + Aux.Fila + ";\n";
                    }
                    Aux = Aux.Derecha;
                }
                Cuerpo += "{rank=same;F" + Fil.idx + ";";
                Aux = Fil.Acceso;
                while (Aux != null) {
                    Cuerpo += "N" + Aux.Columna + Aux.Fila + ";";
                    Aux = Aux.Derecha;
                }
                Cuerpo += "}\n";
            }
            if (Fil.Siguiente != null) {
                Cuerpo += "F" + Fil.idx + " -> F" + Fil.Siguiente.idx + ";\n";
                Cuerpo += "F" + Fil.Siguiente.idx + " -> F" + Fil.idx + ";\n";
            }
            Fil = Fil.Siguiente;
        }

        NodoCabecera Col = Columnas.Inicio;
        if (Col != null) {
            Cuerpo += "Inicio -> C" + Col.idx + ";\n";
        }
        while (Col != null) {
            Cuerpo += "C" + Col.idx + "[label= \"" + Col.Nombre + "\"];\n";
            if (Col.Acceso != null) {
                Cuerpo += "C" + Col.idx + " -> N" + Col.Acceso.Columna + Col.Acceso.Fila + ";\n";
                NodoNota Aux = Col.Acceso;
                while (Aux != null) {
                    Cuerpo += "N" + Aux.Columna + Aux.Fila + "[label = \"" + Col.Acceso.Nota + "\"];\n";
                    if (Aux.Abajo != null) {
                        Cuerpo += "N" + Aux.Columna + Aux.Fila + " -> N" + Aux.Abajo.Columna + Aux.Abajo.Fila + ";\n";
                        Cuerpo += "N" + Aux.Abajo.Columna + Aux.Abajo.Fila + " -> N" + Aux.Columna + Aux.Fila + ";\n";
                    }
                    Aux = Aux.Abajo;
                }
            }
            if (Col.Siguiente != null) {
                Cuerpo += "C" + Col.idx + " -> C" + Col.Siguiente.idx + ";\n";
                Cuerpo += "C" + Col.Siguiente.idx + " -> C" + Col.idx + ";\n";

            }
            Col = Col.Siguiente;
        }

        Col = Columnas.Inicio;
        Cuerpo += "{rank = same;Inicio;";
        while (Col != null) {
            Cuerpo += "C" + Col.idx + ";";
            Col = Col.Siguiente;
        }
        Cuerpo += "}\n";
        return Cuerpo;
    }
    
    public String EstadisticasMatriz(String Actividad){
        String Res = "";
        int IndexCol = Columnas.GetIndex(Actividad);
        if(IndexCol != -1){
            NodoCabecera  Aux = Columnas.GetCabecera(IndexCol);
            if(Aux != null){
                int TotalAlumnos = Filas.Fin.idx;
                int TotalEntrega = 0;
                NodoNota AX = Aux.Acceso;
                if(AX != null){
                    while(AX != null){
                        TotalEntrega++;
                        AX = AX.Abajo;
                    }
                }
                Res += "Total Entregaron: " + TotalEntrega +"\n";
                Res += "Total NO Entragaron: " + (TotalAlumnos-TotalEntrega) + "\n";
                
                if(TotalEntrega != 0){
                    AX = Aux.Acceso;
                    double[] Datos = new double[TotalEntrega];
                    for(int i=0;i<TotalEntrega;i++){
                        Datos[i] = AX.Nota;
                    }
                    
                    double Promedio = 0;
                    for(int i = 0;i<TotalEntrega;i++){
                        Promedio = Promedio + Datos[i];
                    }
                    
                    Promedio = Promedio/TotalEntrega;
                    Res+= "Media: " + Promedio+ "\n";
                    
                    
                    for(int i = 0; i< TotalEntrega - 1;i++){
                        for(int j = i+1; j<TotalEntrega;j++){
                            if(Datos[i] <= Datos[j]){
                                double A = Datos[i];
                                Datos[i] = Datos[j];
                                Datos[j] = A;
                            }
                        }
                    }
                    double Mediana = 0;
                    if(TotalEntrega%2 == 0){
                        Mediana = (Datos[TotalEntrega/2]+Datos[(TotalEntrega/2)+1])/2;
                    } 
                    else
                    {
                         Mediana = Datos[(TotalEntrega+1)/2];
                    }
                    
                    Res += "Mediana: " + Mediana + "\n";
                    
                    int Repe = 1;
                    int ARep = 0;
                    double Moda = Datos[0];
                    for(int i = 1; i<TotalEntrega;i++){
                        if(Datos[i] == Datos[i-1]){
                            Repe++;
                        }
                        else
                        {
                            if(Repe > ARep && ARep!= 0){
                                Moda = Datos[i];
                                ARep = Repe;
                            }
                            Repe = 0;
                        }
                    }
                    
                    Res += "Moda: " + Moda +"\n";
                    
                    //Suma del valor menos el promedio al cuadrado dividido n
                    double Varianza = 0;
                    for(int i = 0;i<TotalEntrega ;i++){
                        Varianza = Varianza + Math.pow(2, (Datos[i]-Promedio));
                    }
                    Varianza  = Varianza/TotalEntrega;
                    double Desviacion = Math.sqrt(Varianza);
                    Res+= "Varianza: " + Varianza + "\n";
                    Res+= "Desviacion: " + Desviacion + "\n";
                    
                    int TotApro = 0;
                    int TotRepro = 0;
                    
                    for(int i = 0; i<TotalEntrega;i++){
                        if(Datos[i] >= 61){
                            TotApro++;
                        }
                        else{
                            TotRepro++;
                        }
                    }
                    
                    Res = TotApro + "/" + TotRepro+ "/" + Res;
                }
                else
                {
                    Res+="Media: 0\n Mediana:0\n Moda:0\n Desviacion: N/A \n Varianza: N/A\n"; 
                }
            }
        }
        return Res;
    }
}
