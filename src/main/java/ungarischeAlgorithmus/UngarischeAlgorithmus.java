package ungarischeAlgorithmus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UngarischeAlgorithmus {
  public static void main(String[] args) {
    double[][] originaleAuslastung = {
        {0.33179749303688955, 0.10743564936124392, 0.3288492062455237, 0.23191765135634287},
        {0.40353911859363345, 0.561378012106575, 0.009048658413007659, 0.02603421088678386},
        {0.05525970978284576, 0.10329219076177368, 0.15234137106388365, 0.6891067283914969},
        {0.20935798825070312, 0.2278337999376552, 0.5097666430583521, 0.05304156875328963}


    };
    int mitarbeiter = originaleAuslastung.length;
    int aufgaben = originaleAuslastung[0].length;
    int tage = 100;

    double[][] auslastung = new double[mitarbeiter][aufgaben];
    double[][] letzteAuslastung = new double[mitarbeiter][aufgaben];
    double[][] gesamteAuslastung = new double[mitarbeiter][aufgaben];

    double maximaleAuslastung = 0;

    Set<String> matchings = new HashSet<>();
    for (int tag = 1; tag <= tage; tag++) {
      System.out.println("Tag " + tag + ":");


      if (tag == 1) {
        for (int i = 0; i < mitarbeiter; i++) {
          auslastung[i] = originaleAuslastung[i].clone();
        }
      } else {
        for (int i = 0; i < mitarbeiter; i++) {
          for (int j = 0; j < aufgaben; j++) {
            auslastung[i][j] = letzteAuslastung[i][j] + originaleAuslastung[i][j];
          }
        }
      }

      System.out.println("Auslastungsmatrix vor der Zuweisung ( Tag" + tag + "):");
      auslastungAusgaben(auslastung);

      int[][] kostenMatrix = new int[mitarbeiter][aufgaben];
      for (int i = 0; i < mitarbeiter; i++) {
        for (int j = 0; j < aufgaben; j++) {
          kostenMatrix[i][j] = (int) ((1.0 - auslastung[i][j]) * 1000);
        }
      }

      int[] zuweisung = ungarischeAlgorithmus(kostenMatrix);

      StringBuilder matchingStr = new StringBuilder();
      for (int i =0; i< mitarbeiter;i++){
        matchingStr.append(zuweisung[i] +1).append("-") ;

      }

      matchings.add(matchingStr.toString());
      for (int i = 0; i < mitarbeiter; i++) {
        int aufgabe = zuweisung[i];
        System.out.printf("[*] Mitarbeiter %d -> Aufgabe %d (Auslastung: %.2f)\n", i + 1,
            aufgabe + 1, auslastung[i][aufgabe]);

        if (auslastung[i][aufgabe] > 1.0) {
          auslastung[i][aufgabe] -= 1.0;
        } else {
          auslastung[i][aufgabe] = 0.0;
        }

      }
      System.out.println("Auslastungsmatrix nach der Zuweisung ( Tag" + tag + "):");
      auslastungAusgaben(auslastung);

      for (int i = 0; i < mitarbeiter; i++) {
        letzteAuslastung[i] = auslastung[i].clone();
        for (int j = 0; j < aufgaben; j++) {
          gesamteAuslastung[i][j] += auslastung[i][j];
          maximaleAuslastung = Math.max(maximaleAuslastung, auslastung[i][j]);
        }
      }
      System.out.println();
    }
    System.out.println(
        "maximale Auslastung über alle Tage:" + String.format("%.2f", maximaleAuslastung));
    System.out.println("Anzahl der verschiedene Matchings" +matchings.size());
  }

    public static void auslastungAusgaben(double[][] matrix){
      System.out.println("\t\t");

      for (int j =0; j< matrix[0].length;j++) {
        System.out.print("Aufgabe" + (j + 1) + "\t");
      }
      System.out.println();
      for (int i =0; i< matrix.length; i++) {
        System.out.print("Mitarbeiter " + (i + 1) + "\t");
        for (int j = 0; j < matrix[0].length; j++) {
          System.out.printf("%.2f\t\t", matrix[i][j]);
        }
        System.out.println();
      }
    }

    public static  int[] ungarischeAlgorithmus(int[][] kosten){
    int n = kosten.length;
    int[] u= new int[n +1];
    int[] v= new int[n +1];
    int[] p= new int[n +1];
    int[] way= new int[n +1];

    for (int i= 1 ; i<=n; i++){
      p[0]=i;
      int j0=0;
      int []minv =new int[n+1];
      boolean[] used=new boolean[n+1];
      Arrays.fill(minv, Integer.MAX_VALUE);

      do {
        used[j0] = true;
        int i0 = p[j0], delta = Integer.MAX_VALUE, j1 = -1;
        for (int j = 1; j <= n; j++) {
          if (!used[j]) {
            int cur = kosten[i0 - 1][j - 1] - u[i0] - v[j];
            if (cur < minv[j]) {
              minv[j] = cur;
              way[j] = j0;
            }
            if (minv[j] < delta) {
              delta = minv[j];
              j1 = j;
            }
          }
        }

        for (int j = 0; j <= n; j++) {
          if (used[j]) {
            u[p[j]] += delta;
            v[j] -= delta;
          } else {
            minv[j] -= delta;
          }
        }
        j0 = j1;
      }while(p[j0] !=0);

      do {
        int j1=way[j0];
        p[j0]=p[j1];
        j0=j1;
      }while (j0 !=0);

      }
    int [] result= new int[n];
    for (int j =1; j<= n; j++){
      result[p[j] -1] = j-1;
    }

    return result;

  }
}
