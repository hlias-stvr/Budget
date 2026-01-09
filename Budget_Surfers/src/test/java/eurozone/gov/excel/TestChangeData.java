package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class TestChangeData {
    Scanner scanner = new Scanner(System.in);
    @Test
    void testNewGrPercent() {
        String[][] budget = ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] A = AvgEurozone.convertToLong(budget);
        double [] grpercent = AvgEurozone.ministrDiv(A);
        String [] grSectors = AvgEurozone.sectors();
        double [] testarray = ChangeData.newGrPercent(grpercent, grSectors, scanner); 
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
    @Test
    void testNewAmountPerRegion() {
        String[][] budget =
        ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        long [] budgetLong = AvgEurozone.convertToLong(budget);
        long [] testarray = ChangeData.newAmountPerRegion(budgetLong, budget, scanner);
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
    @Test
    void testNewRevenue() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData25.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        long [] testarray = ChangeData.newRevenue(LongData25, revenue, scanner);
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
}
