    package eurozone.gov.excel;

    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.Scanner;

    public class ReadTwoCsvFiles {

        public static void main(String[] args) {
            String file1 = "src\\main\\resourses\\gr_revenue_expenses_25.csv";
            String file2 = "src\\main\\resourses\\gr_ministy_25.csv";
            String file3 = "src\\main\\resourses\\Gdp_population_euz.csv";

            String[][] revenue = readCsv(file1);   
            String[][] budget = readCsv(file2);
            String[][] gdppop = readCsv(file3);   

            System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
            printFirstRows(revenue, 33);

            System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
            printFirstRows(budget,35);

            System.out.println("\n=== ΑΡΧΕΙΟ 3: Gdp_population_euz.csv ===");
            printFirstRows(gdppop,20);

            long [][] D = EuzLivingStandard.compareToLong(gdppop);
            double [] E = EuzLivingStandard.findStandLiving(D);
            for (int i = 0; i<E.length; i++) {
            System.out.println(E[i]);
            }
        
           Scanner scanner1 = new Scanner(System.in);
           int a = -1;
           
           while (true) { //μέχρι να δώσει ο χρήστης σωστή τιμή
            try {
             System.out.println("Δώσε αριθμό για την χώρα που θες να συγκρίνεις με την Ελλάδα");
             a = scanner1.nextInt();
            if (a < 1 || a > 20) {
                throw new IllegalArgumentException(" Ο αριθμός πρέπει να είναι από 1 μέχρι 20");
            }
            System.out.println("Έβαλες τον αριθμό" + a);
            EuzLivingStandard.compareStdLive(a,E,gdppop );
            
            scanner1.close();
            
            
            break;
            } catch(IllegalArgumentException e) { 
                System.out.println("Σφάλμα" + e.getMessage());

            } catch (Exception e) {
                System.out.println("Πρέπει να δώσεις αριθμό");
                scanner1.nextLine(); //καθάρισμα εισόδου
            }
                
                
            }
            
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
