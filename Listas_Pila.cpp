#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <ctime>
#include <stdio.h>
#include <string.h>   // std::cout
#include <string>
#include <opencv2/highgui.hpp>
using namespace std;

typedef struct ListaDepartamentos;// doble enlazada ordenada
typedef struct NodoDepartamento; //doble enlazado

typedef struct ListaEmpleados;
typedef struct NodoEmpleado;

typedef struct PilaDocumentos;
typedef struct NodoDocumento;

typedef struct ListaClientes;
typedef struct NodoCliente;

//Departamentos
//Nodo Dep
struct NodoDepartamento
{
    int Numero_Departamento;//solo para departamentos
    string Nombre_Departamento;//solo para departamentos
    int Empleados_Libres;
    int Empleados_Ocupados;
    int Clientes_Atendiendo; //actual
    int Clientes_Atendidos_Departamento; //Terminados / solo para departamentos
    int Empleados_Disponibles;
    int Documentos_Procesados; //Solo para departamentos
    ListaEmpleados *Empleados_Departamento;
    NodoDepartamento *Siguiente;
    NodoDepartamento *Anterior;
};

NodoDepartamento *Nuevo_Departamento(int Numero_Departamento,string Nombre_Departamento,int Empleados_Libres, int Clientes_Atendiendo, int Clientes_Atendidos_Departamento, int Empleados_Disponibles, int Documentos_Procesados, ListaEmpleados* Empleados_Departamento)
{
    NodoDepartamento* Nuevo = (NodoDepartamento*) malloc(sizeof(NodoDepartamento));

    Nuevo->Numero_Departamento = Numero_Departamento;
    Nuevo->Nombre_Departamento = Nombre_Departamento;
    Nuevo->Empleados_Libres = Empleados_Libres;
    Nuevo->Clientes_Atendiendo = Clientes_Atendiendo;
    Nuevo->Clientes_Atendidos_Departamento = Clientes_Atendidos_Departamento;
    Nuevo->Empleados_Disponibles = Empleados_Disponibles;
    Nuevo->Documentos_Procesados = Documentos_Procesados;
    Nuevo->Empleados_Departamento = Empleados_Departamento;
    Nuevo->Siguiente = NULL;
    Nuevo->Anterior = NULL;

    return Nuevo;
};
//Lista Deps
struct ListaDepartamentos
{
    NodoDepartamento* Inicio;
    NodoDepartamento* Fin;
};

void IniciarDepartamenttos(ListaDepartamentos* Deps)
{
    Deps->Inicio = NULL;
    Deps->Fin = NULL;
}
//Crear Departamento
void Insertar_Departamento(ListaDepartamentos* Departamentos,NodoDepartamento* Nuevo)
{
    bool Cambio = true;
    if(Departamentos->Inicio == NULL && Departamentos->Fin == NULL)
    {
        Departamentos->Inicio = Nuevo;
        Departamentos->Fin = Nuevo;
        cout << "Se creo el departamento " << Nuevo->Nombre_Departamento << endl;
    }
    else
    {
        NodoDepartamento* aux = Departamentos->Inicio;
        while(aux!=NULL)
        {
            if(Nuevo->Numero_Departamento < aux->Numero_Departamento)
            {
                 if(aux == Departamentos->Inicio)
                {
                    Nuevo->Siguiente = Departamentos->Inicio;
                    Departamentos->Inicio->Anterior = Nuevo;
                    Departamentos->Inicio = Nuevo;
                }
                else
                {
                    Nuevo->Siguiente = aux;
                    Nuevo->Anterior = aux->Anterior;
                    aux->Anterior->Siguiente = Nuevo;
                    aux->Anterior = Nuevo;
                }

                cout << "Se creo el departamento " << Nuevo->Nombre_Departamento << endl;
                Cambio = false;
                break;
            }
            else if(Nuevo->Numero_Departamento == aux->Numero_Departamento)
            {
                cout << "YA EXISTE UN DEPARTAMENTO CON ESE CODIGO." << endl;
                Cambio = false;
                break;
            }
            aux = aux->Siguiente;
        }
        if(Cambio)
        {
            Departamentos->Fin->Siguiente = Nuevo;
            Nuevo->Anterior = Departamentos->Fin;
            Departamentos->Fin = Nuevo;
            cout << "Se creo el departamento " << Nuevo->Nombre_Departamento << endl;
        }
    }
}
//Eliminar un departamento
void Eliminar_Departamento(ListaDepartamentos* Departamentos, int ValEl)
{
    bool Cambio_Lista = true;
    if(Departamentos->Inicio != NULL && Departamentos->Fin!=NULL)
    {
        NodoDepartamento* aux = Departamentos->Inicio;
        while(aux != NULL)
        {
            if(aux->Numero_Departamento == ValEl)
            {
                if(aux == Departamentos->Inicio)
                {
                    if(aux == Departamentos->Fin)
                    {
                        Departamentos->Inicio = NULL;
                        Departamentos->Fin = NULL;
                    }
                    else
                    {
                        Departamentos->Inicio = aux->Siguiente;
                        aux->Siguiente->Anterior = NULL;
                    }
                }
                else if(aux == Departamentos->Fin)
                {
                    Departamentos->Fin = aux->Anterior;
                    aux->Anterior->Siguiente = NULL;
                }
                else
                {
                    aux->Siguiente->Anterior = aux->Anterior;
                    aux->Anterior->Siguiente = aux->Siguiente;
                }
                cout << "Se elimino el departamento " << aux->Nombre_Departamento << endl;
               // free(aux);
                Cambio_Lista = false;
                break;
            }
            aux = aux->Siguiente;
        }

        if(Cambio_Lista)
        {
            cout << "EL DEPARTAMENTO NO EXISTE" << endl;
        }
    }
}



//Empleados
//Nodo Empleado
struct NodoEmpleado
{
    int Numero_Empleado; // solo para empleados
    string Nombre_Empleado; //solo para empleados;
    int DPI;
    bool Estado; //True = Libre; False = Ocupado
    NodoCliente* Cliente_Actual; //Cliente que se esta atendiendo
    string Departamento_Asignado;
    int Total_Clientes_Atendidos; //solo para empleados
    int Total_Documentos_Procesados; //solo para empleados
    PilaDocumentos *Documentos;
    NodoEmpleado* Siguiente;
};

