package eurozone.gov.excel;

public class EuzLivingStandard {
    // μετατροπή πίνακα gdpPop σε long 
    public static long[][] compareToLong(String[][] gdpPop) {
        long[][] longGdpPop = new long[19][2];
        for (int i = 0 ; i < 19 ; i++) {
            for (int j = 0 ; j < 2 ; j++) {
                longGdpPop[i][j] = Long.parseLong(gdpPop[i][j+1]);
            }
        }
        return longGdpPop;
    }
    public static final int GRAVGGDP = 25300;
    public static double[] findStandLiving(long[][] gdppopulation) {
        double[] livingStd = new double[20];
        double sumLivingStd = 0;
        for (int k = 0 ; k < 19 ; k++) {
            livingStd[k] = (double) (gdppopulation[k][0] / gdppopulation[k][1]);
            sumLivingStd = sumLivingStd + livingStd[k];
        }
        livingStd[19] = (double) ((sumLivingStd + GRAVGGDP) / 20);
        return livingStd;
    }
    public static void compareStdLive(int a, double stdLive[], String [][] gdppop) {
        if (a != 20) {
            double stdLiveDiff = stdLive[a-1] - GRAVGGDP; /* a - 1 because we
             want user's coices to start from number 1 */
        if (stdLiveDiff > 0) {
            System.out.println("Η " + gdppop[a-1][0] + " έχει μεγαλύτερο " +
            "βιοτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff +
            " μονάδες του ευρώ.");
        } else if (stdLiveDiff < 0) {
            System.out.println("Η " + gdppop[a-1][0] + " έχει μικρότερο βιοτικό"
            + " επίπεδο από την Ελλάδα κατά " + Math.abs(stdLiveDiff) +
            " μονάδες του ευρώ.");
        } else {
            System.out.println("Η " + gdppop[a-1][0] +
            " έχει το ίδιο βιοτικό επίπεδο με την Ελλάδα.");
        }
        } else {
            double stdLiveGrEuz = Math.round((stdLive[19] - GRAVGGDP) * 10.0) / 10.0;
            if (stdLiveGrEuz > 0) {
            System.out.println("Η Ελλάδα έχει μικρότερο βιοτικό επίπεδο από " +
            "τον μέσο όρο της Ευρωζώνης κατά " + stdLiveGrEuz +
            " μονάδες του ευρώ.");
        } else if (stdLiveGrEuz < 0) {
            System.out.println("Η Ελλάδα έχει μεγαλύτερο βιοτικό επίπεδο από" +
            " τον μέσο όρο της Ευρωζώνης κατά " + Math.abs(stdLiveGrEuz) +
            " μονάδες του ευρώ.");
        } else {
            System.out.println("Η Ελλάδα έχει το ίδιο βιοτικό επίπεδο " +
            "με τον μέσο όρο της Ευρωζώνης");
        }
        }
    }
}
