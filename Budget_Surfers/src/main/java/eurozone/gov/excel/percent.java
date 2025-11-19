package eurozone.gov.excel;
    
public class percent {
    public static long[][] converterToLong(String[][] revenue,int k, int a) { // k how many lines and a the line of excel where the copy of the other array begin 
        long[][] dataToLong = new long[k][5];
            for (int i = a - 2 ; i < k + a - 2 ; i++) {
                for (int j = 0 ; j < 5 ; j++) {
                    dataToLong[i][j] = Long.parseLong(revenue[i][j+2]);
                }
            }
            return dataToLong;
    }

    public static double[][] percentual(long[][] LongData) {
        double[][] variance = new double[32][4];
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 4  ; j++) {
                    variance[i][j] = ((LongData[i][j] - LongData[i][j+1]) / LongData[i][j+1]) * 100;
                }
            }
        return variance;
    }
    public static double[][] amount(long[][] LongData) {
        double[][] amountdiff = new double[LongData.length][4];
            for (int i = 0; i < amountdiff.length ; i++) {
                for (int j = 0; j < 4  ; j++) {
                    amountdiff[i][j] = (LongData[i][j] - LongData[i][j+1]);
                }
            }
        return amountdiff;
    }
}
