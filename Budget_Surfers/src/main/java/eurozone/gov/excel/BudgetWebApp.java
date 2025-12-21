package eurozone.gov.excel;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BudgetWebApp {
    private static String[][] revenue;
    private static String[][] budget;
    private static String[][] gdppop;

    // Το HTML του μενού (το κρατάμε σε στατική μεταβλητή για εύκολη χρήση)
    private static final String MENU_HTML = """
        <!DOCTYPE html>
        <html lang="el">
        <head>
            <meta charset="UTF-8">
            <title>Ανάλυση Κρατικού Προϋπολογισμού</title>
            <style>
                body { font-family: Arial, sans-serif; background: #f0f8ff; text-align: center; padding: 40px; }
                h1 { color: #003366; }
                .container { max-width: 900px; margin: 0 auto; }
                button { 
                    display: block; width: 100%; padding: 18px; margin: 12px 0; 
                    font-size: 18px; border: none; border-radius: 10px; 
                    color: white; cursor: pointer; transition: 0.3s;
                }
                .blue { background: #007bff; }
                .blue:hover { background: #0056b3; }
                .red { background: #dc3545; }
                .red:hover { background: #c82333; }
                #result { 
                    margin: 40px auto; padding: 25px; background: white; 
                    border: 2px solid #007bff; border-radius: 10px; text-align: left;
                    white-space: pre-wrap; font-family: monospace; max-width: 900px;
                }
                a.back { display: block; margin: 30px; color: #007bff; font-size: 18px; text-decoration: none; }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Επίλεξε λειτουργία</h1>
                <button class="blue" onclick="location.href='/run?choice=1'">1 - Προβολή στοιχείων κρατικού προϋπολογισμού</button>
                <button class="blue" onclick="location.href='/run?choice=2'">2 - Σύγκριση ποσοστιαίων δαπανών ανά τομέα με Ευρωζώνη</button>
                <button class="blue" onclick="location.href='/run?choice=3'">3 - Σύγκριση προϋπολογισμού τελευταία 5 έτη</button>
                <button class="blue" onclick="location.href='/run?choice=4'">4 - Σύγκριση βιοτικού επιπέδου Ελλάδας με Ευρωζώνη</button>
                <button class="blue" onclick="location.href='/run?choice=5'">5 - Ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια</button>
                <button class="blue" onclick="location.href='/run?choice=6'">6 - Σύγκριση φορολογικών εσόδων με μέσο όρο Ευρωζώνης</button>
                <button class="blue" onclick="location.href='/run?choice=7'">7 - Επεξεργασία στοιχείων προϋπολογισμού</button>
                <button class="red" onclick="if(confirm('Έξοδος;')) location.href='/run?choice=0'">0 - Έξοδος</button>
                
                <div id="result">%s</div>
                <a class="back" href="/">← Πίσω στο κύριο μενού</a>
            </div>
        </body>
        </html>
        """;

    public static void main(String[] args) throws Exception {
        // Φόρτωση CSV
       String file1 = "src\\main\\resourses\\gr_revenue_expenses_25.csv";
        String file2 = "src\\main\\resourses\\gr_ministy_25.csv";
        String file3 = "src\\main\\resourses\\Gdp_population_euz.csv";


        revenue = readCsv(file1);
        budget = readCsv(file2);
        gdppop = readCsv(file3);

        // Web server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MenuHandler());
        server.createContext("/run", new RunHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server ξεκίνησε στη διεύθυνση http://localhost:8080/");
        Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
    }

    static class MenuHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = MENU_HTML.formatted(""); // Κενό result στην αρχική σελίδα
            sendHtml(t, response);
        }
    }

    static class RunHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = parseQuery(t.getRequestURI().getQuery());
            String choiceStr = params.getOrDefault("choice", "0");
            StringBuilder result = new StringBuilder();

            try {
                int choice = Integer.parseInt(choiceStr);

                // Πιάνουμε την έξοδο της System.out
                PrintStream originalOut = System.out;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream capture = new PrintStream(baos, true, StandardCharsets.UTF_8);
                System.setOut(capture);

                if (choice == 0) {
                    result.append("<h2 style='color:red;'>Έξοδος από την εφαρμογή. Κλείσε το παράθυρο.</h2>");
                } else {
                    runChoice(choice);
                }

                System.out.flush();
                System.setOut(originalOut);

                String consoleOutput = baos.toString(StandardCharsets.UTF_8);
                if (!consoleOutput.isEmpty()) {
                    result.append("<h2>Αποτελέσματα</h2><pre>")
                          .append(escapeHtml(consoleOutput))
                          .append("</pre>");
                }

            } catch (Exception e) {
                result.append("<p style='color:red;'>Σφάλμα: ")
                      .append(escapeHtml(e.toString()))
                      .append("</p>");
                e.printStackTrace();
            }

            // Φτιάχνουμε τη σελίδα με τα αποτελέσματα μέσα
            String finalPage = MENU_HTML.formatted(result.toString());

            sendHtml(t, finalPage);
        }
    }

    // Εκτέλεση της επιλογής (ακριβώς όπως στην console έκδοση)
    private static void runChoice(int choice) {
        if (choice == 1) {
            System.out.println("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===");
            printFirstRows(revenue, 33);
            System.out.println("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===");
            printFirstRows(budget, 35);

        } else if (choice == 6) {
            long[][] LongData = Percent.converterToLong(revenue, 14, 2);
            long[] LongData25 = new long[LongData.length];
            for (int i = 0; i < LongData.length; i++) {
                LongData25[i] = LongData[i][0];
            }
            CompareEuzTaxes.Calculation(LongData25);

        } else if (choice == 2) {
            System.out.println("Επιλογή 2 - Σύγκριση δαπανών (προς υλοποίηση στη web)");
            // Μπορούμε να προσθέσουμε και τις άλλες αργότερα
        } else {
            System.out.println("Η επιλογή " + choice + " δεν έχει υλοποιηθεί ακόμα στη web έκδοση.");
        }
    }

    // Οι ίδιες βοηθητικές μέθοδοι με τον αρχικό σου κώδικα
    static String[][] readCsv(String path) {
        var rows = new ArrayList<String[]>();
        try (var br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";", -1);
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim().replace("\"", "");
                }
                rows.add(parts);
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα ανάγνωσης " + path + ": " + e.getMessage());
        }
        return rows.toArray(new String[0][]);
    }

    static void printFirstRows(String[][] data, int n) {
        for (int i = 0; i < Math.min(n, data.length); i++) {
            StringBuilder sb = new StringBuilder();
            for (String cell : data[i]) {
                sb.append(cell).append(" | ");
            }
            System.out.println(sb.toString());
        }
    }

    // Βοηθητικές για server
    private static void sendHtml(HttpExchange t, String html) throws IOException {
        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = t.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    map.put(pair[0], pair[1]);
                }
            }
        }
        return map;
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}