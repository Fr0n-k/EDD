#include <iostream>

using namespace std;

typedef struct NodoPila nodo;

typedef struct Pila LisP;

struct NodoPila{

    struct NodoPila * siguiente;

    int dato;

};

struct Pila{
    nodo *tope;
};

void IniciarPila(LisP **Valores){

    (*Valores)->tope=NULL;
}

nodo *NuevoNodo(int dato){

    nodo * nuevo = (nodo*) malloc(sizeof(nodo));

    nuevo->dato = dato;

    nuevo->siguiente = NULL;

    return nuevo;

};

void Push(LisP *vals, int dato){

    nodo *nuevo = NuevoNodo(dato);

    if(vals->tope == NULL){

        vals->tope = nuevo;
    }
    else
    {
        nuevo->siguiente = vals->tope;
        vals->tope = nuevo;
    }

    cout << "Se inserto el valor, el tope es ahora: " << dato << endl;
}


void Pop(LisP *vals)
{
    if(vals->tope == NULL)
    {
        cout << "La pila esta vacía." << endl;
    }
    else
    {

        cout << "Se elimino el valor: " << vals->tope->dato << endl;
        vals->tope = vals->tope->siguiente;

        cout << "El tope es ahora: " << vals->tope->dato << endl;
    }
}

void Peek(LisP *vals)
{
    if(vals->tope==NULL)
    {
        cout << "La pila esta vacía." << endl;
    }
    else
    {
        nodo *aux = vals->tope->siguiente;
        cout << "Tope: " << vals->tope->dato << endl;
    }
}

int main()
{
    int numero;
    int valnu;
    LisP *nueva_pila = (LisP*) malloc(sizeof(LisP));
    do
    {
        cout << "Ingrese la operacion que desea" << endl;
        cout << "1. Insertar (Push)" << endl;
        cout << "2. Eliminar (Pop)" << endl;
        cout << "3. Ver tope (Peek)" << endl;
        cout << "4. Salir" << endl;

        cin >> numero;

        switch(numero)
        {
            case 1:
                cout << "Ingrese el valor a insertar" << endl;
                cin >> valnu;

                Push(nueva_pila,valnu);
                break;
            case 2:
                Pop(nueva_pila);
                break;
            case 3:
                Peek(nueva_pila);
                break;
        }

    }while(numero != 4);
    return 0;
}
