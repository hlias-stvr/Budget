package eurozone.gov.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestPercent {
    @Test
    void testConvertToLong() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome = Percent.converterToLong(revenue, 14, 2);
        long[][] testLongExpenses = Percent.converterToLong(revenue, 16, 16);
        assertNotNull(testLongIncome);
        assertNotNull(testLongExpenses);
        assertEquals(14, testLongIncome.length);
        assertEquals(5, testLongIncome[0].length);
        assertEquals(16, testLongExpenses.length);
        assertEquals(5, testLongExpenses[0].length);
        for (int i = 0; i < testLongIncome.length; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(testLongIncome[i][j] >= 0);
            }
        }
        for (int i = 0; i < testLongExpenses.length; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(testLongExpenses[i][j] >= 0);
            }
        }
    }

    @Test
    void testPercentual() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome = Percent.converterToLong(revenue, 14, 2);
        long[][] testLongExpenses = Percent.converterToLong(revenue, 16, 16);
        double[][] testPercVarIncome = Percent.percentual(testLongIncome);
        double[][] percVarExpenses = Percent.percentual(testLongExpenses);
        assertNotNull(testPercVarIncome);
        assertEquals(0, testPercVarIncome[8][3]);
        assertNotNull(percVarExpenses);
        assertEquals(0, percVarExpenses[15][1]);
    }

    @Test
    void testAmount() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome = Percent.converterToLong(revenue, 14, 2);
        long[][] testLongExpenses = Percent.converterToLong(revenue, 16, 16);
        long[][] testAmountVarIncome = Percent.amount(testLongIncome);
        long[][] amountVarExpenses = Percent.amount(testLongExpenses);
        assertNotNull(testAmountVarIncome);
        assertEquals(2000000, testAmountVarIncome[2][3]);
        assertNotNull(amountVarExpenses);
        assertEquals(240000, amountVarExpenses[5][2]);
    }
}
