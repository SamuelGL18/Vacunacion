import java.io.FileWriter;
import java.io.IOException;

class Nodo {
    long dpi;
    String nombre;
    Nodo izquierda, derecha;

    public Nodo(long dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
        izquierda = derecha = null;
    }
}

public class ArbolBB {
    Nodo raiz;
    public FileWriter arbolDot;
    public FileWriter arbolTxt;

    public ArbolBB() {
        raiz = null;
        try {
            arbolDot = new FileWriter("arbolBB.dot");
            arbolTxt = new FileWriter("arbolBB.txt");
            arbolDot.append("digraph G {\n");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void insertar(long dpi, String nombre) {
        raiz = insertarNodo(raiz, dpi, nombre);
    }

    Nodo insertarNodo(Nodo raiz, long dpi, String nombre) {
        if (raiz == null) {
            raiz = new Nodo(dpi, nombre);
            System.out.println(raiz.nombre);
            try {
                String dpiConvertido = "" + raiz.dpi;
                arbolDot.append("\t" + dpiConvertido + " [label=\"" + raiz.nombre + "\"]\n");
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
            return raiz;
        }
        if (dpi < raiz.dpi) {
            raiz.izquierda = insertarNodo(raiz.izquierda, dpi, nombre);
        } else if (dpi > raiz.dpi) {
            raiz.derecha = insertarNodo(raiz.derecha, dpi, nombre);
        }
        return raiz;
    }

    void inorder() {
        recorridoInorder(raiz);
    }

    void recorridoInorder(Nodo raiz) {
        if (raiz != null) {
            recorridoInorder(raiz.izquierda);
            System.out.print("Nombre: " + raiz.nombre + " | DPI: " + raiz.dpi);
            recorridoInorder(raiz.derecha);
        }
    }

    public void preorder() {
        try {
            recorridoPreorder(raiz);
            arbolTxt.close();
            arbolDot.append("}");
            arbolDot.close();
        } catch (IOException df) {
            System.out.println(df.getMessage());
        }
    }

    public void recorridoPreorder(Nodo raiz) {
        try {
            if (raiz != null) {
                String registro = raiz.nombre + "\t" + raiz.dpi + "\n";
                arbolTxt.append(registro);

                // Estableciendo relaciones en el documento dot
                // Depurando datos
                String stringDpi = "" + raiz.dpi;

                // Manejando los distintos casos
                if (raiz.derecha != null && raiz.izquierda != null) {
                    arbolDot.append("\t" + stringDpi + " -> " + raiz.izquierda.dpi + "\n");
                    arbolDot.append("\t" + stringDpi + " -> " + raiz.derecha.dpi + "\n");
                } else if(raiz.derecha == null && raiz.izquierda != null) {
                    arbolDot.append("\t" + stringDpi + " -> " + raiz.izquierda.dpi + "\n");
                } else if(raiz.izquierda == null && raiz.derecha != null) {
                    arbolDot.append("\t" + stringDpi + " -> " + raiz.derecha.dpi + "\n");
                }
                recorridoPreorder(raiz.izquierda);
                recorridoPreorder(raiz.derecha);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    void postorder() {
        recorridoPostorder(raiz);
    }

    void recorridoPostorder(Nodo raiz) {
        if (raiz != null) {
            recorridoPostorder(raiz.izquierda);
            recorridoPostorder(raiz.derecha);
            System.out.print("Nombre: " + raiz.nombre + " | DPI: " + raiz.dpi);
        }
    }

    public void eliminar(long dpi) {
        raiz = eliminarNodo(raiz, dpi);
    }

    private Nodo minimo(Nodo nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo;
    }

    Nodo eliminarNodo(Nodo raiz, long dpi) {
        if (raiz == null) {
            return raiz; // Node not found
        }
        // Search for the node to be deleted
        if (dpi < raiz.dpi) {
            raiz.izquierda = eliminarNodo(raiz.izquierda, dpi);
        } else if (dpi > raiz.dpi) {
            raiz.derecha = eliminarNodo(raiz.derecha, dpi);
        } else { // Node to be deleted found

            // Case 1: Node has no children (leaf node)
            if (raiz.izquierda == null && raiz.derecha == null) {
                return null;
            }

            // Case 2: Node has one child
            else if (raiz.izquierda == null) {
                return raiz.derecha;
            } else if (raiz.derecha == null) {
                return raiz.izquierda;
            }

            // Case 3: Node has two children
            else {
                // Find the inorder successor (smallest in the right subtree)
                Nodo sucesorInorder = minimo(raiz.derecha);

                // Replace the node's data with the successor's data
                raiz.dpi = sucesorInorder.dpi;
                raiz.nombre = sucesorInorder.nombre;

                // Delete the inorder successor from the right subtree
                raiz.derecha = eliminarNodo(raiz.derecha, sucesorInorder.dpi);
            }
        }
        return raiz;
    }

    public void editar(long dpi, int dpiNuevo, String nombre) {
        boolean encontrado = editarNodo(raiz, dpi, dpiNuevo, nombre);
        if (encontrado) {
            System.out.println("Editado");
        } else {
            System.out.println("No se encontro");
        }
    }
    boolean editarNodo(Nodo raiz, long dpiAntiguo, long nuevoDpi, String nombre) {
        if (raiz == null) {
            return false; // Node not found
        }
        if (dpiAntiguo == raiz.dpi) {
            raiz.dpi = nuevoDpi;
            raiz.nombre = nombre;// Update the node's data
            return true; // Node found and updated
        } else if (dpiAntiguo < raiz.dpi) {
            return editarNodo(raiz.izquierda, dpiAntiguo, nuevoDpi, nombre);
        } else {
            return editarNodo(raiz.derecha, dpiAntiguo, nuevoDpi, nombre);
        }
    }
}
