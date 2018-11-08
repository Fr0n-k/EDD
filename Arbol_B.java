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
public class ArbolB {

    int Contador;
    int Nivel;
    Pagina P;
    Pagina XDer;
    Pagina XIzq;
    NodoB X;
    Pagina XR;
    boolean Editado;
    NodoB Buscado;
    boolean EmpA;
    boolean Esta;

    public ArbolB() {
        this.Contador = 0;
        this.Nivel = 0;
        this.P = new Pagina();
        this.XDer = new Pagina();
        this.XIzq = new Pagina();
        this.X = null;
        this.Editado = false;
        this.Buscado = null;
        this.EmpA = false;
        this.Esta = false;
    }

    public boolean Vacio(Pagina p) {
        return (p == null || p.Cuenta == 0) ? true : false;
    }

    //METODO PUBLICO ENCARGADO DE HACER LA INSERCIÓN
    public boolean InsertarNB(NodoB Clave) {
        return InsertaPrivado(Clave, this.P);
    }

    //METODO PRIVADO QUE HACE EL LLAMADO A LOS DEMAS METODOS
    public boolean InsertaPrivado(NodoB Clave, Pagina Raiz) {
        Empujar(Clave, Raiz);
        if (this.EmpA) {
            this.P = new Pagina();
            this.P.Cuenta = 1;
            this.P.Nodos[0] = this.X;
            this.P.Ramas[0] = Raiz;
            this.P.Ramas[1] = this.XR;
        }
        return true;
    }

    //METODO ENCARGADO DE EMPUJAR
    public void Empujar(NodoB Clave, Pagina Raiz) {
        int k = 0;
        this.Esta = false;
        if (Vacio(Raiz)) {
            this.EmpA = true;
            this.X = Clave;
            this.XR = null;
        } else {
            k = BuscarNodo(Clave, Raiz);
            if (this.Esta) {
                this.EmpA = false;
            } else {
                Empujar(Clave, Raiz.Ramas[k]);
                if (this.EmpA) {
                    if (Raiz.Cuenta < 4) {
                        this.EmpA = false;
                        MeterHoja(this.X, Raiz, k);
                    } else {
                        this.EmpA = true;
                        DividirNodo(this.X, Raiz, k);
                    }
                }
            }
        }
    }

    //DIVIDIR NODO
    public void DividirNodo(NodoB Clave, Pagina Raiz, int k) {
        int pos = 0;
        int posmda = 0;
        if (k <= 2) {
            posmda = 2;
        } else {
            posmda = 3;
        }
        Pagina mder = new Pagina();
        pos = posmda + 1;
        while (pos != 5) {
            mder.Nodos[(pos - posmda) - 1] = Raiz.Nodos[pos - 1];
            mder.Ramas[pos - posmda] = Raiz.Ramas[pos];
            ++pos;
        }
        mder.Cuenta = 4 - posmda;
        Raiz.Cuenta = posmda;
        if (k <= 2) {
            MeterHoja(Clave, Raiz, k);
        } else {
            MeterHoja(Clave, mder, (k - posmda));
        }
        this.X = Raiz.Nodos[Raiz.Cuenta - 1];
        mder.Ramas[0] = Raiz.Ramas[Raiz.Cuenta];
        Raiz.Cuenta = --Raiz.Cuenta;
        this.XR = mder;
    }

//METODO ENCARGADO DE METER HOJA
    void MeterHoja(NodoB Clave, Pagina Raiz, int k) {
        int i = Raiz.Cuenta;
        while (i != k) {
            Raiz.Nodos[i] = Raiz.Nodos[i - 1];
            Raiz.Ramas[i + 1] = Raiz.Ramas[i];
            i--;
        }
        Raiz.Nodos[k] = Clave;
        Raiz.Ramas[k + 1] = this.XR;
        Raiz.Cuenta = ++Raiz.Cuenta;
    }

    public void Sucesor(Pagina Raiz, int k) {
        Pagina q = Raiz.Ramas[k];
        while (!Vacio(q.Ramas[0])) {
            q = q.Ramas[0];
        }
        Raiz.Nodos[k - 1] = q.Nodos[0];
    }

