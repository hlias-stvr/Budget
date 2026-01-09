package eurozone.gov.excel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
public class BudgetApp {

    private static String[][] revenue;
    private static String[][] budget;
    private static String[][] gdppop;
    private static double[] modifiedSectorPercents;
    private static double[] modifiedRegionAmounts;
    private static double[] modifiedRevenueAmounts;

    private static final Map<String, Map<String, Object>> sessions = new HashMap<>();
    static List<String> historyTypes = new ArrayList<>();
    static List<String> historyTypeNames = new ArrayList<>();
    static List<double[]> historyOldValues = new ArrayList<>();
    static List<double[]> historyNewValues = new ArrayList<>();
    static List<LocalDateTime> historyTimestamps = new ArrayList<>();

    public static void main(String[] args) {
        revenue = ReadTwoCsvFiles.readCsv("/gr_revenue_expenses_25.csv");
        budget  = ReadTwoCsvFiles.readCsv("/gr_ministy_25.csv");
        gdppop  = ReadTwoCsvFiles.readCsv("/Gdp_population_euz.csv");
        modifiedSectorPercents = initializeData("sectors");
        modifiedRegionAmounts = initializeData("regions");
        modifiedRevenueAmounts = initializeData("revenues");

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
            server.createContext("/", new MainMenuHandler());
            server.createContext("/submenu", new SubMenuHandler());
            server.createContext("/edit", new EditHandler());
            server.createContext("/exit", new ExitHandler());
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            int port = server.getAddress().getPort();
            String url = "http://localhost:" + port;

            System.out.println("=================================================");
            System.out.println("ΕΦΑΡΜΟΓΗ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ");
            System.out.println("Άνοιξε το μενού στο: " + url);
            System.out.println("=================================================");

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception e) {
                    System.out.println("Άνοιξε χειροκίνητα: " + url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addToHistory(String type, double[] oldData, double[] newData) {
        if (!Arrays.equals(oldData, newData)) {
            historyTypes.add(type);
            historyOldValues.add(Arrays.copyOf(oldData, oldData.length));
            historyNewValues.add(Arrays.copyOf(newData, newData.length));
            historyTimestamps.add(LocalDateTime.now());
            
            String typeName;
            switch(type) {
                case "sectors": typeName = "Τομείς Δαπανών"; break;
                case "regions": typeName = "Περιφέρειες"; break;
                case "revenues": typeName = "Έσοδα"; break;
                default: typeName = type;
            }
            historyTypeNames.add(typeName);
        }
    }
// ================= ΚΥΡΙΟ ΜΕΝΟΥ =================
    static class MainMenuHandler implements HttpHandler {
        private static final String HTML = """
            <!DOCTYPE html>
            <html lang="el">
            <head>
                <meta charset="UTF-8">
                <title>Μενού Προϋπολογισμού</title>
                <style>
                    body {font-family: Arial, sans-serif; background: #f0f8ff; text-align: center; padding: 40px;}
                    h1 {color: #003366;}
                    .container {max-width: 900px; margin: 0 auto;}
                    button {display: block; width: 100%; padding: 18px; margin: 12px 0; font-size: 19px;
                            border: none; border-radius: 10px; color: white; cursor: pointer;}
                    button.blue {background: #007bff;}
                    button.blue:hover {background: #0056b3;}
                    button.green {background: #28a745;}
                    button.green:hover {background: #218838;}
                    button.red {background: #dc3545;}
                    button.red:hover {background: #c82333;}
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Επίλεξε λειτουργία</h1>
                    <button class="blue" onclick="go(1)">1 - Προβολή στοιχείων κρατικού προϋπολογισμού</button>
                    <button class="blue" onclick="go(2)">2 - Σύγκριση ποσοστιαίων δαπανών ανά τομέα με Ευρωζώνη</button>
                    <button class="blue" onclick="go(3)">3 - Σύγκριση του προϋπολογισμού τα τελευταία 5 έτη</button>
                    <button class="blue" onclick="go(4)">4 - Σύγκριση βιοτικού επιπέδου της Ελλάδας</button>
                    <button class="blue" onclick="go(5)">5 - Ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια</button>
                    <button class="blue" onclick="go(6)">6 - Σύγκριση φορολογικών εσόδων με Ευρωζώνη</button>
                    <button class="green" onclick="location.href='/edit'">7 - Επεξεργασία στοιχείων προϋπολογισμού</button>
                    <button class="green" onclick="go(8)">8 - Προβολή ιστορικού αλλαγών</button>
                    <button class="red" onclick="if(confirm('Έξοδος;')) location.href='/exit'">0 - Έξοδος</button>
                </div>
                <script>
                    function go(choice) {
                        location.href = '/submenu?main=' + choice;
                    }
                </script>
            </body>
            </html>
            """;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            sendHtml(exchange, HTML);
        }
    }

     // ================= ΥΠΟΜΕΝΟΥ & ΑΠΟΤΕΛΕΣΜΑΤΑ =================
    static class SubMenuHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            int main = parseParam(query, "main", 0);
            int sub = parseParam(query, "sub", 0);
 
            if (sub == 0 && (main == 1 || main == 6 || main == 8)) {
                String result = executeChoice(main, 0);
                sendHtml(exchange, resultPage(result, main));
            } else if (sub == 0) {
                sendHtml(exchange, getSubMenuPage(main));
            } else {
                String result = executeChoice(main, sub);
                sendHtml(exchange, resultPage(result, main));
            }
        }
    }


    // ================= INTERACTIVE ΕΠΕΞΕΡΓΑΣΙΑ 7 =================
    static class EditHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String sessionId = getSessionId(exchange);
        Map<String, Object> session = sessions.computeIfAbsent(sessionId, k -> new HashMap<>());
 
        if ("GET".equals(method)) {
            if ("/edit".equals(path)) {
                sendHtml(exchange, getEditMenu());
                return;
            } else if (path.startsWith("/edit/")) {
                String type = path.substring(6);
                session.put("editType", type);
                session.put("originalSum", calculateOriginalSum(type));
                session.put("data", initializeData(type));
                sendHtml(exchange, getEditPage(session,false));
                return;
            }
        } else if ("POST".equals(method)) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = parseParams(body);
 
            String action = params.get("action");
 
            if ("cancel".equals(action)) {
                session.clear();
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(302, -1);
                return;
            }
 
            if ("finish".equals(action)) {
                String type = (String) session.get("editType");
                double[] data = (double[]) session.get("data");
 
                // ΑΠΟΘΗΚΕΥΣΗ GLOBAL
                if ("sectors".equals(type)) {
                    if (modifiedSectorPercents != null) {
                        addToHistory(type, modifiedSectorPercents, data);
                    }
                    modifiedSectorPercents = Arrays.copyOf(data, 11);
                } else if ("regions".equals(type)) {
                    if (modifiedRegionAmounts != null) {
                        addToHistory(type, modifiedRegionAmounts, data);
                    }
                    modifiedRegionAmounts = Arrays.copyOf(data, 7);
                } else if ("revenues".equals(type)) {
                    if (modifiedRevenueAmounts != null) {
                        addToHistory(type, modifiedRevenueAmounts, data);
                    }
                    modifiedRevenueAmounts = Arrays.copyOf(data, data.length);
                }
 
                session.clear();
 
                // ΕΜΦΑΝΙΣΗ ΑΠΟΤΕΛΕΣΜΑΤΩΝ ΜΕΤΑ ΤΗΝ ΑΠΟΘΗΚΕΥΣΗ
                String resultsHtml = getResultsAfterEdit(type);
                sendHtml(exchange, resultPageWithTitle("Αποτελέσματα μετά την αποθήκευση αλλαγών", resultsHtml));
                return;
            }
 
            // Κανονική αλλαγή τιμής
            processEdit(session, params);
            sendHtml(exchange, getEditPage(session, false));
            return;
        }
 
        // Fallback
        exchange.getResponseHeaders().set("Location", "/");
        exchange.sendResponseHeaders(302, -1);
    }
}

