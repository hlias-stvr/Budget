package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestChangeData {
    @Test
    void testNewGrPercent() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\main\resourses\gr_ministy_25.csv");
        long [] A = avgeurozone.convertToLong(budget);
        double [] grpercent = avgeurozone.ministrDiv(A);
        String [] grSectors = avgeurozone.sectors();
        double [] testarray = ChangeData.newGrPercent(grpercent, grSectors); 
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
    @Test
    void testNewAmountPerRegion() {
        String[][] budget =
        ReadTwoCsvFiles.readCsv("src\main\resourses\gr_ministy_25.csv");
        long [] budgetLong = avgeurozone.convertToLong(budget);
        long [] testarray = ChangeData.newAmountPerRegion(budgetLong, budget);
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
    @Test
    void testNewRevenue() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv(
        "src\main\resourses\gr_revenue_expenses_25.csv");
        long[][] LongData = percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData25.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        long [] testarray = ChangeData.newRevenue(LongData25, revenue);
        assertNotNull(testarray);
        for (int i = 0; i < testarray.length - 1; i++) {
            assertTrue(testarray[i] > 0);
        }
    }
}
