package eurozone.gov.excel;
    
public class percent {
    public static long[][] converterToLong(String[][] revenue,int k, int a) {
        /*k how many lines and a the line of excel where the
        copy of the other array begin*/
        long[][] LongData = new long[k][5];
        int i = 0;
            for (int m = a - 2 ; m < k + a - 2 ; m++) {
                for (int j = 0 ; j < 5 ; j++) {
                    LongData[i][j] = Long.parseLong(revenue[m][j+2]);
                }
                i++;
            }
            return LongData;
    }
    public static double[][] percentual(long[][] LongData) {
        double[][] variance = new double [LongData.length][4];
            for (int i = 0; i < LongData.length; i++) {
                for (int j = 0; j < 4  ; j++) {
                    if (LongData[i][j+1]!=0) {
                    variance[i][j] = Math.round(((LongData[i][j] -
                    LongData[i][j+1]) / (double) LongData[i][j+1])
                    * 100.0 * 100.0) / 100.0;
                    } else {
                        variance[i][j] = 0;
                    }
                }
            }
        return variance;
    }
    public static long[][] amount(long[][] LongData) {
        long[][] amountdiff = new long[LongData.length][4];
            for (int i = 0; i < amountdiff.length ; i++) {
                for (int j = 0; j < 4  ; j++) {
                    amountdiff[i][j] = (LongData[i][j] - LongData[i][j+1]);
                }
            }
        return amountdiff;
    }
}
