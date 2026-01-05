package eurozone.gov.excel;
import java.util.Scanner;

public class Choices {
    Subchoises sub = new Subchoises();
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
                        sub.subchoice2a(B, grSectors);
                    } else if (choice2 == 2) {
                        sub.subchoice2b(C, grSectors);
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
    public void choice3(String[][] revenue, Scanner scanner) {
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
                    if(choice3 ==1) {
                        sub.subchoice3a(g, revenue, n, scanner);
                    } else if (choice3 == 2) {
                        sub.subchoice3b(h, revenue, m, scanner);
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
        } while (choice3 != 0);
    }
    public void choice4(String[][] gdppop, Scanner scanner) {
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
                    if (choice5 == 1) {
                        for (int i = 0; i<E.length; i++) {
                            if (i < 19) {
                                System.out.println("Τα ΚΚΑΕΠ της χώρας "+gdppop[i][0]+" είναι "+E[i]);
                            } else if (i == 19) {
                                System.out.println("Τα ΚΚΑΕΠ του ΜΟ της Ευρωζώνης είναι "+E[i]);
                            }
                        }
                    } else if (choice5 == 2) {
                        int a = -1;
                        System.out.println("Γράψε\n 1 για Αυστρία\n 2 για Βέλγιο\n 3 για Κροατία\n"
                        +" 4 για Κύπρο\n 5 για Εσθονία\n 6 για Φινλανδία\n 7 για Γαλλία\n 8 για Γερμανία\n" 
                        +" 9 για Ιρλανδία\n 10 για Ιταλία\n 11 για Λετονία\n 12 για Λιθουανία\n" 
                        +" 13 για Λουξεμβούργο\n 14 για Μάλτα\n 15 για Ολλανδία\n 16 για Πορτογαλία\n" 
                        +" 17 για Σλοβακία\n 18 για Σλοβενία\n 19 για Ισπανία\n 20 για ΜΟ Ευρωζώνης ");
                        do {
                            while (true) { //μέχρι να δώσει ο χρήστης σωστή τιμή
                                try {
                                    System.out.println("Δώσε αριθμό για την χώρα που θες να συγκρίνεις με την Ελλάδα ή 0 για πίσω");
                                    a = scanner.nextInt();
                                    if (a < 0 || a > 20) {
                                        throw new IllegalArgumentException(" Ο αριθμός πρέπει να είναι από 1 μέχρι 20");
                                    }
                                    if (a == 0) {
                                        break;
                                    }
                                    if (a != 20) {
                                    System.out.println("Έβαλες την χώρα " + gdppop[a-1][0]);
                                    }
                                    EuzLivingStandard.compareStdLive(a,E,gdppop );
                                    break;
                                } catch (IllegalArgumentException e) { 
                                    System.out.println("Σφάλμα" + e.getMessage());
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Πρόβλημα με τον πίνακα: " + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Πρέπει να δώσεις αριθμό");
                                    scanner.nextLine(); //καθάρισμα εισόδου
                                }
                            }
                        } while (a!= 0);
                    } 
                    break;
                } catch (IllegalArgumentException e){
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }  
        } while (choice5!=0);
    }
    public void choice5(String[][] budget, Scanner scanner) {
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
                    if (choice6 == 1) {
                        for (int i = 0; i < perPerson.length; i++) {
                            System.out.println(budget[i+25][1]+ " " + perPerson[i]);
                        }
                    } else if (choice6 == 2) {
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
        } while(choice6 !=0);
    }
    public void choice6(String[][] revenue) {
        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        CompareEuzTaxes.Calculation(LongData25);
    }
    public void choice7(String[][] revenue, String[][] budget, Scanner scanner) {
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
    }
}
