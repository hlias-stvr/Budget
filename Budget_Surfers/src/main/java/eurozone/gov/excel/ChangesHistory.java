package eurozone.gov.excel;

import java.util.ArrayList;

public class ChangesHistory {
    private static ArrayList<BudgetChange> changes = new ArrayList<>();

    public static void addChangeDouble(String name, double amount) {
        changes.add(new BudgetChange(name, amount));
    }

    public static void addChangeLong(String name, long amount) {
        changes.add(new BudgetChange(name, amount));
    }

    public static void printAll() {
        if (changes.isEmpty()) {
            System.out.println("Η λίστα αλλαγών είναι κενή.");
        } else {
            System.out.println("Οι αλλαγές που έχουν γίνει είναι:");
            System.out.println("Κατηγορία      Νέα Τιμή");
            for (BudgetChange change : changes) {
                System.out.println(change);
            }
        }
    }
}
