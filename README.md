# Budget
Εφαρμογή σε JAVA για την ανάλυση των στοιχείων του κρατικού προϋπολογισμού της Ελλάδας

## Απαιτήσεις
**Java JDK/JRE 17+**
**Maven 3.6+ (Προαιρετικά)**

## Οδηγίες μεταγλώττισης του προγράμματος
```
bash
Για μεταγλώττιση:
1) a) Για περιβάλλον κονσόλας (CLI):
      javac -cp target/classes eurozone.gov.excel.Main.java
   b) Για γραφικό περιβάλλον (GUI):
      javac -cp target/classes eurozone.gov.excel.BudgetApp.java
2) Αν υπάρχει διαθέσιμη έκδοση Maven 3.6+:
      mvn clean compile (πιο ασφαλές)
      ή
      mvn compile
```

## Οδηγίες εκτέλεσης του προγράμματος
```
bash
Για εκτέλεση:
1) a) Για περιβάλλον κονσόλας (CLI):
      java -cp target/classes eurozone.gov.excel.Main
   b) Για γραφικό περιβάλλον (GUI):
      java -cp target/classes eurozone.gov.excel.BudgetApp
2) Αν υπάρχει διαθέσιμη έκδοση Maven 3.6+:
   a) Για περιβάλλον κονσόλας (CLI):
      mvn exec:java -Dexec.mainClass="eurozone.gov.excel.Main"
   b) Για γραφικό περιβάλλον (GUI):
       mvn exec:java -Dexec.mainClass="eurozone.gov.excel.BudgetApp"
```

## Οδηγίες χρήσης του προγράμματος
Ο χρήστης έχει 7 βασικές επιλογές και την επιλογή εξόδου από την εφαρμογή.
Εισάγοντας τον αντίστοιχο αριθμό, του εμφανίζονται και οι υποεπιλογές ανάλογα με την αρχική του επιλογή.
Ο χρήστης έχει συνεχώς τη δυνατότητα να επιστρέφει στο προηγούμενο στρώμα επιλογών εισάγοντας τον αριθμό 0.

## Βασικές επιλογές:
- **1**: Προβολή στοιχείων κρατικού προϋπολογισμού
- **2**: Σύγκριση ποσοστιαίων δαπανών ανά τομέα με τον μέσο όρο της Ευρωζώνης
- **3**: Σύγκριση εσόδων και εξόδων τα τελευταία έτη
- **4**: Σύγκριση βιοτικού επιπέδου Ελλάδας με χώρες της Ευρωζώνης
- **5**: Ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια
- **6**: Σύγκριση φορολογικών εσόδων με τον μέσο όρο της Ευρωζώνης
- **7**: Επεξεργασία δεδομένων προϋπολογισμού
- **0**: Έξοδος από την εφαρμογή

## Παρουσίαση της δομής των περιεχομένων του αποθετηρίου
* Budget_Surfers/
    * └── .vscode/
        * └── settings.json
    * └── src/
        * └── main/
            * └── java/eurozone/gov/excel/
                * ├── AvgEurozone.java
                * ├── BudgetApp.java
                * ├── BudgetChange.java
                * ├── BudgetVariance.java
                * ├── ChangedData.java
                * ├── ChangesHistory.java
                * ├── ChildChoices.java
                * ├── Choices.java
                * ├── CompareEuzTaxes.java
                * ├── EuzLivingStandard.java
                * ├── Main.java
                * ├── ReadCsvFiles.java
                * ├── RegionalPer.java
                * └── Subchoises.java
            * └── resources/
                * ├── Gdp_population_euz.csv
                * ├── gr_ministy_25.csv
                * ├── gr_revenue_expenses_25.csv
                * └── logo.png
        * └── test/java/eurozone/gov/excel/
            * ├── TestAvgEurozone.java
            * ├── TestBudgetVariance.java
            * ├── TestChangedData.java
            * ├── TestEuzLivingStandard.java
            * ├── TestEuzTaxes.java
            * └── TestRegionalPer.java
    * ├── .gitignore
    * ├── .htmlhintrc.json
    * ├── Budget.lnk
    * ├── checkstyle.xml
    * └── pom.xml
* docs/
    * └── UML_Diagram_For_Budget_Surfers.png
* LICENCE
* README.md


## Διάγραμμα UML σχετικά με το σχεδιασμό του κώδικα
<p align="center">
<img src="docs/UML_Diagram_For_Budget_Surfers_Project.png" alt="UML Diagram">
</p>

## Επισκόπηση των δομών δεδομένων και των αλγορίθμων που χρησιμοποιεί η εφαρμογή
### 1. Δυναμικές Δομές Δεδομένων
Η εφαρμογή χρησιμοποιεί δυναμικές λίστες (`ArrayList`) για τη διαχείριση των δεδομένων, εξασφαλίζοντας:

- Ευελιξία: Δυνατότητα αποθήκευσης διαφορετικών τύπων δεδομένων και αντικειμένων.  
- Δυναμική Προσαρμογή: Η δομή μεταβάλλεται ανάλογα με τον όγκο των δεδομένων.  
- Ιστορικότητα: Δυνατότητα παρουσίασης και διαχείρισης ιστορικού προσαρμογών στον χρήστη.

### 2. Σχεσιακή Οργάνωση
Για την καλύτερη οργάνωση και επεξεργασία της πληροφορίας, τα δεδομένα κατανέμονται σε πολλαπλούς πίνακες, κάτι που επιτρέπει:

- Καλύτερη κατηγοριοποίηση των οντοτήτων της εφαρμογής.  
- Ταχύτερη αναζήτηση και φιλτράρισμα των εγγραφών.


## Πρόσθετη Τεκμηρίωση

- Δοκιμές εκτέλεσης (manual tests) έχουν γίνει για όλες τις επιλογές του μενού
- Καλύπτονται τα βασικά δεδομένα και υπολογισμοί
- Επιπλέον αρχεία UML & παραδείγματα CSV βρίσκονται στο repository

