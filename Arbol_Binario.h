#ifndef ARBOL_BINARIO_H
#define ARBOL_BINARIO_H
#include <string>
#include <qtablewidget.h>
#include "lrep.h"

using namespace std;
typedef struct Nodo_Binario Nodo_Binario;
typedef struct Arbol_Binario Arbol_Binario;

struct Nodo_Binario
{
    string Codigo_Articulo;
    string Tienda;
    string Cliente_Proveedor;
    string Codigo_Cliente;
    string Nombre_Cliente;
    string Codigo_Sucursal;
    string Nombre_Sucursal;
    string Region;
    string Departamento;
    int Cantidad;
    double Precio_Unitario;
    double Costo;
    int fe;

    Nodo_Binario* Izq;
    Nodo_Binario* Der;
    Nodo_Binario(string Codigo_Articulo,string Tienda,string Cliente_Proveedor,string Codigo_Cliente,string Nombre_Cliente,string Codigo_Sucursal,string Nombre_Sucursal,string Region,string Departamento,int Cantidad,double Precio_Unitario,double Costo);
};



struct Arbol_Binario
{
public:
    Nodo_Binario* Raiz;
    Arbol_Binario();
    void Insertar(Nodo_Binario* Nuevo, Nodo_Binario* Origen);
    void Visualizar(Nodo_Binario* Origen,int ValComp,int Comp, QTableWidget* Contenedor);
    Nodo_Binario* Visualizar(Nodo_Binario* Origen,string Codigo);
    void Eliminar(string Codigo);
    Nodo_Binario* Eliminar(Nodo_Binario* Raiz, string Codigo);
    Nodo_Binario* Reemplazar(Nodo_Binario *Act);
    int CAltura(Nodo_Binario* Inicio);
    int CRamas(Nodo_Binario* Inicio,int Val);
    int CHojas(Nodo_Binario* Inicio, int Val);
    string Graficar(Nodo_Binario* Inicio, string Grafo);
    void Cfe(Nodo_Binario* Inicio);
    string GraficarFe(Nodo_Binario* Inicio, string Grafo);
};

#endif // ARBOL_BINARIO_H
