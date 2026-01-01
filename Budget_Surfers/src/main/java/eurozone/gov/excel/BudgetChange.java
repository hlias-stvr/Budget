package eurozone.gov.excel;

public class BudgetChange {
    String name;
    double amount;
    public BudgetChange(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
    @Override
    public String toString() {
        return name + ": " + amount;
    }
}
