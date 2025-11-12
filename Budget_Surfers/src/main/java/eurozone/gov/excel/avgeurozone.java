package eurozone.gov.excel;
public class avgeurozone {
    public static double ministrDiv(String[][] budget) {
        final int GTP = 257100000000;
        int publicserv = budget[4][2] + budget[20][2] + budget[5][2] + budget[11][2];
        int def = budget[6][2];
        int pubordsaf = budget[22][2] + budget[8][2] + (int) 0.78 * budget[23][2];
        int econ = budget[16][2] + (int) 0.80 * budget[17][2] + budget[12][2] + budget[19][2] + budget[18][2] + (int) 0.37 * budget[13][2];
        int envprot = (int) 0.41 * budget[13][2] + (int) 0.22 * budget[23][2];
        int houscomamen = (int) 0.20 * budget[17][2] + (int) 0.22 * budget[13][2];
        int health = budget[7][2];
        int recrculrel = budget[10][2] + (int) 0.06 * budget[9][2];
        int edu = (int) 0.94 * budget[9][2];
        int socialprot = budget[14][2] + budget[15][2] + budget[21][2];
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
