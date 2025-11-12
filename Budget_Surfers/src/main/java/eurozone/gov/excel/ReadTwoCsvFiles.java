package eurozone.gov.excel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReadTwoCsvFiles {

    public static void main(String[] args) {
        
        String file1 = "src\\main\\resourses\\gr_revenue_expenses_25.csv";
        String file2 = "src\\main\\resourses\\gr_ministy_25.csv";

        String[][] revenue = readCsv(file1);   
        String[][] budget = readCsv(file2);   

        System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
        printFirstRows(revenue, 30);

        System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
        printFirstRows(budget,34);
    }

    // --- ΜΕΘΟΔΟΣ: Διάβασμα CSV σε String[][] ---
    static String[][] readCsv(String path) {
        var rows = new ArrayList<String[]>();
        try (var br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            br.readLine(); // Παραλείπει header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Παραλείπει κενές γραμμές

                String[] parts = line.split(";", -1); // Χωρίζει με ;
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim().replace("\"", ""); // Καθαρίζει
                }
                rows.add(parts);
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα στο " + path + ": " + e.getMessage());
        }
        return rows.toArray(new String[0][]);
    }

    // --- ΕΚΤΥΠΩΣΗ ΠΡΩΤΩΝ ΓΡΑΜΜΩΝ ---
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