package eurozone.gov.excel;

public class BudgetChange {
    String name;
    double amount1;
    long amount2;
    public BudgetChange(String name, double amount) {
        this.name = name;
        amount1 = amount;
    }
    public BudgetChange(String name, long amount) {
        this.name = name;
        amount2 = amount;
    }
    @Override
    public String toString() {
        if (amount2 == 0) {
            return name + ": " + amount1;
        } else {
            return name + ": " + amount2;
        }
    }
}
