package eurozone.gov.excel;

import java.util.Scanner;

public class Choices {
    Subchoises sub = new Subchoises();

    public void mainChoice1(String[][] revenueExpenses, String[][] budget) {
        System.out.println("Προυπολογισμός Ελλάδας μετά την αφαίρεση του" +
            " αναλυκλώσιμου χρέους");
        ReadCsvFiles.printFirstRows(revenueExpenses, 33);
        ReadCsvFiles.printFirstRows(budget, 35);
    }

    public void mainChoice2(String[][] budget, Scanner scanner) {
        long [] longMinistrExpenses = AvgEurozone.convertToLong(budget);
        double [] percSectorsExpenses =
            AvgEurozone.ministrDiv(longMinistrExpenses);
        double [] percdiffSectorsExpenses =
            AvgEurozone.compareGrToEurozone(percSectorsExpenses);
        String[] grSectors = AvgEurozone.sectors();
        int choice2 = -1;
        do {
            System.out.println("Επίλεξε 1 για να δείς τις ποσοστιάιες δαπάνες"
                + " της Ελλάδας ανά τομέα");
            System.out.println("2 για να τις συγκρίνεις με τους" +
                " τομείς της Ευρωζώνης");
            System.out.println("0 για πίσω");
            // υποεπιλογές για την επιλογή 2
            while (true) {
                // μέχρι να δωθεί έγκυρη τιμή
                try {
                    choice2 = scanner.nextInt();
                    if (choice2 < 0 || choice2 > 2) {
                        //έλεγχος για τις υποεπιλογές
                        throw new IllegalArgumentException(" Η επιλογή" +
                            " πρέπει να είναι 1 ή 2 ή 0");
                    }
                    switch(choice2) {
                        case 1:
                            sub.subchoice2a(percSectorsExpenses, grSectors);
                            break;
                        case 2:
                            sub.subchoice2b(percdiffSectorsExpenses, grSectors);
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

    public void mainChoice3(String[][] revenueExpenses, Scanner scanner) {
        long[][] longIncome =
            BudgetVariance.converterToLong(revenueExpenses, 14, 2);
        long[][] longExpenses =
            BudgetVariance.converterToLong(revenueExpenses, 16, 16);
        double[][] percVarIncome = BudgetVariance.percentual(longIncome);
        double[][] percVarExpenses = BudgetVariance.percentual(longExpenses);
        long[][] amountVarIncome = BudgetVariance.amount(longIncome);
        long[][] amountVarExpenses = BudgetVariance.amount(longExpenses);
        int choice3 = -1;
        // υποεπιλογές για την επιλογή 3
        System.out.println("Γράψε 1 για σύγκριση εσόδων");
        System.out.println("Γράψε 2 για σύγκριση εξόδων");
        System.out.println("0 για πίσω");
        do {
            // μέχρι να δωθεί έγκυρη τιμή
            while (true) {
                try {
                    choice3 = scanner.nextInt();
                    // έλεγχος για τις υποεπιλογές
                    if (choice3 < 0 || choice3 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή" +
                            " πρέπει να είναι 1 ή 2");
                    }
                    switch(choice3) {
                        case 1:
                            sub.subchoice3a(percVarIncome, revenueExpenses,
                                amountVarIncome, scanner);
                            break;
                        case 2:
                            sub.subchoice3b(percVarExpenses, revenueExpenses,
                                amountVarExpenses, scanner);
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

    public void mainChoice4(String[][] gdpPop, Scanner scanner) {
        long [][] longGdpPop = EuzLivingStandard.compareToLong(gdpPop);
        double [] euzGdpPerCapita =
            EuzLivingStandard.findStandLiving(longGdpPop);
        int choice5 = -1;
        do {
            System.out.println("Γράψε 1 για να δεις τα" +
                " ΚΚΑΕΠ των χωρών της Ευρωζώνης");
            System.out.println("2 για να συγκρίνεις το" +
                " βιοτικό επίπεδο της Ελλάδας με άλλες χώρες");
            System.out.println("0 για πίσω");
            while (true) { //  μέχρι να δωθεί σωστή τιμή
                try {
                    choice5 = scanner.nextInt();
                    // υπο επιλογές
                    if (choice5 < 0 || choice5 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή" +
                            " πρέπει να είναι 1 ή 2");
                    }
                    switch(choice5) {
                        case 1:
                            sub.subchoice4a(euzGdpPerCapita, gdpPop);
                            break;
                        case 2:
                            sub.subchoice4b(gdpPop, euzGdpPerCapita, scanner);
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
        } while (choice5 != 0);
    }

    public void mainChoice5(String[][] budget, Scanner scanner) {
        long longRegionExpenses[] = RegionalPer.transformToLong(budget);
        double expensesPerPerson[] =
            RegionalPer.calcBudgetPerPerson(longRegionExpenses);
        double expensesPerRegion[] =
            RegionalPer.calcBudgetPerRegion(longRegionExpenses);
        int choice6 = -1;
        do {
            System.out.println("Γράψε\n1 για να δεις την δαπάνη ανά πολίτη");
            System.out.println("2 για να δεις την" +
                " ποσοστιαία δαπάνη ανά περιφέρεια");
            System.out.println("0 για πίσω");
            while (true) { // μέχρι να δωθεί σωστή τιμή
                try {
                    // υποεπιλογές
                    choice6 = scanner.nextInt();
                    if (choice6 < 0 || choice6 > 2) {
                        throw new IllegalArgumentException(" Η επιλογή" +
                            " πρέπει να είναι 1 ή 2");
                    }
                    switch(choice6) {
                        case 1:
                            sub.subchoice5a(expensesPerPerson, budget);
                            break;
                        case 2:
                            sub.subchoice5b(expensesPerRegion, budget);
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
        } while(choice6 != 0);
    }

    public void mainChoice6(String[][] revenueExpenses) {
        long[][] longIncome =
            BudgetVariance.converterToLong(revenueExpenses, 14, 2);
        long[] oneDimensLongImcome = new long[longIncome.length];
        for (int i = 0; i < longIncome.length; i++) {
            oneDimensLongImcome[i] = longIncome[i][0];
        }
        CompareEuzTaxes.calcDiffGrEuzTaxes(oneDimensLongImcome);
    }

    public void mainChoice7(String[][] revenueExpenses, String[][] budget,
        Scanner scanner) {
        int choice8 = -1;
        do {
            System.out.println("Γράψε\n1 Για να μεταβάλλεις" +
                " τις δαπάνες ανά τομέα");
            System.out.println("2 Για να μεταβάλλεις" +
                " τις δαπάνες ανά περιφέρεια");
            System.out.println("3 για να μεταβάλλεις τα έσοδα");
            System.out.println("0 Για πίσω");
            while (true) { // μέχρι να δωθεί σωστή τιμή
                try {
                    // υποεπιλογές
                    choice8 = scanner.nextInt();
                    if (choice8 < 0 || choice8 > 3) {
                        throw new IllegalArgumentException("Η επιλογή" +
                         " πρέπει να είναι από 0 μέχρι 3");
                    }
                    switch(choice8) {
                        case 1:
                            sub.subchoice7a(budget, scanner);
                            break;
                        case 2:
                            sub.subchoice7b(budget, scanner);
                            break;
                        case 3:
                            sub.subchoice7c(revenueExpenses, scanner);
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
        } while (choice8 != 0);
    }
}