private static void processEdit(Map<String, Object> session, Map<String, String> params) {
    if (params.containsKey("index") && params.containsKey("value")) {
        try {
            int idx = Integer.parseInt(params.get("index")) - 1;
            double newVal = Double.parseDouble(params.get("value"));
            double[] data = (double[]) session.get("data");
            String type = (String) session.get("editType");
 
            if (idx >= 0 && idx < data.length && newVal >= 0) {
                data[idx] = newVal;
            }
        } catch (Exception ignored) {
            // Ignore invalid input
        }
    }
}

private static String getResultsAfterEdit(String type) {
    StringBuilder sb = new StringBuilder();
    sb.append("<div style='background:#f4f9ff; padding:25px; border-radius:12px; border-left:6px solid #28a745; text-align:left;'>");
    sb.append("<h2 style='color:#155724;'>✅ Οι αλλαγές εφαρμόστηκαν!</h2>");
    sb.append("<p style='font-size:1.1em;'>Ακολουθούν οι νέοι υπολογισμοί βάσει των στοιχείων που τροποποιήσατε:</p><hr>");

    try {
        // --- ΤΟΜΕΙΣ (SECTORS) ---
        if ("sectors".equals(type) && modifiedSectorPercents != null) {
            // Καλούμε τη σύγκριση - η AvgEurozone πλέον θα βρει 11 στοιχεία
            double[] newCompare = AvgEurozone.compareGrToEurozone(modifiedSectorPercents);
            String[] sectors = AvgEurozone.sectors();
            
            // Εμφανίζουμε μόνο τους 10 τομείς (χωρίς το grsum) στα αποτελέσματα για καθαρότητα
            int limit = Math.min(10, newCompare.length); 
            
            sb.append("<ul style='list-style-type:none; padding:0;'>");
            for (int i = 0; i < limit; i++) {
                String text = (newCompare[i] > 0) 
                    ? "Λιγότερο κατά <b>" + String.format("%.2f", newCompare[i]) + "%</b>"
                    : "Περισσότερο κατά <b>" + String.format("%.2f", Math.abs(newCompare[i])) + "%</b>";
                
                sb.append("<li style='margin-bottom:8px; background:white; padding:10px; border-radius:5px;'>");
                sb.append("<b>").append(sectors[i]).append("</b>: ").append(text);
                sb.append("</li>");
            }
            sb.append("</ul>");
        }
        // --- ΠΕΡΙΦΕΡΕΙΕΣ (REGIONS) ---
        else if ("regions".equals(type) && modifiedRegionAmounts != null) {
            sb.append("<h3 style='color:#003366;'>2. Ανάλυση Δαπανών ανά Περιφέρεια</h3>");
            
            long[] longAmounts = new long[modifiedRegionAmounts.length];
            for (int i = 0; i < modifiedRegionAmounts.length; i++) {
                longAmounts[i] = (long) modifiedRegionAmounts[i];
            }

            double[] perPerson = RegionalPer.calcBudgetPerPerson(longAmounts);
            double[] perRegion = RegionalPer.calcBudgetPerRegion(longAmounts);
            
            sb.append("<h4>Νέα Στοιχεία ανά Περιφέρεια:</h4>");
            sb.append("<table border='1' style='width:100%; border-collapse:collapse; background:white;'>");
            sb.append("<tr style='background:#eee;'><th>Περιφέρεια</th><th>Ποσό ανά Πολίτη</th><th>% Συνολικής Δαπάνης</th></tr>");
            
            for (int i = 0; i < perPerson.length; i++) {
                // Διασφάλιση ότι δεν θα βγούμε εκτός ορίων του πίνακα budget
                String regionName = (i + 25 < budget.length) ? budget[i + 25][1] : "Περιφέρεια " + (i+1); 
                sb.append("<tr>");
                sb.append("<td style='padding:8px;'>").append(regionName).append("</td>");
                sb.append("<td style='padding:8px; font-weight:bold;'>").append(String.format("%,.2f €", perPerson[i])).append("</td>");
                sb.append("<td style='padding:8px;'>").append(String.format("%.2f%%", perRegion[i])).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
        }

        // --- ΕΣΟΔΑ (REVENUES) ---
        else if ("revenues".equals(type) && modifiedRevenueAmounts != null) {
            sb.append("<h3 style='color:#003366;'>3. Νέα Σύγκριση Φορολογικών Εσόδων με Ευρωζώνη</h3>");

    try {
        // Διασφάλιση ότι ο πίνακας έχει τουλάχιστον 3 στοιχεία για να βρούμε τους φόρους (index 1 και 2)
        if (modifiedRevenueAmounts.length >= 3) {
            
            // Υπολογισμός Φόρων (π.χ. Άμεσοι + Έμμεσοι Φόροι)
            long grTaxes = (long) modifiedRevenueAmounts[0] + (long) modifiedRevenueAmounts[1];
            
            // Υπολογισμός ποσοστού επί του ΑΕΠ (206 δις)
            double grPct = (grTaxes / 206000000000.0) * 100;
            double avg = 40.9; // Μέσος Όρος Ευρωζώνης
            double diff = Math.abs(grPct - avg);

            sb.append("<div style='background:white; padding:20px; border-radius:10px; border:1px solid #ddd; display:inline-block; text-align:left;'>");
            sb.append("<p style='font-size:18px;'>Συνολικά Φορολογικά Έσοδα: <b>").append(String.format("%,d €", grTaxes)).append("</b></p>");
            sb.append("<p style='font-size:18px;'>Ποσοστό Ελλάδας: <b style='color:#007bff;'>").append(String.format("%.2f%%", grPct)).append("</b> του ΑΕΠ</p>");
            sb.append("<p style='font-size:18px;'>Μέσος Όρος Ευρωζώνης: <b>40.90%</b></p>");
            sb.append("<hr>");

            if (grPct > avg) {
                sb.append("<p style='color:#d9534f; font-weight:bold; font-size:18px;'> Η Ελλάδα υπερβαίνει τον Μ.Ο. κατά ").append(String.format("%.2f", diff)).append(" ποσοστιαίες μονάδες.</p>");
            } else if (grPct < avg) {
                sb.append("<p style='color:#5cb85c; font-weight:bold; font-size:18px;'> Η Ελλάδα υστερεί κατά ").append(String.format("%.2f", diff)).append(" ποσοστιαίες μονάδες.</p>");
            } else {
                sb.append("<p style='color:#5cb85c; font-weight:bold; font-size:18px;'>Το ποσοστό είναι ακριβώς ίσο με τον Μ.Ο. της Ευρωζώνης.</p>");
            }
            sb.append("</div>");

            // Προαιρετικά: Εμφάνιση αναλυτικής λίστας όλων των εσόδων
            sb.append("<h4 style='margin-top:20px;'>Αναλυτικά Έσοδα:</h4>");
            sb.append("<ul style='list-style:none; padding:0; text-align:left; display:inline-block;'>");
            String[] revenueLabels = getLabels("revenues");
            for (int i = 0; i < Math.min(modifiedRevenueAmounts.length, revenueLabels.length); i++) {
                sb.append("<li style='padding:5px 0; border-bottom:1px solid #eee;'>");
                sb.append(revenueLabels[i]).append(": <b>").append(String.format("%,.0f €", modifiedRevenueAmounts[i])).append("</b>");
                sb.append("</li>");
            }
            sb.append("</ul>");
            
        } else {
            sb.append("<p style='color:red;'>❌ Σφάλμα: Ο πίνακας εσόδων είναι πολύ μικρός για τον υπολογισμό (Length: ").append(modifiedRevenueAmounts.length).append(").</p>");
        }
    } catch (Exception e) {
        sb.append("<p style='color:red;'>❌ Σφάλμα κατά την επεξεργασία των εσόδων: ").append(e.getMessage()).append("</p>");
    }
}

    } catch (Exception e) {
        sb.append("<p style='color:red; background:white; padding:10px; border:1px solid red;'>❌ Σφάλμα κατά τον υπολογισμό: ").append(e.getMessage()).append("</p>");
    }

    sb.append("</div>");
    return sb.toString();
}

private static String resultPageWithTitle(String title, String content) {
    return """
        <!DOCTYPE html>
        <html lang="el">
        <head>
            <meta charset="UTF-8">
            <title>%s</title>
            <style>
                body {font-family: Arial, sans-serif; background: #f0f8ff; padding: 40px; text-align: center;}
                .content {max-width: 900px; margin: 0 auto; background: white; padding: 40px; border-radius: 15px; box-shadow: 0 8px 25px rgba(0,0,0,0.1);}
                h1 {color: #003366; margin-bottom: 30px;}
                h3 {color: #007bff;}
                button {padding: 18px 40px; font-size: 18px; background: #dc3545; color: white; border: none; border-radius: 10px; cursor: pointer; margin-top: 40px;}
                button:hover {background: #c82333;}
            </style>
        </head>
        <body>
            <div class="content">
                <h1>%s</h1>
                %s
                <button onclick="location.href='/'">Επιστροφή στο κύριο μενού</button>
            </div>
        </body>
        </html>
        """.formatted(title, title, content);
}
    
    private static String getEditMenu() {
        return """
            <!DOCTYPE html>
            <html lang="el">
            <head><meta charset="UTF-8"><title>Επεξεργασία</title>
            <style>
                body {font-family: Arial; background: #f0f8ff; text-align: center; padding: 50px;}
                button {width: 80%; max-width: 600px; padding: 20px; margin: 15px; font-size: 18px; border: none; border-radius: 10px; color: white;}
                .blue {background: #007bff;}
                .red {background: #dc3545;}
            </style></head>
            <body>
                <h1>Επεξεργασία Στοιχείων</h1>
                <button class="blue" onclick="location.href='/edit/sectors'">1 - Μεταβολή δαπανών ανά τομέα</button>
                <button class="blue" onclick="location.href='/edit/regions'">2 - Μεταβολή δαπανών ανά περιφέρεια</button>
                <button class="blue" onclick="location.href='/edit/revenues'">3 - Μεταβολή εσόδων</button>
                <br><br>
                <button class="red" onclick="location.href='/'">Πίσω στο κύριο μενού</button>
            </body>
            </html>
            """;
    }
    private static double calculateOriginalSum(String type) {
        if ("sectors".equals(type)) {
            long[] A = AvgEurozone.convertToLong(budget);
            double[] B = AvgEurozone.ministrDiv(A);
            double sum = 0;
            for (int i = 0; i < 10; i++) sum += B[i];
            return Math.round(sum * 100.0) / 100.0;
        } else if ("regions".equals(type)) {
            long[] data = RegionalPer.transformToLong(budget);
            long sum = 0;
            for (long l : data) sum += l;
            return sum;
        } else if ("revenues".equals(type)) {
            long[][] full = Percent.converterToLong(revenue, 14, 2);
            long sum = 0;
            for (int i = 1; i < 14; i++) {
                sum += full[i][0];
            }
            return sum;
        }
        return 0;
    }
private static double[] initializeData(String type) {
        if ("sectors".equals(type)) {
        long[] A = AvgEurozone.convertToLong(budget);
        double[] B = AvgEurozone.ministrDiv(A); // Παράγει 11 στοιχεία
        
        double[] copy = new double[11];
        // Αν το B έχει 11 στοιχεία, τα παίρνουμε. Αν έχει 10, γεμίζουμε τα υπόλοιπα.
        System.arraycopy(B, 0, copy, 0, Math.min(B.length, 11));
        
        copy[10] = 50.69; /* Η 11η θέση που "ικανοποιεί" την AvgEurozone */ 
        return copy;
    
        } else if ("regions".equals(type)) {
            long[] lData = RegionalPer.transformToLong(budget);
            double[] dData = new double[lData.length];
            for (int i = 0; i < lData.length; i++) dData[i] = lData[i];
            return dData;
        } else if ("revenues".equals(type)) {
            long[][] full = Percent.converterToLong(revenue, 14, 2);
            // Διασφαλίζουμε ότι ο πίνακας έχει το μήκος που περιμένει η Calculation (π.χ. 13)
            double[] dData = new double[13]; 
            for (int i = 0; i < 13; i++) {
                
                dData[i] = (double)full[i + 1][0];
                
            }
            return dData;
        }
        return new double[0];
    }
   private static String getEditPage(Map<String, Object> session, boolean afterSave) {
    if (afterSave) {
        return getResultsAfterEdit((String) session.get("editType"));
    }
    String type = (String) session.getOrDefault("editType", "sectors");
    double[] data = (double[]) session.getOrDefault("data", new double[0]);
    double originalSum = (double) session.getOrDefault("originalSum", 0.0);

    // Ορίζουμε το εσωτερικό μήκος και το μήκος που θα εμφανίζεται στον χρήστη
    int dataLength = data.length; 
    int displayLimit = (type.equals("sectors")) ? 10 : dataLength;

    double currentSum = 0;
    // Το άθροισμα υπολογίζεται μόνο για τα στοιχεία που βλέπει ο χρήστης
    for (int i = 0; i < displayLimit; i++) {
        currentSum += data[i];
    }

    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html><html lang='el'><head><meta charset='UTF-8'><title>Επεξεργασία ").append(type).append("</title>");
    html.append("<style>body{font-family:Arial;padding:20px;background:#f0f9f9;}table{border-collapse:collapse;width:100%;}th,td{border:1px solid #ddd;padding:12px;text-align:left;}th{background:#e9ecef;}input{padding:8px;margin:5px;}button{padding:10px 20px;margin:5px;border:none;border-radius:5px;cursor:pointer;}</style></head><body>");

    html.append("<h2>Επεξεργασία ").append(switch (type) {
        case "sectors" -> "ποσοστιαίων δαπανών ανά τομέα";
        case "regions" -> "δαπανών ανά περιφέρεια";
        case "revenues" -> "εσόδων";
        default -> "";
    }).append("</h2>");

    html.append("<p><strong>Αρχικό σύνολο (προς εξισορρόπηση):</strong> ").append(String.format("%.2f", originalSum)).append(type.equals("sectors") ? "%" : " €").append("</p>");

    html.append("<table><tr><th>#</th><th>Κατηγορία</th><th>Τιμή</th></tr>");
    String[] labels = getLabels(type);
    
    // Εμφανίζουμε μόνο τις 10 πρώτες κατηγορίες για τους sectors
    for (int i = 0; i < displayLimit; i++) {
        html.append("<tr><td>").append(i+1).append("</td><td>").append(labels[i]).append("</td><td>").append(String.format("%.2f", data[i])).append("</td></tr>");
    }
    
    html.append("<tr style='background:#d4edda;font-weight:bold'><td colspan='2'>Τρέχον σύνολο (επεξεργασμένων)</td><td>").append(String.format("%.2f", currentSum)).append("</td></tr></table>");

    // Η 11η τιμή (50.69) υπάρχει στο background αλλά δεν επηρεάζει τον έλεγχο ισορροπίας του χρήστη
    double diff = currentSum - originalSum;
    if (Math.abs(diff) > 0.01) {
        html.append("<p style='color:red;font-weight:bold'>Μένει να ").append(diff > 0 ? "αφαιρέσεις" : "προσθέσεις").append(" ").append(String.format("%.2f", Math.abs(diff))).append("</p>");
    } else {
        html.append("<p style='color:green;font-weight:bold'>Ισορροπημένο! Οι αλλαγές θα εφαρμοστούν στους υπολογισμούς.</p>");
    }

    html.append("<form method='post'>");
    html.append("Κατηγορία (1-").append(displayLimit).append("): <input type='number' name='index' min='1' max='").append(displayLimit).append("' required><br>");
    html.append("Νέα τιμή: <input type='number' name='value' step='0.01' required><br>");
    html.append("<button type='submit' style='background:#007bff;color:white'>Εφάρμοσε αλλαγή</button></form>");

    // Το κουμπί αποθήκευσης εμφανίζεται όταν το άθροισμα των 10 είναι σωστό
    if (Math.abs(diff) < 0.01) {
        html.append("<form method='post'><button name='action' value='finish' style='background:green;color:white;margin-top:20px'>Τέλος - Αποθήκευση & Εμφάνιση Αποτελεσμάτων</button></form>");
    }

    html.append("<form method='post'><button name='action' value='cancel' style='background:#dc3545;color:white;margin-top:20px'>Άκυρο - Επιστροφή</button></form>");
    html.append("</body></html>");
    return html.toString();
}
    private static String[] getLabels(String type) {
        if ("sectors".equals(type)) return AvgEurozone.sectors();
        if ("regions".equals(type)) return new String[]{"Αττική",
        "Κεντρική Μακεδονία", "Δυτική Ελλάδα", "Κρήτη", "Α.Μ.Θ.",
        "Ήπειρος", "Θεσσαλία"};
        if ("revenues".equals(type)) {
            String[] labels = new String[13];
            for (int i = 0; i < 13; i++) {
                labels[i] = revenue[i+1][1]; // από γραμμή 1 και μετά (0 είναι σύνολο)
            }
            return labels;
        }
        return new String[0];
    }
    // ================= ΥΠΟΜΕΝΟΥ ΣΕΛΙΔΑ =================
    private static String getSubMenuPage(int main) {
        String title = switch (main) {
            case 2 -> "Σύγκριση ποσοστιαίων δαπανών ανά τομέα με Ευρωζώνη";
            case 3 -> "Σύγκριση προϋπολογισμού τελευταία 5 έτη";
            case 4 -> "Σύγκριση βιοτικού επιπέδου της Ελλάδας";
            case 5 -> "Ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια";
            default -> "Υπομενού";
        };
         String buttons = switch (main) {
            case 2 -> """
                <button class="blue" onclick="location.href='/submenu?main=2&sub=1'">1 - Ποσοστιαίες δαπάνες Ελλάδας ανά τομέα</button>
                <button class="blue" onclick="location.href='/submenu?main=2&sub=2'">2 - Σύγκριση με Μ.Ο. Ευρωζώνης</button>
                """;
            case 3 -> """
                <button class="blue" onclick="location.href='/submenu?main=3&sub=1'">1 - Σύγκριση εσόδων</button>
                <button class="blue" onclick="location.href='/submenu?main=3&sub=2'">2 - Σύγκριση δαπανών</button>
                """;
            case 4 -> """
                <button class="blue" onclick="location.href='/submenu?main=4&sub=1'">1 - ΚΚΑΕΠ όλων των χωρών</button>
                <button class="blue" onclick="location.href='/submenu?main=4&sub=2'">2 - Σύγκριση Ελλάδας με άλλες χώρες</button>
                """;
            case 5 -> """
                <button class="blue" onclick="location.href='/submenu?main=5&sub=1'">1 - Δαπάνη ανά πολίτη</button>
                <button class="blue" onclick="location.href='/submenu?main=5&sub=2'">2 - Ποσοστιαία δαπάνη ανά περιφέρεια</button>
                """;
            default -> "";
        };

        return """
            <!DOCTYPE html>
            <html lang="el">
            <head><meta charset="UTF-8"><title>%s</title>
            <style>
                body {font-family: Arial; background: #f0f8ff; text-align: center; padding: 40px;}
                button {width: 80%%; max-width: 600px; padding: 18px; margin: 15px auto; font-size: 18px; border: none; border-radius: 10px; color: white; cursor: pointer;}
                .blue {background: #007bff;}
                .blue:hover {background: #0056b3;}
                .red {background: #dc3545;}
            </style></head>
            <body>
                <h1>%s</h1>
                %s
                <br><br>
               <button class="red" onclick="location.href='/'">0 - Πίσω στο κύριο μενού</button>
            </body>
            </html>
            """.formatted(title, title, buttons);
    }

     // ================= ΣΕΛΙΔΑ ΑΠΟΤΕΛΕΣΜΑΤΟΣ =================
    private static String resultPage(String result, int main) {
        boolean hasNoSubMenu = (main == 1 || main == 6 || main == 8);
        String subMenuButtonStyle = hasNoSubMenu ? "display:none;" : "display:inline-block;";
        return """
            <!DOCTYPE html>
            <html lang="el">
            <head><meta charset="UTF-8"><title>Αποτέλεσμα</title>
            <style>
                body {font-family: Arial; background: #f9f9f9; padding: 20px;}
                pre {background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); white-space: pre-wrap; font-family: monospace;}
                button {padding: 15px 30px; margin: 20px; font-size: 18px; border: none; border-radius: 8px; cursor: pointer;}
                .blue {background: #007bff; color: white;}
                .red {background: #dc3545; color: white;}
            </style></head>
            <body>
                <pre>%s</pre>
                <button class="blue" style="%s" onclick="location.href='/submenu?main=%d'">Πίσω στο υπομενού</button>
                <button class="red" onclick="location.href='/'">Κύριο μενού</button>
            </body>
            </html>
            """.formatted(result,subMenuButtonStyle, main);
    }

             
         // ================= ΕΚΤΕΛΕΣΗ ΕΠΙΛΟΓΩΝ =================
    private static String executeChoice(int main, int sub) {
        StringBuilder sb = new StringBuilder();
        switch (main) {
            case 1 -> {
                sb.append("=== ΑΡΧΕΙΟ 1: gr_revenue_expenses_25.csv ===\n");
                appendFirstRows(sb, revenue, 33);
                sb.append("\n=== ΑΡΧΕΙΟ 2: gr_ministy_25.csv ===\n");
                appendFirstRows(sb, budget, 35);
            } 
            case 2 -> {
                long[] A = AvgEurozone.convertToLong(budget);
                double[] B = AvgEurozone.ministrDiv(A);
                double[] C = AvgEurozone.compareGrToEurozone(B);
                String[] sectors = AvgEurozone.sectors();

                if (sub == 1) {
                    sb.append("Ποσοστιαίες δαπάνες Ελλάδας ανά τομέα:\n\n");
                    for (int i = 0; i < 11; i++) {
                        sb.append("Η Ελλάδα δαπανά ").append(String.format("%.2f", B[i])).append("% στον τομέα ").append(sectors[i]).append("\n");
                    }
                } else if (sub == 2) {
                    sb.append("Σύγκριση με Μ.Ο. Ευρωζώνης:\n\n");
                    for (int i = 0; i < 11; i++) {
                        if (C[i] > 0) sb.append("Λιγότερο κατά ").append(String.format("%.2f", C[i])).append("% στον ").append(sectors[i]).append("\n");
                        else if (C[i] < 0) sb.append("Περισσότερο κατά ").append(String.format("%.2f", Math.abs(C[i]))).append("% στον ").append(sectors[i]).append("\n");
                        else sb.append("Ίδιο με Μ.Ο. στον ").append(sectors[i]).append("\n");
                    }
                }
            }case 3 -> {
                long[][] s = Percent.converterToLong(revenue, 14, 2);
                long[][] f = Percent.converterToLong(revenue, 16, 16);
                double[][] g = Percent.percentual(s);
                double[][] h = Percent.percentual(f);
                long[][] n = Percent.amount(s);
                long[][] m = Percent.amount(f);

                if (sub == 1) {
                    sb.append("ΣΥΓΚΡΙΣΗ ΕΣΟΔΩΝ ΤΕΛΕΥΤΑΙΑ 5 ΕΤΗ\n\n");
                    sb.append("Ποσοστιαίες διαφορές (%):\n");
                    sb.append("Έτη: 24-25 | 23-24 | 22-23 | 21-22\n");
                    for (int i = 0; i < g.length; i++) {
                        sb.append(String.format("%-40s", revenue[i][1]));
                        for (int j = 0; j < 4; j++) sb.append(String.format(" %8.2f%%", g[i][j]));
                        sb.append("\n");
                    }
                    sb.append("\nΠοσά διαφορές:\n");
                    for (int i = 0; i < n.length; i++) {
                        sb.append(String.format("%-40s", revenue[i][1]));
                        for (int j = 0; j < 4; j++) sb.append(String.format(" %,12d", n[i][j]));
                        sb.append("\n");
                    }
                } else if (sub == 2) {
                    sb.append("ΣΥΓΚΡΙΣΗ ΔΑΠΑΝΩΝ ΤΕΛΕΥΤΑΙΑ 5 ΕΤΗ\n\n");
                    sb.append("Ποσοστιαίες διαφορές (%):\n");
                    sb.append("Έτη: 24-25 | 23-24 | 22-23 | 21-22\n");
                    for (int i = 0; i < h.length; i++) {
                        sb.append(String.format("%-40s", revenue[i+14][1]));
                        for (int j = 0; j < 4; j++) sb.append(String.format(" %8.2f%%", h[i][j]));
                        sb.append("\n");
                    }
                    sb.append("\nΠοσά διαφορές:\n");
                    for (int i = 0; i < m.length; i++) {
                        sb.append(String.format("%-40s", revenue[i+14][1]));
                        for (int j = 0; j < 4; j++) sb.append(String.format(" %,12d", m[i][j]));
                        sb.append("\n");
                    }

                } 
            }
            case 4 -> {
                long[][] D = EuzLivingStandard.compareToLong(gdppop);
                double[] E = EuzLivingStandard.findStandLiving(D);

                if (sub == 1) {
                    sb.append("ΚΑΤΑ ΚΕΦΑΛΗΝ ΑΕΠ ΧΩΡΩΝ ΕΥΡΩΖΩΝΗΣ (2025)\n\n");
                    String[] countries = {"Αυστρία", "Βέλγιο", "Κροατία", "Κύπρος", "Εσθονία", "Φινλανδία", "Γαλλία", "Γερμανία", "Ιρλανδία", "Ιταλία", "Λετονία", "Λιθουανία", "Λουξεμβούργο", "Μάλτα", "Ολλανδία", "Πορτογαλία", "Σλοβακία", "Σλοβενία", "Ισπανία"};
                    for (int i = 0; i < 19; i++) {
                        sb.append(countries[i]).append(": ").append(String.format("%,.0f", E[i])).append(" €\n");
                    }
                    sb.append("\nΜέσος όρος Ευρωζώνης: ").append(String.format("%,.0f", E[19])).append(" €\n");
                } else if (sub == 2) {
                    sb.append("ΣΥΓΚΡΙΣΗ ΒΙΟΤΙΚΟΥ ΕΠΙΠΕΔΟΥ ΕΛΛΑΔΑΣ ΜΕ ΑΛΛΕΣ ΧΩΡΕΣ\n\n");
                    sb.append("Ελλάδα: 25.300 €\n");
                    String[] countries = {"Αυστρία", "Βέλγιο", "Κροατία", "Κύπρος", "Εσθονία", "Φινλανδία", "Γαλλία", "Γερμανία", "Ιρλανδία", "Ιταλία", "Λετονία", "Λιθουανία", "Λουξεμβούργο", "Μάλτα", "Ολλανδία", "Πορτογαλία", "Σλοβακία", "Σλοβενία", "Ισπανία"};
                    for (int i = 0; i < 19; i++) {
                        double diff = E[i] - 25300;
                        if (diff > 0) {
                            sb.append(countries[i]).append(" έχει υψηλότερο κατά ").append(String.format("%,.0f", diff)).append(" €\n");
                        } else if (diff < 0) {
                            sb.append(countries[i]).append(" έχει χαμηλότερο κατά ").append(String.format("%,.0f", Math.abs(diff))).append(" €\n");
                        } else {
                            sb.append(countries[i]).append(" έχει ίδιο με Ελλάδα\n");
                        }
                    }
                }
            }
            case 5 -> {
                long[] bl = RegionalPer.transformToLong(budget);
                double[] pp = RegionalPer.calcBudgetPerPerson(bl);
                double[] pr = RegionalPer.calcBudgetPerRegion(bl);
                String[] regions = {"Αττική", "Κεντρική Μακεδονία", "Δυτική Ελλάδα", "Κρήτη", "Α.Μ.Θ.", "Ήπειρος", "Θεσσαλία"};

                if (sub == 1) {
                    sb.append("ΔΑΠΑΝΗ ΑΝΑ ΠΟΛΙΤΗ ΑΝΑ ΠΕΡΙΦΕΡΕΙΑ\n\n");
                    for (int i = 0; i < 7; i++) {
                        sb.append(regions[i]).append(": ").append(String.format("%.1f", pp[i])).append(" €\n");
                    }
                } else if (sub == 2) {
                    sb.append("ΠΟΣΟΣΤΙΑΙΑ ΔΑΠΑΝΗ ΑΝΑ ΠΕΡΙΦΕΡΕΙΑ\n\n");
                    for (int i = 0; i < 7; i++) {
                        sb.append(regions[i]).append(": ").append(String.format("%.1f", pr[i])).append("%\n");
                    }
                }
            }
            case 6 -> {
                long[][] LongDataFull = Percent.converterToLong(revenue, 14, 2);
                long[] LongData25 = new long[LongDataFull.length];
                for (int i = 0; i < LongDataFull.length; i++) LongData25[i] = LongDataFull[i][0];

                long grTaxes = LongData25[1] + LongData25[2];
                double grPct = Math.round((grTaxes / 206000000000.0 * 100) * 10.0) / 10.0;
                double avg = 40.9;
                double diff = Math.round(Math.abs(grPct - avg) * 10.0) / 10.0;

                sb.append("ΣΥΓΚΡΙΣΗ ΦΟΡΟΛΟΓΙΚΩΝ ΕΣΟΔΩΝ ΜΕ ΕΥΡΩΖΩΝΗ (2025)\n\n");
                sb.append("Ελλάδα: ").append(String.format("%.1f", grPct)).append("% του ΑΕΠ\n");
                sb.append("Μ.Ο. Ευρωζώνης: ").append(avg).append("%\n\n");
                sb.append(grPct > avg ? "Η Ελλάδα έχει υψηλότερα κατά " : "Η Ευρωζώνη έχει υψηλότερα κατά ").append(diff).append(" ποσοστιαίες μονάδες\n");
            }
            case 8 -> {
                if (historyTypes.isEmpty()) {
                    sb.append("ΔΕΝ ΥΠΑΡΧΟΥΝ ΚΑΤΑΓΕΓΡΑΜΜΕΝΕΣ ΑΛΛΑΓΕΣ\n\n");
                    sb.append("Δεν έχετε κάνει καμία επεξεργασία ακόμα.\n");
                    sb.append("Χρησιμοποιήστε την επιλογή 7 για να κάνετε αλλαγές.");
                } else {
                    sb.append("=== ΙΣΤΟΡΙΚΟ ΑΛΛΑΓΩΝ ===\n\n");
                    sb.append("Σύνολο αλλαγών: ").append(historyTypes.size()).append("\n\n");
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                
                    for (int i = 0; i < historyTypes.size(); i++) {
                        sb.append("─────────────────────────────────────────\n");
                        sb.append("Αλλαγή #").append(i + 1).append("\n");
                        sb.append("Τύπος: ").append(historyTypeNames.get(i)).append("\n");
                        sb.append("Ημερομηνία: ").append(historyTimestamps.get(i).format(formatter)).append("\n\n");
                        
                        String[] labels = getLabels(historyTypes.get(i));
                        double[] oldVals = historyOldValues.get(i);
                        double[] newVals = historyNewValues.get(i);
                        
                        sb.append("Αλλαγές:\n");
                        for (int j = 0; j <= oldVals.length - 1; j++) {
                            if (oldVals[j] != newVals[j]) {
                                sb.append("  • ").append(labels[j]).append(": ");
                                sb.append(String.format("%.2f", oldVals[j]));
                                sb.append(" → ");
                                sb.append(String.format("%.2f", newVals[j]));
                                
                                double diff = newVals[j] - oldVals[j];
                                sb.append(String.format(" (%+.2f)", diff));
                                sb.append("\n");
                            }
                        }
                        sb.append("\n");
                    }
                }
            }
                    
            // Προσθέτεις τις υπόλοιπες όπως θέλεις
            default -> sb.append("Αποτέλεσμα για επιλογή ").append(main).append(" - υποεπιλογή ").append(sub);
        }
        return sb.toString();
    }
    // ================= ΒΟΗΘΗΤΙΚΕΣ =================
    private static int parseParam(String query, String param, int def) {
        if (query == null) return def;
        for (String p : query.split("&")) {
            if (p.startsWith(param + "=")) {
                try {
                    return Integer.parseInt(p.substring(param.length() + 1));
                } catch (Exception e) {
                    return def;
                }
            }
        }
        return def;
    }
    private static void appendFirstRows(StringBuilder sb, String[][] data, int n) {
        for (int i = 0; i < Math.min(n, data.length); i++) {
            for (int j = 0; j < data[i].length; j++) {
                sb.append(data[i][j]);
                if (j < data[i].length - 1) sb.append(" | ");
            }
            sb.append("\n");
        }
    }
    private static String getSessionId(HttpExchange exchange) {
        List<String> cookies = exchange.getRequestHeaders().get("Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.contains("session=")) {
                    return cookie.split("session=")[1].split(";")[0];
                }
            }
        }
        String newId = UUID.randomUUID().toString().substring(0, 8);
        exchange.getResponseHeaders().add("Set-Cookie", "session=" + newId + "; Path=/");
        return newId;
    }
    private static Map<String, String> parseParams(String body) {
        Map<String, String> map = new HashMap<>();
        if (body == null || body.isEmpty()) return map;
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                map.put(parts[0], java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }
    private static void sendHtml(HttpExchange e, String html) throws IOException {
        byte[] b = html.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        e.sendResponseHeaders(200, b.length);
        try (OutputStream os = e.getResponseBody()) {
            os.write(b);
        }
    }
    static class ExitHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = """
            <!DOCTYPE html>
            <html lang="el">
            <head><meta charset="UTF-8"></head>
            <body style="font-family: Arial; text-align: center; padding-top: 100px;">
                <h1>Ευχαριστούμε που χρησιμοποιήσατε την εφαρμογή Budget Surfers!</h1>
                <p>Μπορείτε τώρα να κλείσετε αυτή την καρτέλα του browser.</p>
            </body>
            </html>
            """;
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
}
                