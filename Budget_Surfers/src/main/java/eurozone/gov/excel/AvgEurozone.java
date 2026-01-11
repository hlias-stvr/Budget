package eurozone.gov.excel;

public class AvgEurozone {
    // μετατροπή πίνακα budget σε long
    public static long[] convertToLong(String[][] budget) {
        long[] grministr = new long[20];
        for (int i = 4; i < 24; i++) {
            grministr[i - 4] = Long.parseLong(budget[i][2]);
        }
        return grministr;
    }

    // διαχωρισμός υπουργείων σε τομείς για σύγκριση με την Ευρωζώνη
    public static double [] ministrDiv(long[] grministr) {
        final long GTP = 206000000000L;
        long publicserv = grministr[0] + grministr[16] + grministr[1] +
            grministr[7];
        long def = grministr[2];
        long pubordsaf = grministr[18] + grministr[4] +
            (long) (0.78 * grministr[19]);
        long econ = grministr[12] + (long) (0.80 * grministr[13])
            + grministr[8] + grministr[15] + grministr[14]
            + (long) (0.37 * grministr[9]);
        long envprot = (long) (0.41 * grministr[9]) +
            (long) (0.22 * grministr[19]);
        long houscomamen = (long) (0.20 * grministr[13])
            + (long) (0.22 * grministr[9]);
        long health = grministr[3];
        long recrculrel = grministr[6] + (long) (0.06 * grministr[5]);
        long edu = (long) (0.94 * grministr[5]);
        long socialprot = grministr[10] + grministr[11] + grministr[17];
        long grsum = publicserv + def + pubordsaf + econ + envprot
            + houscomamen + health + recrculrel + edu + socialprot;
        long [] sectors = {publicserv, def, pubordsaf, econ, envprot,
            houscomamen, health, recrculrel, edu, socialprot, grsum};
        double [] grpercent = new double[11];
        //εύρεση ποσοστών Ελλάδας ανά τομέα
        for (int i = 0; i < 11; i++) {
            grpercent[i] = Math.round(((double) sectors[i] / GTP * 100)
                * 100.0) / 100.0;
        }
        return grpercent;
    }

    // ονόματα τομέων 
    public static String[] sectors() {
        String grSectors[] = {"publicserv", "def", "pubordsaf", "econ",
            "envprot", "houscomamen", "health", "recrculrel",
            "edu", "socialprot", "grsum"};
        return grSectors;
    }

    // σύγκριση με Μέσο όρο ευρωζώνης
    public static double [] compareGrToEurozone(double[] grpercent) {
        // ποσοστά Ευρωζώνης στους τομείς
        double [] euzpercent = {6.0, 1.2, 1.7, 5.7, 0.9, 1.2,
            7.4, 1.1, 4.6, 19.8, 49.5};
            // σύγκριση ποσοστών Ελλάδας με Μέσο όρο ευρωζώνης
        double [] diffgreuz = new double[11];
        for (int i = 0; i < 11; i++) {
            diffgreuz[i] = Math.round((euzpercent[i] - grpercent[i])
                * 100.0) / 100.0;
        }
        return diffgreuz;
    }
}
