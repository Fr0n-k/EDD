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
public class NodoB {

    int Cuenta;
    int Carnet;
    String DPI;
    String Password;
    String Token;
    String Nombre;
    String Apellido;
    int Credito;
    String Correo;
    ListaAprobados Cursos;
    public NodoB(int Carnets, String DPIS, String Passwords, String Tokens, String Nombres, String Apellidos, int Creditos, String Correos) {
        this.Cuenta = 0;
        this.Carnet = Carnets;
        this.DPI = DPIS;
        this.Password = Passwords;
        this.Token = Tokens;
        this.Nombre = Nombres;
        this.Apellido = Apellidos;
        this.Credito = Creditos;
        this.Correo = Correos;
        this.Cursos = new ListaAprobados();
    }

}
