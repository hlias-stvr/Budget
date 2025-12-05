package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPercent {
    @Test
    void testconvertToLong() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_revenue_expenses_25.csv");
        long[][] testarray1 = percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = percent.converterToLong(revenue, 16, 16);
        assertNotNull(testarray1);
        assertNotNull(testarray2);
        assertEquals(14,testarray1.length);
        assertEquals(2,testarray1[0].length);
        assertEquals(16,testarray2.length);
        assertEquals(16,testarray1[0].length);
        for (int i = 0 ; i < (testarray1.length + testarray2.length) ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                if (i < testarray1.length) {
                    assertTrue(testarray1[i][j] >= 0);
                } else {
                    assertTrue(testarray2[i][j] >= 0);
                }
            }
        }
    }
    @Test
    void testpercentual() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_revenue_expenses_25.csv");
        long[][] testarray1 = percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = percent.converterToLong(revenue, 16, 16);
        double[][] testarray3 = percent.percentual(testarray1);
        assertNotNull(testarray3);
        assertEquals(0, testarray3[8][3]);
        double[][] testarray4 = percent.percentual(testarray2);
        assertNotNull(testarray4);
        assertEquals(0, testarray4[15][2]);
    }
    @Test
    void testamount() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_revenue_expenses_25.csv");
        long[][] testarray1 = percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = percent.converterToLong(revenue, 16, 16);
        long[][] testarray5 = percent.amount(testarray1);
        assertNotNull(testarray5);
        assertEquals(2000000, testarray5[2][3]);
        long[][] testarray6 = percent.amount(testarray2);
        assertNotNull(testarray6);
        assertEquals(240000, testarray6[5][2]);
    }
}
