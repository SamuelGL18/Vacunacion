import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Comprimidor {

    Map<String, String> diccionario;

    public void comprimirArchivo(String rutaArchivo, String nombreArchivoComprimido) {
        // Obtener el archivo original
        List<String> contenidoOriginal = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenidoOriginal.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cantidad de veces que aparece cada palabra del archivo original
        Map<String, Integer> frecuenciasPalabras= obtenerFrecuencias(contenidoOriginal);

        // Crear el diccionario. Se escogen solo las 10 palabras mas frecuentes
        diccionario = crearDiccionario(frecuenciasPalabras, 10);

        // Comprimir el contenido original
        List<String> contenidoComprimido = comprimirDatos(contenidoOriginal, diccionario);

        // Crear archivo
        crearArchivoComprimido(contenidoComprimido, nombreArchivoComprimido);
    }

    private Map<String, Integer> obtenerFrecuencias(List<String> archivoOriginal) {
        Map<String, Integer> frecuenciasPalabras = new HashMap<>();
        for (String linea : archivoOriginal) {
            String[] palabras = linea.split("\\s+");
            for (String palabra : palabras) {
                // Contando la frecuencia en que aparecen a CADA palabra
                frecuenciasPalabras.put(palabra, frecuenciasPalabras.getOrDefault(palabra, 0) + 1);
            }
        }
        return frecuenciasPalabras;
    }

    private Map<String, String> crearDiccionario(Map<String, Integer> frecuenciaPalabras, int palabrasMaximas) {
        // Crea el diccionario en orden descendente y solo con el maximo especificado
        Map<String, String> diccionario = new HashMap<>();
        List<Map.Entry<String, Integer>> palabrasFitradas = frecuenciaPalabras.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(palabrasMaximas)
                .collect(Collectors.toList());

        // Agrega el codigo de cada palabra
        for (int i = 0; i < palabrasFitradas.size(); i++) {
            diccionario.put(palabrasFitradas.get(i).getKey(), "T" + i);
        }

        return diccionario;
    }

    private List<String> comprimirDatos(List<String> contenidoOriginal, Map<String, String> diccionario) {
        List<String> contenidoComprimido = new ArrayList<>();
        for (String linea : contenidoOriginal) {
            // Reemplazar cada palabra frecuencia segun el dicciionario por su codigo.
            for (Map.Entry<String, String> entradaDiccionario : diccionario.entrySet()) {
                linea = linea.replace(entradaDiccionario.getKey(), entradaDiccionario.getValue());
            }
            contenidoComprimido.add(linea);
        }
        return contenidoComprimido;
    }

    private void crearArchivoComprimido(List<String> contenidoComprimido, String nombreArchivoComprimido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivoComprimido))) {
            for (String line : contenidoComprimido) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void descomprimirArchivo(String rutaArchivoComprimido, String nombreArchivoDescomprimido) {
        // Obteniendo el contenido comprimido
        List<String> contenidoComprimido = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoComprimido))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenidoComprimido.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cambia las abreviaciones por el contenido original
        List<String> contenidoDescomprimido =  descomprimirDatos(contenidoComprimido);

        // Genera el archivo descomprimido
        crearArchivoDescomprimido(contenidoDescomprimido, nombreArchivoDescomprimido);
    }

    private List<String> descomprimirDatos(List<String> contenidoComprimido) {
        List<String> contenidoDescomprimido = new ArrayList<>();
        for (String linea : contenidoComprimido) {
            for (Map.Entry<String, String> entradaDiccionario : diccionario.entrySet()) {
                // Cambia el codigo por su valor original
                linea = linea.replace(entradaDiccionario.getValue(), entradaDiccionario.getKey());
            }
            contenidoDescomprimido.add(linea);
        }
        return contenidoDescomprimido;
    }

    private void crearArchivoDescomprimido(List<String> contenidoDescomprimido, String nombreArchivoDescomprimido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivoDescomprimido))) {
            for (String linea : contenidoDescomprimido) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
