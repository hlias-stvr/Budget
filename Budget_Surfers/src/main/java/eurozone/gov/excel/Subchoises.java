package eurozone.gov.excel;

public class Subchoises {
    public void subchoice2a(double[] B, String[] grSectors) {
        for(int i = 0; i < 11; i++) {
            System.out.println("Η Ελλάδα δαπανά " + B[i] + "%" + " στον τομέα " + grSectors[i]);
        }
    }
}
