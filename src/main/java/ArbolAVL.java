import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class NodoAVL {
    long dpi;
    int altura;
    String nombre;
    NodoAVL izquierda, derecha;
    String departamento;
    String municipio;
    String dosis;
    String fechaPrimeraVacuna;
    String fechaSegundaVacuna;
    String fechaTerceraVacuna;
    String lugarVacunacion;

    NodoAVL(long dpi, String nombre) {
        altura = 1;
        this.dpi = dpi;
        this.nombre = nombre;
        izquierda = derecha = null;
        departamento = municipio = dosis = fechaPrimeraVacuna = fechaSegundaVacuna = fechaTerceraVacuna = lugarVacunacion = "Vacio";
    }
}

public class ArbolAVL {

    private NodoAVL raiz;
    EncriptacionSustitucion encriptador;
    Comprimidor comprimidor;

    public ArbolAVL() {
        raiz = null;
        encriptador = new EncriptacionSustitucion();
        comprimidor = new Comprimidor();
        cargarArbol();
    }

    // Get altura
    int altura(NodoAVL nodo) {
        if (nodo == null)
            return 0;
        return nodo.altura;
    }

    // Obtener el subarbol con mayor altura
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Rotacion derecha
    NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;

        // Rotacion
        x.derecha = y;
        y.izquierda = T2;

        // Update heights
        y.altura = max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = max(altura(x.izquierda), altura(x.derecha)) + 1;

