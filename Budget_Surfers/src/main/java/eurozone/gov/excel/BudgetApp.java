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
                    <button class="green" onclick="location.href='/edit'">7 - Επεξεργασία στοιχείων προϋπολογισμού (Interactive!)</button>
                    <button class="green" onclick="go(8)">8 - Επεξεργασία στοιχείων προϋπολογισμού (Interactive!)</button>
                    <button class="red" onclick="if(confirm('Έξοδος;')) window.close()">0 - Έξοδος</button>
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
                    modifiedSectorPercents = Arrays.copyOf(data, 10);
                } else if ("regions".equals(type)) {
                    modifiedRegionAmounts = Arrays.copyOf(data, 7);
                } else if ("revenues".equals(type)) {
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
 
                // ΕΝΗΜΕΡΩΝΟΥΜΕ ΑΜΕΣΩΣ ΤΙΣ GLOBAL ΜΕΤΑΒΛΗΤΕΣ
                if ("sectors".equals(type)) {
                    modifiedSectorPercents = new double[10];
                    System.arraycopy(data, 0, modifiedSectorPercents, 0, 10);
                } else if ("regions".equals(type)) {
                    modifiedRegionAmounts = new double[7];
                    System.arraycopy(data, 0, modifiedRegionAmounts, 0, 7);
                } else if ("revenues".equals(type)) {
                    modifiedRevenueAmounts = Arrays.copyOf(data, data.length);
                }
            }
        } catch (Exception ignored) {
            // Ignore invalid input
        }
    }
}
// Εδώ αρχίζει ο Άγγελος
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
            long grTaxes = (long) modifiedRevenueAmounts[1] + (long) modifiedRevenueAmounts[2];
            
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
                sb.append("<p style='color:#d9534f; font-weight:bold; font-size:18px;'>⚠️ Η Ελλάδα υπερβαίνει τον Μ.Ο. κατά ").append(String.format("%.2f", diff)).append(" ποσοστιαίες μονάδες.</p>");
            } else if (grPct < avg) {
                sb.append("<p style='color:#5cb85c; font-weight:bold; font-size:18px;'>✅ Η Ελλάδα υστερεί κατά ").append(String.format("%.2f", diff)).append(" ποσοστιαίες μονάδες.</p>");
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