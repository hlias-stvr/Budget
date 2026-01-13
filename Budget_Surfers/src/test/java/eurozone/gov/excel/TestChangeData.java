package eurozone.gov.excel;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;

public class TestChangeData {
    @Test
    void testNewGrPercent() {
        String[][] budget = ReadCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        double [] testPercSectorsExpenses =
            AvgEurozone.ministrDiv(testLongMinistrExpenses);
        String [] testGrSectors = AvgEurozone.sectors();
        String input =
            "2\n2\n" +
            "9\n3.99\n" +
            "0\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        double [] testNewSectorsExpenses =
            ChangeData.newGrPercent(testPercSectorsExpenses,
            testGrSectors, scanner);
        assertEquals(2, testNewSectorsExpenses[1]);
        assertEquals(3.99, testNewSectorsExpenses[8]);
    }

    @Test
    void testNewAmountPerRegion() {
        String[][] budget =
        ReadCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        String input =
            "1\n12091000\n" +
            "4\n15918000\n" +
            "0\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        long [] testNewRegionExpenses =
            ChangeData.newAmountPerRegion(testLongMinistrExpenses, budget,
            scanner);
        assertEquals(12091000, testNewRegionExpenses[0]);
        assertEquals(15918000, testNewRegionExpenses[3]);
    }

    @Test
    void testNewRevenue() {
        String[][] revenue =
        ReadCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testLongIncome = BudgetVariance.converterToLong(
            revenue, 14, 2);
        long[] testLongIncome25 = new long[testLongIncome.length];
        for (int i = 0; i < testLongIncome25.length; i++) {
            testLongIncome25[i] = testLongIncome[i][0];
        }
        String input =
            "2\n55000000\n" +
            "8\n25000000\n" +
            "0\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        long [] testarray =
            ChangeData.newRevenue(testLongIncome25, revenue, scanner);
        assertEquals(55000000, testarray[1]);
        assertEquals(25000000, testarray[7]);
    }
}
