package eurozone.gov.excel;
    
public class Percent {
    public static long[][] converterToLong(String[][] revenue,int k, int a) {
        /*k how many lines and a the line of excel where the
        copy of the other array begin*/
        long[][] longData = new long[k][5];
        int i = 0;
            for (int m = a - 2 ; m < k + a - 2 ; m++) {
                for (int j = 0 ; j < 5 ; j++) {
                    longData[i][j] = Long.parseLong(revenue[m][j+2]);
                }
                i++;
            }
        return longData;
    }

    //Ποσοστιαίες μεταβολές εσόδων ή εξόδων ανά έτη
    public static double[][] percentual(long[][] longData) {
        double[][] percVariance = new double [longData.length][4];
            for (int i = 0; i < longData.length; i++) {
                for (int j = 0; j < 4  ; j++) {
                    if (longData[i][j+1]!=0) {
                    percVariance[i][j] = Math.round(((longData[i][j] -
                    longData[i][j+1]) / (double) longData[i][j+1])
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
            for (int i = 0; i < amountVariance.length ; i++) {
                for (int j = 0; j < 4  ; j++) {
                    amountVariance[i][j] = (longData[i][j] - longData[i][j+1]);
                }
            }
        return amountVariance;
    }
}
