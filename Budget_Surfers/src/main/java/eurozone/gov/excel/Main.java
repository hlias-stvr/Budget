package eurozone.gov.excel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[][] revenue = ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        String[][] budget  = ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        String[][] gdppop  = ReadTwoCsvFiles.readCsv("/Gdp_population_euz.csv"); 
        Choices ch = new Choices();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do { // βασικό μενού επιλογών
            System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
            System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
            System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
            System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
            System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
            System.out.println("6 για σύγκριση φορολογικών εσόδων αναλογικά με τον μέσο όρο της Ευρωζώνης");
            System.out.println("7 για επεξεργασία στοιχείων προϋπολογισμού");
            System.out.println("8 για προβολή ιστορικού αλλαγών");
            System.out.println("0 για έξοδο");
            
            while (true) { // μεχρι ο χρηστης να δώσει σωστή τιμή
                try {
                    choice = scanner.nextInt(); 
                    if (choice < 0 || choice > 8) { // έλεγχος για τις βασικές επιλογές 
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι από 0 μέχρι 8");
                    }
                    if (choice == 0) { 
                        System.out.println("Έξοδος από το πρόγραμμα");
                        break;
                    } else if (choice == 1) {        
                        ch.choice1(revenue, budget);
                    } else if (choice == 2) {
                        ch.choice2(budget, scanner);
                    } else if (choice == 3) {
                        ch.choice3(revenue, scanner);
                    } else if (choice == 4) {
                        ch.choice4(gdppop, scanner);
                    } else if (choice == 5) {
                        ch.choice5(budget, scanner);
                    } else if (choice == 6) {
                        ch.choice6(revenue);
                    } else if (choice == 7) {
                        ch.choice7(revenue, budget, scanner);
                    } else if (choice == 8) {
                        ChangesHistory.printAll();
                    }
                    break;
                } catch (IllegalArgumentException e){
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice != 0);
        scanner.close();
    }
}
