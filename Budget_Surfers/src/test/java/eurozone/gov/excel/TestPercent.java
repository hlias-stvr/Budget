package eurozone.gov.excel;
 
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class TestPercent {
    @Test
    void testconvertToLong() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testarray1 = Percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = Percent.converterToLong(revenue, 16, 16);
        assertNotNull(testarray1);
        assertNotNull(testarray2);
        assertEquals(14,testarray1.length);
        assertEquals(5,testarray1[0].length);
        assertEquals(16,testarray2.length);
        assertEquals(5,testarray1[0].length);
        for (int i = 0 ; i < testarray1.length; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                assertTrue(testarray1[i][j] >= 0);
            }
        }
        for (int i = 0 ; i < testarray2.length; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                assertTrue(testarray2[i][j] >= 0);
            }
        }
    }
    @Test
    void testpercentual() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testarray1 = Percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = Percent.converterToLong(revenue, 16, 16);
        double[][] testarray3 = Percent.percentual(testarray1);
        assertNotNull(testarray3);
        assertEquals(0, testarray3[8][3]);
        double[][] testarray4 = Percent.percentual(testarray2);
        assertNotNull(testarray4);
        assertEquals(0, testarray4[15][1]);
    }
    @Test
    void testamount() {
        String[][] revenue = ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] testarray1 = Percent.converterToLong(revenue, 14, 2);
        long[][] testarray2 = Percent.converterToLong(revenue, 16, 16);
        long[][] testarray5 = Percent.amount(testarray1);
        assertNotNull(testarray5);
        assertEquals(2000000, testarray5[2][3]);
        long[][] testarray6 = Percent.amount(testarray2);
        assertNotNull(testarray6);
        assertEquals(240000, testarray6[5][2]);
    }
}
 