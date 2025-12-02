    package eurozone.gov.excel;

    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class ReadTwoCsvFiles {

        public static void main(String[] args) {
            String file1 = "src\\main\\resourses\\gr_revenue_expenses_25.csv";
            String file2 = "src\\main\\resourses\\gr_ministy_25.csv";
            String file3 = "src\\main\\resourses\\Gdp_population_euz.csv";

            String[][] revenue = readCsv(file1);   
            String[][] budget = readCsv(file2);
            String[][] gdppop = readCsv(file3);   

           int choice = -1;
            do {
                System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                System.out.println("0 για έξοδο");
                Scanner scanner2 = new Scanner(System.in);
                while (true) {
                    try {
                            choice = scanner2.nextInt(); 
                        if (choice < 0 || choice > 6) {
                            throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι από 0 μέχρι 6");
                        }
                        if (choice == 0) {
                            System.out.println("Έξοδος από το πρόγραμμα");
                            break;
                        } else if (choice == 1) {
                            
                            
                            System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===" );
                            printFirstRows(revenue, 33);

                            System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
                            printFirstRows(budget,35);
                             
                            System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                            System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                            System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                            System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                            System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                            System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                            System.out.println("0 για έξοδο");
                        } else if (choice == 2) {
                            long [] A = avgeurozone.convertToLong(budget);
                            double [] B = avgeurozone.ministrDiv(A);
                            double [] C = avgeurozone.compareGrToEurozone(B);
                            String[] grSectors = avgeurozone.sectors();
                            int choice2 = -1;
                            do {
                               
                            System.out.println("Επίλεξε 1 για να δείς τις ποσοστιάιες δαπάνες της Ελλάδας ανά τομέα");
                            System.out.println("2 για να τις συγκρίνεις με τους τομείς της Ευρωζώνης");
                            System.out.println("0 για πίσω");
                            Scanner scanner3 = new Scanner(System.in);
                            while (true) {
                                try {
                                     choice2 = scanner3.nextInt();
                                    if (choice2 < 0 || choice2 > 2) {
                                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2 ή 0");
                                    }
                                    if (choice2 == 1) {
                                        for(int i = 0; i < 11; i++) {
                                            System.out.println("Η Ελλάδα δαπανά " + B[i] + "%"+" στον τομέα "+grSectors[i]);
                                        }
                                    } else if (choice2 == 2) {
                                        for(int i = 0; i < 11; i++) {
                                            if(C[i] > 0) {
                                                System.out.println("Η Ελλάδα δαπανά "+ C[i] + "% λιγότερο στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                                            } else if(C[i] < 0) {
                                                System.out.println("Η Ελλάδα δαπανά "+ Math.abs(C[i]) + "% περισσότερο στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                                            } else {
                                                System.out.println("Η Ελλάδα δαπανά το ίδιο ποσοστό στον τομέα "+grSectors[i]+" από τον ΜΟ της Ευρωζώνης");
                                            }
                                        }
                                    }
                                    break;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Σφάλμα" + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Πρέπει να δώσεις αριθμό");
                                    scanner3.nextLine(); //καθάρισμα εισόδου
                                }
                            }
                        } while (choice2 != 0);
                         if (choice2 == 0) {
                                    System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                                    System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                                    System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                                    System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                                    System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                                    System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                                    System.out.println("0 για έξοδο");
                                }
                        } else if (choice == 3) {
                            long[][] s = percent.converterToLong(revenue, 14,2);
                            long[][] f = percent.converterToLong(revenue, 16, 16);
                            double[][] g = percent.percentual(s);
                            double[][] h = percent.percentual(f);
                            long[][] n = percent.amount(s);
                            long[][] m = percent.amount(f);
                            int choice3 = -1;
                            System.out.println("Γράψε 1 για σύγκριση εσόδων");
                            System.out.println("Γράψε 2 για σύγκριση εξόδων");
                            System.out.println("0 για πίσω");
                            do {
                           
                            Scanner scanner4 = new Scanner(System.in);
                            while(true) {
                                try {
                                    choice3 = scanner4.nextInt();
                                    if (choice3 < 0 || choice3 > 2){
                                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι 1 ή 2");
                                    }
                            if(choice3 ==1) {
                                int choice4 = -1;
                            do {
                                System.out.println("Επίλεξε 1 για να δεις τα ποσoστά ανά έτος");
                                System.out.println("Επίλεξε 2 για να δεις τα ποσά ανά έτος");
                                System.out.println("0 για πίσω");
                                while(true) {
                                    Scanner scanner5 = new Scanner(System.in);
                                try {
                                 choice4 = scanner5.nextInt();
                                if (choice4 < 0 || choice4 > 2) {
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
                                    scanner5.nextLine(); //καθάρισμα εισόδου
                                }
                                }
                                 if (choice4 == 0) {
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
                                Scanner scanner5 = new Scanner(System.in);
                                while(true){
                                try {
                                 choice4 = scanner5.nextInt();
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
                                    scanner5.nextLine(); //καθάρισμα εισόδου
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
                                    scanner4.nextLine(); //καθάρισμα εισόδου
                                }
                            }
                            if(choice3 == 0) {
                                 System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                                    System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                                    System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                                    System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                                    System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                                    System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                                    System.out.println("0 για έξοδο");
                            }
                        } while (choice3 != 0);
                        
                    
                        } else if (choice == 4) {
                            long [][] D = EuzLivingStandard.compareToLong(gdppop);
                            double [] E = EuzLivingStandard.findStandLiving(D);
                            
                            int choice5 = -1;
                             
                            do {
                               
                                            System.out.println("Γράψε 1 για να δεις τα ΚΚΑΕΠ των χωρών της Ευρωζώνης");
                                            System.out.println("2 για να συγκρίνεις το βιοτικό επίπεδο της Ελλάδας με άλλες χώρες");
                                            System.out.println("0 για πίσω");
                                        
                            while (true) {
                                try {
                                    Scanner scanner6 = new Scanner(System.in);
                                    choice5 = scanner6.nextInt();
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
                                        Scanner scanner1 = new Scanner(System.in);
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
                                                a = scanner1.nextInt();
                                                if (a < 0 || a > 20) {
                                                    throw new IllegalArgumentException(" Ο αριθμός πρέπει να είναι από 1 μέχρι 20");
                                                }
                                                if (a == 0) {
                                                    break;
                                                }
                                                System.out.println("Έβαλες την χώρα " + gdppop[a-1][0]);
                                                EuzLivingStandard.compareStdLive(a,E,gdppop );
                                                break;
                                            } catch(IllegalArgumentException e) { 
                                                System.out.println("Σφάλμα" + e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println("Πρέπει να δώσεις αριθμό");
                                                scanner1.nextLine(); //καθάρισμα εισόδου
                                            }
                                        }
                                        
                                        
                                    } while (a!= 0);
                                    } 
                                 break;
                                } catch (IllegalArgumentException e){
                                    System.out.println("Σφάλμα" + e.getMessage());
                                } catch (Exception e) {
                                    System.out.println("Πρέπει να δώσεις αριθμό");
                                    scanner2.nextLine(); //καθάρισμα εισόδου
                                }
                                
                                }
                                if (choice5 == 0) {
                                    System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                                    System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                                    System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                                    System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                                    System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                                    System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                                    System.out.println("0 για έξοδο");
                                }
                                
                            } while (choice5!=0);
                            
                        } else if (choice == 5) {
                            long budgetLong[] = regionalPer.transformToLong(budget);
                            double perPerson[] = regionalPer.calcBudgetPerPerson(budgetLong);
                            double perRegion[] = regionalPer.calcBudgetPerRegion(budgetLong);
                            int choice6 = -1;
                            do {
                            System.out.println("Γράψε 1 για να δεις την δαπάνη ανά πολίτη");
                            System.out.println("2 για να δεις την ποσοστιαία δαπάνη ανά περιφέρεια");
                            System.out.println("0 για πίσω");
                            while (true) {
                                try {
                                    Scanner scanner7 = new Scanner(System.in);
                                     choice6 = scanner7.nextInt();
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
                                    scanner2.nextLine(); //καθάρισμα εισόδου
                                }
                            }
                            if (choice6 == 0) {
                                System.out.println("Επίλεξε:\n1 για προβολή στοιχείων κρατικού προϋπολογισμού");
                                System.out.println("2 για σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης");
                                System.out.println("3 για σύγκριση του προϋπολογισμού τα τελευτάια 5 έτη");
                                System.out.println("4 για σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης");
                                System.out.println("5 για ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια" );
                                System.out.println("6 για επεξεργασία στοιχείων προϋπολογισμού");
                                System.out.println("0 για έξοδο");
                            }
                        } while(choice6 !=0);
                        }
            
                    } catch (IllegalArgumentException e){
                        System.out.println("Σφάλμα" + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        scanner2.nextLine(); //καθάρισμα εισόδου
                    }
                }
        
            } while (choice != 0);
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
