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
public class Pagina {

    int Cuenta;
    Pagina[] Ramas;
    NodoB[] Nodos;

    public Pagina() {
        Ramas = new Pagina[6];
        Nodos = new NodoB[5];
        Cuenta = 0;
        for (int i = 0; i < 5; i++) {
            Nodos[i] = null;
        }
        for (int i = 0; i < 6; i++) {
            Ramas[i] = null;
        }
    }

    public boolean nodoLLeno(Pagina Actual) {
        return (Actual.Cuenta == 5 - 1);
    }

    public boolean nodoSemiVacio(Pagina Actual) {
        return (Actual.Cuenta < 5 / 2);
    }
    
    public NodoB EncuentraNodo(int Carnet)
    {
        for(int i = 0; i<this.Cuenta; i++)
        {
            if(Nodos[i]!=null)
            {
                if(Nodos[i].Carnet == Carnet)
                {
                    return Nodos[i];
                }
            }
        }
        return null;
    }

    public boolean EditarNodo(int Carnet, NodoB Nuevo)
    {
        for(int i = 0; i<this.Cuenta; i++)
        {
            if(Nodos[i]!=null)
            {
                if(Nodos[i].Carnet == Carnet)
                {
                    Nodos[i].Carnet = Nuevo.Carnet;
                    Nodos[i].DPI = Nuevo.DPI;
                    Nodos[i].Password = Nuevo.Password;
                    Nodos[i].Token = Nuevo.Token;
                    Nodos[i].Nombre = Nuevo.Nombre;
                    Nodos[i].Apellido = Nuevo.Apellido;
                    Nodos[i].Credito = Nuevo.Credito;
                    Nodos[i].Correo = Nuevo.Correo;
                    Nodos[i].Carnet = Nuevo.Carnet;
                    return true;
                }
            }
        }
        return false;
    }
    
    public String PrintPage()
    {
        String Cadena = "";
        for(int i = 0; i< this.Cuenta; i++)
        {
            if(Nodos[i]!=null)
            {
                Cadena += "[ Carnet: " + Nodos[i].Carnet + " Creditos: " + Nodos[i].Credito  + " ] ";
            }
        }
        return Cadena;
    }
}
