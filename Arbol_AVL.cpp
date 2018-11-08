#include "arbol_binario.h"

Nodo_Binario::Nodo_Binario(string Codigo_Articulo, string Tienda, string Cliente_Proveedor, string Codigo_Cliente, string Nombre_Cliente, string Codigo_Sucursal, string Nombre_Sucursal, string Region, string Departamento, int Cantidad, double Precio_Unitario, double Costo)
{
    this->Codigo_Articulo = Codigo_Articulo;
    this->Tienda = Tienda;
    this->Cliente_Proveedor = Cliente_Proveedor;
    this->Codigo_Cliente = Codigo_Cliente;
    this->Nombre_Cliente = Nombre_Cliente;
    this->Codigo_Sucursal = Codigo_Sucursal;
    this->Nombre_Sucursal = Nombre_Sucursal;
    this->Region = Region;
    this->Departamento = Departamento;
    this->Cantidad = Cantidad;
    this->Precio_Unitario = Precio_Unitario;
    this->Costo = Costo;
    this->fe = 0;
    this->Izq = NULL;
    this->Der = NULL;
}


Arbol_Binario::Arbol_Binario()
{
    this->Raiz = NULL;
}

void Arbol_Binario::Insertar(Nodo_Binario *Nuevo, Nodo_Binario *Origen)
{
    if(this->Raiz == NULL)
    {
        this->Raiz = Nuevo;
    }
    else
    {
        string AX = "";
        string Aux = Nuevo->Codigo_Articulo;
        string AX2 = "";
        string Aux2 = Origen->Codigo_Articulo;
        int CodNuevo;
        int CodOrigen;
        int Tope1 = Aux.size();
        int Tope2 =  Aux2.size();
        for(int i = 0; i < Tope1;i++)
        {
            if(Aux[i]>= 48 && Aux[i] <= 57)
            {
                AX = AX + Aux[i];
            }
        }

        for(int i = 0; i < Tope2;i++)
        {
            if(Aux2[i]>= 48 && Aux2[i] <= 57)
            {
                AX2 = AX2 + Aux2[i];
            }
        }

        CodNuevo = stoi(AX);
        CodOrigen = stoi(AX2);
        if(CodNuevo < CodOrigen)
        {
            if(Origen->Izq == NULL)
            {
                Origen->Izq = Nuevo;
            }
            else
            {
                Insertar(Nuevo,Origen->Izq);
            }
        }
        else if(CodNuevo > CodOrigen)
        {
            if(Origen->Der == NULL)
            {
                Origen->Der = Nuevo;
            }
            else
            {
                Insertar(Nuevo,Origen->Der);
            }
        }
        else
        {

        }
    }
}