NodoEmpleado *Nuevo_Empleado(int Numero_Empleado,string Nombre_Empleado,int DPI,bool Estado,string Departamento_Asignado,NodoCliente* Cliente_Actual, int Total_Clientes_Atendidos, int Total_Documentos_Procesados, PilaDocumentos* Documentos){

    NodoEmpleado* Nuevo = (NodoEmpleado*) malloc(sizeof(NodoEmpleado));

    Nuevo->Numero_Empleado = Numero_Empleado;
    Nuevo->Nombre_Empleado = Nombre_Empleado;
    Nuevo->DPI = DPI;
    Nuevo->Estado = Estado;
    Nuevo->Departamento_Asignado = Departamento_Asignado;
    Nuevo->Cliente_Actual = Cliente_Actual;
    Nuevo->Total_Clientes_Atendidos = Total_Clientes_Atendidos;
    Nuevo->Total_Documentos_Procesados = Total_Documentos_Procesados;
    Nuevo->Documentos = Documentos;
    Nuevo->Siguiente = NULL;
    return Nuevo;

};

//Cola Empleados
struct ListaEmpleados
{
    NodoEmpleado* Inicio;
    NodoEmpleado* Fin;
};

void IniciarEmpleados(ListaEmpleados* Emps)
{
    Emps->Inicio = NULL;
    Emps->Fin = NULL;
}

void Insertar_Empleado(ListaEmpleados* Emps, NodoEmpleado* Nuevo) //Push Cola
{
    bool ex = true;
    if(Emps->Inicio == NULL && Emps->Fin == NULL)
    {
        Emps->Inicio = Nuevo;
        Emps->Fin = Nuevo;
        cout << "Se creo el empleado " << Nuevo->Nombre_Empleado << " con el codigo " << Nuevo->Numero_Empleado << " en el departamento " << Nuevo->Departamento_Asignado << endl;
    }
    else
    {
        NodoEmpleado* aux = Emps->Inicio;
        while(aux != NULL)
        {
            if(aux->Numero_Empleado == Nuevo->Numero_Empleado)
            {
                ex = false;
                cout << "YA EXISTE UN EMPLEADO CON ESTE CODIGO EN ESTE DEPARTAMENTO" << endl;
                break;
            }
            aux = aux->Siguiente;
        }
        if(ex)
        {
            Emps->Fin->Siguiente = Nuevo;
            Emps->Fin = Nuevo;
            cout << "Se creo el empleado " << Nuevo->Nombre_Empleado  << " con el codigo " << Nuevo->Numero_Empleado << " en el departamento " << Nuevo->Departamento_Asignado << endl;
        }
    }
}

void Eliminar_Empleado(ListaEmpleados* Emps, int ValEL)
{
    int au;
    if(Emps->Inicio != NULL)
    {
        if(Emps->Inicio->Numero_Empleado == ValEL)
        {
            if(Emps->Inicio == Emps->Fin)
            {
                cout << "Se elimino el empleado: " << Emps->Inicio->Nombre_Empleado << ", del departamento: " << Emps->Inicio->Departamento_Asignado << endl;
                Emps->Inicio = NULL;
                Emps->Fin = NULL;
            }
            else
            {
                NodoEmpleado *aux2 = Emps->Inicio;
                Emps->Inicio = Emps->Inicio->Siguiente;
                cout << "Se elimino el empleado: " << aux2->Nombre_Empleado << ", del departamento: " << aux2->Departamento_Asignado << endl;
                //free(aux2);
            }
        }
        else
        {
            NodoEmpleado* aux = Emps->Inicio;
            NodoEmpleado* piv = Emps->Inicio->Siguiente;
            if(piv->Estado)
            {
                while(piv!=NULL)
                {
                    if(piv->Numero_Empleado == ValEL)
                    {
                        aux->Siguiente = piv->Siguiente;
                        if(piv == Emps->Fin)
                        {
                            Emps->Fin = aux;
                        }
                        cout << "Se elimino el empleado: " << piv->Nombre_Empleado << ", del departamento: " << piv->Departamento_Asignado << endl;
                        //free(piv);
                        break;
                    }
                    piv = piv->Siguiente;
                    aux = aux->Siguiente;
                }
            }
            else
            {
                cout << "El empleado que desea eliminar esta ocupado, desea eliminarlo de todos modos (1.-Si,2.-No)" << endl;
                cin >>au;
                if(au == 1)
                {
                    while(piv!=NULL)
                    {
                        if(piv->Numero_Empleado == ValEL)
                        {
                            aux->Siguiente = piv->Siguiente;
                            if(piv == Emps->Fin)
                            {
                                Emps->Fin = aux;
                            }
                            cout << "Se elimino el empleado: " << piv->Nombre_Empleado << ", del departamento: " << piv->Departamento_Asignado << endl;
                            free(piv);
                            break;
                        }
                        piv = piv->Siguiente;
                        aux = aux->Siguiente;
                    }
                }
            }
        }
    }
}

//Documentos
//Nodo Documentos
struct NodoDocumento
{
    int Numero_Documento; // solo para documentos
    int Prioridad; //10 mas grande/ 1 mas pequeña
    NodoDocumento* Siguiente;
};

NodoDocumento *Nuevo_Documento(int Numero_Documento,int Prioridad){

    NodoDocumento* Nuevo = (NodoDocumento*) malloc(sizeof(NodoDocumento));

    Nuevo->Numero_Documento = Numero_Documento;
    Nuevo->Prioridad = Prioridad;
    Nuevo->Siguiente = NULL;
    return Nuevo;

};

struct PilaDocumentos
{
    NodoDocumento* Tope;
};

void IniciarDocs(PilaDocumentos* Docs)
{
    Docs->Tope = NULL;
}

void Push(PilaDocumentos* Docs, NodoDocumento *DAc)
{
    if(Docs->Tope == NULL)
    {
        Docs->Tope = DAc;
        cout << "Se creo el documento con el numero " << DAc->Numero_Documento << " y la prioridad " << DAc->Prioridad << endl;
    }
    else
    {
        DAc->Siguiente = Docs->Tope;
        Docs->Tope = DAc;
        cout << "Se creo el documento con el numero " << DAc->Numero_Documento << " y la prioridad " << DAc->Prioridad << endl;
    }
}

