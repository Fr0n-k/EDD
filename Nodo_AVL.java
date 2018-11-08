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
public class NodoTutores {

    int Carnet;
    String Curso;
    String Periodo;
    String Pass;
    int fe;
    MatrizNotas NotasCurso;
    HashListaEstudiantes Listado;
    NodoTutores Izquierda;
    NodoTutores Derecha;

    public NodoTutores(int Carnet, String Curso, String Periodo, String Pass) {
        this.Carnet = Carnet;
        this.Curso = Curso;
        this.Periodo = Periodo;
        this.Pass = Pass;
        this.fe = 0;
        NotasCurso = new MatrizNotas();
        Listado = new HashListaEstudiantes(23);
        this.Izquierda = null;
        this.Derecha = null;
    }

}
