import java.util.HashMap;
import java.util.Random;

public class encriptacionSustitucion {

        private final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789áéíóúÁÉÍÓÚ";

        private HashMap<Character, Character> mapa;

        public encriptacionSustitucion() {
            mapa = crearMapa();
            System.out.println(mapa);
        }

        // Crear mapa randomizado
        private HashMap<Character, Character> crearMapa() {
            HashMap<Character, Character> mapa = new HashMap<>();
            char[] alfabetoRandomizado = ALFABETO.toCharArray();
            randomizarArray(alfabetoRandomizado);

            for (int i = 0; i < ALFABETO.length(); i++) {
                mapa.put(ALFABETO.charAt(i), alfabetoRandomizado[i]);
            }
            return mapa;
        }

        // Randomiza el mapa
        private void randomizarArray(char[] array) {
            Random rand = new Random();
            for (int i = array.length - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                char temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
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
