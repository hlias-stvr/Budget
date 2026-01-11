package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegionalPer {
    @Test
    void testTransformToLong() {
        String[][] budget =
        ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        long[] testLongRegionExpenses = RegionalPer.transformToLong(budget);
        assertNotNull(testLongRegionExpenses);
        assertEquals(7, testLongRegionExpenses);
        for (int i = 0; i < testLongRegionExpenses.length; i++) {
            assertTrue(testLongRegionExpenses[i] > 0);
        }
    }
    @Test
    void testCalcBudgetPerPerson() {
        String[][] budget =
        ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        long[] testLongRegionExpenses = RegionalPer.transformToLong(budget);
        double[] testExpensesPerPerson =
            RegionalPer.calcBudgetPerPerson(testLongRegionExpenses);
        assertNotNull(testExpensesPerPerson);
        assertEquals(7, testExpensesPerPerson.length);
        for (int i = 0; i < testExpensesPerPerson.length; i++) {
            assertTrue(testExpensesPerPerson[i] > 0);
        }
    }
    @Test
    void testCalcBudgetPerRegion() {
        String[][] budget =
        ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        long[] testLongRegionExpenses = RegionalPer.transformToLong(budget);
        double[] testPercExpensesPerRegion =
            RegionalPer.calcBudgetPerRegion(testLongRegionExpenses);
        assertNotNull(testPercExpensesPerRegion);
        assertEquals(7, testPercExpensesPerRegion.length);
        for (int i = 0; i < testPercExpensesPerRegion.length; i++) {
            assertTrue(testPercExpensesPerRegion[i] > 0);
        }
    }
}
