package eurozone.gov.excel;

public class BudgetVariance {
    public static long[][] converterToLong(String[][] revenueExpenses,
        int k, int a) {
        /*
        *k πόσες γραμμές να μετατραπούν και a η γραμμή του πίνακα
        *(στην μορφή csv) από την οποία θα ξεκινήσει η μετατροπή
        */
        long[][] longData = new long[k][5];
        int i = 0;
        for (int m = a - 2; m < k + a - 2; m++) {
            for (int j = 0; j < 5; j++) {
                longData[i][j] = Long.parseLong(revenueExpenses[m][j + 2]);
            }
            i++;
        }
        return longData;
    }

    //Ποσοστιαίες μεταβολές εσόδων ή εξόδων ανά έτη
    public static double[][] percentual(long[][] longData) {
        double[][] percVariance = new double [longData.length][4];
        for (int i = 0; i < longData.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (longData[i][j + 1] != 0) {
                    percVariance[i][j] = Math.round(((longData[i][j] -
                    longData[i][j + 1]) / (double) longData[i][j + 1])
                    * 100.0 * 100.0) / 100.0;
                } else {
                    percVariance[i][j] = 0;
                }
            }
        }
        return percVariance;
    }

    //Ποσοτικές μεταβολές εσόδων ή εξόδων ανά έτη
    public static long[][] amount(long[][] longData) {
        long[][] amountVariance = new long[longData.length][4];
        for (int i = 0; i < amountVariance.length; i++) {
            for (int j = 0; j < 4; j++) {
                amountVariance[i][j] = (longData[i][j] - longData[i][j + 1]);
            }
        }
        return amountVariance;
    }
}
