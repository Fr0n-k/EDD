#ifndef ARBOL_AVL_H
#define ARBOL_AVL_H
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <cstring>
#include <fstream>
#include <sstream>
#include <qtablewidget.h>
using namespace std;

typedef struct NodoAvl NodoAvl;
typedef struct Arbol_AVL Arbol_AVL;

struct NodoAvl
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

    NodoAvl* Izq;
    NodoAvl* Der;
    NodoAvl(string Codigo_Articulo,string Tienda,string Cliente_Proveedor,string Codigo_Cliente,string Nombre_Cliente,string Codigo_Sucursal,string Nombre_Sucursal,string Region,string Departamento,int Cantidad,double Precio_Unitario,double Costo);
};


struct Arbol_AVL
{
    NodoAvl *Raiz;
    Arbol_AVL();
    void rotacionII(NodoAvl **n, NodoAvl *n1);
    void rotacionDD(NodoAvl **n, NodoAvl *n1);
    void rotacionID(NodoAvl **n, NodoAvl *n1);
    void rotacionDI(NodoAvl **n, NodoAvl *n1);
    void insertarEquilibrado(NodoAvl **raiz, string Codigo_Articulo,string Tienda,string Cliente_Proveedor,string Codigo_Cliente,string Nombre_Cliente,string Codigo_Sucursal,string Nombre_Sucursal,string Region,string Departamento,int Cantidad,double Precio_Unitario,double Costo, int*h);
    void borrarBalanceado(NodoAvl** raiz, string Codgio, int* cambiaAltura);
    void reemplazar(NodoAvl **n, NodoAvl **act, int * cambiaAltura);
    void equilibrar1(NodoAvl **n, int* cambiarAltura);
    void equilibrar2(NodoAvl **n, int* cambiarAltura);

    void Visualizar(NodoAvl* Origen,int ValComp,int Comp, QTableWidget* Contenedor);
    NodoAvl* Visualizar(NodoAvl* Origen,string Codigo);
    int Altura(NodoAvl* Inicio);
    int CRamas(NodoAvl* Inicio,int Val);
    int CHojas(NodoAvl* Inicio, int Val);
    string Graficar(NodoAvl* Inicio, string Grafo);
    string GraficarFe(NodoAvl* Inicio, string Grafo);
};

#endif // ARBOL_AVL_H
