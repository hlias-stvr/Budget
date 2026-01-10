package eurozone.gov.excel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[][] revenue =
         ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        String[][] budget  = ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        String[][] gdppop  = ReadTwoCsvFiles.readCsv("/Gdp_population_euz.csv");
        Choices ch = new Choices();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do { // βασικό μενού επιλογών
            System.out.println("Επίλεξε:\n1 για προβολή στοιχείων" +
                " κρατικού προϋπολογισμού");
            System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά" +
                " τομέα με τους μέσους όρους της Ευρωζώνης");
            System.out.println("3 για σύγκριση του" +
                " προϋπολογισμού τα τελευτάια 5 έτη");
            System.out.println("4 για σύγκριση βιοτικού" +
                " επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
            System.out.println("5 για ανάλυση ποσοστιαίων" +
                " δαπανών ανά περιφέρεια");
            System.out.println("6 για σύγκριση φορολογικών" +
                " εσόδων αναλογικά με τον μέσο όρο της Ευρωζώνης");
            System.out.println("7 για επεξεργασία στοιχείων προϋπολογισμού");
            System.out.println("8 για προβολή ιστορικού αλλαγών");
            System.out.println("0 για έξοδο");

            while (true) { // μεχρι ο χρηστης να δώσει σωστή τιμή
                try {
                    choice = scanner.nextInt();
                    if (choice < 0 || choice > 8) {
                        // έλεγχος για τις βασικές επιλογές
                        throw new IllegalArgumentException(" Η επιλογή" +
                         " πρέπει να είναι από 0 μέχρι 8");
                    }
                    switch (choice) {
                        case 0:
                            System.out.println("Έξοδος από το πρόγραμμα");
                            break;
                        case 1:
                            ch.mainChoice1(revenue, budget);
                            break;
                        case 2:
                            ch.mainChoice2(budget, scanner);
                            break;
                        case 3:
                            ch.mainChoice3(revenue, scanner);
                            break;
                        case 4:
                            ch.mainChoice4(gdppop, scanner);
                            break;
                        case 5:
                            ch.mainChoice5(budget, scanner);
                            break;
                        case 6:
                            ch.mainChoice6(revenue);
                            break;
                        case 7:
                            ch.mainChoice7(revenue, budget, scanner);
                            break;
                        case 8:
                            ChangesHistory.printAll();
                            break;
                    }
                    break;
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                }
                catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice != 0);
        scanner.close();
    }
}
