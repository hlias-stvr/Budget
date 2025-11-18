package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Testavgeurozone {
    @Test
    void testconvertToLong() {
        long[] testarray = avgeurozone.convertToLong(budget);
        assertNotNull(testarray);
        assertEquals(20,testarray.length);
        for (int i = 0; i < testarray.length; i++) {
            assertTrue(testarray[i] > 400000000);
        }
    }
}
