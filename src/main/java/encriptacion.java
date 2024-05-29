public class encriptacion {

        // The substitution table (can be customized)
        private static final String KEY = "ZEBRASCDFGHIJKLMNOPQTUVWXY";

        public static String encrypt(String plaintext) {
            StringBuilder ciphertext = new StringBuilder();

            for (int i = 0; i < plaintext.length(); i++) {
                char currentChar = plaintext.charAt(i);

                if (Character.isLetter(currentChar)) {
                    // Determine the position of the current character in the alphabet (0-based)
                    int originalIndex = Character.toUpperCase(currentChar) - 'A';

                    // Get the corresponding character from the key
                    char substitutedChar = KEY.charAt(originalIndex);

                    // Maintain the original case
                    if (Character.isLowerCase(currentChar)) {
                        substitutedChar = Character.toLowerCase(substitutedChar);
                    }

                    ciphertext.append(substitutedChar);
                } else {
                    // Non-letter characters remain unchanged
                    ciphertext.append(currentChar);
                }
            }
            return ciphertext.toString();
        }

        public static String decrypt(String ciphertext) {
            StringBuilder plaintext = new StringBuilder();

            for (int i = 0; i < ciphertext.length(); i++) {
                char currentChar = ciphertext.charAt(i);

                if (Character.isLetter(currentChar)) {
                    // Determine the position of the current character in the key
                    int keyIndex = KEY.indexOf(Character.toUpperCase(currentChar));

                    // Get the corresponding character from the alphabet
                    char originalChar = (char) ('A' + keyIndex);

                    // Maintain the original case
                    if (Character.isLowerCase(currentChar)) {
                        originalChar = Character.toLowerCase(originalChar);
                    }

                    plaintext.append(originalChar);
                } else {
                    plaintext.append(currentChar);
                }
            }

            return plaintext.toString();
        }

        public static void main(String[] args) {
            String plaintext = "HELLO WORLD!";
            String ciphertext = encrypt(plaintext);
            String decrypted = decrypt(ciphertext);

            System.out.println("Plaintext:  " + plaintext);
            System.out.println("Ciphertext: " + ciphertext);
            System.out.println("Decrypted:  " + decrypted);
        }
}
