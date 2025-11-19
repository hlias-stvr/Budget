package eurozone.gov.excel;

public class regionalPer {
    
    public static long[] transformToLong(String[][] budget) {
        long[] budgetLong = new long[7];
        for (int i = 25 ; i < 32 ; i++) {
            budgetLong[i - 25] = Long.parseLong(budget[i][2]); 
        }
        return budgetLong;
    }
    public static double[] calcBudgetPerPerson(long []budgetLong) { 
        double[] perPerson = new double[7];
        long[] population = new long[7];
        population[0] = 3814064;
        population[1] = 1196509;
        population[2] = 574586;
        population[3] = 1392287;
        population[4] = 522763;
        population[5] = 624408;
        population[6] = 2357870;
        for (int l = 0 ; l < 7 ; l++) {
            perPerson[l] = (double) (budgetLong[l] / population[l] ) * 100;
        }
        return perPerson;
    }
    public static double[] calcBudgetPerRegion(long []budgetLong, double perPerson) {
        double[] perRegion = new double[7];
        long sum = 0;
        for (int i = 0 ; i < 7 ; i++) {
            sum = sum + budgetLong[i];
        }
        for (int j = 0 ; j < 7 ; j++) {
            perRegion[j] = (double) (budgetLong[j] / sum) * 100;
        }
        return perRegion;
    } 
}