    public void Combina(Pagina Raiz, int pos) {
        int j;
        this.XDer = Raiz.Ramas[pos];
        this.XIzq = Raiz.Ramas[pos - 1];
        this.XIzq.Cuenta++;
        this.XIzq.Nodos[this.XIzq.Cuenta - 1] = Raiz.Nodos[pos - 1];
        this.XIzq.Ramas[this.XIzq.Cuenta] = this.XDer.Ramas[0];
        j = 1;
        while (j != this.XDer.Cuenta + 1) {
            this.XIzq.Cuenta++;
            this.XIzq.Nodos[this.XIzq.Cuenta - 1] = this.XDer.Nodos[j - 1];
            this.XIzq.Ramas[this.XIzq.Cuenta] = this.XDer.Ramas[j];
            j++;
        }
        Quitar(Raiz, pos);
    }

    public void MoverDer(Pagina Raiz, int pos) {
        int i = Raiz.Ramas[pos].Cuenta;
        while (i != 0) {
            Raiz.Ramas[pos].Nodos[i] = Raiz.Ramas[pos].Nodos[i - 1];
            Raiz.Ramas[pos].Ramas[i + 1] = Raiz.Ramas[pos].Ramas[i];
            --i;
        }
        Raiz.Ramas[pos].Cuenta++;
        Raiz.Ramas[pos].Ramas[1] = Raiz.Ramas[pos].Ramas[0];
        Raiz.Ramas[pos].Nodos[0] = Raiz.Nodos[pos - 1];
        Raiz.Nodos[pos - 1] = Raiz.Ramas[pos - 1].Nodos[Raiz.Ramas[pos - 1].Cuenta - 1];
        Raiz.Ramas[pos].Ramas[0] = Raiz.Ramas[pos - 1].Ramas[Raiz.Ramas[pos - 1].Cuenta];
        Raiz.Ramas[pos - 1].Cuenta--;
    }

    public void MoverIzq(Pagina Raiz, int pos) {
        int i;
        Raiz.Ramas[pos - 1].Cuenta++;
        Raiz.Ramas[pos - 1].Nodos[Raiz.Ramas[pos - 1].Cuenta - 1] = Raiz.Nodos[pos - 1];
        Raiz.Ramas[pos - 1].Ramas[Raiz.Ramas[pos - 1].Cuenta] = Raiz.Ramas[pos].Ramas[0];
        Raiz.Nodos[pos - 1] = Raiz.Ramas[pos].Nodos[0];
        Raiz.Ramas[pos].Ramas[0] = Raiz.Ramas[pos].Ramas[1];
        Raiz.Ramas[pos].Cuenta--;
        i = 1;
        while (i != Raiz.Ramas[pos].Cuenta + 1) {
            Raiz.Ramas[pos].Nodos[i - 1] = Raiz.Ramas[pos].Nodos[i];
            Raiz.Ramas[pos].Ramas[i] = Raiz.Ramas[pos].Ramas[i + 1];
            i++;
        }
    }

    public void Quitar(Pagina Raiz, int pos) {
        int j = pos + 1;
        while (j != Raiz.Cuenta + 1) {
            Raiz.Nodos[j - 2] = Raiz.Nodos[j - 1];
            Raiz.Ramas[j - 1] = Raiz.Ramas[j];
            j++;
        }
        Raiz.Cuenta--;
    }

    public int BuscarNodo(NodoB clave, Pagina Raiz) {
        int j = 0;
        if (clave.Carnet < Raiz.Nodos[0].Carnet) {
            this.Esta = false;
            j = 0;
        } else {
            j = Raiz.Cuenta;
            while (clave.Carnet < Raiz.Nodos[j - 1].Carnet && j > 1) {
                --j;
            }
            this.Esta = (clave.Carnet == Raiz.Nodos[j - 1].Carnet);
        }
        return j;
    }

    public void Restablecer(Pagina Raiz, int pos) {
        if (pos > 0) {
            if (Raiz.Ramas[pos - 1].Cuenta > 2) {
                MoverDer(Raiz, pos);
            } else {
                Combina(Raiz, pos);
            }
        } else if (Raiz.Ramas[1].Cuenta > 2) {
            MoverIzq(Raiz, 1);
        } else {
            Combina(Raiz, 1);
        }
    }

