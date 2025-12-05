package eurozone.gov.excel;
public class CompareEuzTaxes {
    public static void Calculation(long LongData[][]) {
        final long GTP = 257100000000L;
        double grtax = Math.round(((LongData[1][2] + LongData[2][2]) / GTP) * 1000.0) / 1000.0;
        double avgeuztax = 0.409;
        double dif = Math.round((grtax - avgeuztax) * 1000.0) / 1000.0;
        if (grtax > avgeuztax) {
            System.out.println("Τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία σε ποσοστό δια του ΑΕΠ της Ελλάδος ("
            + grtax + ") ξεπερνούν εκείνα του μέσου όρου της ευρωζώνης (" + avgeuztax + ") κατά" + dif + " %");
        } else {
            System.out.println("Τα έσοδα που λαμβάνουν κατα μέσο όρο οι χώρες-μέλη της ευρωζώνης από τη φορολογία σε ποσοστό δια του ΑΕΠ ("
            + avgeuztax + ") ξεπερνούν εκείνα του ελληνικού κράτους (" + grtax + ") κατά" + dif + " %");
        }
    }
}
