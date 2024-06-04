import java.io.File;
import java.io.IOException;
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
        if (dpi < raiz.dpi)
            raiz.izquierda = insertar(raiz.izquierda, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        else if (dpi > raiz.dpi)
            raiz.derecha = insertar(raiz.derecha, dpi, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
        else
            return raiz;

        // 2. Actualizar la altura de cada nodo
        raiz.altura = 1 + max(altura(raiz.izquierda), altura(raiz.derecha));

        // 3.Obtener factores de balance
        int balance = getFactorBalance(raiz);

        // Casos de desbalance

        // Caso izquierda izquierda
        if (balance > 1 && dpi < raiz.izquierda.dpi)
            return rotacionDerecha(raiz);

        // Caso derecha derecha
        if (balance < -1 && dpi > raiz.derecha.dpi)
            return rotacionIzquierda(raiz);

        // Caso izquierda derecha
        if (balance > 1 && dpi > raiz.izquierda.dpi) {
            raiz.izquierda = rotacionIzquierda(raiz.izquierda);
            return rotacionDerecha(raiz);
        }

        // Caso derecha izquierda
        if (balance < -1 && dpi < raiz.derecha.dpi) {
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

    NodoAVL eliminarNodo(NodoAVL raiz, long dpi) {
        // Eliminacion normal
        if (raiz == null)
            return raiz;

        if (dpi < raiz.dpi)
            raiz.izquierda = eliminarNodo(raiz.izquierda, dpi);

        else if (dpi > raiz.dpi)
            raiz.derecha = eliminarNodo(raiz.derecha, dpi);

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
                raiz.derecha = eliminarNodo(raiz.derecha, temp.dpi);
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
}

