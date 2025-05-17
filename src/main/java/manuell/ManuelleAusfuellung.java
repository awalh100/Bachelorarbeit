package manuell;

import java.util.HashSet;
import java.util.Set;

public class ManuelleAusfuellung {
  public static void main(String[] args) {

    double [][] originaleAuslastung={
        {0.33179749303688955, 0.10743564936124392, 0.3288492062455237, 0.23191765135634287},
        {0.40353911859363345, 0.561378012106575	,0.009048658413007659, 0.02603421088678386},
        {0.05525970978284576, 0.10329219076177368, 0.15234137106388365, 0.6891067283914969},
        {0.20935798825070312, 0.2278337999376552, 0.5097666430583521, 0.05304156875328963}



    };
    int mitarbeiter= originaleAuslastung.length;
    int aufgaben = originaleAuslastung[0].length;
    int tage = 100 ;

    double[][] auslastung= new double[mitarbeiter][aufgaben];
    double[][] letzteAuslastung= new double[mitarbeiter][aufgaben];
    double[][] gesamteAuslastung= new double[mitarbeiter][aufgaben];

    double maximaleAuslastung=0;

    Set<String> matchings = new HashSet<>();
    int [] zugewieseneAufgabe = new int[mitarbeiter];
    for (int tag = 1; tag <= tage; tag++) {
      System.out.println("Tag " + tag + ":");


      if (tag == 1) {
        for (int i = 0; i < mitarbeiter; i++) {
          auslastung[i] = originaleAuslastung[i].clone();
        }
      }else{
        for (int i =0; i< mitarbeiter; i++){
          for (int j =0; j< aufgaben; j++){
            auslastung[i][j] = letzteAuslastung[i][j] + originaleAuslastung[i][j];
          }
        }
      }

      System.out.println("Auslastungsmatrix vor der Zuweisung ( Tag"+ tag+"):");
      auslastungAusgaben(auslastung, mitarbeiter, aufgaben);

      double maximaleAuslastungTag= berechneMaximaleAuslastung(auslastung);
      if (maximaleAuslastungTag> maximaleAuslastung) {
        maximaleAuslastung= maximaleAuslastungTag;

      }
      boolean[] aufgabeZuweisen = new boolean[aufgaben];
      boolean[] mitarbeiterZuweisen = new boolean[mitarbeiter];

      for (int i =0; i < mitarbeiter; i++){
        zugewieseneAufgabe[i]=-1;
      }
      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        int bevorzugteAufgabe = -1;
        int bevorzugterMitarbeiter =-1;
        double maxWert = -1;

        for (int i=0; i<mitarbeiter;i++) {
          if (mitarbeiterZuweisen[i]) continue;
          for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
            if (!aufgabeZuweisen[aufgabe] && auslastung[i][aufgabe] > maxWert) {
              maxWert = auslastung[i][aufgabe];
              bevorzugteAufgabe = aufgabe;
              bevorzugterMitarbeiter =i;
            }
          }

        }

        if (bevorzugteAufgabe != -1 && bevorzugterMitarbeiter!= -1) {

          System.out.println(
              "[*] Arbeiter " + (bevorzugterMitarbeiter + 1) + " -> Aufgabe " + (bevorzugteAufgabe + 1) +
                  " (Gewicht: " + String.format("%.2f", maxWert) + ")");
          aufgabeZuweisen[bevorzugteAufgabe] = true;
          mitarbeiterZuweisen[bevorzugterMitarbeiter]= true;
          zugewieseneAufgabe[bevorzugterMitarbeiter]= bevorzugteAufgabe;

          if (auslastung[bevorzugterMitarbeiter][bevorzugteAufgabe] > 1.0){
            auslastung[bevorzugterMitarbeiter][bevorzugteAufgabe] -=1.0;
          } else {
            auslastung[bevorzugterMitarbeiter][bevorzugteAufgabe] = 0;

          }
        }
      }

      System.out.println("Auslastungsmatrix nach der Zuweisung (Tag" +tag+"):");
      auslastungAusgaben(auslastung, mitarbeiter, aufgaben);


      for (int i = 0; i < mitarbeiter; i++){
        letzteAuslastung[i]= auslastung[i].clone();
      }
      for (int i =0; i < mitarbeiter; i++){
        for (int j =0; j < aufgaben; j++){
          gesamteAuslastung[i][j] += auslastung[i][j];
        }
      }
      String matching = "";
      for (int i =0; i< mitarbeiter;i++){
       matching +=(zugewieseneAufgabe[i] +1)+ "-";

        }

      matchings.add(matching);
      System.out.println();


    }



    System.out.println("die Orginale Matrix :");
    auslastungAusgaben(originaleAuslastung, mitarbeiter,aufgaben);
    System.out.println("die Maximale Auslastung:" + String.format("%.2f", maximaleAuslastung));

    System.out.println("Anzahl der Matchings : "+ matchings.size());
  }

  public static void auslastungAusgaben(double[][] matrix, int mitarbeiter, int aufgaben){
    System.out.print("\t\t\t\t");
    for (int aufgabe =0; aufgabe< aufgaben; aufgabe++){
      System.out.print("aufgabe" + (aufgabe+1)+ "\t");
    }
    System.out.println();
    for(int arbeiter =0; arbeiter< mitarbeiter; arbeiter++){
      System.out.print("Mitarbeiter "+(arbeiter+1)+ "\t");
      for (int aufgabe =0; aufgabe<aufgaben; aufgabe++){
        System.out.printf("%.2f\t\t", matrix[arbeiter][aufgabe]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public static double berechneMaximaleAuslastung(double[][]auslastung){
    double maxAuslatung = 0;
    for (int i=0; i<auslastung.length; i++){
      for (int j =0; j < auslastung[i].length; j ++){
        if (auslastung[i][j] > maxAuslatung){
          maxAuslatung= auslastung[i][j];
        }
      }
    }
    return maxAuslatung;
  }


}
