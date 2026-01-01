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
        int input3 = 1;
        do {
            int input1 = -1;
            double input2 = -1.0;
            while (true) {
                try {
                    System.out.println("Μπορείς να μεταβάλλεις το ποσοστό οποιουδήποτε τομέα θέλεις, όμως " +
                    "το συνολικό ποσοστό πρέπει να μείνει ίσο με το αρχικό.");
                    System.out.println("Αρχικό συνολικό ποσοστό: " + startsumpercent + "%");
                    System.out.println("Νέο συνολικό ποσοστό: " + grpercent[10] + "%");
                    if (grpercent[10] < startsumpercent) {
                        System.out.println("Μένει να πρσθέσεις " + Math.round(((startsumpercent - grpercent[10]) * 100.0) / 100.0)
                        + "% σε κάποιον/ους τομέα/εις");
                        } else if (grpercent[10] > startsumpercent) {
                        System.out.println("Μένει να αφαιρέσεις " + Math.abs(Math.round((startsumpercent - grpercent[10]) * 100.0) / 100.0)
                        + "% από κάποιον/ους τομέα/εις");
                        }
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
                    ChangesHistory.addChange(grSectors[input1 -1], input2);
                    System.out.println("Ο νέος πίνακας είναι ο εξής:");
                    for (int i = 0; i < grpercent.length; i++) {
                            System.out.println(i + 1 + " "+ grSectors[i] + " " + grpercent[i] + "%");
                    }
                    if (grpercent[10] != startsumpercent) {
                        System.out.println("Το συνολικό ποσοστό δεν είναι ίσο με το αρχικό ποσοστό, " +
                        "άρα θα πρέπει να μεταβάλλεις ποσοστά και άλλου/ων τομέα/ων");
                    } else {
                        System.out.println("Το συνολικό ποσοστό είναι ίσο με το αρχικό.");
                        System.out.println("Γράψε:\n0 Aν τελείωσες την μετατροπή των ποσοστών");
                        System.out.println("1 Αν θέλεις να συνεχίσεις την μετατροπή των ποσοστών");
                        while (true) {
                            try {
                                input3 = input.nextInt();
                                if (input3 < 0 || input3 > 1) {
                                    throw new IllegalArgumentException("Η επιλογή πρέπει να είναι 0 ή 1");
                                }
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Σφάλμα" + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("Πρέπει να δώσεις αριθμό");
                                input.nextLine();
                            }
                        }   
                    }
                    break;
                } catch (IllegalArgumentException e) {
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
        } while ((grpercent[10] != startsumpercent) || (input3 == 1));
        return grpercent;
    }
    public static long[] newAmountPerRegion(long[] budgetLong, String[][] budget) {
        Scanner input = new Scanner(System.in);
        long sum = 0;
        for (int i = 0; i < budgetLong.length; i++) {
            sum += budgetLong[i];
        }
        System.out.println("Αυτός είναι ο πίνακας με τα αρχικά ποσά δαπανών ανά περιφέρεια");
        for (int i = 0; i < budgetLong.length; i++) {
            System.out.print((i + 1) + " " + budget[i + 25][1] + " " + budgetLong[i]);
            System.out.println(" που αντιστοιχεί στο " + (Math.round(((budgetLong[i] /(double) sum) * 100) * 100.0) / 100.0)
            + "% των συνολικών δαπανών για περιφέρειες");
        }
        int input3 = 1;
        do {
            int input1 = -1;
            long input2 = -10000000000L;
            while (true) {
                try {
                    System.out.println("Μπορείς να μεταβάλλεις το ποσό οποιασδήποτε περιφέρειας θέλεις, όμως " +
                    "το συνολικό ποσό πρέπει να μείνει ίσο με το αρχικό.");
                    System.out.println("Αρχικό συνολικό ποσό: " + "79592000");
                    System.out.println("Νέο συνολικό ποσό: " + sum);
                    if (sum < 79592000) {
                        System.out.println("Μένει να προσθέσεις " + (79592000 - sum)
                        + " σε κάποια/ες περιφέρεια/ες");
                    } else if (sum > 79592000) {
                        System.out.println("Μένει να αφαιρέσεις " + (sum - 79592000)
                        + " από κάποια/ες περιφέρεια/ες");
                    }
                    System.out.println("Διάλεξε τον αριθμό της περιφέρειας της οποίας " +
                    "την δαπάνη θέλεις να αλλάξεις.");
                    System.out.println("Επιλέγοντας τον αριθμό για την " +
                    "συγκεκριμένη περιφέρεια θα σου ζητηθεί να εισάγεις το νέο ποσό.");
                    input1 = input.nextInt();
                    if (input1 < 1 || input1 > 7) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι από 1 μέχρι 7.");
                    }
                    break;
                } catch (IllegalArgumentException e){
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
            System.out.println("Δώσε το νέο ποσό για την περιφέρεια " + budget[input1 + 25 - 1][1]);
            while (true) {
                try {
                    input2 = input.nextLong();
                    if (input2 <= 0) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι μεγαλύτερη από 0.");
                    }
                    sum = sum + input2 - budgetLong[input1 - 1];
                    budgetLong[input1 - 1] = input2;
                    ChangesHistory.addChange(budget[input1 + 25 -1][1], (double) input2);
                    System.out.println("Ο νέος πίνακας είναι ο εξής:");
                    for (int i = 0; i < budgetLong.length; i++) {
                            System.out.print((i + 1) + " "+ budget[i + 25][1] + " " + budgetLong[i]);
                            System.out.println(" που αντιστοιχεί στο " + (Math.round(((budgetLong[i] /(double) sum) * 100) * 100.0) / 100.0)
                            + "% των συνολικών δαπανών για περιφέρειες");
                    }
                    if (sum != 79592000) {
                        System.out.println("Το συνολικό ποσό δεν είναι ίσο με το αρχικό ποσό, " +
                        "άρα θα πρέπει να μεταβάλλεις ποσό/ά και άλλης/ων περιφέρειας/ων");
                    } else {
                        System.out.println("Το συνολικό ποσό είναι ίσο με το αρχικό.");
                        System.out.println("Γράψε:\n0 Aν τελείωσες την μετατροπή των ποσών");
                        System.out.println("1 Αν θέλεις να συνεχίσεις την μετατροπή των ποσών");
                        while (true) {
                            try {
                                input3 = input.nextInt();
                                if (input3 < 0 || input3 > 1) {
                                    throw new IllegalArgumentException("Η επιλογή πρέπει να είναι 0 ή 1");
                                }
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Σφάλμα" + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("Πρέπει να δώσεις αριθμό");
                                input.nextLine();
                            }
                        }   
                    }
                    break;
                } catch (IllegalArgumentException e) {
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
        } while ((sum != 79592000) || (input3 == 1));
         return budgetLong;
    }
    public static long[] newRevenue(long[] LongData, String[][] revenue) {
        Scanner input = new Scanner(System.in);
        long startsum = LongData[0];
        System.out.println("Αυτός είναι ο πίνακας με τα αρχικά αναλυτικά έσοδα");
        System.out.println("ΣΥΝΟΛΙΚΑ " + revenue[0][1] + " " + LongData[0]);
        for (int i = 1; i < LongData.length; i++) {
            System.out.print(i + " " + revenue[i][1] + " " + LongData[i]);
            System.out.println(" που αντιστοιχεί στο " + (Math.round(((LongData[i] /(double) startsum) * 100) * 100.0) / 100.0)
            + "% των συνολικών εσόδων");
        }
        int input3 = 1;
        do {
            int input1 = -1;
            long input2 = -10000000000L;
            while (true) {
                try {
                    System.out.println("Μπορείς να μεταβάλλεις το ποσό οποιασδήποτε κατηγορίας εσόδων θέλεις, όμως " +
                    "το συνολικό ποσό πρέπει να μείνει ίσο με το αρχικό.");
                    System.out.println("Αρχικό συνολικό ποσό: " + startsum);
                    System.out.println("Νέο συνολικό ποσό: " + LongData[0]);
                    System.out.print("Προτείνουμε ,αρχικά, να μεταβάλλεις το ποσό των φόρων,");
                    System.out.print(" καθώς στην συνέχεια θα έχεις την επιλογή");
                    System.out.print(" να συγκρίνεις τα νέα φορλογικά έσοδα");
                    System.out.println("αναλογικά με τον μέσο όρο της Ευρωζώνης");
                    if (LongData[0] < startsum) {
                        System.out.println("Μένει να προσθέσεις " + (startsum - LongData[0])
                        + " σε κάποια/ες κατηγορία/ες");
                    } else if (LongData[0] > startsum) {
                        System.out.println("Μένει να αφαιρέσεις " + (LongData[0] - startsum)
                        + " από κάποια/ες κατηγορία/ες");
                    }
                    System.out.println("Διάλεξε τον αριθμό της κατηγορίας της οποίας " +
                    "το ποσό θέλεις να αλλάξεις.");
                    System.out.println("Επιλέγοντας τον αριθμό για την " +
                    "συγκεκριμένη κατηγορία θα σου ζητηθεί να εισάγεις το νέο ποσό.");
                    input1 = input.nextInt();
                    if (input1 < 1 || input1 > 13) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι από 1 μέχρι 13.");
                    }
                    break;
                } catch (IllegalArgumentException e){
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
            System.out.println("Δώσε το νέο ποσό για την κατηγορία " + revenue[input1][1]);
            while (true) {
                try {
                    input2 = input.nextLong();
                    if (input2 <= 0) {
                        throw new IllegalArgumentException(" Η επιλογή πρέπει να είναι μεγαλύτερη από 0.");
                    }
                    LongData[0] = LongData[0] + input2 - LongData[input1];
                    LongData[input1] = input2;
                    ChangesHistory.addChange(revenue[input1][1], input2);
                    System.out.println("Ο νέος πίνακας είναι ο εξής:");
                    System.out.println("ΣΥΝΟΛΙΚΑ " + revenue[0][1] + " " + LongData[0]);
                    for (int i = 1; i < LongData.length; i++) {
                            System.out.print(i + " "+ revenue[i][1] + " " + LongData[i]);
                            System.out.println(" που αντιστοιχεί στο " + (Math.round(((LongData[i] /(double) LongData[0]) * 100) * 100.0) / 100.0)
                            + "% των συνολικών εσόδων");
                    }
                    if (LongData[0] != startsum) {
                        System.out.println("Το συνολικό ποσό δεν είναι ίσο με το αρχικό ποσό, " +
                        "άρα θα πρέπει να μεταβάλλεις ποσό/ά και άλλης/ων κατηγορίας/ων");
                    } else {
                        System.out.println("Το συνολικό ποσό είναι ίσο με το αρχικό.");
                        System.out.println("Γράψε:\n0 Aν τελείωσες την μετατροπή των ποσών");
                        System.out.println("1 Αν θέλεις να συνεχίσεις την μετατροπή των ποσών");
                        while (true) {
                            try {
                                input3 = input.nextInt();
                                if (input3 < 0 || input3 > 1) {
                                    throw new IllegalArgumentException("Η επιλογή πρέπει να είναι 0 ή 1");
                                }
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Σφάλμα" + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("Πρέπει να δώσεις αριθμό");
                                input.nextLine();
                            }
                        }   
                    }
                    break;
                } catch (IllegalArgumentException e) {
                        System.out.println("Σφάλμα" + e.getMessage());
                } catch (Exception e) {
                        System.out.println("Πρέπει να δώσεις αριθμό");
                        input.nextLine();
                }
            }
        } while ((LongData[0] != startsum) || (input3 == 1));
        return LongData;
    }
}
