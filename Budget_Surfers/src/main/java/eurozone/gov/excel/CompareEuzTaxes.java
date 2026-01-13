package eurozone.gov.excel;

/*
* Σύγκριση φορολογικών εσόδων σε ποσοστό διά του ΑΕΠ της Ελλάδας με τα
* αντίστοιχα φορολογικά έσοδα σε ποσοστό διά του ΑΕΠ του μέσου όρου της
* Ευρωζώνης
*/
public class CompareEuzTaxes {
    public static void calcDiffGrEuzTaxes(long longIncome25[]) {
        final long GTP = 206000000000L;
        double grTaxes = Math.round((((longIncome25[1] + longIncome25[2]) /
            (double) GTP) * 100) * 10.0) / 10.0;
        double avgEuzTaxes = Math.round(40.9 * 10.0) / 10.0;
        double diffGrEuzTaxes =
            Math.round((Math.abs((grTaxes - avgEuzTaxes))) * 10.0) / 10.0;
        if (grTaxes > avgEuzTaxes) {
            System.out.println("Τα έσοδα που λαμβάνει το ελληνικό κράτος από " +
                "τη φορολογία σε ποσοστό δια του ΑΕΠ της Ελλάδος ("
                + grTaxes + "%) ξεπερνούν εκείνα του μέσου όρου της " +
                "ευρωζώνης (" + avgEuzTaxes + "%) κατά " + diffGrEuzTaxes +
                 " %");
        } else {
            System.out.println("Τα έσοδα που λαμβάνουν κατα μέσο όρο οι " +
                "χώρες-μέλη της ευρωζώνης από τη " +
                "φορολογία σε ποσοστό δια του ΑΕΠ ("
                + avgEuzTaxes + "%) ξεπερνούν εκείνα του ελληνικού " +
                "κράτους (" + grTaxes + "%) κατά " + diffGrEuzTaxes + " %");
        }
    }
}
