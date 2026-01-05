package eurozone.gov.excel;

import java.util.Scanner;

public class Subchoises {
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
                                    if (choice4 == 1) {
                                        System.out.println("Η διαφορά των ποσοστών των εσόδων ανά έτος είναι:");
                                        for(int i = 0; i < g.length; i++) {
                                            for(int j = 0; j < 4; j++) {
                                                if (j == 0) {
                                                    System.out.print(revenue[i][1]+" "+g[i][j]+"% ");
                                                } else if (j == 1 || j ==2) {
                                                    System.out.print(g[i][j]+"% ");
                                                } else if (j == 3) {
                                                    System.out.println(g[i][j]+"%");
                                                }
                                            }
                                        }
                                    } else if (choice4 == 2) {
                                        System.out.println("η διαφορά των ποσών των εσόδων ανά έτος είναι:");
                                        for(int i = 0; i < n.length; i++) {
                                            for(int j = 0; j < 4; j++) {
                                                if (j == 0) {
                                                    System.out.print(revenue[i][1]+" "+n[i][j]);
                                                } else if (j == 1 || j ==2) {
                                                    System.out.print(" "+n[i][j]);
                                                } else if (j == 3) {
                                                    System.out.println(" "+n[i][j]);
                                                }
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
                                    if (choice4 == 1) {
                                        System.out.println("Η διαφορά των ποσοστών ανά έτος είναι:");
                                        for(int i = 0; i < h.length; i++) {
                                            for(int j = 0; j < 4; j++) {
                                                if (j == 0) {
                                                    System.out.print(revenue[i][1]+" "+h[i][j]+"% ");
                                                } else if (j == 1 || j ==2) {
                                                    System.out.print(h[i][j]+"% ");
                                                } else if (j == 3) {
                                                    System.out.println(h[i][j]+"% ");
                                                }
                                            }
                                        }
                                    } else if (choice4 == 2) {
                                        System.out.println("Η διαφορά των ποσών ανά έτος είναι:");
                                        for(int i = 0; i < m.length; i++) {
                                            for(int j = 0; j < 4; j++) {
                                                if (j == 0) {
                                                    System.out.print(revenue[i][1]+" "+m[i][j]);
                                                } else if (j == 1 || j ==2) {
                                                    System.out.print(" "+m[i][j]);
                                                } else if (j == 3) {
                                                    System.out.println(" "+m[i][j]);
                                                }  
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
                            if (choice4 == 0) {
                                System.out.println("Γράψε 1 για σύγκριση εσόδων");
                                System.out.println("Γράψε 2 για σύγκριση εξόδων");
                                System.out.println("0 για πίσω");
                            }
                        }while (choice4!=0);
    }
}

