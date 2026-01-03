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
 
            if (sub == 0 && (main == 1 || main == 6)) {
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