void Pop_Prioridad(PilaDocumentos* Docs)
{
    if(Docs->Tope != NULL)
    {
        bool match = false;
        NodoDocumento* aux = Docs->Tope;
        NodoDocumento* piv = Docs->Tope->Siguiente;
        int i = 10;
        while(i > 0)
        {
            aux = Docs->Tope;
            piv = aux->Siguiente;

            if(aux->Prioridad == i)
            {
                Docs->Tope = Docs->Tope->Siguiente;
                cout << " el documento con id " << aux->Numero_Documento << " y prioridad " << aux->Prioridad << endl;
               // free(aux);
                match = true;
                i=0;
                break;
            }
            else
            {
                while(piv !=NULL)
                {
                    if(piv->Prioridad == i)
                    {
                        aux->Siguiente = piv->Siguiente;
                        cout << " el documento con id " << piv->Numero_Documento << " y la prioridad " << piv->Prioridad << endl;
                     //   free(piv);
                        match = true;
                        i=0;
                        break;
                    }
                    aux = aux->Siguiente;
                    piv = piv->Siguiente;
                }
            }
        i--;
        }
    }
}

//Clientes
struct NodoCliente
{
    int Numero_Cliente; //solo para clientes
    int NIT;
    std::tm* Hora_Entrada;
    std::tm* Hora_Salida;
    int Empleado_Atencion;
    const char* Departamento_Visitado;
    int Numero_Documentos;
    int Turnos_Espera;
    NodoCliente *Siguiente;
};

NodoCliente *Nuevo_Cliente(int Numero_Cliente,int NIT,std::tm* Hora_Entrada,std::tm* Hora_Salida,int Empleado_Atencion,const char* Departamento_Visitado,int Numero_Documentos,int Turnos_Espera){

    NodoCliente* Nuevo = (NodoCliente*) malloc(sizeof(NodoCliente));

    Nuevo->Numero_Cliente = Numero_Cliente;
    Nuevo->NIT = NIT;
    //Nuevo->Hora_Entrada = Hora_Entrada;
    //Nuevo->Hora_Salida = Hora_Salida;
    Nuevo->Empleado_Atencion = Empleado_Atencion;
    Nuevo->Departamento_Visitado = Departamento_Visitado;
    Nuevo->Numero_Documentos = Numero_Documentos;
    Nuevo->Turnos_Espera = Turnos_Espera;
    Nuevo->Siguiente = NULL;
    return Nuevo;
};

struct ListaClientes
{
    NodoCliente *Inicio;
    NodoCliente *Fin;
};

void IniciarClientes(ListaClientes* Cli)
{
    Cli->Inicio = NULL;
    Cli->Fin = NULL;
}

void Insertar_Cliente(ListaClientes* Cli, NodoCliente* Nuevo)
{
    bool Auto = true;
    if(Cli->Inicio == NULL && Cli->Fin == NULL)
    {
        Nuevo->Siguiente = Nuevo;
        Cli->Inicio = Nuevo;
        Cli->Fin = Nuevo;
        cout << "Se creo el cliente con el id " << Nuevo->Numero_Cliente << " y el NIT " << Nuevo->NIT << endl;//<< " entro al sistema a la hora " << (Nuevo->Hora_Entrada->tm_hour) <<":"<< (Nuevo->Hora_Entrada->tm_min) << endl;
    }
    else
    {
        NodoCliente* aux = Cli->Inicio;
        do
        {
            if(aux->Numero_Cliente == Nuevo->Numero_Cliente)
            {
                cout << "YA EXISTE UN CLIENTE CON ESTE NUMERO." << endl;
                Auto = false;
                break;
            }
            aux = aux->Siguiente;
        }while(aux->Siguiente != Cli->Inicio);

        if(Auto)
        {
            Cli->Fin->Siguiente = Nuevo;
            Cli->Fin = Nuevo;
            Nuevo->Siguiente = Cli->Inicio;
            cout << "El cliente con el id " << Nuevo->Numero_Cliente << " y el NIT " << Nuevo->NIT << endl;//<< " entro al sistema a la hora " <<endl;<< (Nuevo->Hora_Entrada->tm_hour) <<":"<< (Nuevo->Hora_Entrada->tm_min) << endl;
        }
    }
}

void Eliminar_Cliente(ListaClientes* Cli, int Idx)
{
    bool cambio = true;
    if(Cli->Inicio != NULL && Cli->Fin != NULL)
    {
        if(Cli->Inicio->Numero_Cliente == Idx)
        {
            if(Cli->Inicio == Cli->Fin)
            {
                cout << "Se elimino el cliente: " << Cli->Inicio->NIT << endl;
                Cli->Inicio = NULL;
                Cli->Fin = NULL;
            }
            else
            {
                NodoCliente* CEl = Cli->Inicio;
                Cli->Inicio = Cli->Inicio->Siguiente;
                Cli->Fin->Siguiente = Cli->Inicio;
                cout << "Se elimino el cliente: " << CEl->Numero_Cliente << endl;
               // free(CEl);
            }
        }
        else
        {
            NodoCliente* aux = Cli->Inicio;
            NodoCliente* piv = Cli->Inicio->Siguiente;
            do
            {
                if(piv->Numero_Cliente == Idx)
                {
                    if(piv == Cli->Fin)
                    {
                        Cli->Fin = aux;
                    }
                    aux->Siguiente = piv->Siguiente;
                    cout << "Se elimino el cliente: " << piv->NIT << endl;
                   // free(piv);
                    cambio = false;
                    break;
                }
                aux = aux->Siguiente;
                piv = piv->Siguiente;
            }while(piv != Cli->Inicio);

            if(cambio)
            {
                cout << "NO EXISTE UN CLIENTE CON ESE NUMERO." << endl;
            }
        }
    }
}

