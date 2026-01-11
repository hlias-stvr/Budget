package eurozone.gov.excel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReadTwoCsvFiles {
    //Διάβασμα CSV σε String[][]
    static String[][] readCsv(String resourcePath) {
        var rows = new ArrayList<String[]>();
        try (var is = ReadTwoCsvFiles.class.getResourceAsStream(resourcePath);
                var br = new BufferedReader(new
                    InputStreamReader(is, StandardCharsets.UTF_8))) {
            br.readLine(); // Παραλείπει header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";", -1);
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim().replace("\"", "");
                }
                rows.add(parts);
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα στο " +
                resourcePath + ": " + e.getMessage());
        }
        return rows.toArray(new String[0][]);
    }

    //εκτύπωση πρώτων γραμμών
    static void printFirstRows(String[][] data, int n) {
        for (int i = 0; i < Math.min(n, data.length); i++) {
            StringBuilder sb = new StringBuilder();
            for (String cell : data[i]) {
                sb.append(cell).append(" | ");
            }
            System.out.println(sb);
        }
    }
}
