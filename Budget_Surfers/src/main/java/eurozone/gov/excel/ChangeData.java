package eurozone.gov.excel;

import java.util.Scanner;

public class ChangeData {
    public static double[] newGrPercent(double[] grpercent, String[] grSectors) {
        Scanner input = new Scanner(System.in);
        double startsumpercent = grpercent[10];
        System.out.println("Αυτός είναι ο πίνακας με τις αρχικές ποσοστιαίες δαπάνες ανά τομέα.");
        for (int i = 0; i < grpercent.length; i++) {
            System.out.println((i + 1) + " " + grSectors[i] + " " + grpercent[i] + "%");
        }
        do {
            int input1 = -1;
            double input2 = -1.0;
            while (true) {
                try {
                    System.out.println("Μπορείς να μεταβάλλεις το ποσοστό οποιουδήποτε τομέα θέλεις, όμως " +
                    "το συνολικό ποσοστό πρέπει να μείνει ίσο με το αρχικό.");
                    System.out.println("Αρχικό συνολικό ποσοστό: " + startsumpercent + "%");
                    System.out.println("Νέο συνολικό ποσοστό: " + grpercent[10] + "%");
                    System.out.println("Διάλεξε τον αριθμό του τομέα του οποίου " +
                    "την ποσοστιαία δαπάνη θέλεις να αλλάξεις.");
                    System.out.println("Επιλέγοντας τον αριθμό για τον " + 
                    "συγκεκριμένο τομέα θα σου ζητηθεί να εισάγεις το νέο ποσοστό.");
                    input1 = input.nextInt();
                    if (input1 < 1 || input1 > 10) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι από 1 μέχρι 9.");
                    }
                    break;
                } catch (IllegalArgumentException e){
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
            System.out.println("Δώσε το νέο ποσοστό για τον τομέα (χωρίς το σύμβολο %)" + grSectors[input1 - 1]);
            while (true) {
                try {
                    input2 = input.nextDouble();
                    if (input2 <= 0.0) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι μεγαλύτερη από 0.");
                    }
                    grpercent[10] = Math.round((grpercent[10] + input2 - grpercent[input1 - 1]) * 100.0) / 100.0;
                    grpercent[input1 - 1] = input2;
                    System.out.println("Ο νέος πίνακας είναι ο εξής:");
                    for (int i = 0; i < grpercent.length; i++) {
                            System.out.println(i + 1 + " "+ grSectors[i] + " " + grpercent[i] + "%");
                    }
                    if (grpercent[10] != startsumpercent) {
                        System.out.println("Το συνολικό ποσοστό δεν είναι ίσο με το αρχικό ποσοστό, " +
                        "άρα θα πρέπει να μεταβάλλεις ποσοστά και άλλου/ων τομέα/ων");
                        if (grpercent[10] < startsumpercent) {
                        System.out.println("Μένει να πρσθέσεις " + Math.round((startsumpercent - grpercent[10]) * 100.0) / 100.0 + " σε κάποιον τομέα");
                        } else if (grpercent[10] > startsumpercent) {
                        System.out.println("Μένει να αφαιρέσεις " + Math.round((startsumpercent - grpercent[10]) * 100.0) / 100.0 + " από κάποιον τομέα");
                        }
                    }
                    break;
                } catch (IllegalArgumentException e){
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
        } while (grpercent[10] != startsumpercent);
        return grpercent;
    }
}
