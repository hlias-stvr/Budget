package eurozone.gov.excel;
public class ChildChoices {
    public void childchoice3a1(double[][] g, String[][] revenue) {
        System.out.println("Η διαφορά των ποσοστών των εσόδων ανά έτος είναι:");
        for(int i = 0; i < g.length; i++) {
            for(int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1]+" "+g[i][j]+"% ");
                } else if (j == 1 || j ==2) {
                    System.out.print(g[i][j]+"% ");
                } else if (j == 3) {
                    System.out.println(g[i][j]+"%");
                }
            }
        }
    }
}