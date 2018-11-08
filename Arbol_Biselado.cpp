#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <fstream>
#include <string>

using namespace std;

struct splay
{
    int Valor;
    splay* Izquierda;
    splay* Derecha;
};

class SplayTree
{
    public:
        SplayTree()
        {
        }

        splay* ZigZig(splay* k2)
        {
            splay* k1 = k2->Izquierda;
            k2->Izquierda = k1->Derecha;
            k1->Derecha = k2;
            return k1;
        }

        splay* ZagZag(splay* k2)
        {
            splay* k1 = k2->Derecha;
            k2->Derecha = k1->Izquierda;
            k1->Izquierda = k2;
            return k1;
        }

        splay* Splay(int Valor, splay* Root)
        {
            if (!Root)
                return NULL;
            splay header;
            header.Izquierda = header.Derecha = NULL;
            splay* LeftTreeMax = &header;
            splay* RightTreeMin = &header;
            while (1)
            {
                if (Valor < Root->Valor)
                {
                    if (!Root->Izquierda)
                        break;
                    if (Valor < Root->Izquierda->Valor)
                    {
                        Root = ZigZig(Root);
                        if (!Root->Izquierda)
                            break;
                    }
                    RightTreeMin->Izquierda = Root;
                    RightTreeMin = RightTreeMin->Izquierda;
                    Root = Root->Izquierda;
                    RightTreeMin->Izquierda = NULL;
                }
                else if (Valor > Root->Valor)
                {
                    if (!Root->Derecha)
                        break;
                    if (Valor > Root->Derecha->Valor)
                    {
                        Root = ZagZag(Root);
                        if (!Root->Derecha)
                            break;
                    }
                    LeftTreeMax->Derecha = Root;
                    LeftTreeMax = LeftTreeMax->Derecha;
                    Root = Root->Derecha;
                    LeftTreeMax->Derecha = NULL;
                }
                else
                    break;
            }
            LeftTreeMax->Derecha = Root->Izquierda;
            RightTreeMin->Izquierda = Root->Derecha;
            Root->Izquierda = header.Derecha;
            Root->Derecha = header.Izquierda;
            return Root;
        }

        splay* Nodo_Nuevo(int Valor)
        {
            splay* pivote = new splay;
            if (!pivote)
            {
                fprintf(stderr, "Out of memory!\n");
                exit(1);
            }
            pivote->Valor = Valor;
            pivote->Izquierda = pivote->Derecha = NULL;
            return pivote;
        }

        splay* Insertar(int Valor, splay* Root)
        {
            static splay* pivote = NULL;
            if (!pivote)
                pivote = Nodo_Nuevo(Valor);
            else
                pivote->Valor = Valor;
            if (!Root)
            {
                Root = pivote;
                pivote = NULL;
                return Root;
            }
            Root = Splay(Valor, Root);
            if (Valor < Root->Valor)
            {
                pivote->Izquierda = Root->Izquierda;
                pivote->Derecha = Root;
                Root->Izquierda = NULL;
                Root = pivote;
            }
            else if (Valor > Root->Valor)
            {
                pivote->Derecha = Root->Derecha;
                pivote->Izquierda = Root;
                Root->Derecha = NULL;
                Root = pivote;
            }
            else
                return Root;
            pivote = NULL;
            return Root;
        }

        splay* Eliminar(int Valor, splay* Root)
        {
            splay* temp;
            if (!Root)
                return NULL;
            Root = Splay(Valor, Root);
            if (Valor != Root->Valor)
                return Root;
            else
            {
                if (!Root->Izquierda)
                {
                    temp = Root;
                    Root = Root->Derecha;
                }
                else
                {
                    temp = Root;
                    Root = Splay(Valor, Root->Izquierda);
                    Root->Derecha = temp->Derecha;
                }
                free(temp);
                return Root;
            }
        }

        splay* Buscar(int Valor, splay* Root)
        {
            return Splay(Valor, Root);
        }

        string Graficar(splay* Root, string Cuerpo){
            if(Root)
            {
                Cuerpo+= (Root->Valor) + ";\n";
                if(Root->Izquierda)
                {
                    Cuerpo+= std::to_string(Root->Valor) + "->" +  std::to_string(Root->Izquierda->Valor) + ";\n";
                }
                if(Root->Derecha)
                {
                    Cuerpo+= std::to_string(Root->Valor) + "->" + std::to_string(Root->Derecha->Valor) + ";\n";
                }
                Cuerpo+= Graficar(Root->Izquierda,"");
                Cuerpo+= Graficar(Root->Derecha,"");
            }
            return Cuerpo;
        }

        void Guardar(string Cuerpo)
        {
            ofstream Arch;
            Arch.open("Grafica.txt");
            Arch << Cuerpo;
            Arch.close();
            cout << "Se guardo el archivo" << endl;
        }

        void InOrder(splay* Root)
        {
            if (Root)
            {
                InOrder(Root->Izquierda);
                cout<< "Valor: " <<Root->Valor;
                if(Root->Izquierda)
                    cout<< " | Hijo Izquierdo: "<< Root->Izquierda->Valor;
                if(Root->Derecha)
                    cout << " | Hijo Derecho: " << Root->Derecha->Valor;
                cout<< "\n";
                InOrder(Root->Derecha);
            }
        }
};

int main()
{
    SplayTree st;
    splay *Root;
    Root = NULL;
    int input, choice;
    while(1)
    {
        cout<<"\nARBOL BISELADO-SPL\n";
        cout<<"1. Insertar "<<endl;
        cout<<"2. Eliminar"<<endl;
        cout<<"3. Buscar"<<endl;
        cout<<"4. Graficar"<<endl;
        cout<<"5. Salir"<<endl;
        cout<<"Ingrese el numero de la operacion: ";
        cin>>choice;
        switch(choice)
        {
        case 1:
            cout<<"Ingrese el valor a insertar: ";
            cin>>input;
            Root = st.Insertar(input, Root);
            cout<<"\nArbol Actual "<<input<<endl;
            st.InOrder(Root);
            break;
        case 2:
            cout<<"Ingrese el valor a eliminar: ";
            cin>>input;
            Root = st.Eliminar(input, Root);
            cout<<"\nArbol Actual: "<<input<<endl;
            st.InOrder(Root);
            break;
        case 3:
            cout<<"Ingrese el valor a buscar: ";
            cin>>input;
            Root = st.Buscar(input, Root);
            cout<<"\nArbol Actual "<<input<<endl;
            st.InOrder(Root);
            break;
        case 4:
            st.Guardar("digraph Biselado{\n" + st.Graficar(Root,"") + "}");
            break;
        case 5:
            exit(1);
        default:
            cout<<"\OPCION INVALIDA\n";
        }
    }
    cout<<"\n";
    return 0;
}
