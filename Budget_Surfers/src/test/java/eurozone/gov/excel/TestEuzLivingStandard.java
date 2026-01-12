package eurozone.gov.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestEuzLivingStandard {
    @Test
    void testCompareToLong() {
        String[][] gdpPop =
        ReadCsvFiles.readCsv("/Gdp_population_euz.csv");
        long[][] testLongGdpPop = EuzLivingStandard.compareToLong(gdpPop);
        assertNotNull(testLongGdpPop);
        assertEquals(19, testLongGdpPop.length);
        assertEquals(2, testLongGdpPop[0].length);
        for (int i = 0; i < testLongGdpPop.length; i++) {
            for (int j = 0; j < testLongGdpPop[0].length; j++) {
                assertTrue(testLongGdpPop[i][j] > 0);
            }
        }
    }

    @Test
    void testFindStandLiving() {
        String[][] gdpPop = ReadCsvFiles.readCsv("/Gdp_population_euz.csv");
        long[][] testLongGdpPop = EuzLivingStandard.compareToLong(gdpPop);
        double[] testEuzGdpPerCapita =
            EuzLivingStandard.findStandLiving(testLongGdpPop);
        assertNotNull(testLongGdpPop);
        assertEquals(19, testLongGdpPop.length);
        assertNotNull(testEuzGdpPerCapita);
        assertEquals(20, testEuzGdpPerCapita.length);
        for (int j = 0; j < testLongGdpPop.length; j++) {
            assertTrue(testLongGdpPop[j][1] > 560000);
            assertTrue(testLongGdpPop[j][0] > 7703840000L);
            assertTrue(testEuzGdpPerCapita[j] > 10000);
        }
        assertTrue(testEuzGdpPerCapita[19] > 10000);
    }
    // The 3rd method does not return any value to be tested, it's just println
}
