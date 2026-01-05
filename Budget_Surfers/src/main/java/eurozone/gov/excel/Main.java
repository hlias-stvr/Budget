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
                        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
                        long[] LongData25 = new long[LongData.length];
                        for (int i = 0; i < LongData.length; i++) {
                            LongData25[i] = LongData[i][0];
                        }
                        CompareEuzTaxes.Calculation(LongData25);
                    } else if (choice == 7) {
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
                                    if (choice8 == 1) {
                                        long [] A = AvgEurozone.convertToLong(budget);
                                        double [] grpercent = AvgEurozone.ministrDiv(A);
                                        String[] grSectors = AvgEurozone.sectors();
                                        double[] newgrPercent = ChangeData.newGrPercent(grpercent, grSectors, scanner);
                                        double [] newCompareAvgEurz = AvgEurozone.compareGrToEurozone(newgrPercent);    
                                        int choice7 = -1;
                                        // αν αλλάξει τα δεδομένα της επιλογής 1           
                                        do {
                                            // ξανακαλεί τις μεθόδους που έχουν να κάνουν με αυτά που άλλαξε
                                            // για να δει την διαφορά
                                            System.out.println("Επίλεξε\n1 για να δείς τις νέες ποσοστιάιες δαπάνες της Ελλάδας ανά τομέα");
                                            System.out.println("2 για να τις συγκρίνεις τα νέα ποσοστά με τους τομείς της Ευρωζώνης");
                                            System.out.println("0 για πίσω");
                                            while (true) {
                                                try {
                                                    choice7 = scanner.nextInt();
                                                    if (choice7 < 0 || choice7 > 2) {
                                                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2 ή 0");
                                                    }
                                                    if (choice7 == 1) {
                                                        for(int i = 0; i < 11; i++) {
                                                            System.out.println("Η Ελλάδα δαπανά " + newgrPercent[i] + "%" + " στον τομέα " + grSectors[i]);
                                                        }
                                                    } else if (choice7 == 2) {
                                                        for(int i = 0; i < 11; i++) {
                                                            if(newCompareAvgEurz[i] > 0) {
                                                                System.out.println("Η Ελλάδα δαπανά "+ newCompareAvgEurz[i] + "% λιγότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
                                                            } else if(newCompareAvgEurz[i] < 0) {
                                                                System.out.println("Η Ελλάδα δαπανά "+ Math.abs(newCompareAvgEurz[i]) + "% περισσότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
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
                                        } while(choice7 !=0);
                                        break;
                                    } else if (choice8 == 2) {
                                        long[] longBudget = RegionalPer.transformToLong(budget);
                                        long[] newLongBudget = ChangeData.newAmountPerRegion(longBudget,  budget, scanner);
                                        int choice9 = -1;
                                        // αν αλλάξει τα δεδομένα της επιλογής 2
                                        do {
                                            double perPerson[] = RegionalPer.calcBudgetPerPerson(newLongBudget);
                                            double perRegion[] = RegionalPer.calcBudgetPerRegion(newLongBudget);
                                            // ξανακαλεί τις μεθόδους που έχουν να κάνουν με αυτά που άλλαξε
                                            // για να δει την διαφορά
                                            System.out.println("Γράψε\n1 για να δεις την νέα δαπάνη ανά πολίτη");
                                            System.out.println("2 για να δεις την νέα ποσοστιαία δαπάνη ανά περιφέρεια");
                                            System.out.println("0 για πίσω");
                                            while (true) {
                                                try {
                                                    choice9 = scanner.nextInt();
                                                    if (choice9 < 0 || choice9 > 2) {
                                                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                                                    }
                                                    if (choice9 == 1) {
                                                        for (int i = 0; i < perPerson.length; i++) {
                                                            System.out.println(budget[i+25][1]+ " " + perPerson[i]);
                                                        }
                                                    } else if (choice9 == 2) {
                                                        for (int i = 0; i < perRegion.length; i++) {
                                                            System.out.println(budget[i+25][1]+ " " + perRegion[i] + "%");
                                                        }
                                                    }
                                                    break;
                                                } catch (IllegalArgumentException e){
                                                    System.out.println("Σφάλμα" + e.getMessage());
                                                } catch (Exception e) {
                                                    System.out.println("Πρέπει να δώσεις αριθμό");
                                                    scanner.nextLine(); //καθάρισμα εισόδου
                                                }
                                            }
                                        } while(choice9 != 0);                            
                                    } else if (choice8 == 3) {
                                        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
                                        long[] LongData25 = new long[LongData.length];
                                        for (int i = 0; i < LongData25.length; i++) {
                                            LongData25[i] = LongData[i][0];
                                        }
                                        long[] newLongData= ChangeData.newRevenue(LongData25, revenue, scanner);
                                        System.out.println("Ακολουθεί η σύγκριση των νέων φορολογικών εσόδων με τον μέσο όρο της Ευρωζώνης");
                                        CompareEuzTaxes.Calculation(newLongData);
                                        
                                    }
                                    break;
                                } catch (IllegalArgumentException e){
                                    System.out.println("Σφάλμα" + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Πρέπει να δώσεις αριθμό");
                                    scanner.nextLine(); //καθάρισμα εισόδου
                                }
                            }
                        } while (choice8 !=0);
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