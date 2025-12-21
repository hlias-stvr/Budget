package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAvgEurozone {
    @Test
    void testconvertToLong() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\\main\\resources\\gr_ministy_25.csv");
        long[] testarray1 = AvgEurozone.convertToLong(budget);
        assertNotNull(testarray1);
        assertEquals(20,testarray1.length);
        for (int i = 0; i < testarray1.length; i++) {
            assertTrue(testarray1[i] > 180000000);
        }
    }
    @Test
    void testministrDiv() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\\main\\resources\\gr_ministy_25.csv");
        long[] testarray1 = AvgEurozone.convertToLong(budget);
        double[] testarray2 = AvgEurozone.ministrDiv(testarray1);
        double sumgrministrpercent = 0.0;
        long sumgrministr = 0;
        final long GTP = 257100000000L;
        assertNotNull(testarray2);
        assertEquals(11,testarray2.length);
        for (int i = 0; i < testarray2.length - 1; i++) {
            assertTrue(testarray2[i] > 0);
            sumgrministrpercent += testarray2[i];
        }
        assertTrue(Math.abs(sumgrministrpercent - testarray2[10]) < 0.000001);
        for (int i = 0; i < testarray1.length; i++) {
            sumgrministr += testarray1[i];
        }
        double t = Math.round(((double) sumgrministr / GTP * 100)*100.0) / 100.0;
        assertTrue(Math.abs(t-sumgrministrpercent)< 0.000001);
    }
}
