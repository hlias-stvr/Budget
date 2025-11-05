package eurozone.gov.excel;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToDatabase {
    private String excelFile;
    private String dbFile;
    private Connection connection;

    public ExcelToDatabase(String excelFile, String dbFile) {
        this.excelFile = excelFile;
        this.dbFile = dbFile;
    }

    public void connect() throws SQLException {
        // Δημιουργία φακέλου για τη βάση αν δεν υπάρχει
        File dbFolder = new File(dbFile).getParentFile();
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }

        // Άνοιγμα ή δημιουργία της SQLite βάσης
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        System.out.println("✅ Συνδέθηκε στη βάση: " + dbFile);
    }

    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS proyp (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                code TEXT,
                description TEXT,
                amount REAL
            )
            """;
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        System.out.println("✅ Δημιουργήθηκε (ή ήδη υπάρχει) ο πίνακας proyp");
    }

    public void readExcelAndInsert() {
        try (FileInputStream fis = new FileInputStream(new File(excelFile));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            String sql = "INSERT INTO proyp (code, description, amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            boolean firstRow = true;
            int count = 0;

            for (Row row : sheet) {
                if (firstRow) { firstRow = false; continue; } // αγνόησε την επικεφαλίδα

                Cell codeCell = row.getCell(0);
                Cell descCell = row.getCell(1);
                Cell amountCell = row.getCell(2);

                if ((codeCell == null || codeCell.toString().isBlank()) &&
                    (descCell == null || descCell.toString().isBlank()) &&
                    amountCell == null)
                    continue;

                String code = (codeCell != null) ? codeCell.toString().trim() : "";
                String description = (descCell != null) ? descCell.toString().trim() : "";
                double amount = 0;

                try {
                    if (amountCell != null) {
                        if (amountCell.getCellType() == CellType.NUMERIC)
                            amount = amountCell.getNumericCellValue();
                        else if (amountCell.getCellType() == CellType.STRING)
                            amount = Double.parseDouble(amountCell.getStringCellValue().replace(",", ""));
                    }
                } catch (Exception e) {
                    amount = 0;
                }

                pstmt.setString(1, code);
                pstmt.setString(2, description);
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();
                count++;
            }

            System.out.println("✅ Εισήχθησαν " + count + " γραμμές από το Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM proyp")) {

            System.out.println("\n📊 Περιεχόμενα πίνακα proyp:");
            while (rs.next()) {
                System.out.println(
                    rs.getString("code") + " | " +
                    rs.getString("description") + " | " +
                    rs.getDouble("amount")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("🔒 Η σύνδεση έκλεισε.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------
    // MAIN METHOD
    // ------------------------------
    public static void main(String[] args) {
        String excelPath = "C:\\Users\\eugen\\Budget\\Budget_Surfers\\src\\main\\resourses\\budget25.xlsx";
        String dbPath = "C:\\Users\\eugen\\Budget\\Budget_Surfers\\target\\db\\budg.db";

        ExcelToDatabase importer = new ExcelToDatabase(excelPath, dbPath);

        try {
            importer.connect();          // Δημιουργεί φάκελο + αρχείο DB
            importer.createTable();      // Δημιουργεί τον πίνακα
            importer.readExcelAndInsert();// Διαβάζει Excel και εισάγει δεδομένα
            importer.printData();        // Τυπώνει τα δεδομένα
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            importer.close();
        }
    }
}
