/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Scanner;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author fcarv
 */
@WebService(serviceName = "BackEnd", targetNamespace= "Proyecto2")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class BackEnd {

    ListaCortos Cortos = new ListaCortos();
    ArbolTutores Auxiliares = new ArbolTutores();
    ArbolB Estudiantes = new ArbolB();
    GrafoPensum Pensum = new GrafoPensum();
    boolean h = false;
    int UAc = 0;

    /**
     * Web service operation
     *
     * @param Carnet
     */
    @WebMethod(operationName = "SetUAc")
    public void SetUAc(@WebParam(name = "Carnet") int Carnet) {
        UAc = Carnet;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "GetUAc")
    public int GetUAc() {
        return UAc;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @param DPI
     * @param Password
     * @param Token
     * @param Nombre
     * @param Apellido
     * @param Creditos
     * @param Correo
     * @return
     */
    @WebMethod(operationName = "Insertar_Estudiante")
    public boolean Insertar_Estudiante(@WebParam(name = "Carnet") int Carnet, @WebParam(name = "DPI") String DPI, @WebParam(name = "Password") String Password, @WebParam(name = "Token") String Token, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Apellido") String Apellido, @WebParam(name = "Creditos") int Creditos, @WebParam(name = "Correo") String Correo) {
        return Estudiantes.InsertarNB(new NodoB(Carnet, DPI, Password, Token, Nombre, Apellido, Creditos, Correo));
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Eliminar_Estudiante")
    public boolean Eliminar_Estudiante(@WebParam(name = "Carnet") int Carnet) {
        NodoB Envio = Estudiantes.BuscarEstu(Carnet);
        boolean R = false;
        if (Envio != null) {
            R = Estudiantes.EliminarNodo(Envio);
        }
        return R;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Buscar_Estudiante")
    public String Buscar_Estudiante(@WebParam(name = "Carnet") int Carnet) {
        String Resp = "El estudiante no existe";
        NodoB R = Estudiantes.BuscarEstu(Carnet);
        if (R != null) {
            Resp = R.Carnet + "/" + R.DPI + "/" + R.Password + "/" + R.Nombre + "/" + R.Apellido + "/" + R.Credito + "/" + R.Correo;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Ver_Creditos")
    public int Ver_Creditos(@WebParam(name = "Carnet") int Carnet) {
        int Resp = -1;
        NodoB R = Estudiantes.BuscarEstu(Carnet);
        if (R != null) {
            Resp = R.Credito;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Ver_Pass")
    public String Ver_Pass(@WebParam(name = "Carnet") int Carnet) {
        String Resp = "";
        NodoB R = Estudiantes.BuscarEstu(Carnet);
        if (R != null) {
            Resp = R.Password;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Ver_Nom")
    public String Ver_Nom(@WebParam(name = "Carnet") int Carnet) {
        String Resp = "";
        NodoB R = Estudiantes.BuscarEstu(Carnet);
        if (R != null) {
            Resp = R.Nombre;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Ver_Corr")
    public String Ver_Corr(@WebParam(name = "Carnet") int Carnet) {
        String Resp = "";
        NodoB R = Estudiantes.BuscarEstu(Carnet);
        if (R != null) {
            Resp = R.Correo;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "Graficar_Arbol_Estudiantes")
    public String Graficar_Arbol_Estudiantes() {
        return "digraph ArbolB{\n" + Estudiantes.GraficarArbol(Estudiantes.P, "") + "}";
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @param Codigo
     * @param Descripcion
     * @param Nota
     * @param Fecha
     * @return
     */
    @WebMethod(operationName = "Insertar_Aprobado")
    public boolean Insertar_Aprobado(@WebParam(name = "Carnet") int Carnet, @WebParam(name = "Codigo") String Codigo, @WebParam(name = "Descripcion") String Descripcion, @WebParam(name = "Nota") String Nota, @WebParam(name = "Fecha") String Fecha) {
        NodoB AX = Estudiantes.BuscarEstu(Carnet);
        boolean Resp = false;
        if (AX != null) {
            Resp = AX.Cursos.InsertarAprobado(Codigo, Descripcion, Nota, Fecha);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Graficar_Aprobados")
    public String Graficar_Aprobados(@WebParam(name = "Carnet") int Carnet) {
        NodoB AX = Estudiantes.BuscarEstu(Carnet);
        String Cuerpo = "digraph Aprobados{\n";
        if (AX != null) {
            Cuerpo += AX.Cursos.GApro(Integer.toString(AX.Carnet));
        }
        Cuerpo += "}";
        return Cuerpo;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Texto_Aprobados")
    public String Texto_Aprobados(@WebParam(name = "Carnet") int Carnet) {
        NodoB AX = Estudiantes.BuscarEstu(Carnet);
        String Cuerpo = "";
        if (AX != null) {
            Cuerpo += AX.Cursos.TxtApro();
        }
        return Cuerpo;
    }

    /**
     * Web service operation
     *
     * @param Codigo
     * @param Nombre
     * @param Creditos
     * @param Area
     * @return
     */
    @WebMethod(operationName = "Insertar_Pensum")
    public boolean Insertar_Pensum(@WebParam(name = "Codigo") String Codigo, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Creditos") String Creditos, @WebParam(name = "Area") String Area) {
        return Pensum.InsertarPensum(Nombre, Codigo, Creditos, Area);
    }

    /**
     * Web service operation
     *
     * @param Codigo
     * @return
     */
    @WebMethod(operationName = "Eliminar_Pensum")
    public boolean Eliminar_Pensum(@WebParam(name = "Codigo") String Codigo) {
        return Pensum.EliminarPensum(Codigo);
    }

    /**
     * Web service operation
     *
     * @param Codigo
     * @return
     */
    @WebMethod(operationName = "Visualizar_Pensum")
    public String Visualizar_Pensum(@WebParam(name = "Codigo") String Codigo) {
        String Resp = "No Esxiste";
        NodoPensum R = Pensum.VisualizarPensum(Codigo);
        if (R != null) {
            Resp = R.Nombre + "/" + R.Codigo + "/" + R.Creditos + "/" + R.Area;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Codigo
     * @param CodigoPre
     * @return
     */
    @WebMethod(operationName = "Insertar_Prerequisito")
    public boolean Insertar_Prerequisito(@WebParam(name = "Codigo") String Codigo, @WebParam(name = "CodigoPre") String CodigoPre) {
        boolean Resp = false;
        NodoPensum B = Pensum.VisualizarPensum(Codigo);
        if (B != null) {
            B.Prerequisitos.InsertarPre(CodigoPre);
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "Graficar_Pensum")
    public String Graficar_Pensum() {
        return "digraph Pensum{\n" + Pensum.GraficarPensum() + "}\n";
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "Matriz_Adyacencia")
    public String Matriz_Adyacencia() {
        return Pensum.MAdyacencia();
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @param Curso
     * @param Periodo
     * @param Pass
     * @return
     */
    @WebMethod(operationName = "Insertar_Tutor")
    public boolean Insertar_Tutor(@WebParam(name = "Carnet") int Carnet, @WebParam(name = "Curso") String Curso, @WebParam(name = "Periodo") String Periodo, @WebParam(name = "Pass") String Pass) {
        boolean Resp = false;
        NodoTutores Nuevo = new NodoTutores(Carnet, Curso, Periodo, Pass);
        NodoTutores Aux;
        Aux = Auxiliares.InsertarTutor(Auxiliares.Raiz, Nuevo, false);
        if (Aux != null) {
            Auxiliares.Raiz = Aux;
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Eliminar_Tutor")
    public boolean Eliminar_Tutor(@WebParam(name = "Carnet") int Carnet) {
        boolean Resp = false;
        if (Auxiliares.Raiz != null) {
            NodoTutores Aux = Auxiliares.EliminarTutor(Auxiliares.Raiz, Carnet, false);
            Auxiliares.Raiz = Aux;
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Visualizar_Tutor")
    public String Visualizar_Tutor(@WebParam(name = "Carnet") int Carnet) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Carnet);
        String Resp = "No existe el tutor";
        if (Aux != null) {
            Resp = Aux.Carnet + "/" + Aux.Curso + "/" + Aux.Periodo;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(action = "LIAux",operationName = "Log_In")
    public String Log_In(@WebParam(name = "Carnet") int Carnet) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Carnet);
        String Resp = "No existe el tutor";
        if (Aux != null) {
            Resp = Aux.Carnet + Aux.Pass;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @return
     */
    @WebMethod(action= "LIEstudiantes", operationName = "Log_In_Estudiantes")
    public String Log_In_Estudiantes(@WebParam(name = "Carnet") int Carnet,@WebParam(name = "Password") String Password) {
        String Resp = "No existe el alumno";
        if(Estudiantes.P != null){
            NodoB Aux = Estudiantes.BuscarEstu(Carnet);
            if (Aux != null) {
                if(Aux.Carnet == Carnet && Aux.Password.equals(Password)){
                    Resp = Aux.Token;
                }
            }
        }
        return Resp;
    }
    
    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "Graficar_Tutores")
    public String Graficar_Tutores() {
        return "digraph Auxiliares{\n" + Auxiliares.GraficarTutores(Auxiliares.Raiz, "") + "}";
    }

    /**
     * Web service operation
     *
     * @param Carnet
     * @param Tutor
     * @return
     */
    @WebMethod(operationName = "Insertar_Fila")
    public boolean Insertar_Fila(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Carnet") String Carnet) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        boolean Resp = false;
        if (Aux != null) {
            Aux.NotasCurso.Filas.InsertaCabecera(Carnet, "", 0, "", "");
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Nombre
     * @param Tipo
     * @param Descripcion
     * @param Fecha
     * @param Ponderacion
     * @return
     */
    @WebMethod(operationName = "Insertar_Columna")
    public boolean Insertar_Columna(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Tipo") String Tipo, @WebParam(name = "Descripcion") String Descripcion, @WebParam(name = "Fecha") String Fecha, @WebParam(name = "Ponderacion") double Ponderacion) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        boolean Resp = false;
        if (Aux != null) {
            Aux.NotasCurso.Columnas.InsertaCabecera(Nombre, Descripcion, Ponderacion, Fecha, Tipo);
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @return
     */
    @WebMethod(operationName = "GraficarNotas")
    public String GraficarNotas(@WebParam(name = "Tutor") int Tutor) {
        String Grafo = "digraph Matriz{\n";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Grafo += Aux.NotasCurso.GraficarMatriz();
        }
        Grafo += "}";
        return Grafo;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Fila
     * @param Columna
     * @param Nota
     * @return
     */
    @WebMethod(operationName = "Insertar_Nota")
    public boolean Insertar_Nota(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Fila") int Fila, @WebParam(name = "Columna") int Columna, @WebParam(name = "Nota") double Nota) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        boolean Resp = false;
        if (Aux != null) {
            Aux.NotasCurso.Insertar(Fila, Columna, Nota);
            Resp = true;
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Index
     * @return
     */
    @WebMethod(operationName = "Ver_Actividad")
    public String Ver_Actividad(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Index") int Index) {
        String Resp = "No existe";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            NodoCabecera AX = Aux.NotasCurso.Columnas.GetCabecera(Index);
            if (AX != null) {
                Resp = AX.Nombre + "/" + AX.Descripcion + "/" + AX.Ponderacion + "/" + AX.Fecha_Entrega + "/" + AX.Tipo;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Nombre
     * @return
     */
    @WebMethod(operationName = "Ver_Index_Columna")
    public int Ver_Index_Columnas(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Nombre") String Nombre) {
        int Resp = -1;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Resp = Aux.NotasCurso.Columnas.GetIndex(Nombre);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Nombre
     * @return
     */
    @WebMethod(operationName = "Ver_Index_Fila")
    public int Ver_Index_Fila(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Nombre") String Nombre) {
        int Resp = -1;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Resp = Aux.NotasCurso.Filas.GetIndex(Nombre);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Fila
     * @param Columna
     * @return
     */
    @WebMethod(operationName = "Ver_Nota")
    public String Ver_Nota(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Fila") int Fila, @WebParam(name = "Columna") int Columna) {
        String Resp = "No existe";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            NodoNota AX = Aux.NotasCurso.VisualizarNota(Fila, Columna);
            if (AX != null) {
                Resp = String.valueOf(AX.Nota);
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Actividad
     * @return
     */
    @WebMethod(operationName = "Ver_Estadisticas")
    public String Ver_Estadisticas(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Actividad") String Actividad) {
        String Resp = "No existe";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Resp = Aux.NotasCurso.EstadisticasMatriz(Actividad);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Fila
     * @param Columna
     * @return
     */
    @WebMethod(operationName = "Eliminar_Nota")
    public boolean Eliminar_Nota(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Fila") int Fila, @WebParam(name = "Columna") int Columna) {
        boolean Resp = false;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            boolean AX = Aux.NotasCurso.EliminarNota(Fila, Columna);
            if (AX) {
                Resp = true;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Columna
     * @return
     */
    @WebMethod(operationName = "Eliminar_Actividad")
    public boolean Eliminar_Actividad(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Columna") int Columna) {
        boolean Resp = false;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            boolean AX = Aux.NotasCurso.EliminarActividad(Columna);
            if (AX) {
                Resp = true;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Carnet
     * @param Nombre
     * @param Email
     * @return
     */
    @WebMethod(operationName = "Inserta_Estudiante_Lista")
    public boolean Inserta_Estudiante_Lista(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Carnet") String Carnet, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Email") String Email) {
        boolean Resp = false;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            boolean R = Aux.Listado.PlegarHash(Aux.Listado, Carnet, Nombre, Email);
            if (R) {
                Resp = true;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @return
     */
    @WebMethod(operationName = "Graficar_Lista")
    public String Graficar_Lista(@WebParam(name = "Tutor") int Tutor) {
        String Resp = "digraph Hash{\n";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Resp += Aux.Listado.GraficarHash(Aux.Listado);
        }
        Resp += "}";
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Eliminar_Posicion")
    public boolean Eliminar_Posicion(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Carnet") String Carnet) {
        boolean Resp = false;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            Resp = Aux.Listado.EliminarPosicion(Carnet);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Carnet
     * @return
     */
    @WebMethod(operationName = "Buscar_Estudiante_Lista")
    public String Buscar_Estudiante_Lista(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Carnet") String Carnet) {
        String Resp = "El alumno no esta asignado en el curso";
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            NodoListaHash R = Aux.Listado.BuscarPosicion(Carnet);
            if (R != null) {
                Resp = R.Nombre + "/" + R.Carnet + "/" + R.Email;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Tutor
     * @param Carnet
     * @param Nombre
     * @param Email
     * @return
     */
    @WebMethod(operationName = "Editar_Estudiante_Lista")
    public boolean Editar_Estudiante_Lista(@WebParam(name = "Tutor") int Tutor, @WebParam(name = "Carnet") String Carnet, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Email") String Email) {
        boolean Resp = false;
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Tutor);
        if (Aux != null) {
            NodoListaHash R = Aux.Listado.BuscarPosicion(Carnet);
            if (R != null) {
                R.Nombre = Nombre;
                R.Email = Email;
                Resp = true;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Nombre
     * @param Room
     * @param Tutor_Academico
     * @return
     */
    @WebMethod(operationName = "Insertar_Corto")
    public boolean Insertar_Corto(@WebParam(name = "Nombre") String Nombre, @WebParam(name = "Room") String Room, @WebParam(name = "Tutor_Academico") String Tutor_Academico) {
        return Cortos.InsertarCorto(Nombre, Tutor_Academico, Room);
    }

    /**
     * Web service operation
     *
     * @param ValEl
     * @return
     */
    @WebMethod(operationName = "Eliminar_Corto")
    public boolean Eliminar_Corto(@WebParam(name = "ValEl") String ValEl) {
        boolean Resp = false;
        NodoCorto Ax = Cortos.getInicio();
        if (Ax != null) {
            Resp = Cortos.EliminarCorto(ValEl);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param ValBus
     * @return
     */
    @WebMethod(operationName = "Visualizar_Corto")
    public String Visualizar_Corto(@WebParam(name = "ValBus") String ValBus) {
        NodoCorto Ax = Cortos.getInicio();
        String Resp = "No Existe";
        if (Ax != null) {
            Ax = Cortos.VisualizarCorto(ValBus);
            if (Ax != null) {

                Resp = Ax.Room + "/" + Ax.Nombre + "/" + Ax.Tutor_Academico;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param ValBus
     * @return
     */
    @WebMethod(operationName = "Habilitar_Deshabilitar_Corto")
    public boolean Habilitar_Deshabilitar_Corto(@WebParam(name = "Room") String Room) {
        NodoCorto Ax = Cortos.getInicio();
        boolean Resp = false;
        if (Ax != null) {
            Ax = Cortos.VisualizarCorto(Room);
            if (Ax != null) {
                Ax.Habilitado = !Ax.Habilitado;
                Resp = Ax.Habilitado;
            }
        }
        return Resp;
    }
    
    /**
     * Web service operation
     *
     * @param ValBus
     * @return
     */
    @WebMethod(operationName = "Ver_Estado_Corto")
    public String Ver_Estado_Corto(@WebParam(name = "Room") String Room) {
        NodoCorto Ax = Cortos.getInicio();
        String Resp = "DESHABILITADO";
        if (Ax != null) {
            Ax = Cortos.VisualizarCorto(Room);
            if (Ax != null) {
                if(Ax.Habilitado){
                    Resp = "HABILITADO";
                }
            }
        }
        return Resp;
    }
    
    /**
     * Web service operation
     *
     * @param Nombre
     * @param Room
     * @param Tutor_Academico
     * @return
     */
    @WebMethod(operationName = "Editar_Corto")
    public boolean Editar_Corto(@WebParam(name = "Nombre") String Nombre, @WebParam(name = "Room") String Room, @WebParam(name = "Tutor_Academico") String Tutor_Academico) {
        boolean Resp = false;
        NodoCorto Ax = Cortos.getInicio();
        if (Ax != null) {
            Ax = Cortos.VisualizarCorto(Room);
            if (Ax != null) {
                Ax.Nombre = Nombre;
                Ax.Tutor_Academico = Tutor_Academico;
                Resp = true;
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "Graficar_Cortos")
    public String Graficar_Cortos() {
        return "digraph Cortos{\n" + Cortos.GraficarCortos() + "}";
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Pregunta
     * @param Tipo
     * @return
     */
    @WebMethod(operationName = "Insertar_Pregunta")
    public boolean Insertar_Pregunta(@WebParam(name = "Room") String Room, @WebParam(name = "Pregunta") String Pregunta, @WebParam(name = "Tipo") int Tipo) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                Resp = AX.PreguntasCorto.InsertarPregunta(Pregunta, Tipo);
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @return
     */
    @WebMethod(operationName = "Eliminar_Pregunta")
    public boolean Eliminar_Pregunta(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                Resp = AX.PreguntasCorto.EliminarPregunta(Numero);
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @return
     */
    @WebMethod(operationName = "Visualizar_Pregunta")
    public String Visualizar_Pregunta(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero) {
        String Resp = "No hay nada en este corto.";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null) {
                    Resp = Res.Pregunta;
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Pregunta
     * @return
     */
    @WebMethod(operationName = "Visualizar_NumPregunta")
    public int Visualizar_NumPregunta(@WebParam(name = "Room") String Room, @WebParam(name = "Pregunta") String Pregunta) {
        int Resp = -1;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarNumero(Pregunta);
                if (Res != null) {
                    Resp = Res.Numero;
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @param Pregunta
     * @return
     */
    @WebMethod(operationName = "Editar_Pregunta")
    public boolean Editar_Pregunta(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero, @WebParam(name = "Pregunta") String Pregunta) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null) {
                    Resp = true;
                    Res.Pregunta = Pregunta;
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @return
     */
    @WebMethod(operationName = "Graficar_Preguntas")
    public String Graficar_Preguntas(@WebParam(name = "Room") String Room) {
        String Resp = "digraph Preguntas{\n";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta AAX = AX.PreguntasCorto.Inicio;
                if (AAX != null) {
                    Resp += AX.Room + " -> Pregunta" + AAX.Numero + ";\n";
                    Resp += AX.PreguntasCorto.GraficarPreguntas();
                }
            }
        }
        Resp += "}";
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Respuesta
     * @return
     */
    @WebMethod(operationName = "Insertar_Respuesta")
    public boolean Insertar_Respuesta(@WebParam(name = "Room") String Room, @WebParam(name = "Respuesta") String Respuesta) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                Resp = AX.RespuestasCorto.InsertarRespuesta(Respuesta);
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @return
     */
    @WebMethod(operationName = "Eliminar_Respuesta")
    public boolean Eliminar_Respuesta(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                Resp = AX.RespuestasCorto.EliminarRespuesta(Numero);
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @return
     */
    @WebMethod(operationName = "Visualizar_Respuesta")
    public String Visualizar_Respuesta(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero) {
        String Resp = "No hay nada en este corto.";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoRespuesta Res = AX.RespuestasCorto.VisualizarRespuesta(Numero);
                if (Res != null) {
                    Resp = Res.Numero + "/" + Res.Respuesta;
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @return
     */
    @WebMethod(operationName = "Graficar_Respuestas")
    public String Graficar_Respuestas(@WebParam(name = "Room") String Room) {
        String Resp = "digraph Respuestas{\n";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoRespuesta AAX = AX.RespuestasCorto.Inicio;
                if (AAX != null) {
                    Resp += AX.Room + " -> Respuesta" + AAX.Numero + ";\n";
                    Resp += AX.RespuestasCorto.GraficarRespuestas();
                }
            }
        }
        Resp += "}";
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @param Opcion
     * @return
     */
    @WebMethod(operationName = "Insertar_Opcion")
    public boolean Insertar_Opcion(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero, @WebParam(name = "Opcion") String Opcion) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null && Res.Tipo == 0) {
                    Resp = Res.Opciones.InsertarOpcion(Opcion);
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @param Opcion
     * @return
     */
    @WebMethod(operationName = "Insertar_OpcionVF")
    public boolean Insertar_OpcionVF(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero, @WebParam(name = "Opcion") String Opcion) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null) {
                    Resp = Res.Opciones.InsertarOpcion(Opcion);
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @param NumeroOpcion
     * @return
     */
    @WebMethod(operationName = "Eliminar_Opcion")
    public boolean Eliminar_Opcion(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero, @WebParam(name = "NumeroOpcion") int NumeroOpcion) {
        boolean Resp = false;
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null) {
                    Resp = Res.Opciones.EliminarOpcion(NumeroOpcion);
                }
            }
        }
        return Resp;

    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @param NumeroOpcion
     * @return
     */
    @WebMethod(operationName = "Visualizar_Opcion")
    public String Visualizar_Opcion(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero, @WebParam(name = "NumeroOpcion") int NumeroOpcion) {
        String Resp = "No se puede ver la opcion.";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta Res = AX.PreguntasCorto.VisualizarPregunta(Numero);
                if (Res != null) {
                    NodoOpcion Resultado = Res.Opciones.VisualizarOpcion(NumeroOpcion);
                    if (Resultado != null) {
                        Resp = Resultado.Opcion;
                    }
                }
            }
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Room
     * @param Numero
     * @return
     */
    @WebMethod(operationName = "Graficar_Operaciones")
    public String Graficar_Operaciones(@WebParam(name = "Room") String Room, @WebParam(name = "Numero") int Numero) {
        String Resp = "digraph Respuestas{\n";
        NodoCorto Aux = Cortos.Inicio;
        if (Aux != null) {
            NodoCorto AX = Cortos.VisualizarCorto(Room);
            if (AX != null) {
                NodoPregunta AAX = AX.PreguntasCorto.Inicio;
                if (AAX != null) {
                    NodoPregunta AAAX = AX.PreguntasCorto.VisualizarPregunta(Numero);
                    if (AAAX != null) {
                        NodoOpcion AAAAX = AAAX.Opciones.Inicio;
                        if (AAAAX != null) {
                            Resp += "Pregunta" + AAAX.Numero + " -> Opcion" + AAAAX.Numero + ";\n";
                            Resp += AAAX.Opciones.GraficarOpcions();
                        }
                    }
                }
            }
        }
        Resp += "}";
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Texto
     * @return
     */
    @WebMethod(operationName = "Encriptar_Token")
    public String Encriptar_Token(@WebParam(name = "Texto") String Texto) {
        CriptografiaAES AES = new CriptografiaAES("lv39eptlvuhaqqsr");
        String Resp = "";
        try {
            Resp = AES.Encriptar(Texto);
        } catch (Exception E) {
            Logger.getLogger(AES.getClass().getName()).log(Level.SEVERE, null, E);
        }
        return Resp;
    }

    /**
     * Web service operation
     *
     * @param Texto
     * @return
     */
    @WebMethod(operationName = "DEncriptar_Token",action = "DEAR")
    public String DEncriptar_Token( @WebParam(name = "Carnet") String Carnet) {
        String Path = "C:\\Users\\fcarv\\Desktop\\" + Carnet + ".txt";
        
        String TXTENC = "";
        try{
            Scanner SC = new Scanner(new FileReader(Path));
            
            while(SC.hasNext()){
                TXTENC+= SC.next();
            }
            
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CriptografiaAES AES = new CriptografiaAES("lv39eptlvuhaqqsr");
        String Resp = "";
        try {
            Resp = AES.DesEncriptar(TXTENC);
  
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\fcarv\\Desktop\\Des" + Carnet + ".txt"));
            writer.write(Resp);
     
            writer.close();
        } catch (Exception E) {
            Logger.getLogger(AES.getClass().getName()).log(Level.SEVERE, null, E);
        }
        return Resp;
    }
    
    /**
     * Web service operation
     *
     * @param Texto
     * @param Token
     * @return
     */
    @WebMethod(operationName = "Encriptar_Texto_Archivo")
    public String Encriptar_Texto_Archivo(@WebParam(name = "Texto") String Texto, @WebParam(name = "Token") String Token) {
        CriptografiaAES AES = new CriptografiaAES(Token);
        String Resp = "";
        try {
            Resp = AES.Encriptar(Texto);
        } catch (Exception E) {
            Logger.getLogger(AES.getClass().getName()).log(Level.SEVERE, null, E);
        }
        return Resp;
    }

    /**
     * Web service operation
     * @param Carnet 
     */
    @WebMethod(operationName = "AprobarNotas")
    public void AprobarNotas(@WebParam(name = "Carnet") int Carnet,@WebParam(name = "Codigo") String Codigo) {
        NodoTutores Aux = Auxiliares.VisualizarTutor(Auxiliares.Raiz, Carnet);
        if(Aux != null){
            NodoCabecera AX = Aux.NotasCurso.Filas.Inicio;
            while(AX != null){
                double NotaFinal = 0;
                NodoNota A2 = AX.Acceso;
                while(A2 != null){
                    NotaFinal = NotaFinal+A2.Nota;
                    A2 = A2.Derecha;
                }
                
                if(NotaFinal >= 61){
                    NodoListaHash A3 = Aux.Listado.BuscarPosicion(AX.Nombre);
                    NodoB A4 = Estudiantes.BuscarEstu(Integer.parseInt(AX.Nombre));
                    if(A3 != null && A4 != null){
                        boolean R = A4.Cursos.InsertarAprobado(Codigo, "Curso Aprobado Satisfactoriamente", String.valueOf(NotaFinal), "5/11/2018");
                    }
                }
                AX = AX.Siguiente;
            }
        }
    }
}
