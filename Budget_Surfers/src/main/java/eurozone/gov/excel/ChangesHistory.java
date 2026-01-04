package eurozone.gov.excel;
import java.util.ArrayList;

public class ChangesHistory {
    private static ArrayList<BudgetChange> changes = new ArrayList<>();
    public static void addChange(String name, double amount) {
        changes.add(new BudgetChange(name, amount)); 
    }
    public static void printAll() {
        if (changes.isEmpty()) {
            System.out.println("Η λίστα είναι κενή.");
        } else {
            for (BudgetChange change : changes) {
                System.out.println(change);
            }
        }
    }
}
