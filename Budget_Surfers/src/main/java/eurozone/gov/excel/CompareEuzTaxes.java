public class CompareEuzTaxes {
    public static void Calculation(long grt){
        long grtax = grt;
        long avgeuztax = 248555555556;
        long dif = grtax - avgeuztax;
        if (grtax > avgeuztax) {
            System.out.println("Τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία (" + grtax + ") ξεπερνούν τον μέσο όρο της ευρωζώνης (" + avgeuztax + ") κατά" + dif);
        } else {
            System.out.println("Τα έσοδα που λαμβάνουν οι χώρες-μέλη της ευρωζώνης κατα μέσο όρο (" + avgeuztax + ") ξεπερνούν τα έσοδα που λαμβάνει το ελληνικό κράτος από τη φορολογία  (" + grtax + ") κατά" + dif);
        }
    }
}
