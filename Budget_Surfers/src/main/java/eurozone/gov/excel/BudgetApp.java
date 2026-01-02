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