package eurozone.gov.excel;
//σύγκριση φορολογικών εσόδων της Ελλάδας με τον μέσο όρο της Ευρωζώνης 
public class CompareEuzTaxes {
    public static void Calculation(long LongData[]) {
        final long GTP = 206000000000L;
        double grtax = Math.round((((LongData[1] + LongData[2]) /
        (double) GTP) * 100) * 10.0) / 10.0;
        double avgeuztax = Math.round(40.9 * 10.0) / 10.0;
        double dif = Math.round((Math.abs((grtax - avgeuztax))) * 10.0) / 10.0;
        if (grtax > avgeuztax) {
            System.out.println("Τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία σε ποσοστό δια του ΑΕΠ της Ελλάδος ("
            + grtax + "%) ξεπερνούν εκείνα του μέσου όρου της ευρωζώνης (" + avgeuztax + "%) κατά " + dif + " %");
        } else {
            System.out.println("Τα έσοδα που λαμβάνουν κατα μέσο όρο οι χώρες-μέλη της ευρωζώνης από τη φορολογία σε ποσοστό δια του ΑΕΠ ("
            + avgeuztax + "%) ξεπερνούν εκείνα του ελληνικού κράτους (" + grtax + "%) κατά " + dif + " %");
        }
    }
}
