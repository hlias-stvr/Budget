public class CompareEuzTaxes {
    public static long Calculation(long grt){
        final long grtax = grt;
        final long avgeuztax = 248555555556;
        if (grtax > avgeuztax) {
            int dif = grtax - avgeuztax;
            System.out.println("Τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία (" + grtax + ") ξεπερνούν τον μέσο όρο της ευρωζώνης (" + avgeuztax + ") κατά" + dif);
        } else {
            System.out.println("Τα έσοδα που λαμβάνουν οι χώρες-μέλη της ευρωζώνης κατα μέσο όρο (" + avgeuztax + ") ξεπερνούν τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία  (" + grtax + ") κατά" + dif);
    }
    }
}
