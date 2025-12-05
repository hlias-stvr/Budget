package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegionalPer {
    @Test
    void testTransformToLong() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_ministy_25.csv");
        long[] testarray1 = regionalPer.transformToLong(budget);
        assertNotNull(testarray1);
        assertEquals(7,testarray1.length);
        for (int i = 0; i < testarray1.length; i++) {
            assertTrue(testarray1[i] > 0);
        }
    }
    @Test
    void testCalcBudgetPerPerson() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_ministy_25.csv");
        long[] testarray1 = regionalPer.transformToLong(budget);
        double[] testarray2 = regionalPer.calcBudgetPerPerson(testarray1);
        assertNotNull(testarray2);
        assertEquals(7,testarray2.length);
        for (int i = 0; i < testarray2.length; i++) {
            assertTrue(testarray2[i] > 0);
        }
    }
    @Test
    void testCalcBudgetPerRegion() {
        String[][] budget = ReadTwoCsvFiles.readCsv("src\\main\\resourses\\gr_ministy_25.csv");
        long[] testarray1 = regionalPer.transformToLong(budget);
        double[] testarray2 = regionalPer.calcBudgetPerRegion(testarray1);
        assertNotNull(testarray2);
        assertEquals(7,testarray2.length);
        for (int i = 0; i < testarray2.length; i++) {
            assertTrue(testarray2[i] > 0);
        }
    }
}    
