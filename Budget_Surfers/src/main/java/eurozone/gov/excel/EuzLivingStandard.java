package eurozone.gov.excel;

public class EuzLivingStandard {
    public static long[][] compareToLong(String[][] gdppop) {
        long[][] gdppopulation = new long[19][2];
        for (int i = 0 ; i < 19 ; i++) {
            for (int j = 0 ; j < 2 ; j++) {
                gdppopulation[i][j] = Long.parseLong(gdppop[i][j+1]);
            }
        }
        return gdppopulation;
    }
    public static final int GRAVGGDP = 25300;
    public static double[] findStandLiving(long[][] gdppopulation) {
        double[] stdLive = new double[20];
        double sum = 0;
        for (int k = 0 ; k < 19 ; k++) {
            stdLive[k] = (double) (gdppopulation[k][0] / gdppopulation[k][1]);
            sum = sum + stdLive[k];
        }
        stdLive[19] = (sum + GRAVGGDP) / 20;
        return stdLive;
    }
    public static void compareStdLive(int a, double stdLive[], String [][] gdppop) {
        if (a != 20) {
            double stdLiveDiff = stdLive[a-1] - GRAVGGDP; // a - 1 because we want user's coices to start from number 1
        if (stdLiveDiff > 0) {
            System.out.println("Η " + gdppop[a-1][0] + " έχει μεγαλύτερο βιοτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff + " μονάδες του ευρώ.");
        } else if (stdLiveDiff < 0) {
            System.out.println("Η " + gdppop[a-1][0] + " έχει μικρότερο βιοτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff + " μονάδες του ευρώ.");
        } else {
            System.out.println("Η " + gdppop[a-1][0] + " έχει το ίδιο βιοτικό επίπεδο με την Ελλάδα.");
        }
        } else {
            double stdLiveGrEuz = stdLive[19] - GRAVGGDP;
            if (stdLiveGrEuz > 0) {
            System.out.println("Η Ελλάδα έχει μικρότερο βιοτικό επίπεδο από τον μέσο όρο της Ευρωζώνης κατά " + stdLiveGrEuz + " μονάδες του ευρώ.");
        } else if (stdLiveGrEuz < 0) {
            System.out.println("Η Ελλάδα έχει μεγαλύτερο βιοτικό επίπεδο από τον μέσο όρο της Ευρωζώνης κατά " + Math.abs(stdLiveGrEuz) + " μονάδες του ευρώ.");
        } else {
            System.out.println("Η Ελλάδα έχει το ίδιο βιοτικό επίπεδο με τον μέσο όρο της Ευρωζώνης");
        }
        }
    }
}
