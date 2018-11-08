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
public class NodoCorto {

    String Nombre;
    String Tutor_Academico;
    String Room;
    boolean Habilitado;
    ListaPreguntas PreguntasCorto;
    ListaRespuestas RespuestasCorto;
    NodoCorto Anterior;
    NodoCorto Siguiente;

    public NodoCorto(String nombre, String tutor_Academico, String room) {
        super();
        Nombre = nombre;
        Tutor_Academico = tutor_Academico;
        Room = room;
        Habilitado = false;
        PreguntasCorto = new ListaPreguntas();
        RespuestasCorto = new ListaRespuestas();
        Anterior = null;
        Siguiente = null;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public NodoCorto getAnterior() {
        return Anterior;
    }

    public void setAnterior(NodoCorto anterior) {
        Anterior = anterior;
    }

    public String getTutor_Academico() {
        return Tutor_Academico;
    }

    public void setTutor_Academico(String Tutor_Academico) {
        this.Tutor_Academico = Tutor_Academico;
    }

    public NodoCorto getSiguiente() {
        return Siguiente;
    }

    public void setSiguiente(NodoCorto Siguiente) {
        this.Siguiente = Siguiente;
    }

    public ListaPreguntas getPreguntasCorto() {
        return PreguntasCorto;
    }

    public void setPreguntasCorto(ListaPreguntas PreguntasCorto) {
        this.PreguntasCorto = PreguntasCorto;
    }

    public ListaRespuestas getRespuestasCorto() {
        return RespuestasCorto;
    }

    public void setRespuestasCorto(ListaRespuestas RespuestasCorto) {
        this.RespuestasCorto = RespuestasCorto;
    }

}
