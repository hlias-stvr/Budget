package eurozone.gov.excel;
public class avgeurozone {
    public static int[] convertToInt(String[][] budget) {
        int[] grministr = new int[20];
        for (int i = 4; i < 24; i++) {
            grministr[i-4] = Integer.parseInt(budget[i][2]);
        }
        return grministr;
    }
    public static double ministrDiv(String[][] grministr) {
        final int GTP = 257100000000;
        int publicserv = grministr[4] + grministr[20] + grministr[5] + grministr[11];
        int def = grministr[6];
        int pubordsaf = grministr[22] + grministr[8] + (int) 0.78 * grministr[23];
        int econ = grministr[16] + (int) 0.80 * grministr[17] + grministr[12] + grministr[19] + grministr[18] + (int) 0.37 * grministr[13];
        int envprot = (int) 0.41 * grministr[13] + (int) 0.22 * grministr[23];
        int houscomamen = (int) 0.20 * grministr[17] + (int) 0.22 * grministr[13];
        int health = grministr[7];
        int recrculrel = grministr[10] + (int) 0.06 * grministr[9];
        int edu = (int) 0.94 * grministr[9];
        int socialprot = grministr[14] + grministr[15] + grministr[21];
        int grsum = publicserv + def + pubordsaf + econ + envprot + houscomamen + health + recrculrel + edu + socialprot;
        int [] sectors = {publicserv, def, pubordsaf, econ, envprot, houscomamen, health, recrculrel, edu, socialprot, sum};
        double [] grpercent = new double[11];
        for(int i = 0; i < 11; i++) {
            grpercent[i] = (double) sectors[i] / GTP * 100;
        }
        return grpercent;
    }
    public static double compareGrToEurozone(double[] grpercent) {
        double euzpercent = {6.0, 1.2, 1.7, 5.7, 0.9, 1.2, 7.4, 1.1, 4.6, 19.8, 49.5};
        double diffgreuz = new double[11];
        for(int i = 0; i<11; i++) {
            diffgreuz[i] = euzpercent[i] - grpercent[i];
        }
        return diffgreuz;
    }
}
