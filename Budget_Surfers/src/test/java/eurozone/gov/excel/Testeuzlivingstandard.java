package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Testeuzlivingstandard {
    @Test
    void testcompareToLong() {
        String[][] gdppop = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\Gdp_population_euz.csv");
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
        String[][] gdppop = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\Gdp_population_euz.csv");
        long[][] testarray2 = EuzLivingStandard.compareToLong(gdppop);
        long[] testarray3 = EuzLivingStandard.findStandLiving(testarray2);
        final int GRAVGGDP = 25300;
        double testsum = EuzLivingStandard.findStandLiving(sum);
        assertNotNull(testarray3);
        assertEquals(20,testarray3.length);
        for (int j = 0 ; j < testarray3.length ; j++) {
            assertTrue(testarray2[j][1] > 300000);
        }
    } 
}