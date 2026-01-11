package eurozone.gov.excel;

public class ChildChoices {
    public void childChoice3a1(double[][] g, String[][] revenue) {
        System.out.println("Η διαφορά των ποσοστών των εσόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " + g[i][j] + "% ");
                } else if (j == 1 || j == 2) {
                    System.out.print(g[i][j] + "% ");
                } else if (j == 3) {
                    System.out.println(g[i][j] + "%");
                }
            }
        }
    }

    public void childChoice3a2(String[][] revenue, long[][] n) {
        System.out.println("η διαφορά των ποσών των εσόδων ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " + n[i][j]);
                } else if (j == 1 || j == 2) {
                    System.out.print(" " + n[i][j]);
                } else if (j == 3) {
                    System.out.println(" " + n[i][j]);
                }
            }
        }
    }

    public void childChoice3b1(String[][] revenue, double[][] h) {
        System.out.println("Η διαφορά των ποσοστών ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " + h[i][j] + "% ");
                } else if (j == 1 || j == 2) {
                    System.out.print(h[i][j] + "% ");
                } else if (j == 3) {
                    System.out.println(h[i][j] + "% ");
                }
            }
        }
    }

    public void childChoice3b2(String[][] revenue, long[][] m) {
        System.out.println("Η διαφορά των ποσών ανά έτος είναι:");
        System.out.println("Έτη 2024-2025,2023-2024,2022-2023,2021-2022");
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1] + " " + m[i][j]);
                } else if (j == 1 || j == 2) {
                    System.out.print(" " + m[i][j]);
                } else if (j == 3) {
                    System.out.println(" " + m[i][j]);
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

    public void childChoice7b1(double[] perPerson, String[][] budget) {
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
