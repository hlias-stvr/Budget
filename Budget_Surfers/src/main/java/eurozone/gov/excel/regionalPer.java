package eurozone.gov.excel;

public class regionalPer {
    public long[] transformToLong(String[][] budget) {
        long[] budgetLong = new long[7];
        long[] perReg = new long[7];
        long[] population = new long[7];
        population[0] = 3814064;
        population[1] = 1196509;
        population[2] = 574586;
        population[3] = 1392287;
        population[4] = 522763;
        population[5] = 624408;
        population[6] = 2357870;
        for (int i = 25 ; i < 32 ; i++) {
            budgetLong[i - 25] = Long.parseLong(budget[i][2]); 
        }
        for (int l = 0 ; l < 7 ; l++) {
            perReg[l] = (budgetLong[l] / population[l] ) * 100;
        }
    }
}
