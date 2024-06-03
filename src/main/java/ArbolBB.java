import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

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
    EncriptacionSustitucion encriptador;
    Comprimidor comprimidor;

    public ArbolBB() {
        raiz = null;
        encriptador = new EncriptacionSustitucion();
        comprimidor = new Comprimidor();
        cargarArbol();
    }

    // Inserta a tabla, archivo de texto, archivo dot
    public void insertarGUI(long dpi, String nombre) {
        raiz = insertarNodo(raiz, dpi, nombre);
        actualizarGUI();
    }

    // Solo para inicializar datos
    public void insertarArbol(long dpi, String nombre) {
        raiz = insertarNodo(raiz, dpi, nombre);
    }

    Nodo insertarNodo(Nodo raiz, long dpi, String nombre) {
        if (raiz == null) {
            raiz = new Nodo(dpi, nombre);
            System.out.println(raiz.nombre);
            return raiz;
        }
        if (dpi < raiz.dpi) {
            raiz.izquierda = insertarNodo(raiz.izquierda, dpi, nombre);
        } else if (dpi > raiz.dpi) {
            raiz.derecha = insertarNodo(raiz.derecha, dpi, nombre);
        }
        return raiz;
    }

    // Actualiza tabla, archivo de texto y archivo dot
    public void actualizarGUI() {
        try {
            FileWriter arbolTxt = new FileWriter("arbolBB.txt");
            FileWriter declaracionDot = new FileWriter("dotDeclaracionesABB.txt");
            FileWriter relacionesDot = new FileWriter("dotRelacionesABB.txt");
            FileWriter archivoDot = new FileWriter("arbolBB.dot");
            archivoDot.append("digraph G {\n");
            recorridoPreorderGUI(raiz, arbolTxt, declaracionDot, relacionesDot);
            declaracionDot.close();
            relacionesDot.close();
            arbolTxt.close();
            Path declaracionesRuta = Path.of("dotDeclaracionesABB.txt");
            String declaraciones = Files.readString(declaracionesRuta);
            archivoDot.append(declaraciones + "\n");
            Path relacionesRuta = Path.of("dotRelacionesABB.txt");
            String relaciones = Files.readString(relacionesRuta);
            archivoDot.append(relaciones + "\n" + "}");
            archivoDot.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Escribe en archivo texto y dot
    public void recorridoPreorderGUI(Nodo raiz, FileWriter arbolTxt, FileWriter declaracionDot, FileWriter relacionesDot) {
        try {
            if (raiz != null) {
                String registro = raiz.nombre + "\t" + raiz.dpi + "\n";
                arbolTxt.append(registro);
                String dpiConvertido = "" + raiz.dpi;
                declaracionDot.append("\t" + dpiConvertido + " [label=\"" + raiz.nombre + "\"]\n");

                // Estableciendo relaciones en el documento dot
                // Manejando los distintos casos
                if (raiz.derecha != null && raiz.izquierda != null) {
                    relacionesDot.append("\t" + dpiConvertido + " -> " + raiz.izquierda.dpi + "\n");
                    relacionesDot.append("\t" + dpiConvertido + " -> " + raiz.derecha.dpi + "\n");
                } else if(raiz.derecha == null && raiz.izquierda != null) {
                    relacionesDot.append("\t" + dpiConvertido + " -> " + raiz.izquierda.dpi + "\n");
                } else if(raiz.izquierda == null && raiz.derecha != null) {
                    relacionesDot.append("\t" + dpiConvertido + " -> " + raiz.derecha.dpi + "\n");
                }
                recorridoPreorderGUI(raiz.izquierda, arbolTxt, declaracionDot, relacionesDot);
                recorridoPreorderGUI(raiz.derecha, arbolTxt, declaracionDot, relacionesDot);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void preorderArbol() {
        recorridoPreorderArbol(raiz);
    }

    public void recorridoPreorderArbol(Nodo raiz) {
            if (raiz != null) {
                String registro = raiz.nombre + "\t" + raiz.dpi + "\n";
                System.out.println(registro);
                recorridoPreorderArbol(raiz.izquierda);
                recorridoPreorderArbol(raiz.derecha);
            }
    }

    public void eliminar(long dpi) {
        raiz = eliminarNodo(raiz, dpi);
        actualizarGUI();
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

    public void editar(long dpi, long dpiNuevo, String nombre) {
        boolean encontrado = editarNodo(raiz, dpi, dpiNuevo, nombre);
        if (encontrado) {
            System.out.println("Editado");
            actualizarGUI();
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

    public void cargarArbol() {
        try (Scanner scanner = new Scanner(new File("arbolBB.txt"))) {
            while(scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split("\t");

                // Procesando datos
                if (datos.length == 2) {
                    String nombre = datos[0];
                    String dpiPuro = datos[1].replaceAll("[^\\d]", "");
                    long dpi = Long.parseLong(dpiPuro);

                    insertarArbol(dpi, nombre);
                }
            }
        } catch(IOException exception) {
        System.out.println(exception.getMessage());
    }
    }
}
