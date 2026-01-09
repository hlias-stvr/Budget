package eurozone.gov.excel;

public class RegionalPer {
    public static long[] transformToLong(String[][] budget) {
        long[] budgetlong = new long[7];
        for (int i = 25; i < 32; i++) {
            budgetlong[i - 25] = Long.parseLong(budget[i][2]);
        }
        return budgetlong;
    }

    public static double[] calcBudgetPerPerson(long[] budgetlong) {
        double[] perperson = new double[7];
        long[] population = new long[7];
        population[0] = 3814064;
        population[1] = 1196509;
        population[2] = 574586;
        population[3] = 1392287;
        population[4] = 522763;
        population[5] = 624408;
        population[6] = 2357870;
        for (int l = 0; l < 7; l++) {
            perperson[l] =
            Math.round((budgetlong[l] / (double) population[l]) * 10.0) / 10.0;
        }
        return perperson;
    }

    public static double[] calcBudgetPerRegion(long []budgetlong) {
        double[] perregion = new double[7];
        long sum = 0;
        for (int i = 0; i < 7; i++) {
            sum = sum + budgetlong[i];
        }
        for (int j = 0; j < 7; j++) {
            perregion[j] =
            Math.round(((budgetlong[j] / (double) sum) * 100) * 10.0) / 10.0;
        }
        return perregion;
    }
}
