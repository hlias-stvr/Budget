package eurozone.gov.excel;

public class AvgEurozone {
    /*
    *Μετατροπή του κομματιού του πίνακα budget που περιέχει τις δαπάνες
    *για τα υπουργεία σε long
    */
    public static long[] convertToLong(String[][] budget) {
        long[] longMinistrExpenses = new long[20];
        for (int i = 4; i < 24; i++) {
            longMinistrExpenses[i - 4] = Long.parseLong(budget[i][2]);
        }
        return longMinistrExpenses;
    }

    // Διαχωρισμός υπουργείων σε τομείς για σύγκριση με την Ευρωζώνη
    public static double [] ministrDiv(long[] ministrExp) {
        final long GTP = 206000000000L;
        long publicserv = ministrExp[0] + ministrExp[16] + ministrExp[1] +
            ministrExp[7];
        long def = ministrExp[2];
        long pubordsaf = ministrExp[18] + ministrExp[4] +
            (long) (0.78 * ministrExp[19]);
        long econ = ministrExp[12] + (long) (0.80 * ministrExp[13])
            + ministrExp[8] + ministrExp[15] + ministrExp[14]
            + (long) (0.37 * ministrExp[9]);
        long envprot = (long) (0.41 * ministrExp[9]) +
            (long) (0.22 * ministrExp[19]);
        long houscomamen = (long) (0.20 * ministrExp[13]) +
            (long) (0.22 * ministrExp[9]);
        long health = ministrExp[3];
        long recrculrel = ministrExp[6] + (long) (0.06 * ministrExp[5]);
        long edu = (long) (0.94 * ministrExp[5]);
        long socialprot = ministrExp[10] + ministrExp[11] + ministrExp[17];
        long grsum = publicserv + def + pubordsaf + econ + envprot
            + houscomamen + health + recrculrel + edu + socialprot;
        long [] sectors = {publicserv, def, pubordsaf, econ, envprot,
            houscomamen, health, recrculrel, edu, socialprot, grsum};
        double [] percSectorsExpenses = new double[11];
        //Εύρεση ποσοστών Ελλάδας ανά τομέα
        for (int i = 0; i < 11; i++) {
            percSectorsExpenses[i] = Math.round(((double) sectors[i] /
                GTP * 100) * 100.0) / 100.0;
        }
        return percSectorsExpenses;
    }

    //Ονόματα τομέων
    public static String[] sectors() {
        String grSectors[] = {"publicserv", "def", "pubordsaf", "econ",
            "envprot", "houscomamen", "health", "recrculrel",
            "edu", "socialprot", "grsum"};
        return grSectors;
    }

    //Σύγκριση με Μέσο όρο ευρωζώνης
    public static double [] compareGrToEurozone(double[] percSectorsExpenses) {
        //Ποσοστά Ευρωζώνης στους τομείς
        double [] euzpercent = {6.0, 1.2, 1.7, 5.7, 0.9, 1.2,
            7.4, 1.1, 4.6, 19.8, 49.5};
            //Σύγκριση ποσοστών Ελλάδας με Μέσο όρο ευρωζώνης
        double [] percDiffGrEuz = new double[11];
        for (int i = 0; i < 11; i++) {
            percDiffGrEuz[i] = Math.round((euzpercent[i] -
                percSectorsExpenses[i]) * 100.0) / 100.0;
        }
        return percDiffGrEuz;
    }
}