void Arbol_Binario::Visualizar(Nodo_Binario* Origen, int ValComp, int Comp, QTableWidget *Contenedor)
{
    if(Origen != NULL)
    {
        switch (Comp) {
        case 1:
            if(Origen->Cantidad == ValComp)
            {
                Contenedor->insertRow(Contenedor->rowCount());
                Contenedor->setItem(Contenedor->rowCount() - 1 , 0 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Articulo)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 1 , new QTableWidgetItem(QString::fromStdString(Origen->Tienda)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 2 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 3 , new QTableWidgetItem(QString::fromStdString(Origen->Nombre_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 4 , new QTableWidgetItem(QString::fromStdString(std::to_string(Origen->Cantidad))));
            }
            break;
        case 2:
            if(Origen->Cantidad < ValComp)
            {
                Contenedor->insertRow(Contenedor->rowCount());
                Contenedor->setItem(Contenedor->rowCount() - 1 , 0 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Articulo)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 1 , new QTableWidgetItem(QString::fromStdString(Origen->Tienda)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 2 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 3 , new QTableWidgetItem(QString::fromStdString(Origen->Nombre_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 4 , new QTableWidgetItem(QString::fromStdString(std::to_string(Origen->Cantidad))));
            }
            break;
        case 3:
            if(Origen->Cantidad > ValComp)
            {
                Contenedor->insertRow(Contenedor->rowCount());
                Contenedor->setItem(Contenedor->rowCount() - 1 , 0 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Articulo)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 1 , new QTableWidgetItem(QString::fromStdString(Origen->Tienda)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 2 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 3 , new QTableWidgetItem(QString::fromStdString(Origen->Nombre_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 4 , new QTableWidgetItem(QString::fromStdString(std::to_string(Origen->Cantidad))));
            }
            break;
        case 4:
            if(Origen->Cantidad <= ValComp)
            {
                Contenedor->insertRow(Contenedor->rowCount());
                Contenedor->setItem(Contenedor->rowCount() - 1 , 0 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Articulo)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 1 , new QTableWidgetItem(QString::fromStdString(Origen->Tienda)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 2 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 3 , new QTableWidgetItem(QString::fromStdString(Origen->Nombre_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 4 , new QTableWidgetItem(QString::fromStdString(std::to_string(Origen->Cantidad))));
            }
            break;
        case 5:
            if(Origen->Cantidad >= ValComp)
            {
                Contenedor->insertRow(Contenedor->rowCount());
                Contenedor->setItem(Contenedor->rowCount() - 1 , 0 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Articulo)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 1 , new QTableWidgetItem(QString::fromStdString(Origen->Tienda)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 2 , new QTableWidgetItem(QString::fromStdString(Origen->Codigo_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 3 , new QTableWidgetItem(QString::fromStdString(Origen->Nombre_Sucursal)));
                Contenedor->setItem(Contenedor->rowCount() - 1 , 4 , new QTableWidgetItem(QString::fromStdString(std::to_string(Origen->Cantidad))));
            }
            break;
        default:
            break;
        }
        Visualizar(Origen->Izq,ValComp,Comp,Contenedor);
        Visualizar(Origen->Der,ValComp,Comp,Contenedor);
    }
}

Nodo_Binario* Arbol_Binario::Visualizar(Nodo_Binario *Origen, string Codigo)
{
    if(Origen != NULL)
    {
        if (Codigo < Origen->Codigo_Articulo)
        {
            return Visualizar(Origen->Izq,Codigo);
        }
        else if(Codigo > Origen->Codigo_Articulo)
        {
            return Visualizar(Origen->Der,Codigo);
        }
        else
        {
            return Origen;
        }
    }
    return NULL;
}

int Arbol_Binario::CAltura(Nodo_Binario * Inicio)
{
    if (Inicio != NULL)
    {
        return max(CAltura(Inicio->Izq), CAltura(Inicio->Der)) + 1;
    }
    return 0;
}

int Arbol_Binario::CRamas(Nodo_Binario* Inicio, int Val)
{
    if(Inicio != NULL)
    {
        if(Inicio->Izq != NULL || Inicio->Der != NULL)
        {
            Val++;
        }
        else
        {
            Val = Val+0;
        }
        Val = Val + CRamas(Inicio->Izq,0);
        Val = Val + CRamas(Inicio->Der,0);
    }
    return Val;
}

int Arbol_Binario::CHojas(Nodo_Binario* Inicio, int Val)
{
    if(Inicio != NULL)
    {
        if(Inicio->Izq == NULL && Inicio->Der == NULL)
        {
            Val++;
        }
        else
        {
            Val = Val+0;
        }
        Val = Val + CHojas(Inicio->Izq,0);
        Val = Val + CHojas(Inicio->Der,0);
    }
    return Val;
}

void Arbol_Binario::Eliminar(string Codigo)
{
    this->Raiz = Eliminar(this->Raiz, Codigo);
}

Nodo_Binario* Arbol_Binario::Eliminar(Nodo_Binario *Raiz, string Codigo)
{
    if(Raiz == NULL)
    {
        //QMessageBox msgBox;
        //msgBox.setText("No se pudo eliminar. No se encontro.  ");
        //msgBox.exec();
    }
    else if (Codigo < Raiz->Codigo_Articulo)
    {
        Nodo_Binario *iz;
        iz = Eliminar(Raiz->Izq, Codigo);
        Raiz->Izq = iz;
    }
    else if(Codigo > Raiz->Codigo_Articulo)
    {
        Nodo_Binario *der;
        der = Eliminar(Raiz->Der, Codigo);
        Raiz->Der = der;
    }
    else
    {
        Nodo_Binario *Aux;
        Aux = Raiz;
        if (Aux->Izq == NULL){
            Raiz = Aux->Der;
        } else if (Aux->Der == NULL){
            Raiz = Aux->Der;
        } else {
            Aux = Reemplazar(Aux);
        }
        Aux = NULL;
    }
    return Raiz;
}

Nodo_Binario* Arbol_Binario::Reemplazar(Nodo_Binario *Act)
{
    Nodo_Binario *AX, *Piv;
    Piv = Act;
    AX = Act->Izq;
    while (AX->Der != NULL)
    {
        Piv = AX;
        AX = AX->Der;
    }
    Act->Codigo_Articulo = AX->Codigo_Articulo;
    Act->Tienda = AX->Tienda;
    Act->Cliente_Proveedor = AX->Cliente_Proveedor;
    Act->Codigo_Cliente = AX->Codigo_Cliente;
    Act->Nombre_Cliente = AX->Nombre_Cliente;
    Act->Codigo_Sucursal = AX->Codigo_Sucursal;
    Act->Nombre_Sucursal = AX->Nombre_Sucursal;
    Act->Region = AX->Region;
    Act->Departamento = AX->Departamento;
    Act->Cantidad = AX->Cantidad;
    Act->Precio_Unitario = AX->Precio_Unitario;
    Act->Costo = AX->Costo;
    if (Piv == Act){
        Piv->Izq = AX->Izq;
    }
    else{
        Piv->Der = AX->Der;
    }
    return AX;
}

string Arbol_Binario::Graficar(Nodo_Binario *Inicio, string Grafo)
{
    if(Inicio != NULL)
    {
        Grafo += Inicio->Codigo_Articulo + ";";

        if(Inicio->Izq != NULL)
        {
            Grafo += Inicio->Codigo_Articulo + "->" + Inicio->Izq->Codigo_Articulo + ";";
        }
        if(Inicio->Der != NULL)
        {
            Grafo += Inicio->Codigo_Articulo + "->" + Inicio->Der->Codigo_Articulo + ";";
        }
        Grafo +=Graficar(Inicio->Izq,"")+ Graficar(Inicio->Der, "") ;
    }
    return Grafo;
}

void Arbol_Binario::Cfe(Nodo_Binario *Inicio)
{
    if(Inicio != NULL)
    {
        Inicio->fe = CAltura(Inicio->Der) - CAltura(Inicio->Izq);
        Cfe(Inicio->Izq);
        Cfe(Inicio->Der);
    }
}

string Arbol_Binario::GraficarFe(Nodo_Binario *Inicio, string Grafo)
{
    string com;
    com = (char) 34;
    if(Inicio != NULL)
    {
        Grafo += Inicio->Codigo_Articulo+"[label = "+ com + Inicio->Codigo_Articulo +"\\nfe:" + std::to_string(Inicio->fe) + com + "];";

        if(Inicio->Izq != NULL)
        {
            Grafo += Inicio->Codigo_Articulo + "->" + Inicio->Izq->Codigo_Articulo + ";";
        }
        if(Inicio->Der != NULL)
        {
            Grafo += Inicio->Codigo_Articulo + "->" + Inicio->Der->Codigo_Articulo + ";";
        }
        Grafo +=GraficarFe(Inicio->Izq,"")+ GraficarFe(Inicio->Der, "") ;
    }
    return Grafo;
}


