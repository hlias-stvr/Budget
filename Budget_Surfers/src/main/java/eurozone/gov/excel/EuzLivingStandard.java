package eurozone.gov.excel;

public class EuzLivingStandard {
    public static long[][] compareToLong(String[][] gdppop) {
        long[][] gdppopulation = new long[19][2];
        for (int i = 0 ; i < 19 ; i++) {
            for (int j = 0 ; j < 2 ; j++) {
                gdppopulation[i][j] = Long.parseLong(gdppop[i][j+1]);
            }
        }
        return Gdppopulation;
    }
    public static final int GRAVGGDP = 25300;
    public static double[] findStandLiving(Long[][] gdppopulation) {
        double[] stdLive = new double[20];
        int sum = 0;
        for (int k = 0 ; k < 19 ; k++) {
            stdLive[k] = (double) (gdppopulation[k][0] / gdppopulation[k][1]);
            sum = sum + stdLive[k];
        }
        stdLive[19] = (sum + GRAVGGDP) / 20;
        return stdLive;
    }
    public static void compareStdLive(int a, double stdLive[], String gdppop[][]) {
        double stdLiveDiff = stdLive[a] - GRAVGGDP;
        if (stdLiveDiff > 0) {
            System.out.println("Η " + gdppop[a][0] + " έχει μεγαλύτερο βιωτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff + " μονάδες του ευρώ.");
        } else if (stdLiveDiff < 0) {
            System.out.println("Η " + gdppop[a][0] + " έχει μικρότερο βιωτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff + " μονάδες του ευρώ.");
        } else {
             System.out.println("Η " + gdppop[a][0] + " έχει το ίδιο βιωτικό επίπεδο με την Ελλάδα.");
        }
    }
}
