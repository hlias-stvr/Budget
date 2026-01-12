package eurozone.gov.excel;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestChangeData {
    Scanner scanner = new Scanner(System.in);

    @Test
    void testNewGrPercent() {
        String[][] budget = ReadCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        double [] testPercSectorsExpenses =
            AvgEurozone.ministrDiv(testLongMinistrExpenses);
        String [] testGrSectors = AvgEurozone.sectors();
        double [] testNewSectorsExpenses =
            ChangeData.newGrPercent(testPercSectorsExpenses,
            testGrSectors, scanner);
        assertNotNull(testNewSectorsExpenses);
        for (int i = 0; i < testNewSectorsExpenses.length - 1; i++) {
            assertTrue(testNewSectorsExpenses[i] > 0);
        }
    }

    @Test
    void testNewAmountPerRegion() {
        String[][] budget =
        ReadCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        long [] testNewRegionExpenses =
            ChangeData.newAmountPerRegion(testLongMinistrExpenses, budget,
            scanner);
        assertNotNull(testNewRegionExpenses);
        for (int i = 0; i < testNewRegionExpenses.length - 1; i++) {
            assertTrue(testNewRegionExpenses[i] > 0);
        }
    }

    @Test
    void testNewRevenue() {
        String[][] revenue =
        ReadCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome = BudgetVariance.converterToLong(revenue, 14, 2);
        long[] testLongIncome25 = new long[testLongIncome.length];
        for (int i = 0; i < testLongIncome25.length; i++) {
            testLongIncome25[i] = testLongIncome[i][0];
        }
        long [] testarray =
            ChangeData.newRevenue(testLongIncome25, revenue, scanner);
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
}
