package eurozone.gov.excel;
import java.util.Scanner;

public class Choices {
    public void choice1(String[][] revenue, String[][] budget) {
        System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
        ReadTwoCsvFiles.printFirstRows(revenue, 33);
        System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
        ReadTwoCsvFiles.printFirstRows(budget,35);           
    }
    public void choice2(String[][] budget, Scanner scanner) {
        long [] A = AvgEurozone.convertToLong(budget);
        double [] B = AvgEurozone.ministrDiv(A);
        double [] C = AvgEurozone.compareGrToEurozone(B);
        String[] grSectors = AvgEurozone.sectors();
        int choice2 = -1;
        do { 
            System.out.println("Επίλεξε 1 για να δείς τις ποσοστιάιες δαπάνες της Ελλάδας ανά τομέα");
            System.out.println("2 για να τις συγκρίνεις με τους τομείς της Ευρωζώνης");
            System.out.println("0 για πίσω");
            // υποεπιλογές για την επιλογή 2 
            while (true) {
                // μέχρι να δωθεί έγκυρη τιμή
                try {
                    choice2 = scanner.nextInt();
                    if (choice2 < 0 || choice2 > 2) { //έλεγχος για τις υποεπιλογές 
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2 ή 0");
                    }
                    if (choice2 == 1) {
                        for(int i = 0; i < 11; i++) {
                            System.out.println("Η Ελλάδα δαπανά " + B[i] + "%" + " στον τομέα " + grSectors[i]);
                        }
                    } else if (choice2 == 2) {
                        for(int i = 0; i < 11; i++) {
                            if(C[i] > 0) {
                                System.out.println("Η Ελλάδα δαπανά "+ C[i] + "% λιγότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
                            } else if(C[i] < 0) {
                                System.out.println("Η Ελλάδα δαπανά "+ Math.abs(C[i]) + "% περισσότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
                            } else {
                                System.out.println("Η Ελλάδα δαπανά το ίδιο ποσοστό στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
                            }
                        }
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice2 != 0);
        // μέχρι να δωθεί το 0 για πίσω    
    }
}