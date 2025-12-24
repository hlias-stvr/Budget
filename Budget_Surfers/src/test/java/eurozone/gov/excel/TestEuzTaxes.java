package eurozone.gov.excel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEuzTaxes {
    @Test 
    void testCalculation() {
        String[][] revenue =
        ReadTwoCsvFiles.readCsv(
        "src\main\resourses\gr_revenue_expenses_25.csv");
        long[][] LongData = percent.converterToLong(revenue, 14, 2);
        assertNotNull(CompareEuzTaxes.Calculation(LongData));
        assertFalse(CompareEuzTaxes.Calculation(LongData).isBlank());
    }
}
