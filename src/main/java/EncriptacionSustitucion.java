import java.util.HashMap;
import java.util.Random;

public class EncriptacionSustitucion {

        private HashMap<Character, Character> mapa;

        public EncriptacionSustitucion() {
            mapa = crearMapa();
        }

        // Generar el diccionario
        private HashMap<Character, Character> crearMapa() {
            HashMap<Character, Character> mapa = new HashMap<>();
            mapa.put('/', 'R');
            mapa.put('0', 'T');
            mapa.put('1', 'E');
            mapa.put('2', 'I');
            mapa.put('3', 'u');
            mapa.put('4', 'y');
            mapa.put('5', 'ú');
            mapa.put('6', '6');
            mapa.put('7', 'f');
            mapa.put('8', 'M');
            mapa.put('9', 'o');
            mapa.put('A', 'Z');
            mapa.put('Á', '1');
            mapa.put('B', 'b');
            mapa.put('C', 'r');
            mapa.put('D', 'q');
            mapa.put('E', 'C');
            mapa.put('F', '9');
            mapa.put('G', 'V');
            mapa.put('H', 'g');
            mapa.put('I', 'd');
            mapa.put('É', 'z');
            mapa.put('J', 'Y');
            mapa.put('K', 'w');
            mapa.put('L', 'c');
            mapa.put('M', 'S');
            mapa.put('Í', '5');
            mapa.put('N', 'P');
            mapa.put('O', 'O');
            mapa.put('P', 'U');
            mapa.put('Q', 'Á');
            mapa.put('R', '/');
            mapa.put('S', 'X');
            mapa.put('Ó', 'h');
            mapa.put('T', 'J');
            mapa.put('U', '7');
            mapa.put('V', '4');
            mapa.put('W', 'é');
            mapa.put('X', 'k');
            mapa.put('Y', 'É');
            mapa.put('Z', 'L');
            mapa.put('Ú', 'K');
            mapa.put('a', 'Q');
            mapa.put('á', '2');
            mapa.put('b', 'W');
            mapa.put('c', 'Í');
            mapa.put('d', 'i');
            mapa.put('e', 'm');
            mapa.put('f', 'l');
            mapa.put('g', 'F');
            mapa.put('h', 'n');
            mapa.put('i', 'H');
            mapa.put('é', 'Ú');
            mapa.put('j', 'B');
            mapa.put('k', 'x');
            mapa.put('l', 'D');
            mapa.put('m', 's');
            mapa.put('í', 'G');
            mapa.put('n', 'N');
            mapa.put('o', 't');
            mapa.put('p', 'ó');
            mapa.put('q', '3');
            mapa.put('r', 'v');
            mapa.put('s', 'e');
            mapa.put('ó', 'á');
            mapa.put('t', '8');
            mapa.put('u', 'í');
            mapa.put('v', 'a');
            mapa.put('w', '0');
            mapa.put('x', 'j');
            mapa.put('y', 'Ó');
            mapa.put('z', 'p');
            mapa.put('ú', 'A');
            return mapa;
        }

        public String encriptar(String textoOriginal) {
            StringBuilder textoEncriptado = new StringBuilder();

            // Iterar por cada letra
            for (char c : textoOriginal.toCharArray()) {
                // Obtiene letra totalmente al azar
                Character letraEncriptada = mapa.get(c);
                textoEncriptado.append(letraEncriptada != null ? letraEncriptada : c); // Si es un espacio no se hace nada
            }
            return textoEncriptado.toString();
        }

        public String desencriptar(String textoEncriptado) {
            StringBuilder textoDesencriptado = new StringBuilder();

            // Para obtener la letra original
            HashMap<Character, Character> mapaInverso = new HashMap<>();

            // Invertir el mapa
            for (char c : mapa.keySet()) {
                mapaInverso.put(mapa.get(c), c);
            }

            // Obteniedo la letra a la que equivale
            for (char c : textoEncriptado.toCharArray()) {
                Character letraDesencriptada = mapaInverso.get(c);
                textoDesencriptado.append(letraDesencriptada != null ? letraDesencriptada : c); // Por si hay espacio vacio
            }
            return textoDesencriptado.toString();
        }
}
