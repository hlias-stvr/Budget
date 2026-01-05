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
                    } else if (choice3 == 2) {
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
}