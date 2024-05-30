import java.util.HashMap;
import java.util.Random;

public class encriptacion {


        private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789áéíóúÁÉÍÓÚ";

        private static HashMap<Character, Character> generateKey() {
            HashMap<Character, Character> key = new HashMap<>();
            char[] shuffledAlphabet = ALPHABET.toCharArray();
            shuffleArray(shuffledAlphabet);

            for (int i = 0; i < ALPHABET.length(); i++) {
                key.put(ALPHABET.charAt(i), shuffledAlphabet[i]);
            }

            return key;
        }

        // Fisher-Yates shuffle to randomize the alphabet
        private static void shuffleArray(char[] array) {
            Random rand = new Random();
            for (int i = array.length - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                char temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }

        public static String encrypt(String plaintext, HashMap<Character, Character> key) {
            StringBuilder ciphertext = new StringBuilder();

            for (char c : plaintext.toCharArray()) {
                Character encryptedChar = key.get(c);
                ciphertext.append(encryptedChar != null ? encryptedChar : c); // Keep unmapped characters
            }

            return ciphertext.toString();
        }

        public static String decrypt(String ciphertext, HashMap<Character, Character> key) {
            StringBuilder plaintext = new StringBuilder();
            HashMap<Character, Character> reverseKey = new HashMap<>(); // Create reverse key

            for (char c : key.keySet()) {
                reverseKey.put(key.get(c), c);
            }

            for (char c : ciphertext.toCharArray()) {
                Character decryptedChar = reverseKey.get(c);
                plaintext.append(decryptedChar != null ? decryptedChar : c); // Keep unmapped characters
            }

            return plaintext.toString();
        }


        public static void main(String[] args) {
            HashMap<Character, Character> key = generateKey();
            String plaintext = "Natalia Maucelia Tomas García 1766455351302";

            String ciphertext = encrypt(plaintext, key);
            System.out.println("Ciphertext: " + ciphertext);

            String decrypted = decrypt(ciphertext, key);
            System.out.println("Decrypted: " + decrypted);
        }
}