        return x;
    }

    // Rotacion izquierda
    NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;

        // Hacer rotacion
        y.izquierda = x;
        x.derecha = T2;

        // Actualizar alturas
        x.altura = max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = max(altura(y.izquierda), altura(y.derecha)) + 1;

        return y;
    }

    // Get el factor de balance
    int getFactorBalance(NodoAVL nodo) {
        if (nodo == null)
            return 0;
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    // Solo para inicializar datos
    public void insertarArbol(long dpi, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        raiz = insertar(raiz, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
    }

    // Para inicializar datos
    NodoAVL insertar(NodoAVL raiz, long dpi, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
        if (raiz == null) {
            raiz = new NodoAVL(dpi, nombre);
            raiz.departamento = departamento;
            raiz.municipio = municipio;
            raiz.dosis = dosis;
            raiz.fechaPrimeraVacuna = fechaPrimeraVacuna;
            raiz.fechaSegundaVacuna = fechaSegundaVacuna;
            raiz.fechaTerceraVacuna = fechaTerceraVacuna;
            raiz.lugarVacunacion = lugarVacunacion;
            return raiz;
        }
        if (nombre.compareTo(raiz.nombre) < 0)
            raiz.izquierda = insertar(raiz.izquierda, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        else if (nombre.compareTo(raiz.nombre) > 0)
            raiz.derecha = insertar(raiz.derecha, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        else
            return raiz;

        // 2. Actualizar la altura de cada nodo
        raiz.altura = 1 + max(altura(raiz.izquierda), altura(raiz.derecha));

        // 3.Obtener factores de balance
        int balance = getFactorBalance(raiz);

        // Casos de desbalance

        // Caso izquierda izquierda
        if (balance > 1 && nombre.compareTo(raiz.izquierda.nombre) < 0)
            return rotacionDerecha(raiz);

        // Caso derecha derecha
        if (balance < -1 && nombre.compareTo(raiz.derecha.nombre) > 0)
            return rotacionIzquierda(raiz);

        // Caso izquierda derecha
        if (balance > 1 && nombre.compareTo(raiz.izquierda.nombre) > 0) {
            raiz.izquierda = rotacionIzquierda(raiz.izquierda);
            return rotacionDerecha(raiz);
        }

        // Caso derecha izquierda
        if (balance < -1 && nombre.compareTo(raiz.derecha.nombre) < 0) {
            raiz.derecha = rotacionDerecha(raiz.derecha);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    // Obteniendo el reemplazo
    NodoAVL minimo(NodoAVL raiz) {
        NodoAVL actual = raiz;

        // Encontrar al que esta mas a la izquierda
        while (actual.izquierda != null)
            actual = actual.izquierda;

        return actual;
    }

    public void eliminar(String nombre) {
        raiz = eliminarNodo(raiz, nombre);
        actualizarGUI();
    }

    NodoAVL eliminarNodo(NodoAVL raiz, String nombre) {
        // Eliminacion normal
        if (raiz == null)
            return raiz;

        if (nombre.compareTo(raiz.nombre) < 0)
            raiz.izquierda = eliminarNodo(raiz.izquierda, nombre);

        else if (nombre.compareTo(raiz.nombre) > 0)
            raiz.derecha = eliminarNodo(raiz.derecha, nombre);

        else {
            // Hoja o solo un hijo
            if ((raiz.izquierda == null) || (raiz.derecha == null)) {
                NodoAVL temp = null;
                if (temp == raiz.izquierda)
                    temp = raiz.derecha;
                else
                    temp = raiz.izquierda;

                // Hoja
                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else // Un solo hijo
                    raiz = temp;
            } else {
                // Caso con dos hijos
                NodoAVL temp = minimo(raiz.derecha);

                // Reemplazando datos
                raiz.dpi = temp.dpi;
                raiz.nombre = temp.nombre;
                raiz.departamento = temp.departamento;
                raiz.municipio = temp.municipio;
                raiz.dosis = temp.dosis;
                raiz.fechaPrimeraVacuna = temp.fechaPrimeraVacuna;
                raiz.fechaSegundaVacuna = temp.fechaSegundaVacuna;
                raiz.fechaTerceraVacuna = temp.fechaTerceraVacuna;
                raiz.lugarVacunacion = temp.lugarVacunacion;

                // Eliminar
                raiz.derecha = eliminarNodo(raiz.derecha, temp.nombre);
            }
        }

        // Caso un nodo
        // Repetir insertar
        if (raiz == null)
            return raiz;

        // Actualizar la altura
        raiz.altura = max(altura(raiz.izquierda), altura(raiz.derecha)) + 1;

        // Obtener factor balance
        int balance = getFactorBalance(raiz);

        // Casos de desbalance

        // Caso izquierda izquierda
        if (balance > 1 && getFactorBalance(raiz.izquierda) >= 0)
            return rotacionDerecha(raiz);

        // Caso izquierda derecha
        if (balance > 1 && getFactorBalance(raiz.izquierda) < 0) {
            raiz.izquierda = rotacionIzquierda(raiz.izquierda);
            return rotacionDerecha(raiz);
        }

        // Caso derecha derecha
        if (balance < -1 && getFactorBalance(raiz.derecha) <= 0)
            return rotacionIzquierda(raiz);

        // Caso Derecha izquierda
        if (balance < -1 && getFactorBalance(raiz.derecha) > 0) {
            raiz.derecha = rotacionDerecha(raiz.derecha);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    public void cargarArbol() {
        try (Scanner scanner = new Scanner(new File("arbolAVL.txt"))) {
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

    public void preorderArbol() {
        recorridoPreorderArbol(raiz);
    }

    public void recorridoPreorderArbol(NodoAVL raiz) {
        if (raiz != null) {
            String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion;
            System.out.println(registro);
            recorridoPreorderArbol(raiz.izquierda);
            recorridoPreorderArbol(raiz.derecha);
        }
    }

    // Inserta a tabla, archivo de texto, archivo dot
    public void insertarGUI(long dpi, String nombre) {
        raiz = insertarNodo(raiz, dpi, nombre);
        actualizarGUI();
    }

    // Cargar los datos anteriores
    NodoAVL insertarNodo(NodoAVL raiz, long dpi, String nombre) {
        if (raiz == null) {
            raiz = new NodoAVL(dpi, nombre);
            return raiz;
        }
        if (nombre.compareTo(raiz.nombre) < 0) {
            raiz.izquierda = insertarNodo(raiz.izquierda, dpi, nombre);
        } else if (nombre.compareTo(raiz.nombre) > 0) {
            raiz.derecha = insertarNodo(raiz.derecha, dpi, nombre);
        }

        // 2. Actualizar la altura de cada nodo
        raiz.altura = 1 + max(altura(raiz.izquierda), altura(raiz.derecha));

        // 3.Obtener factores de balance
        int balance = getFactorBalance(raiz);

        // Casos de desbalance

        // Caso izquierda izquierda
        if (balance > 1 && nombre.compareTo(raiz.izquierda.nombre) < 0)
            return rotacionDerecha(raiz);

        // Caso derecha derecha
        if (balance < -1 && nombre.compareTo(raiz.derecha.nombre) > 0)
            return rotacionIzquierda(raiz);

        // Caso izquierda derecha
        if (balance > 1 && nombre.compareTo(raiz.izquierda.nombre) > 0) {
            raiz.izquierda = rotacionIzquierda(raiz.izquierda);
            return rotacionDerecha(raiz);
        }

        // Caso derecha izquierda
        if (balance < -1 && nombre.compareTo(raiz.derecha.nombre) < 0) {
            raiz.derecha = rotacionDerecha(raiz.derecha);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    // Actualiza tabla, archivo de texto y archivo dot
    public void actualizarGUI() {
        try {
            FileWriter arbolTxt = new FileWriter("arbolAVL.txt");
            FileWriter declaracionDot = new FileWriter("dotDeclaracionesAVL.txt");
            FileWriter relacionesDot = new FileWriter("dotRelacionesAVL.txt");
            FileWriter archivoDot = new FileWriter("arbolAVL.dot");
            archivoDot.append("digraph G {\n");
            recorridoPreorderGUI(raiz, arbolTxt, declaracionDot, relacionesDot);
            declaracionDot.close();
            relacionesDot.close();
            arbolTxt.close();
            Path declaracionesRuta = Path.of("dotDeclaracionesAVL.txt");
            String declaraciones = Files.readString(declaracionesRuta);
            archivoDot.append(declaraciones + "\n");
            Path relacionesRuta = Path.of("dotRelacionesAVL.txt");
            String relaciones = Files.readString(relacionesRuta);
            archivoDot.append(relaciones + "\n" + "}");
            archivoDot.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Escribe en archivo texto y dot
    public void recorridoPreorderGUI(NodoAVL raiz, FileWriter arbolTxt, FileWriter declaracionDot, FileWriter relacionesDot) {
        try {
            if (raiz != null) {
                String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.lugarVacunacion + "\n";
                arbolTxt.append(registro);
                String dpiConvertido = "" + raiz.dpi;
                declaracionDot.append("\t" + raiz.nombre + " [label=\"" + "Nombre: " + raiz.nombre + "\n" + "DPI: " + raiz.dpi + "\n" + "Departamento: " + raiz.departamento + "\n" + "Municipio: " + raiz.municipio + "\n" + "Dosis: " + raiz.dosis + "\n" + "Primera vacuna" + raiz.fechaPrimeraVacuna + "\n" + "Segunda vacuna" + raiz.fechaSegundaVacuna + "\n" + "Tercera vacuna: " + raiz.fechaTerceraVacuna + "\n" + "Lugar vacunacion: " + raiz.lugarVacunacion + "\"]\n");

                // Estableciendo relaciones en el documento dot
                // Manejando los distintos casos
                if (raiz.derecha != null && raiz.izquierda != null) {
                    relacionesDot.append("\t" + raiz.nombre + " -> " + raiz.izquierda.nombre + "\n");
                    relacionesDot.append("\t" + raiz.nombre + " -> " + raiz.derecha.nombre + "\n");
                } else if(raiz.derecha == null && raiz.izquierda != null) {
                    relacionesDot.append("\t" + raiz.nombre + " -> " + raiz.izquierda.nombre + "\n");
                } else if(raiz.izquierda == null && raiz.derecha != null) {
                    relacionesDot.append("\t" + raiz.nombre + " -> " + raiz.derecha.nombre + "\n");
                }
                recorridoPreorderGUI(raiz.izquierda, arbolTxt, declaracionDot, relacionesDot);
                recorridoPreorderGUI(raiz.derecha, arbolTxt, declaracionDot, relacionesDot);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void buscar(long dpi) {
        long tiempoInicio = System.nanoTime();
        NodoAVL persona = buscarNodo(raiz, dpi);
        long tiempoCompletado = System.nanoTime();
        long duracion = (tiempoCompletado - tiempoInicio);
        JOptionPane.showMessageDialog(null, "Nombre: " + persona.nombre + "\n" + "DPI: " + persona.dpi + "\n" + "Departamento: " + persona.departamento + "\n" + "Municipio: " + persona.municipio + "\n" + "Dosis: " + persona.dosis + "\n" + "Fecha primera vacuna: " + persona.fechaPrimeraVacuna + "\n" + "Fecha segunda vacuna: " + persona.fechaSegundaVacuna + "\n" + "Fecha tercera vacuna: " + persona.fechaTerceraVacuna + "\n" + "Lugar de vacunacion: " + persona.lugarVacunacion + "\n" + "Tiempo que duro la busqueda: " + duracion + " nanosegundos", "Persona", JOptionPane.PLAIN_MESSAGE);
    }

    private NodoAVL buscarNodo(NodoAVL nodoActual, long dpi) {
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

    void recorridoInorder(NodoAVL raiz) {
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

    void recorridoPostorder(NodoAVL raiz) {
        if (raiz != null) {
            recorridoPostorder(raiz.izquierda);
            recorridoPostorder(raiz.derecha);
            String registro = raiz.nombre + "\t" + raiz.dpi + "\t" + raiz.departamento + "\t" + raiz.municipio + "\t" + raiz.dosis + "\t" + raiz.fechaPrimeraVacuna + "\t" + raiz.fechaSegundaVacuna + "\t" + raiz.fechaTerceraVacuna + "\t" + raiz.lugarVacunacion + "\n";
            System.out.print(registro);
        }
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

    boolean editarNodo(NodoAVL raiz, long dpi, long dpiNuevo, String nombre, String departamento, String municipio, String dosis, String fechaPrimeraVacuna, String fechaSegundaVacuna, String fechaTerceraVacuna, String lugarVacunacion) {
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

    void encriptar() {
        recorridoPreorderEncriptacion(raiz);
        actualizarGUI();
    }

    void recorridoPreorderEncriptacion(NodoAVL raiz) {
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

    void recorridoPreorderDesencriptacion(NodoAVL raiz) {
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

    public void comprimir() {
        comprimidor.comprimirArchivo("arbolAVL.txt", "arbolAVL_comprimido.txt");
    }

    public void descomprimir() {
        comprimidor.descomprimirArchivo("arbolAVL_comprimido.txt", "arbolAVL_descomprimido.txt");
    }
}