void Reporte_Departamentos (ListaDepartamentos* Lista_Actual)
{
    const int width = 500, height = 500;
    int i = 0;
    string com;
    com = (char) 34;
    string RepDep = "digraph RDep{\n";
    if(Lista_Actual->Inicio != NULL)
    {
        NodoDepartamento* aux = Lista_Actual->Inicio;
        while(aux != NULL)
        {
            string label = "\n";
            RepDep += "Dep" + std::to_string(i) + " [shape = record,label = "+com+ "ID: " + std::to_string(aux->Numero_Departamento) + "\\n" + aux->Nombre_Departamento + "\\nEmpleados Libres: " + std::to_string(aux->Empleados_Libres)+ "\\nEmpleados Ocupados: " + std::to_string(aux->Empleados_Ocupados) + "\\nClientes Atendiendo: "+std::to_string(aux->Clientes_Atendiendo) + "\\nClientes Atendidos: " +std::to_string(aux->Clientes_Atendidos_Departamento)+ "\\nDocumentos Procesados:" + std::to_string(aux->Documentos_Procesados) +com+"];\n";
            if(aux->Siguiente !=NULL)
            {
                RepDep += "Dep" + std::to_string(i) + "->Dep" + std::to_string(i+1) +";\n";
                RepDep += "Dep" + std::to_string(i+1) + "->Dep" + std::to_string(i)+";\n";
            }
            aux = aux->Siguiente;
            i++;
        }
        RepDep+="{rank = same;";
        while(i>0)
        {
            RepDep+="Dep" + std::to_string(i-1)+ ";";
            i--;
        }
        RepDep+="}\n";
        RepDep+="}";

        ofstream archivo;
        archivo.open("RepDeps.dot",ios::out);
        if(archivo.fail()){
            return;
        }
        archivo<<RepDep;
        archivo.close();

        string dot = "dot -Tjpg  RepDeps.dot -o RepDep.jpg";

        system(dot.c_str());
        cout<<"SE CREO EL REPORTE DE DEPARTAMENTOS" << endl;
        /*cv::Mat image;
        image = cv::imread("RepDep.jpg");

        if(! image.data ) {
            std::cout <<  "Could not open or find the image" << std::endl ;
        }
        cv::namedWindow( "Display window", cv::WINDOW_AUTOSIZE );
        cv::imshow( "Display window", image );
        cv::waitKey(0);*/
    }
}

void Reporte_Empleados(ListaDepartamentos* Departamentos_Actuales)
{
    string RepEmp = "digraph REmps{\n";
    string com;
    com = (char) 34;
    if(Departamentos_Actuales->Inicio !=NULL)
    {
        NodoDepartamento* aux = Departamentos_Actuales->Inicio;
        while(aux!=NULL)
        {
            int i=0;
            if(aux->Empleados_Departamento != NULL)
            {
                NodoEmpleado* aux2 = aux->Empleados_Departamento->Inicio;
                RepEmp +="subgraph cluster" + std::to_string(aux->Numero_Departamento) + "{\n";
                while(aux2!=NULL)
                {
                    RepEmp+="Emp" + std::to_string(i) +aux->Nombre_Departamento+ "[shape = record, label=" + com + "Numero Empleado: " + std::to_string(aux2->Numero_Empleado) + "\\nNombre Empleado: " + aux2->Nombre_Empleado + "\\nDPI: " + std::to_string(aux2->DPI) + "\\nClientes Atendidos: " +std::to_string(aux2->Total_Clientes_Atendidos)+ "\\nDocumentos Procesados:"+ std::to_string(aux2->Total_Documentos_Procesados) + com + "];\n";
                    if(aux2->Siguiente !=NULL)
                    {
                        RepEmp+= "Emp" + std::to_string(i) +aux->Nombre_Departamento+ "->Emp" + std::to_string(i+1) +aux->Nombre_Departamento+ ";\n";
                    }

                    if(aux2->Cliente_Actual != NULL)
                    {
                        NodoCliente* aux3 = aux2->Cliente_Actual;
                        RepEmp+="Cli" + std::to_string(i)+aux->Nombre_Departamento  + " [shape = record,label = "+com+ "No: " + std::to_string(aux3->Numero_Cliente) +"\\nNIT: " + std::to_string(aux3->NIT)+ "\\nEmpleado Que Atendio: " + std::to_string(aux3->Empleado_Atencion) + "\\nDepartamento Visitado: "+aux3->Departamento_Visitado +  "\\nNumero Documentos:" + std::to_string(aux3->Numero_Documentos) +com+"];\n";
                        RepEmp+="Emp" + std::to_string(i)+aux->Nombre_Departamento+ "-> Cli" + std::to_string(i)+aux->Nombre_Departamento+ ";\n";
                    }
                    i++;
                    aux2 = aux2->Siguiente;
                }
            RepEmp+="{rank = same;";
            while(i >0)
            {
                RepEmp+="Emp"+std::to_string(i-1)+aux->Nombre_Departamento + ";";
                i--;
            }
            RepEmp+="};\n";
            RepEmp+="label = " +com+ aux->Nombre_Departamento+ com + ";\n";
            RepEmp+="}\n";
            }
            aux = aux->Siguiente;
        }

        RepEmp+="}";
        ofstream archivo;
        archivo.open("RepEmps.dot",ios::out);
        if(archivo.fail()){
            return;
        }
        archivo<<RepEmp;
        archivo.close();

        string dot = "dot -Tjpg  RepEmps.dot -o RepEmp.jpg";

        system(dot.c_str());
        cout<<"SE CREO EL REPORTE DE EMPLEADOS" << endl;
    }
}

void Reporte_Documentos(PilaDocumentos* Documentos_Actuales,string dep, string emp)
{
    string RepDoc = "Digraph RDocs{\n";
    string com;
    com = (char) 34;
    if(Documentos_Actuales != NULL)
    {
        if(Documentos_Actuales->Tope != NULL)
        {
            int i =0;
            NodoDocumento* aux = Documentos_Actuales->Tope;
            RepDoc+="subgraph Pila{\n";
            while(aux !=NULL)
            {
                RepDoc+= "Doc" + std::to_string(i) + "[shape = record, label = " + com + "Numero: " + std::to_string(aux->Numero_Documento) + "\\nPrioridad: " + std::to_string(aux->Prioridad) +com+"];\n";
                if(aux->Siguiente !=NULL)
                {
                    RepDoc+= "Doc" + std::to_string(i)  + "->Doc" + std::to_string(i+1) + ";\n";
                }
                i++;
                aux = aux->Siguiente;
            }
            RepDoc+="{rank=same;";
            while(i>0)
            {
                RepDoc+="Doc"+std::to_string(i-1)+";";
                i--;
            }
            RepDoc+="}\n";
            RepDoc+="label ="+ com + emp + " " + dep+ com+ ";\n";
            RepDoc+="}\n";
            RepDoc+="}";

            ofstream archivo;
            archivo.open("RepDocs.dot",ios::out);
            if(archivo.fail()){
                return;
            }
            archivo<<RepDoc;
            archivo.close();

            string dot = "dot -Tjpg  RepDocs.dot -o RepDoc.jpg";

            system(dot.c_str());

            cout<<"SE CREO EL REPORTE DE DOCUMENTOS" << endl;

        }

    }
}

