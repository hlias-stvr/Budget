package eurozone.gov.excel;

public class Subchoises {
    public void subchoice2a(double[] B, String[] grSectors) {
        for(int i = 0; i < 11; i++) {
            System.out.println("Η Ελλάδα δαπανά " + B[i] + "%" + " στον τομέα " + grSectors[i]);
        }
    }
    public void subchoice2b(double[] C, String[] grSectors) {
        for(int i = 0; i < 11; i++) {
            if(C[i] > 0) {
                System.out.println("Η Ελλάδα δαπανά "+ C[i] + "% λιγότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
            } else if(C[i] < 0) {
                System.out.println("Η Ελλάδα δαπανά "+ Math.abs(C[i]) + "% περισσότερο στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
            } else {
                System.out.println("Η Ελλάδα δαπανά το ίδιο ποσοστό στον τομέα " + grSectors[i] + " από τον ΜΟ της Ευρωζώνης");
            }
        }
    }
}

