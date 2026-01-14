package eurozone.gov.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestEuzTaxes {
    @Test
    void testCalcDiffGrEuzTaxes() {
        String[][] revenueExpenses =
        ReadCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome =
         BudgetVariance.converterToLong(revenueExpenses, 14, 2);
        long[] testLongIncome25 = new long[testLongIncome.length];
        for (int i = 0; i < testLongIncome.length; i++) {
            testLongIncome25[i] = testLongIncome[i][0];
        }
        final long GRGDP = 206000000000L;
        double testGrTaxes = Math.round((((testLongIncome25[1] +
            testLongIncome25[2]) / (double) GRGDP) * 100) * 10.0) / 10.0;
        double testAvgEuzTaxes = Math.round(40.9 * 10.0) / 10.0;
        double dif = Math.round((Math.abs((testGrTaxes - testAvgEuzTaxes))) *
            10.0) / 10.0;
        assertEquals(testGrTaxes, 30.2);
        assertEquals(testAvgEuzTaxes, 40.9);
        assertEquals(dif, 10.7);
    }
}