void Reporte_Clientes(ListaClientes* Clientes_Actuales)
{
    int i = 0;
    string com;
    com = (char) 34;
    string RepCli = "digraph RCli{\n";
    if(Clientes_Actuales->Inicio != NULL)
    {
        if(Clientes_Actuales->Inicio == Clientes_Actuales->Fin)
        {
            RepCli+="Cli" + std::to_string(i) + + " [shape = record,label = "+com+ "No: " + std::to_string(Clientes_Actuales->Inicio->Numero_Cliente) +"\\nNIT: " + std::to_string(Clientes_Actuales->Inicio->NIT)+ "\\nEmpleado Que Atendio: " + std::to_string(Clientes_Actuales->Inicio->Empleado_Atencion) + "\\nDepartamento Visitado: "+Clientes_Actuales->Inicio->Departamento_Visitado +  "\\nNumero Documentos:" + std::to_string(Clientes_Actuales->Inicio->Numero_Documentos) +com+"];\n";
            RepCli+= "Cli" + std::to_string(i)+ "-> Cli" + std::to_string(i);
        }
        else
        {
            NodoCliente* aux =Clientes_Actuales->Inicio;
            do
            {
                string label = "\n";
                RepCli += "Cli" + std::to_string(i) + + " [shape = record,label = "+com+ "No: " + std::to_string(aux->Numero_Cliente) +"\\nNIT: " + std::to_string(aux->NIT)+ "\\nEmpleado Que Atendio: " + std::to_string(aux->Empleado_Atencion) + "\\nDepartamento Visitado: "+aux->Departamento_Visitado +  "\\nNumero Documentos:" + std::to_string(aux->Numero_Documentos) +com+"];\n";
                if(aux == Clientes_Actuales->Fin)
                {
                    RepCli += "Cli" + std::to_string(i) + "->Cli0;\n";
                }
                else
                {
                    RepCli += "Cli" + std::to_string(i) + "->Cli" + std::to_string(i+1) +";\n";
                }
                aux = aux->Siguiente;
                i++;
            }while(aux != Clientes_Actuales->Inicio);
        }
        RepCli+="{rank = same;";
        while(i>0)
        {
            RepCli+="Cli" + std::to_string(i-1)+ ";";
            i--;
        }
        RepCli+="}\n";
        RepCli+="}";

        ofstream archivo;
        archivo.open("RepClis.dot",ios::out);
        if(archivo.fail()){
            return;
        }
        archivo<<RepCli;
        archivo.close();

        string dot = "dot -Tjpg  RepClis.dot -o RepCli.jpg";

        system(dot.c_str());
        cout<<"SE CREO EL REPORTE DE CLIENTES" << endl;
    }
}

