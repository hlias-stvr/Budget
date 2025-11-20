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
           int choice = -1;
           do {
            System.out.println("Επίλεξε 1 για προβολή στοιχείων κρατικού πεουπολογισμού");
            System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τουσ μέσους όρους της Ευρωζώνης");
            System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
            System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
            System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
            System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
            System.out.println("0 για έξοδο");
            Scanner scanner2 = new Scanner(System.in);
            choice = scanner2.nextInt(); 
            if (choice == 0) {
                System.out.println("Έξοδος από το πρόγραμμα");
                break;
            } else if (choice == 1) {
                System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
            printFirstRows(revenue, 33);

            System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
            printFirstRows(budget,35);
            break;
            } else if (choice ==2) {
                long [] A = avgeurozone.convertToLong(budget);
                double [] B = avgeurozone.ministrDiv(A);
                double [] C = avgeurozone.compareGrToEurozone(B);
                String[] grSectors = avgeurozone.sectors();
                System.out.println("Επίλεξε 1 για να δείς τις ποσοστιάιες δαπάνες της Ελλάδας ανά τομέα");
                System.out.println("2 για να τις συγκρίνεις με τους τομείς της Ευρωζώνης");
                Scanner scanner3 = new Scanner(System.in);
                int choice2 = scanner3.nextInt();
                if (choice2 == 1) {
                    for(int i = 0; i < 11; i++) {
                        System.out.println("Η Ελλάδα δαπανεί " + B[i] + "%"+" στον τομέα "+grSectors[i]);
                    }
                } else if (choice2 == 2) {
                    for(int i = 0; i < 11; i++) {
                        if(C[i] > 0) {
                        System.out.println("Η Ελλάδα δαπανεί "+ C[i] + "% λιγότερο στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                        } else if(C[i] < 0) {
                            System.out.println("Η Ελλάδα δαπανεί "+ Math.abs(C[i]) + "% περισσότερο στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                        } else {
                            System.out.println("Η Ελλάδα δαπανεί το ίδιο ποσοστό στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                        }
                    }
                } else if (choice == 3) {
                    long[][] s = percent.converterToLong(revenue, 14,2);
                    long[][] f = percent.converterToLong(revenue, 16, 16);
                    double[][] g = percent.percentual(s);
                    double[][] h = percent.percentual(f);
                    long[][] n = percent.amount(s);
                    long[][] m = percent.amount(f);
                    System.out.println("Επίλεξε 1 για να δεις τα ποσά ανά έτος");
                    System.out.println("Επίλεξε 2 για να δεις τα ποσοστά ανά έτος");
                    Scanner scanner4 = new Scanner(System.in);
                    int choice3 = scanner4.nextInt();
                    
                }   
                break;
             }
        } while (choice != 0);
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
