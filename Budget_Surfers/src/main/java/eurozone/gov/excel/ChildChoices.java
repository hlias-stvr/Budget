package eurozone.gov.excel;
public class ChildChoices {
    public void childChoice3a1(double[][] g, String[][] revenue) {
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
    public void childChoice3a2(String[][] revenue, long[][] n) {
        System.out.println("η διαφορά των ποσών των εσόδων ανά έτος είναι:");
        for(int i = 0; i < n.length; i++) {
            for(int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1]+" "+n[i][j]);
                } else if (j == 1 || j ==2) {
                    System.out.print(" "+n[i][j]);
                } else if (j == 3) {
                    System.out.println(" "+n[i][j]);
                }
            }
        }
    }
    public void childChoice3b1(String[][] revenue, double[][] h) {
        System.out.println("Η διαφορά των ποσοστών ανά έτος είναι:");
        for(int i = 0; i < h.length; i++) {
            for(int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1]+" "+h[i][j]+"% ");
                } else if (j == 1 || j ==2) {
                    System.out.print(h[i][j]+"% ");
                } else if (j == 3) {
                    System.out.println(h[i][j]+"% ");
                }
            }
        }
    }
    public void childChoice3b2(String[][] revenue, long[][] m) {
        System.out.println("Η διαφορά των ποσών ανά έτος είναι:");
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.print(revenue[i][1]+" "+m[i][j]);
                } else if (j == 1 || j ==2) {
                    System.out.print(" "+m[i][j]);
                } else if (j == 3) {
                    System.out.println(" "+m[i][j]);
                }  
            }
        }
    }
}