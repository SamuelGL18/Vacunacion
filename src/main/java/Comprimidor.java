import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Comprimidor {

    public static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static Map<String, Integer> countTermFrequency(List<String> data) {
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String line : data) {
            String[] terms = line.split("\\s+");
            for (String term : terms) {
                termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
            }
        }
        return termFrequency;
    }

    public static Map<String, String> createDictionary(Map<String, Integer> termFrequency, int maxTerms) {
        Map<String, String> dictionary = new HashMap<>();
        List<Map.Entry<String, Integer>> sortedTerms = termFrequency.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(maxTerms)
                .collect(Collectors.toList());

        for (int i = 0; i < sortedTerms.size(); i++) {
            dictionary.put(sortedTerms.get(i).getKey(), "T" + i);
        }
        return dictionary;
    }

    public static List<String> compressData(List<String> data, Map<String, String> dictionary) {
        List<String> compressedData = new ArrayList<>();
        for (String line : data) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                line = line.replace(entry.getKey(), entry.getValue());
            }
            compressedData.add(line);
        }
        return compressedData;
    }

    public static void saveCompressedData(List<String> data, Map<String, String> dictionary, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                bw.write(entry.getValue() + "=" + entry.getKey());
                bw.newLine();
            }
            bw.newLine();
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> readDictionary(BufferedReader br) throws IOException {
        Map<String, String> dictionary = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                dictionary.put(parts[0], parts[1]);
            }
        }
        return dictionary;
    }

    public static List<String> readCompressedData(BufferedReader br) throws IOException {
        List<String> data = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            data.add(line);
        }
        return data;
    }

    public static List<String> decompressData(List<String> data, Map<String, String> dictionary) {
        List<String> decompressedData = new ArrayList<>();
        for (String line : data) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                line = line.replace(entry.getKey(), entry.getValue());
            }
            decompressedData.add(line);
        }
        return decompressedData;
    }

    public static void saveDecompressedData(List<String> data, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        List<String> data = readFile("arbolBB.txt");
//        Map<String, Integer> termFrequency = countTermFrequency(data);
//        Map<String, String> dictionary = createDictionary(termFrequency, 10); // Adjust maxTerms as needed
//        List<String> compressedData = compressData(data, dictionary);
////        saveCompressedData(compressedData, dictionary, "compressed_data.txt");

        try (BufferedReader br = new BufferedReader(new FileReader("compressed_data.txt"))) {
            Map<String, String> dictionary = readDictionary(br);
            List<String> compressedData = readCompressedData(br);
            List<String> decompressedData = decompressData(compressedData, dictionary);
            saveDecompressedData(decompressedData, "decompressed_data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
