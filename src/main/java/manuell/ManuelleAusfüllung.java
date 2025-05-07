package manuell;

import java.util.Random;

public class ManuelleAusfüllung {
  public static void main(String[] args) {
    double [][] originaleAuslastung={
        {0.25, 0.25, 0.25,0.25},
        {0.25, 0.25, 0.25,0.25},
        {0.25, 0.25, 0.25,0.25},
        {0.25, 0.25, 0.25,0.25}

    };
    int mitarbeiter= originaleAuslastung.length;
    int aufgaben = originaleAuslastung[0].length;
    int tage = 100;

    double[][] auslastung= new double[mitarbeiter][aufgaben];
    double[][] letzteAuslastung= new double[mitarbeiter][aufgaben];
    double[][] gesamteAuslastung= new double[mitarbeiter][aufgaben];

    double maximaleAuslastung=0;
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

      System.out.println();

      System.out.println();
    }



    System.out.println("die Orginale Matrix :");
    auslastungAusgaben(originaleAuslastung, mitarbeiter,aufgaben);
    System.out.println("die Maximale Auslastung:" + String.format("%.2f", maximaleAuslastung));

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