    //INICIA LAS OPERACIONES DE ELIMINAR
    public boolean EliminarNodo(NodoB clave) {
        boolean Resp = false;
        if (Vacio(this.P)) {
            //NO ELIMINA
        } else {
            Resp = EliminarPrivad(this.P, clave);
        }
        return Resp;
    }

    //ELIMINAR PRIVADO
    public boolean EliminarPrivad(Pagina Raiz, NodoB clave) {
        try {
            ElimarRegistro(Raiz, clave);
        } catch (Exception E) {
            this.Esta = false;
        }
        if (!Esta) {
            return false;
        } else {
            if (Raiz.Cuenta == 0) {
                Raiz = Raiz.Ramas[0];
            }
            this.P = Raiz;
            return true;
        }
    }

    //ELIMINA EL REGISTRO
    public void ElimarRegistro(Pagina Raiz, NodoB clave) {
        int pos = 0;
        if (Vacio(Raiz)) {
            this.Esta = false;
        } else {
            pos = BuscarNodo(clave, Raiz);
            if (Esta) {
                if (Vacio(Raiz.Ramas[pos - 1])) {
                    Quitar(Raiz, pos);
                } else {
                    Sucesor(Raiz, pos);
                    ElimarRegistro(Raiz.Ramas[pos], Raiz.Nodos[pos - 1]);
                }
            } else {
                ElimarRegistro(Raiz.Ramas[pos], clave);
                if ((Raiz.Ramas[pos] != null) && (Raiz.Ramas[pos].Cuenta < 2)) {
                    Restablecer(Raiz, pos);
                }
            }
        }
    }

    public String GraficarArbol(Pagina Raiz, String Cuerpo) {
        String Grafo = "";


        if (!Raiz.PrintPage().equals("")) {
            Grafo += "Pagina" + this.Contador + "[shape = record, label=\"" + Raiz.PrintPage() + "\"];\n";
            int RamPag = 0;
            for(int i=0;i<5;i++){
                if(Raiz.Ramas[i]!=null){
                    RamPag++;
                }
            }
            
            this.Contador++;
            for (int i = 0; i <= Raiz.Cuenta; i++) {
                if (Raiz.Ramas[i] != null) {
                    if (!Raiz.Ramas[i].PrintPage().equals("")) {
                        Grafo += GraficarArbol(Raiz.Ramas[i],"");
                    }
                }
            }
        }
        return Grafo;
    }

    public boolean EditarNodo(int Carnet, NodoB Nuevo) {
        this.Editado = false;
        if (this.P.EditarNodo(Carnet, Nuevo)) {
            return true;
        }
        EditarNodoPrivado(Carnet, Nuevo, this.P);
        return this.Editado;
    }

    private void EditarNodoPrivado(int codigo, NodoB Nuevo, Pagina Raiz) {
        if (Raiz.Cuenta > 0 && Raiz.Ramas[0] != null) {
            for (int i = 0; i <= Raiz.Cuenta; i++) {
                if (Raiz.Ramas[i] != null) {
                    if (Raiz.Ramas[i].EditarNodo(codigo, Nuevo)) {
                        this.Editado = true;
                        return;
                    } else {
                        EditarNodoPrivado(codigo, Nuevo, Raiz.Ramas[i]);
                    }
                }
            }
        }
    }

    public NodoB BuscarEstu(int Carnet) {
        this.Buscado = null;
        for (int i = 0; i <= this.P.Cuenta; i++) {
            if (this.P.Nodos[i] != null) {
                if (this.P.Nodos[i].Carnet == Carnet) {
                    this.Buscado = this.P.Nodos[i];
                    return this.Buscado;
                }
            }
        }
        BuscarPrivado(Carnet, this.P);
        return this.Buscado;
    }

    void BuscarPrivado(int Carnet, Pagina Raiz) {
        if (Raiz.Cuenta > 0 && Raiz.Ramas[0] != null) {
            for (int i = 0; i <= Raiz.Cuenta; i++) {
                if (Raiz.Ramas[i] != null) {
                    NodoB Aux = Raiz.Ramas[i].EncuentraNodo(Carnet);
                    if (Aux != null) {
                        this.Buscado = Aux;
                    } else {
                        BuscarPrivado(Carnet, Raiz.Ramas[i]);
                    }
                }
            }
        }
    }
}
