package eurozone.gov.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestAvgEurozone {
    @Test
    void testConvertToLong() {
        String[][] budget =
        ReadCsvFiles.readCsv("src\\main\\resources\\gr_ministy_25.csv");
        long[] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        assertNotNull(testLongMinistrExpenses);
        assertEquals(20, testLongMinistrExpenses.length);
        for (int i = 0; i < testLongMinistrExpenses.length; i++) {
            assertTrue(testLongMinistrExpenses[i] > 180000000);
        }
    }

    @Test
    void testMinistrDiv() {
        String[][] budget =
        ReadCsvFiles.readCsv("src\\main\\resources\\gr_ministy_25.csv");
        long[] testLongMinistrExpenses = AvgEurozone.convertToLong(budget);
        double[] testPercSectorsExpenses =
            AvgEurozone.ministrDiv(testLongMinistrExpenses);
        double percSumGrMinistr = 0.0;
        long sumGrMinistr = 0;
        final long GTP = 257100000000L;
        assertNotNull(testPercSectorsExpenses);
        assertEquals(11, testPercSectorsExpenses.length);
        for (int i = 0; i < testPercSectorsExpenses.length - 1; i++) {
            assertTrue(testPercSectorsExpenses[i] > 0);
            percSumGrMinistr += testPercSectorsExpenses[i];
        }
        assertTrue(Math.abs(percSumGrMinistr - testPercSectorsExpenses[10])
            < 0.000001);
        for (int i = 0; i < testLongMinistrExpenses.length; i++) {
            sumGrMinistr += testLongMinistrExpenses[i];
        }
        double testPercSumGrMinistr =
            Math.round(((double) sumGrMinistr / GTP * 100) * 100.0) / 100.0;
        assertTrue(Math.abs(testPercSumGrMinistr - percSumGrMinistr) <
            0.000001);
    }
}
