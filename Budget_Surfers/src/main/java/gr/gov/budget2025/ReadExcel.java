package gr.gov.budget2025;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;

public class ReadExcel {
    public static void main(String[] args) {
        String fileName = "budget25.xlsx"; // το αρχείο μέσα στο resources

        try (InputStream inputStream = ReadExcel.class.getClassLoader().getResourceAsStream(fileName);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // παίρνουμε το πρώτο φύλλο Excel

            System.out.println("📊 Διαβάζοντας δεδομένα από το φύλλο: " + sheet.getSheetName());
            System.out.println("----------------------------------------------------");

            // Επανάληψη σε όλες τις γραμμές
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Εκτύπωση ανάλογα με τον τύπο του κελιού
                    switch (cell.getCellType()) {
                        case STRING -> System.out.print(cell.getStringCellValue() + " | ");
                        case NUMERIC -> System.out.print(cell.getNumericCellValue() + " | ");
                        case BOOLEAN -> System.out.print(cell.getBooleanCellValue() + " | ");
                        default -> System.out.print(" | ");
                    }
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("⚠️ Σφάλμα κατά την ανάγνωση του Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
