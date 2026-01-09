package eurozone.gov.excel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestEuzLivingStandard {
    @Test
    void testcompareToLong() {
        String[][] gdppop =
        ReadTwoCsvFiles.readCsv("/Gdp_population_euz.csv");
        long[][] testarray2 = EuzLivingStandard.compareToLong(gdppop);
        assertNotNull(testarray2);
        assertEquals(19,testarray2.length);
        assertEquals(2,testarray2[0].length);
        for (int i = 0 ; i < testarray2.length ; i++) {
            for (int j = 0 ; j < testarray2[0].length ; j++) {
                assertTrue(testarray2[i][j] > 0);
            }
        }
    }
    @Test
    void testfindStandLiving() {
        String[][] gdppop = ReadTwoCsvFiles.readCsv("/Gdp_population_euz.csv");
        long[][] testarray2 = EuzLivingStandard.compareToLong(gdppop);
        double[] testarray3 = EuzLivingStandard.findStandLiving(testarray2);
        assertNotNull(testarray2);
        assertEquals(19,testarray2.length);
        assertNotNull(testarray3);
        assertEquals(20,testarray3.length);
        for (int j = 0 ; j < testarray2.length ; j++) {
            assertTrue(testarray2[j][1] > 560000);
            assertTrue(testarray2[j][0] > 7703840000L);
            assertTrue(testarray3[j] > 10000);
        }
        assertTrue(testarray3[19] > 10000);
    }
    // The 3rd method does not return any value to be tested, it's just println
}
