package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEuzTaxes {
    @Test 
    void testCalculation() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        long[][] LongData = Percent.converterToLong(revenue, 14, 2);
        long[] LongData25 = new long[LongData.length];
        for (int i = 0; i < LongData.length; i++) {
            LongData25[i] = LongData[i][0];
        }
        final long GTP = 206000000000L;
        double grtax = Math.round((((LongData25[1] + LongData25[2]) /
        (double) GTP) * 100) * 10.0) / 10.0;
        double avgeuztax = Math.round(40.9 * 10.0) / 10.0;
        double dif = Math.round((Math.abs((grtax - avgeuztax))) * 10.0) / 10.0;
        assertEquals(grtax, 30.2);
        assertEquals(avgeuztax, 40.9);
        assertEquals(dif, 10.7);
    }
}
