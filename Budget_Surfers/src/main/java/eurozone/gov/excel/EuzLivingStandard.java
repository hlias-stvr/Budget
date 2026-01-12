package eurozone.gov.excel;

public class EuzLivingStandard {
    //Mετατροπή πίνακα gdpPop σε long
    public static long[][] compareToLong(String[][] gdpPop) {
        long[][] longGdpPop = new long[19][2];
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 2; j++) {
                longGdpPop[i][j] = Long.parseLong(gdpPop[i][j + 1]);
            }
        }
        return longGdpPop;
    }

    public static final int GRAVGGDP = 25300;

    //Δημιουργεί πίνακα με τα κατά κεφαλήν ΑΕΠ όλων των χωρών της Ευρωζώνης
    public static double[] findStandLiving(long[][] longGdpPop) {
        double[] euzGdpPerCapita = new double[20];
        double sumLivingStd = 0;
        for (int k = 0; k < 19; k++) {
            euzGdpPerCapita[k] = (double) (longGdpPop[k][0] / longGdpPop[k][1]);
            sumLivingStd = sumLivingStd + euzGdpPerCapita[k];
        }
        euzGdpPerCapita[19] = (double) ((sumLivingStd + GRAVGGDP) / 20);
        return euzGdpPerCapita;
    }

    /*
    *Συγκρίνει το κατά κεφαλήν ΑΕΠ(ως δείκτη του βιοτικού επιπέδου)
    *της Ελλάδας με αυτό κάποιας επιλεγμένης χώρας
    */
    public static void compareStdLive(int a,
        double[] euzStdLiv,
        String[][] gdpPop) {
        if (a != 20) {
            double stdLiveDiff = euzStdLiv[a - 1] - GRAVGGDP; /* a - 1 because
            we want user's coices to start from number 1 */
            if (stdLiveDiff > 0) {
                System.out.println("Η " + gdpPop[a - 1][0] +
                    " έχει μεγαλύτερο " +
                    "βιοτικό επίπεδο από την Ελλάδα κατά " + stdLiveDiff +
                    " μονάδες του ευρώ.");
            } else if (stdLiveDiff < 0) {
                System.out.println("Η " + gdpPop[a - 1][0] +
                    " έχει μικρότερο βιοτικό" +
                    " επίπεδο από την Ελλάδα κατά " + Math.abs(stdLiveDiff) +
                    " μονάδες του ευρώ.");
            } else {
                System.out.println("Η " + gdpPop[a - 1][0] +
                    " έχει το ίδιο βιοτικό επίπεδο με την Ελλάδα.");
            }
        } else {
            double stdLiveGrEuz = Math.round((euzStdLiv[19] - GRAVGGDP) * 10.0)
                / 10.0;
            if (stdLiveGrEuz > 0) {
                System.out.println("Η Ελλάδα έχει μικρότερο βιοτικό επίπεδο" +
                    " από τον μέσο όρο της Ευρωζώνης κατά " + stdLiveGrEuz +
                    " μονάδες του ευρώ.");
            } else if (stdLiveGrEuz < 0) {
                System.out.println("Η Ελλάδα έχει μεγαλύτερο βιοτικό επίπεδο" +
                    " από τον μέσο όρο της Ευρωζώνης κατά " +
                    Math.abs(stdLiveGrEuz) +
                    " μονάδες του ευρώ.");
            } else {
                System.out.println("Η Ελλάδα έχει το ίδιο βιοτικό επίπεδο " +
                    "με τον μέσο όρο της Ευρωζώνης");
            }
        }
    }
}
