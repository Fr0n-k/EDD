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
public class HashListaEstudiantes {

    int PosLlenas;
    int TamActual;
    ListaHash[] Tabla;

    public HashListaEstudiantes(int Longitud) {
        this.Tabla = new ListaHash[Longitud];
        this.PosLlenas = 0;
        this.TamActual = Longitud;

        for (int i = 0; i < Longitud; i++) {
            Tabla[i] = new ListaHash();
        }
    }

    public boolean PlegarHash(HashListaEstudiantes LAc, String Carnet, String Nombre, String Email) {
        int PosClave = Fold(Nombre, LAc.TamActual);
        boolean Resp = false; 
        NodoListaHash PivEX = BuscarPosicion(Carnet);
        if(PivEX == null){
            if (LAc.Tabla[PosClave].Inicio == null) {
                LAc.PosLlenas = LAc.PosLlenas + 1;
            }
            Resp = LAc.Tabla[PosClave].InsertarClave(Carnet, Nombre, Email);
            if(Resp){
                double AA = Double.valueOf(LAc.PosLlenas);
                double AB = Double.valueOf(LAc.TamActual);
                double ocup = AA/AB;
                if(ocup >= 0.8){
                    LAc = ReHash(LAc);
                }
            }
        }
        return Resp;
    }

    public boolean EliminarPosicion(String Carnet){
        NodoListaHash Enc = BuscarPosicion(Carnet);
        boolean Resp = false;
        if(Enc != null)
        {
            for(int i = 0;i<TamActual;i++){
                if(Tabla[i].Inicio != null){
                    NodoListaHash Piv = Tabla[i].BuscarCarnet(Carnet);
                    if(Piv != null){
                        Tabla[i].EliminarCarnet(Carnet);
                        if(Tabla[i].Inicio == null){
                            PosLlenas = PosLlenas - 1;
                        }
                        Resp = true;
                    }
                }
            }
        }
        return Resp;
    }
    
    public NodoListaHash BuscarPosicion(String Carnet){
        NodoListaHash Resp = null;
        for(int i = 0;i<TamActual;i++){
            if(Tabla[i].Inicio != null){
                NodoListaHash Piv = Tabla[i].BuscarCarnet(Carnet);
                if(Piv != null){
                    Resp = Piv;
                    break;
                }
            }
        }
        return Resp;
    }
    
    private HashListaEstudiantes ReHash(HashListaEstudiantes Actual) {
        HashListaEstudiantes Aux = new HashListaEstudiantes(Actual.TamActual * 2);
        for (int i = 0; i < Actual.TamActual; i++) {
            if(Actual.Tabla[i].Inicio != null){
                NodoListaHash Ax = Actual.Tabla[i].Inicio;
                while(Ax != null){
                    boolean Resp = Aux.PlegarHash(Aux, Ax.Carnet, Ax.Nombre, Ax.Email);
                    Ax = Ax.Siguiente;
                }
            }
        }
        return Aux;
    }

    private int Fold(String X, int M) {
        char ch[];
        ch = X.toCharArray();
        int XL = X.length();
        int i, sum;
        for (sum = 0, i = 0; i < XL; i++) {
            sum += ch[i];
        }
        return sum % M;
    }
    
    public String GraficarHash(HashListaEstudiantes HAc){
        String Cuerpo = "";
        for(int i = 0; i< HAc.TamActual;i++){
            Cuerpo+= "Posicion" + i + ";\n";
            Cuerpo+= "Posicion" + i + " -> Posicion" + (i+1) +";\n";
            if(HAc.Tabla[i].Inicio != null){
                Cuerpo+= "Posicion" + i + " -> " + HAc.Tabla[i].Inicio.Carnet + "\n;";
                Cuerpo+= HAc.Tabla[i].GrPosicion(i);
            }
        }
        return Cuerpo;
    }
}

