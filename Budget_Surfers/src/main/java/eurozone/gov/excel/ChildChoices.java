package eurozone.gov.excel;

public class ChildChoices {
    public void childChoice3a1(String[][] revenue, double[][] percVarIncome) {
        System.out.println("Η διαφορά των ποσοστών των εσόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < percVarIncome.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " + percVarIncome[i][j]
                        + "% ");
                } else if (j == 1 || j == 2) {
                    System.out.print(percVarIncome[i][j] + "% ");
                } else if (j == 3) {
                    System.out.println(percVarIncome[i][j] + "%");
                }
            }
        }
    }

    public void childChoice3a2(String[][] revenue, long[][] amountVarIncome) {
        System.out.println("Η διαφορά των ποσών των εσόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < amountVarIncome.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " +
                        amountVarIncome[i][j]);
                } else if (j == 1 || j == 2) {
                    System.out.print(" " + amountVarIncome[i][j]);
                } else if (j == 3) {
                    System.out.println(" " + amountVarIncome[i][j]);
                }
            }
        }
    }

    public void childChoice3b1(String[][] revenue, double[][] percVarExpenses) {
        System.out.println("Η διαφορά των ποσοστών των εξόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < percVarExpenses.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " +
                        percVarExpenses[i][j] + "% ");
                } else if (j == 1 || j == 2) {
                    System.out.print(percVarExpenses[i][j] + "% ");
                } else if (j == 3) {
                    System.out.println(percVarExpenses[i][j] + "% ");
                }
            }
        }
    }

    public void childChoice3b2(String[][] revenue, long[][] amountVarExpenses) {
        System.out.println("Η διαφορά των ποσών των εξόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < amountVarExpenses.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " +
                        amountVarExpenses[i][j]);
                } else if (j == 1 || j == 2) {
                    System.out.print(" " + amountVarExpenses[i][j]);
                } else if (j == 3) {
                    System.out.println(" " + amountVarExpenses[i][j]);
                }
            }
        }
    }

    public void childChoice7a1(double[] newgrPercent, String[] grSectors) {
        for (int i = 0; i < 11; i++) {
            System.out.println("Η Ελλάδα" +
                " δαπανά " + newgrPercent[i] + "%" + " στον" +
                " τομέα " + grSectors[i]);
        }
    }

    public void childChoice7a2(double[] newCompareAvgEurz, String[] grSectors) {
        for (int i = 0; i < 11; i++) {
            if (newCompareAvgEurz[i] > 0) {
                System.out.println("Η Ελλάδα" +
                    " δαπανά " + newCompareAvgEurz[i] + "% λιγότερο" +
                    " στον τομέα " + grSectors[i] + " από" +
                    " τον ΜΟ της Ευρωζώνης");
            } else if (newCompareAvgEurz[i] < 0) {
                System.out.println("Η Ελλάδα" +
                    " δαπανά " + Math.abs(newCompareAvgEurz[i]) + "%" +
                    " περισσότερο στον" +
                    " τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
            } else {
                System.out.println("Η Ελλάδα δαπανά το ίδιο ποσοστό στον" +
                    " τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
            }
        }
    }

    public void childChoice7b1(String[][] budget, double[] perPerson) {
        for (int i = 0; i < perPerson.length; i++) {
            System.out.println(budget[i + 25][1] + " " + perPerson[i]);
        }
    }

    public void childChoice7b2(String[][] budget, double[] perRegion) {
        for (int i = 0; i < perRegion.length; i++) {
            System.out.println(budget[i + 25][1] + " " + perRegion[i] + "%");
        }
    }
}