int TotClientes = 0;
int TotEmpleados = 0;
int ClienteActual = 1;
int Totdep = 3;
int EmpAC = 1;
int Ope = 0;
bool ELOG = true;
int main()
{
    ListaDepartamentos *Departamentos_Sistema = (ListaDepartamentos*) malloc(sizeof(ListaDepartamentos));
    ListaClientes* Clientes_Sistema = (ListaClientes*) malloc(sizeof(ListaClientes));
    srand(time(NULL));

    const char* a = "Finanzas";
    Insertar_Departamento(Departamentos_Sistema,Nuevo_Departamento(1,a,0,0,0,0,0,NULL));
    Insertar_Departamento(Departamentos_Sistema,Nuevo_Departamento(2,"Compras",0,0,0,0,0,NULL));
    Insertar_Departamento(Departamentos_Sistema,Nuevo_Departamento(3,"Denuncias",0,0,0,0,0,NULL));

    cout << "DATOS INICIALES" << endl;
    NodoDepartamento* AxDep = Departamentos_Sistema->Inicio;
    int Loop = 0;
    do
    {
        EmpAC = 1;
        switch(Loop)
        {
            case 0:
                cout << "Ingrese el numero inicial de empleados en el departamento de Finanzas:" << endl;
                break;
            case 1:
                cout << "Ingrese el numero inicial de empleados en el departamento de Compras:" << endl;
                break;
            case 2:
                cout << "Ingrese el numero inicial de empleados en el departamento de Denuncias:" << endl;
                break;
        }
        cin >> TotEmpleados;
        AxDep->Empleados_Disponibles = TotEmpleados;
        AxDep->Empleados_Libres = TotEmpleados;
        for(int i = 0; i < TotEmpleados; i++)
        {
            if(AxDep->Empleados_Departamento == NULL)
            {
                AxDep->Empleados_Departamento = (ListaEmpleados*) malloc(sizeof(ListaEmpleados));
            }
            string nom = "Empleado" + std::to_string(EmpAC);
            const char* NuevoNom = nom.c_str();
            int DPIAC = rand() % 100000;
            Insertar_Empleado(AxDep->Empleados_Departamento, Nuevo_Empleado(EmpAC,nom,DPIAC,true,AxDep->Nombre_Departamento,NULL,0,0,NULL));;
            EmpAC++;
        }
        Loop++;
        AxDep = AxDep->Siguiente;
    }
    while(Loop != 3);

    do
    {
        cout << "MENU PRINCIPAL" << endl;
        cout << "Ingrese la opcion que desea:" << endl;
        cout << "1.- Acciones de departamentos." << endl;
        cout << "2.- Acciones de empleados." << endl;
        cout << "3.- Acciones de clientes." << endl;
        cout << "4.- Reportes." << endl;
        cout << "5.- Empezar turno." << endl;
        cout << "6.- Terminar simulacion." << endl;
        cin >> Ope;
        switch(Ope)
        {
            case 1:
                do
                {
                    string Nombre_Nuevo;
                    int Codigo_Nuevo;
                    int Codigo_Eliminacion = 0;
                    NodoDepartamento* aux = Departamentos_Sistema->Inicio;
                    cout << "MENU DEPARTAMENTOS" << endl;
                    cout << "1.- Crear nuevo departamento." << endl;
                    cout << "2.- Eliminar departamento existente." << endl;
                    cout << "3.- Regresar." << endl;
                    cin >> Ope;
                    switch(Ope)
                    {
                        case 1:
                                cout << "Ingrese el codigo del departamento." << endl;
                                cin  >> Codigo_Nuevo;
                                cout << "Ingrese el nombre del departamento." << endl;
                                cin  >> Nombre_Nuevo;
                                Insertar_Departamento(Departamentos_Sistema,Nuevo_Departamento(Codigo_Nuevo,Nombre_Nuevo,0,0,0,0,0,NULL));
                            break;
                       case 2:
                                cout << "Ingrese el codigo del departamento que desea eliminar: " << endl;
                                if(aux == NULL)
                                {
                                    cout << "No hay departamentos en la lista"<< endl;
                                }
                                else
                                {
                                    while(aux!=NULL)
                                    {
                                        cout << aux->Numero_Departamento << ".-"<< aux->Nombre_Departamento << endl;
                                        aux = aux->Siguiente;
                                    }
                                    cin >>Codigo_Eliminacion;
                                    Eliminar_Departamento(Departamentos_Sistema,Codigo_Eliminacion);
                                }
                            break;
                        case 3:
                                break;
                        default:
                            cout << "OPCION NO VALIDA, INGRESE DE NUEVO." << endl;
                            break;
                    }
                } while(Ope != 1 && Ope != 2 && Ope != 3);
                break;
            case 2:
                do
                {
                    NodoDepartamento *aux2 = Departamentos_Sistema->Inicio;
                    ListaEmpleados* EmpAux = NULL;
                    NodoEmpleado* AuxEmp = NULL;
                    int OPSE;
                    int NuNu;
                    int DPIAUX = rand() % 100000;
                    int dis = 0;
                    int CodEL;
                    string NuNom;
                    string NuNomD;
                    bool SelEX = true;
                    cout << "MENU EMPLEADOS" << endl;
                    cout << "1.-Agregar empleado (solo 1)." << endl;
                    cout << "2.-Agreagar empleado (mas de 1)." << endl;
                    cout << "3.-Eliminar empleado." << endl;
                    cout << "4.-Regresar." << endl;
                    cin >> Ope;

                    cout << "Seleccione el departamento en el cual se realizara la operacion" << endl;
                    while(aux2!=NULL)
                    {
                        cout << aux2->Numero_Departamento << ".-"<< aux2->Nombre_Departamento << endl;
                        aux2 = aux2->Siguiente;
                    }
                    cin >> OPSE;
                    aux2 = Departamentos_Sistema->Inicio;

                    while(aux2 != NULL)
                    {
                        if(aux2->Numero_Departamento == OPSE)
                        {
                            cout << "SE SELECCIONO EL DEPARTAMENTO: " << aux2->Nombre_Departamento <<endl;
                            SelEX = false;
                            break;
                        }
                        aux2 = aux2->Siguiente;
                    }

                    if(SelEX)
                    {
                        cout << "NO SE SELECCIONO NINGUN DEPARTAMENTO, SELECCIONE UNO PARA REALIZAR LA OPERACION." << endl;
                        Ope = 4;
                    }
                    else
                    {
                        if(aux2->Empleados_Departamento == NULL)
                        {
                            aux2->Empleados_Departamento = (ListaEmpleados*) malloc(sizeof(ListaEmpleados));
                            EmpAux = aux2->Empleados_Departamento;
                            AuxEmp = NULL;
                            NuNomD = aux2->Nombre_Departamento;
                        }
                        else
                        {
                            EmpAux = aux2->Empleados_Departamento;
                            AuxEmp = EmpAux->Inicio;
                        }
                    }
                    switch(Ope)
                    {
                        case 1:
                            cout << "Ingrese el numero del empleado:" << endl;
                            cin >> NuNu;
                            cout << "Ingrese el nombre del empleado:" << endl;
                            cin >> NuNom;
                            if(AuxEmp == NULL)
                            {
                                Insertar_Empleado(EmpAux,Nuevo_Empleado(NuNu,NuNom,DPIAUX,true,NuNomD,NULL,0,0,NULL));
                                aux2->Empleados_Disponibles = aux2->Empleados_Disponibles+1;
                                aux2->Empleados_Libres = aux2->Empleados_Libres+1;
                            }
                            else
                            {
                                Insertar_Empleado(EmpAux,Nuevo_Empleado(NuNu,NuNom,DPIAUX,true,EmpAux->Inicio->Departamento_Asignado,NULL,0,0,NULL));
                                aux2->Empleados_Disponibles = aux2->Empleados_Disponibles+1;
                                aux2->Empleados_Libres = aux2->Empleados_Libres+1;
                            }
                            break;
                        case 2:
                            cout << "Ingrese el numero de empleados a agregar al departamento: " << endl;
                            cin >> OPSE;
                            if(AuxEmp == NULL)
                            {

                                aux2->Empleados_Disponibles = aux2->Empleados_Disponibles+OPSE;
                                aux2->Empleados_Libres = aux2->Empleados_Libres+OPSE;
                                while(dis < OPSE)
                                {
                                    Insertar_Empleado(EmpAux,Nuevo_Empleado(dis+1,"Empleado"+std::to_string(dis+1),rand()%100000,true,NuNomD,NULL,0,0,NULL));
                                    dis++;
                                }
                            }
                            else
                            {
                                aux2->Empleados_Disponibles = aux2->Empleados_Disponibles+OPSE;
                                aux2->Empleados_Libres = aux2->Empleados_Libres+OPSE;
                                while(dis < OPSE)
                                {
                                    Insertar_Empleado(EmpAux,Nuevo_Empleado(EmpAux->Fin->Numero_Empleado+1,"Empleado"+std::to_string(EmpAux->Fin->Numero_Empleado+1),rand()%100000,true,EmpAux->Inicio->Departamento_Asignado,NULL,0,0,NULL));
                                    dis++;
                                }
                            }

                            break;
                        case 3:
                            if(AuxEmp == NULL)
                            {
                                cout << "NO HAY EMPLEADOS EN ESTE DEPARTAMENTO." << endl;
                                aux2->Empleados_Departamento = NULL;
                            }
                            else
                            {
                                while(AuxEmp !=NULL)
                                {
                                    cout << AuxEmp->Numero_Empleado << ".-" << AuxEmp->Nombre_Empleado << endl;
                                    AuxEmp = AuxEmp->Siguiente;
                                }
                                cin >> CodEL;
                                Eliminar_Empleado(EmpAux,CodEL);
                                aux2->Empleados_Disponibles = aux2->Empleados_Disponibles-1;
                                aux2->Empleados_Libres = aux2->Empleados_Libres-1;
                            }
                            break;
                        case 4:
                            break;
                        default:
                            cout << "OPCION NO VALIDA, INGRESE DE NUEVO" << endl;
                            break;
                    }
                    dis = 0;
                    AuxEmp = NULL;
                    aux2 = Departamentos_Sistema->Inicio;
                }
                while (Ope != 1 && Ope != 2 && Ope !=3 && Ope !=4);
                break;
            case 3:
                do
                {
                    int TotNueClientes = 0;
                    int IdxEl;
                    NodoCliente* auxiliar = Clientes_Sistema->Inicio;
                    cout << "MENU CLIENTES" << endl;
                    cout << "1.- Agregar clientes." << endl;
                    cout << "2.- Eliminar cliente." << endl;
                    cout << "3.- Regresar." << endl;
                    cin >> Ope;

                    switch(Ope)
                    {
                        case 1:
                            cout << "Ingrese el numero de clientes que desea agregar" << endl;
                            cin >> TotNueClientes;
                            for(int i = 0; i < TotNueClientes;i++)
                            {
                                int NITAC = rand() % 100000;
                                NodoCliente *Cliente_Nuevo = Nuevo_Cliente(ClienteActual,NITAC,NULL,NULL,0,"",0,0);
                                Insertar_Cliente(Clientes_Sistema,Cliente_Nuevo);
                                ClienteActual++;
                            }
                            break;
                        case 2:
                            if(auxiliar != NULL)
                            {
                                do
                                {
                                    cout << auxiliar->Numero_Cliente << ".-" << auxiliar->NIT << endl;
                                    auxiliar = auxiliar->Siguiente;
                                }
                                while(auxiliar != Clientes_Sistema->Inicio);
                                cout << "Seleccione el id del usuario que desea eliminar."<< endl;
                                cin >>IdxEl;
                                Eliminar_Cliente(Clientes_Sistema,IdxEl);
                            }
                            else
                            {
                                cout << "LA LISTA DE CLIENTES ESTA VACIA" << endl;
                            }
                            break;
                        case 3:
                            break;
                        default:
                            cout << "OPCION NO VALIDA, INGRESE DE NUEVO" << endl;
                            break;
                    }
                }
                while(Ope != 1 && Ope != 2 && Ope !=3);
                break;
            case 4:
                do
                {
                    NodoDepartamento* aux2 = Departamentos_Sistema->Inicio;
                    ListaEmpleados* auxiliar;
                    NodoEmpleado*ax2;
                    int OPSE;
                    cout << "MENU REPORTES" << endl;
                    cout << "1.- Reporte de Departamentos." << endl;
                    cout << "2.- Reporte de Empleados." << endl;
                    cout << "3.- Reporte de Documentos." << endl;
                    cout << "4.- Reporte de Clientes." << endl;
                    //cout << "5.- Top 5 Empleados (Documentos Procesados)." << endl;
                    //cout << "6.- Top 5 Empleados (Clientes Atendidos)." << endl;
                    cout << "5.- TODOS GRAFICOS(1-5)." << endl;
                    //cout << "8.- TODOS TOP 5(6-7)." << endl;
                    cout << "6.- Regresar." << endl;
                    cin >> Ope;

                    switch(Ope)
                    {
                        case 1:
                            Reporte_Departamentos(Departamentos_Sistema);
                            break;
                        case 2:
                            Reporte_Empleados(Departamentos_Sistema);
                            break;
                        case 3:
                            cout << "Seleccione el departamento en el cual se realizara la operacion" << endl;
                            while(aux2!=NULL)
                            {
                                cout << aux2->Numero_Departamento << ".-"<< aux2->Nombre_Departamento << endl;
                                aux2 = aux2->Siguiente;
                            }
                            cin >> OPSE;
                            aux2 = Departamentos_Sistema->Inicio;
                            while(aux2!=NULL)
                            {
                                if(aux2->Numero_Departamento == OPSE)
                                {
                                    auxiliar = aux2->Empleados_Departamento;
                                }
                                aux2=aux2->Siguiente;
                            }
                            if(auxiliar->Inicio !=NULL)
                            {
                                ax2= auxiliar->Inicio;

                                if(ax2 == NULL)
                                {
                                    cout << "NO HAY EMPLEADOS EN ESTE DEPARTAMENTO." << endl;
                                    aux2->Empleados_Departamento = NULL;
                                }
                                else
                                {
                                    while(ax2 !=NULL)
                                    {
                                        cout << ax2->Numero_Empleado << ".-" << ax2->Nombre_Empleado << endl;
                                        ax2 = ax2->Siguiente;
                                    }
                                    cin >> OPSE;

                                    ax2 = auxiliar->Inicio;
                                    while(ax2!=NULL)
                                    {
                                        if(ax2->Numero_Empleado == OPSE)
                                        {
                                            Reporte_Documentos(ax2->Documentos,ax2->Departamento_Asignado,ax2->Nombre_Empleado);
                                            break;
                                        }
                                        ax2 = ax2->Siguiente;
                                    }
                                }
                            }
                            break;
                        case 4:
                            Reporte_Clientes(Clientes_Sistema);
                            break;
                        case 5:
                            Reporte_Departamentos(Departamentos_Sistema);
                            Reporte_Empleados(Departamentos_Sistema);
                            Reporte_Clientes(Clientes_Sistema);
                            break;
                        case 6:

                            break;
                        default:
                            cout << "OPCION NO VALIDA INGRESE DE NUEVO" << endl;
                            break;
                    }
                }
                while(Ope > 6 && Ope < 0);
                break;
            case 5:
                if(Departamentos_Sistema->Inicio != NULL)
                {
                    //Ingreso de clientes a la cola.
                    cout << "Ingrese el numero de clientes qe quiere añadir al sistema:" << endl;
                    cin >> TotClientes;
                    for(int i = 0; i < TotClientes;i++)
                    {
                        int NITAC = rand() % 100000;
                        NodoCliente *Cliente_Nuevo = Nuevo_Cliente(ClienteActual,NITAC,NULL,NULL,0,"",rand()%50+1,0);
                        Insertar_Cliente(Clientes_Sistema,Cliente_Nuevo);
                        ClienteActual++;
                    }
                    //Paso con los empleados
                    cout << "PROCESO DE ASIGNACION A LOS EMPLEADOS" << endl;
                    bool abor = false;
                    NodoDepartamento* aux = Departamentos_Sistema->Inicio;
                    while(aux != NULL)
                    {
                        NodoEmpleado* auxdep;

                        if(aux->Empleados_Departamento != NULL)
                        {
                            auxdep  = aux->Empleados_Departamento->Inicio;
                        }
                        else
                        {
                            auxdep = NULL;
                        }

                        while(auxdep != NULL)
                        {
                            if(auxdep->Cliente_Actual == NULL && Clientes_Sistema->Inicio != NULL)
                            {
                                NodoCliente* axCli = Clientes_Sistema->Inicio;
                                int Rango = Clientes_Sistema->Fin->Numero_Cliente - Clientes_Sistema->Inicio->Numero_Cliente;
                                if(Rango == 0)
                                {
                                    axCli = Clientes_Sistema->Inicio;
                                }
                                else
                                {
                                    int posPasar = rand()%Rango;
                                    for(int i = 0; i<posPasar; i++)
                                    {
                                        axCli = axCli->Siguiente;
                                    }
                                }
                                aux->Clientes_Atendiendo = aux->Clientes_Atendiendo+1;
                                aux->Empleados_Libres = aux->Empleados_Libres-1;
                                aux->Empleados_Ocupados = aux->Empleados_Ocupados+1;
                                axCli->Turnos_Espera = rand()%5+1;
                                axCli->Empleado_Atencion = auxdep->Numero_Empleado;
                                string axst = auxdep->Departamento_Asignado;
                                axCli->Departamento_Visitado = axst.c_str();
                                auxdep->Cliente_Actual = axCli;
                                auxdep->Total_Clientes_Atendidos = auxdep->Total_Clientes_Atendidos +1;
                                cout << "El cliente con el NIT " << axCli->NIT << " y codigo "<< axCli->Numero_Cliente << " pasara con el empleado con el numero " << auxdep->Numero_Empleado << " en el departamento " << auxdep->Departamento_Asignado << " con " << axCli->Numero_Documentos << " documentos y pasara " << axCli->Turnos_Espera << " turnos ahi." << endl;

                                if(auxdep->Documentos == NULL)
                                {
                                    auxdep->Documentos = (PilaDocumentos*) malloc(sizeof(PilaDocumentos));
                                }

                                for(int i = 0; i< axCli->Numero_Documentos; i++)
                                {
                                    int NumDoc;
                                    Push(auxdep->Documentos,Nuevo_Documento(rand()%1000,rand()%10+1));
                                }
                                Eliminar_Cliente(Clientes_Sistema,axCli->Numero_Cliente);
                            }
                            else
                            {
                                abor = true;
                                break;
                            }
                            auxdep = auxdep->Siguiente;
                        }

                        if(abor)
                        {
                            break;
                        }
                        aux = aux->Siguiente;
                    }

                    //Extraccion de documentos
                    aux = Departamentos_Sistema->Inicio;
                    cout << "PROCESO DE EXTRACCION DE DOCUEMENTOS" << endl;
                    while(aux != NULL)
                    {
                        if(aux->Empleados_Departamento != NULL)
                        {
                            NodoEmpleado *EmpAx = aux->Empleados_Departamento->Inicio;
                            while(EmpAx!=NULL)
                            {
                                bool Prem = true;
                                int Extracciones = rand()%3+1;
                                if(EmpAx->Cliente_Actual !=NULL)
                                {
                                    cout << "El empleado " << EmpAx->Nombre_Empleado << " del departamento " << EmpAx->Departamento_Asignado << " procesara un total de " << Extracciones << " documentos." << endl;
                                    for(int i = 0; i<Extracciones;i++)
                                    {
                                        if(EmpAx->Documentos->Tope == NULL)
                                        {
                                            cout << "El cliente con el id " << EmpAx->Cliente_Actual->Numero_Cliente << " termino sus turnos con el empleado " << EmpAx->Numero_Empleado << " del departamento " << EmpAx->Departamento_Asignado << endl;
                                            EmpAx->Cliente_Actual = NULL;
                                            aux->Clientes_Atendidos_Departamento = aux->Clientes_Atendidos_Departamento+1;
                                            aux->Clientes_Atendiendo = aux->Clientes_Atendiendo-1;
                                            aux->Empleados_Disponibles = aux->Empleados_Libres + 1;
                                            aux->Empleados_Ocupados = aux->Empleados_Ocupados -1;
                                            Prem = false;
                                            break;
                                        }
                                        else
                                        {
                                            cout << "El empleado " << EmpAx->Numero_Empleado << " del departamento " << EmpAx->Departamento_Asignado<<" proceso : " ;
                                            Pop_Prioridad(EmpAx->Documentos);
                                            EmpAx->Total_Documentos_Procesados = EmpAx->Total_Documentos_Procesados+1;
                                            aux->Documentos_Procesados = aux->Documentos_Procesados+1;
                                        }
                                    }
                                    if(Prem)
                                    {
                                        EmpAx->Cliente_Actual->Turnos_Espera = EmpAx->Cliente_Actual->Turnos_Espera - 1;
                                        if(EmpAx->Cliente_Actual->Turnos_Espera == 0)
                                        {
                                            cout << "El cliente con el id " << EmpAx->Cliente_Actual->Numero_Cliente << " termino sus turnos con el empleado " << EmpAx->Numero_Empleado << " del departamento " << EmpAx->Departamento_Asignado << endl;
                                            EmpAx->Cliente_Actual = NULL;
                                            aux->Clientes_Atendidos_Departamento = aux->Clientes_Atendidos_Departamento+1;
                                            aux->Clientes_Atendiendo = aux->Clientes_Atendiendo-1;
                                            aux->Empleados_Disponibles = aux->Empleados_Libres + 1;
                                            aux->Empleados_Ocupados = aux->Empleados_Ocupados -1;
                                        }
                                    }
                                }
                                EmpAx = EmpAx->Siguiente;
                            }
                        }
                        aux = aux->Siguiente;
                    }
                }
                break;
            case 6:
                cout << "GRACIAS POR USAR ESTE SISTEMA" << endl;
                break;
            default:
                cout <<"OPCION NO VALIDA INTENTE DE NUEVO." << endl;
                break;
        }
    }while(Ope != 6);
    return 0;
}
