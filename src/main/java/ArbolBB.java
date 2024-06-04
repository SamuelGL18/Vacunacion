import javax.swing.*;
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
    String departamento;
    String municipio;
    String dosis;
    String fechaPrimeraVacuna;
    String fechaSegundaVacuna;
    String fechaTerceraVacuna;
    String lugarVacunacion;

    public Nodo(long dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
        izquierda = derecha = null;
        departamento = municipio = dosis = fechaPrimeraVacuna = fechaSegundaVacuna = fechaTerceraVacuna = lugarVacunacion = "Vacio";
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
    public void insertarArbol(long dpi, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        raiz = insertar(raiz, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
    }

    // Para inicializar datos
    Nodo insertar(Nodo raiz, long dpi, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        if (raiz == null) {
            raiz = new Nodo(dpi, nombre);
            raiz.departamento = departamento;
            raiz.municipio = municipio;
            raiz.dosis = dosis;
            raiz.fechaPrimeraVacuna = fechaPrimeraVacuna;
            raiz.fechaSegundaVacuna = fechaSegundaVacuna;
            raiz.fechaTerceraVacuna = fechaTerceraVacuna;
            raiz.lugarVacunacion = lugarVacunacion;
            return raiz;
        }
        if (dpi < raiz.dpi) {
            raiz.izquierda = insertar(raiz.izquierda, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        } else if (dpi > raiz.dpi) {
            raiz.derecha = insertar(raiz.derecha, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        }
        return raiz;
    }

    // Cargar los datos anteriores
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
                String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.lugarVacunacion + "\n";
                arbolTxt.append(registro);
                String dpiConvertido = "" + raiz.dpi;
                declaracionDot.append("\t" + dpiConvertido + " [label=\"" + "Nombre: " + raiz.nombre + "\n" + "DPI: " + raiz.dpi + "\n" + "Departamento: " + raiz.departamento + "\n" + "Municipio: " + raiz.municipio + "\n" + "Dosis: " + raiz.dosis + "\n" + "Primera vacuna" + raiz.fechaPrimeraVacuna + "\n" + "Segunda vacuna" + raiz.fechaSegundaVacuna + "\n" + "Tercera vacuna: " + raiz.fechaTerceraVacuna + "\n" + "Lugar vacunacion: " + raiz.lugarVacunacion + "\"]\n");

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
                String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion;
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
            return raiz;
        }

        if (dpi < raiz.dpi) {
            raiz.izquierda = eliminarNodo(raiz.izquierda, dpi);
        } else if (dpi > raiz.dpi) {
            raiz.derecha = eliminarNodo(raiz.derecha, dpi);
        } else {

            // Caso hoja
            if (raiz.izquierda == null && raiz.derecha == null) {
                return null;
            }

            // Caso un hijo
            else if (raiz.izquierda == null) {
                return raiz.derecha;
            } else if (raiz.derecha == null) {
                return raiz.izquierda;
            }

            // Caso 2 hijos
            else {
                // Encontrando sucesor
                Nodo sucesorInorder = minimo(raiz.derecha);

                // Reemplazar datos
                raiz.dpi = sucesorInorder.dpi;
                raiz.nombre = sucesorInorder.nombre;
                raiz.departamento = sucesorInorder.departamento;
                raiz.municipio = sucesorInorder.municipio;
                raiz.dosis = sucesorInorder.dosis;
                raiz.fechaPrimeraVacuna = sucesorInorder.fechaPrimeraVacuna;
                raiz.fechaSegundaVacuna = sucesorInorder.fechaSegundaVacuna;
                raiz.fechaTerceraVacuna = sucesorInorder.fechaTerceraVacuna;
                raiz.lugarVacunacion = sucesorInorder.lugarVacunacion;

                // Eliminarlo
                raiz.derecha = eliminarNodo(raiz.derecha, sucesorInorder.dpi);
            }
        }
        return raiz;
    }

    public void editar(long dpi, long dpiNuevo, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        boolean encontrado = editarNodo(raiz, dpi, dpiNuevo, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        if (encontrado) {
            System.out.println("Editado");
            actualizarGUI();
        } else {
            System.out.println("No se encontro");
        }
    }

    boolean editarNodo(Nodo raiz, long dpi, long dpiNuevo, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        if (raiz == null) {
            return false;
        }
        if (dpi == raiz.dpi) {
            raiz.dpi = dpiNuevo;
            raiz.nombre = nombre;
            raiz.departamento = departamento;
            raiz.municipio = municipio;
            raiz.dosis = dosis;
            raiz.fechaPrimeraVacuna = fechaPrimeraVacuna;
            raiz.fechaSegundaVacuna = fechaSegundaVacuna;
            raiz.fechaTerceraVacuna = fechaTerceraVacuna;
            raiz.lugarVacunacion = lugarVacunacion;
            return true;
        } else if (dpi < raiz.dpi) {
            return editarNodo(raiz.izquierda, dpi, dpiNuevo, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        } else {
            return editarNodo(raiz.derecha, dpi, dpiNuevo, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        }
    }

    public void cargarArbol() {
        try (Scanner scanner = new Scanner(new File("arbolBB.txt"))) {
            while(scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split("\t");

                // Procesando datos
                    String nombre = datos[0];
                    String dpiPuro = datos[1].replaceAll("[^\\d]", "");
                    long dpi = Long.parseLong(dpiPuro);
                    String departamento = datos[2];
                    String municipio = datos[3];
                    String dosis = datos[4];
                    String fechaPrimeraVacuna = datos[5];
                    String fechaSegundaVacuna = datos[6];
                    String fechaTerceraVacuna = datos[7];
                    String lugarVacunacion = datos[8];

                    insertarArbol(dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
            }
        } catch(IOException exception) {
        System.out.println(exception.getMessage());
    }
    }

    public void buscar(long dpi) {
        long tiempoInicio = System.nanoTime();
        Nodo persona = buscarNodo(raiz, dpi);
        long tiempoCompletado = System.nanoTime();
        long duracion = (tiempoCompletado - tiempoInicio);
        JOptionPane.showMessageDialog(null, "Nombre: " + persona.nombre + "\n" + "DPI: " + persona.dpi + "\n" + "Departamento: " + persona.departamento + "\n" + "Municipio: " + persona.municipio + "\n" + "Dosis: " + persona.dosis + "\n" + "Fecha primera vacuna: " + persona.fechaPrimeraVacuna + "\n" + "Fecha segunda vacuna: " + persona.fechaSegundaVacuna + "\n" + "Fecha tercera vacuna: " + persona.fechaTerceraVacuna + "\n" + "Lugar de vacunacion: " + persona.lugarVacunacion + "\n" + "Tiempo que duro la busqueda: " + duracion + " nanosegundos", "Persona", JOptionPane.PLAIN_MESSAGE);
    }

    private Nodo buscarNodo(Nodo nodoActual, long dpi) {
        if (nodoActual == null || nodoActual.dpi == dpi) {
            return nodoActual;
        }
        if (dpi < nodoActual.dpi) {
            return buscarNodo(nodoActual.izquierda, dpi);
        }
        return buscarNodo(nodoActual.derecha, dpi);
    }

    void inorder() {
        recorridoInorder(raiz);
    }

    void recorridoInorder(Nodo raiz) {
        if (raiz != null) {
            recorridoInorder(raiz.izquierda);
            String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion + "\n";
            System.out.println(registro);
            recorridoInorder(raiz.derecha);
        }
    }

    void postorder() {
        recorridoPostorder(raiz);
    }

    void recorridoPostorder(Nodo raiz) {
        if (raiz != null) {
            recorridoPostorder(raiz.izquierda);
            recorridoPostorder(raiz.derecha);
            String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion + "\n";
            System.out.print(registro);
        }
    }

    void encriptar() {
        recorridoPreorderEncriptacion(raiz);
        actualizarGUI();
    }

    void recorridoPreorderEncriptacion(Nodo raiz) {
        if (raiz != null) {
            String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion;
            String registroEncriptado = encriptador.encriptar(registro);
            String[] datosEncriptados = registroEncriptado.split("\t");
            raiz.nombre = datosEncriptados[0];
            raiz.departamento = datosEncriptados[2];
            raiz.municipio = datosEncriptados[3];
            raiz.dosis = datosEncriptados[4];
            raiz.fechaPrimeraVacuna = datosEncriptados[5];
            raiz.fechaSegundaVacuna = datosEncriptados[6];
            raiz.fechaTerceraVacuna = datosEncriptados[7];
            raiz.lugarVacunacion = datosEncriptados[8];
            recorridoPreorderEncriptacion(raiz.izquierda);
            recorridoPreorderEncriptacion(raiz.derecha);
        }
    }

    void desencriptar() {
        recorridoPreorderDesencriptacion(raiz);
        actualizarGUI();
    }

    void recorridoPreorderDesencriptacion(Nodo raiz) {
        if (raiz != null) {
            String registroEncriptado = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion;
            String registroDesencriptado = encriptador.desencriptar(registroEncriptado);
            String[] datosDesencriptados = registroDesencriptado.split("\t");

            raiz.nombre = datosDesencriptados[0];
            raiz.departamento = datosDesencriptados[2];
            raiz.municipio = datosDesencriptados[3];
            raiz.dosis = datosDesencriptados[4];
            raiz.fechaPrimeraVacuna = datosDesencriptados[5];
            raiz.fechaSegundaVacuna = datosDesencriptados[6];
            raiz.fechaTerceraVacuna = datosDesencriptados[7];
            raiz.lugarVacunacion = datosDesencriptados[8];

            recorridoPreorderDesencriptacion(raiz.izquierda);
            recorridoPreorderDesencriptacion(raiz.derecha);
        }
    }
}
