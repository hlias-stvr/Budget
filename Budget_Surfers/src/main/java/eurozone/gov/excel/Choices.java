package eurozone.gov.excel;
import java.util.Scanner;

public class Choices {
    Subchoises sub = new Subchoises();
    public void mainChoice1(String[][] revenue, String[][] budget) {
        System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
        ReadTwoCsvFiles.printFirstRows(revenue, 33);
        System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
        ReadTwoCsvFiles.printFirstRows(budget,35);           
    }
    public void mainChoice2(String[][] budget, Scanner scanner) {
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
                    switch(choice2) {
                        case 1:
                            sub.subchoice2a(B, grSectors);
                            break;
                        case 2:
                            sub.subchoice2b(C, grSectors);
                            break;
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice2 != 0);
        // μέχρι να δωθεί το 0 για πίσω    
    }
    public void mainChoice3(String[][] revenue, Scanner scanner) {
        long[][] s = Percent.converterToLong(revenue, 14,2);
        long[][] f = Percent.converterToLong(revenue, 16, 16);
        double[][] g = Percent.percentual(s);
        double[][] h = Percent.percentual(f);
        long[][] n = Percent.amount(s);
        long[][] m = Percent.amount(f);
        int choice3 = -1;
        // υποεπιλογές για την επιλογή 3 
        System.out.println("Γράψε 1 για σύγκριση εσόδων");
        System.out.println("Γράψε 2 για σύγκριση εξόδων");
        System.out.println("0 για πίσω");
        do {           
            // μέχρι να δωθεί έγκυρη τιμή
            while(true) {
                try {
                    choice3 = scanner.nextInt();
                    // έλεγχος για τις υποεπιλογές 
                    if (choice3 < 0 || choice3 > 2){
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                    }
                    switch(choice3) {
                        case 1:
                            sub.subchoice3a(g, revenue, n, scanner);
                            break;
                        case 2:
                            sub.subchoice3b(h, revenue, m, scanner);
                            break;
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice3 != 0);
    }
    public void mainChoice4(String[][] gdppop, Scanner scanner) {
        long [][] D = EuzLivingStandard.compareToLong(gdppop);
        double [] E = EuzLivingStandard.findStandLiving(D);        
        int choice5 = -1;     
        do {       
            System.out.println("Γράψε 1 για να δεις τα ΚΚΑΕΠ των χωρών της Ευρωζώνης");
            System.out.println("2 για να συγκρίνεις το βιοτικό επίπεδο της Ελλάδας με άλλες χώρες");
            System.out.println("0 για πίσω");                                        
            while (true) { //  μέχρι να δωθεί σωστή τιμή
                try {
                    choice5 = scanner.nextInt();
                    // υπο επιλογές
                    if (choice5 < 0 || choice5 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                    }
                    switch(choice5) {
                        case 1:
                            sub.subchoice4a(E, gdppop);
                            break;
                        case 2:
                            sub.subchoice4b(gdppop, E, scanner);
                            break;
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }  
        } while (choice5!=0);
    }
    public void mainChoice5(String[][] budget, Scanner scanner) {
        long budgetLong[] = RegionalPer.transformToLong(budget);
        double perPerson[] = RegionalPer.calcBudgetPerPerson(budgetLong);
        double perRegion[] = RegionalPer.calcBudgetPerRegion(budgetLong);
        int choice6 = -1;
        do {
            System.out.println("Γράψε\n1 για να δεις την δαπάνη ανά πολίτη");
            System.out.println("2 για να δεις την ποσοστιαία δαπάνη ανά περιφέρεια");
            System.out.println("0 για πίσω");
            while (true) { // μέχρι να δωθεί σωστή τιμή
                try {
                    // υποεπιλογές 
                    choice6 = scanner.nextInt();
                    if (choice6 < 0 || choice6 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                    }
                    switch(choice6) {
                        case 1:
                            sub.subchoice5a(perPerson, budget);
                            break;
                        case 2:
                            sub.subchoice5b(perRegion, budget);
                            break;
                    }
                    break;
                } catch (IllegalArgumentException e){
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while(choice6 !=0);
    }
    public void mainChoice6(String[][] revenue) {
        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        CompareEuzTaxes.Calculation(LongData25);
    }
    public void mainChoice7(String[][] revenue, String[][] budget, Scanner scanner) {
        int choice8 = -1;            
        do{
            System.out.println("Γράψε\n1 Για να μεταβάλλεις τις δαπάνες ανά τομέα");
            System.out.println("2 Για να μεταβάλλεις τις δαπάνες ανά περιφέρεια");
            System.out.println("3 για να μεταβάλλεις τα έσοδα");
            System.out.println("0 Για πίσω");   
            while(true) { // μέχρι να δωθεί σωστή τιμή 
                try{
                    // υποεπιλογές
                    choice8 = scanner.nextInt();
                    if (choice8 < 0 || choice8 > 3) {
                        throw new IllegalArgumentException("Η επιλογή πρέπει να είναι από 0 μέχρι 3");
                    }
                    switch(choice8) {
                        case 1:
                            sub.subchoice7a(budget, scanner);
                            break;
                        case 2:
                            sub.subchoice7b(budget, scanner);    
                            break;                        
                        case 3:
                            sub.subchoice7c(revenue, scanner);
                            break;
                    }
                    break;
                } catch (IllegalArgumentException e){
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις ακέραιο αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice8 !=0);
    }
}
