package eurozone.gov.excel;

import java.util.Scanner;

public class ChangeData {
    public static double[] newgrpercent(double[] grpercent, String[] grSectors) {
        Scanner input = new Scanner(System.in);
        double startsumpercent = grpercent[10];
        System.out.println("Αυτός είναι ο πίνακας με τις αρχικές ποσοστιαίες δαπάνες ανά τομέα.");
        for (int i = 0; i < grpercent.length; i++) {
            System.out.println(i + 1 + " " + grSectors[i] + " " + grpercent[i]);
        }
        do {
            System.out.println("Μπορείς να μεταβάλλεις το ποσοστό οποιουδήποτε τομέα θέλεις, όμως " +
            "το συνολικό ποσοστό πρέπει να μείνει ίσο με το αρχικό.");
            System.out.println("Αρχικό συνολικό ποσοστό: " + startsumpercent);
            System.out.println("Νέο συνολικό ποσοστό: " + grpercent[10]);
            System.out.println("Διάλεξε τον αριθμό του τομέα του οποίου " +
            "την ποσοστιαία δαπάνη θέλεις να αλλάξεις.");
            System.out.println("Επιλέγοντας τον αριθμό για τον" + 
            "συγκεκριμένο τομέα θα σου ζητηθεί να εισάγεις το νέο ποσοστό.");
            int input1 = input.nextInt();
            System.out.println("Δώσε το νέο ποσοστό για τον τομέα " + grSectors[input1 - 1]);
            double input2 = input.nextDouble();
            if (input2 > grpercent[input1 - 1]) {
                grpercent[10] = grpercent[10] + input2 - grpercent[input1 - 1];
            } else {
                grpercent[10] = grpercent[10] - input2 + grpercent[input1 - 1]; 
            }
            grpercent[input1 - 1] = input2;
            System.out.println("Ο νέος πίνακας είναι ο εξής:");
            for (int i = 0; i < grpercent.length; i++) {
                System.out.println(i + 1 + " " + grSectors[i] + " " + grpercent[i]);
            }
            if (grpercent[10] != startsumpercent) {
                System.out.println("Το συνολικό ποσοστό δεν είναι ίσο με το αρχικό ποσοστό, " +
                "άρα θα πρέπει να μεταβάλλεις ποσοστά και άλλου/ων τομέα/ων");
            }
        } while (grpercent[10] == startsumpercent);
        return grpercent;
    }
}