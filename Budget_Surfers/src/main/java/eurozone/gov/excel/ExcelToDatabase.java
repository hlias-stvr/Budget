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
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
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
    }

    public void readExcelAndInsert() {
        try (FileInputStream fis = new FileInputStream(new File(excelFile));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            String sql = "INSERT INTO budget (code, description, amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            for (Row row : sheet) {
                Cell codeCell = row.getCell(0);
                Cell descCell = row.getCell(1);
                Cell amountCell = row.getCell(2);

                if (descCell == null || amountCell == null) continue;

                String code = (codeCell != null) ? codeCell.toString().trim() : "";
                String description = descCell.toString().replace("\n", " ").trim();
                double amount = 0;
                try {
                    amount = amountCell.getNumericCellValue();
                } catch (Exception e) {
                    amount = 0;
                }

                pstmt.setString(1, code);
                pstmt.setString(2, description);
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void printData(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM budget");
        System.out.println("Περιεχόμενα πίνακα budget:");
        while (rs.next()) {
            System.out.println(rs.getString("code") + " | " +
                               rs.getString("description") + " | " +
                               rs.getDouble("amount"));
        }
    }
}
