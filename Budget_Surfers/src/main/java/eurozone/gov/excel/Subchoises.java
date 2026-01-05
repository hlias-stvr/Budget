package eurozone.gov.excel;

import java.util.Scanner;

public class Subchoises {
    ChildChoices child = new ChildChoices();
    public void subchoice2a(double[] B, String[] grSectors) {
        for(int i = 0; i < 11; i++) {
            System.out.println("Η Ελλάδα δαπανά " + B[i] + "%" + " στον τομέα " + grSectors[i]);
        }
    }
    public void subchoice2b(double[] C, String[] grSectors) {
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
    public void subchoice3a(double[][] g, String[][] revenue, long[][] n, Scanner scanner ) {
        int choice4 = -1;
        do {
            System.out.println("Επίλεξε 1 για να δεις τα ποσoστά ανά έτος");
            System.out.println("Επίλεξε 2 για να δεις τα ποσά ανά έτος");
            System.out.println("0 για πίσω");
            while(true) { // έλεγχος για τις υποεπιλογές 
                try {
                    // υπο επιλογές για τις υποεπιλογές 
                    choice4 = scanner.nextInt();
                    if (choice4 < 0 || choice4 > 2) { // έλεγχος για αυτές τις υποεπιλογές 
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2 ή 0");
                    }
                    switch(choice4) {
                        case 1:
                            child.childChoice3a1(g, revenue);
                        case 2:
                            child.childChoice3a2(revenue, n);
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
            if (choice4 == 0) { 
                // ξαναεμφανίζει τις προηγούμενες υπο επιλογές αν ο χρήστης επιλέξει το 0
                System.out.println("Γράψε 1 για σύγκριση εσόδων");
                System.out.println("Γράψε 2 για σύγκριση εξόδων");
                System.out.println("0 για πίσω");
            }
        }while (choice4!= 0);
    }
    public void subchoice3b(double[][] h, String[][] revenue, long[][] m, Scanner scanner) {
        int choice4 = -1;  
        do {
            System.out.println("Επίλεξε 1 για να δεις τα ποσoστά ανά έτος");
            System.out.println("Επίλεξε 2 για να δεις τα ποσά ανά έτος");
            System.out.println("0 για πίσω");
            while(true){
                // άλλες υπο επιλογές για την δεύτερη υπο επιλογή
                try {
                    choice4 = scanner.nextInt();
                    if (choice4 < 0 || choice4 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                    }
                    switch(choice4) {
                        case 1:
                            child.childChoice3b1(revenue, h);
                        case 2:
                            child.childChoice3b2(revenue, m);
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Πρέπει να δώσεις αριθμό");
                    scanner.nextLine(); //καθάρισμα εισόδου
                }
            }
            if (choice4 == 0) {
                System.out.println("Γράψε 1 για σύγκριση εσόδων");
                System.out.println("Γράψε 2 για σύγκριση εξόδων");
                System.out.println("0 για πίσω");
            }
        }while (choice4!=0);
    }
    public void subchoice4a(double[] E, String[][] gdppop) {
        for (int i = 0; i<E.length; i++) {
            if (i < 19) {
                System.out.println("Τα ΚΚΑΕΠ της χώρας "+gdppop[i][0]+" είναι "+E[i]);
            } else if (i == 19) {
                System.out.println("Τα ΚΚΑΕΠ του ΜΟ της Ευρωζώνης είναι "+E[i]);
            }
        }
    }
    public void subchoice4b(String[][] gdppop, double[] E, Scanner scanner) {
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
    public void subchoice5a(double[] perPerson, String[][] budget) {
        for (int i = 0; i < perPerson.length; i++) {
            System.out.println(budget[i+25][1]+ " " + perPerson[i]);
        }
    }
    public void subchoice5b(double[] perRegion, String[][] budget) {
        for (int i = 0; i < perRegion.length; i++) {
            System.out.println(budget[i+25][1]+ " " + perRegion[i] + "%");
        }
    }
    public void subchoice7a(String[][] budget, Scanner scanner) {
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
                    switch(choice7) {
                        case 1:
                            child.childChoice7a1(newgrPercent, grSectors);
                        case 2:
                            child.childChoice7a2(newCompareAvgEurz, grSectors);
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
    }
    public void subchoice7b(String[][] budget, Scanner scanner) {
        long[] longBudget = RegionalPer.transformToLong(budget);
        long[] newLongBudget = ChangeData.newAmountPerRegion(longBudget, budget, scanner);
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
                    switch(choice9) {
                        case 1:
                            child.childChoice7b1(perPerson, budget);
                        case 2:
                            child.childChoice7b2(budget, perRegion);
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
    }
    public void subchoice7c(String[][] revenue, Scanner scanner) {
        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData25.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        long[] newLongData= ChangeData.newRevenue(LongData25, revenue, scanner);
        System.out.println("Ακολουθεί η σύγκριση των νέων φορολογικών εσόδων με τον μέσο όρο της Ευρωζώνης");
        CompareEuzTaxes.Calculation(newLongData);
    }
}
