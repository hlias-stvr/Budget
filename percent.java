public class percent {
    public static long converterToLong(String[][] revenue,int k, int a) { // k how many lines and a the line of excel where the copy of the other array begin 
        long[][] dataToLong = new long[k][5];
            for (int i = a - 2 ; i < k + a ; i++) {
                for (int j = 0 ; j < 5 ; j++) {
                    dataToLong[i][j] = Long.Parselong(revenue[i][j+2]);   
                }
            }
    }
    long G = converterToLong(revenue, 14, 2); // must be in main
    long H = converterToLong(revenue, 16, 16); // must be in main
    long I = converterToLong(revenue, 1, 32); // must be in main
    public static double[] percentual(long[][] LongData) {
        double[][] varrev = new double[14][4];
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 4  ; j++) {
                    var[i][j] = ((LongData[i][j] - LongData[i][j+1]) / LongData[i][j+1]) * 100;
                }
            }       
        return var;
    }
}
