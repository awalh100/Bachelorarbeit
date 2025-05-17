package globaleAlgorithmus;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GlobaleGreedyAlgorithmus {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Bitte geben Sie die Anzahl der Mitarbeiter ein: ");
    int mitarbeiter = scanner.nextInt();

    System.out.print("Bitte geben Sie die Anzahl der Aufgaben ein: ");
    int aufgaben = scanner.nextInt();

    System.out.print("Bitte geben Sie die Anzahl der Tage ein: ");
    int tage = scanner.nextInt();

    scanner.close();

    double[][] originaleAuslastung = auslastungGenerieren(mitarbeiter, aufgaben);
    double[][] auslastung = new double[mitarbeiter][aufgaben];
    double[][] letzteAuslastung = new double[mitarbeiter][aufgaben];
    double[][] gesmteAuslastung = new double[mitarbeiter][aufgaben];

    double maximaleAuslastung =0;

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

      double [][] vorZuweisung = new double[mitarbeiter][aufgaben];
      for (int i =0; i< mitarbeiter; i++){
        vorZuweisung[i]= auslastung[i].clone();
      }

      double maximaleAuslastungTag= berechneMaximaleAuslastung(auslastung);

      if (maximaleAuslastungTag > maximaleAuslastung){
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
                  " (Gewicht: " + String.format("%.3f", maxWert) + ")");
          aufgabeZuweisen[bevorzugteAufgabe] = true;
          mitarbeiterZuweisen[bevorzugterMitarbeiter]= true;
          zugewieseneAufgabe[bevorzugterMitarbeiter] = bevorzugteAufgabe;

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
          gesmteAuslastung[i][j] += auslastung[i][j];
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
    orginaleAuslastungAusgaben(originaleAuslastung, mitarbeiter,aufgaben);
    System.out.println("die Maximale Auslastung:" + String.format("%.3f", maximaleAuslastung));
    System.out.println("Anzahl der Matchings : "+ matchings.size());
  }

  public static void auslastungAusgaben(double[][] matrix, int mitarbeiter, int aufgaben){
    System.out.print("\t\t");
    for (int aufgabe =0; aufgabe< aufgaben; aufgabe++){
      System.out.print("aufgabe" + (aufgabe+1)+ "\t");
    }
    System.out.println();
    for(int arbeiter =0; arbeiter< mitarbeiter; arbeiter++){
      System.out.print("Mitarbeiter "+(arbeiter+1)+ "\t");
      for (int aufgabe =0; aufgabe<aufgaben; aufgabe++){
        System.out.printf("%.3f\t\t", matrix[arbeiter][aufgabe]);
      }
      System.out.println();
    }
    System.out.println();
  }
  public static void orginaleAuslastungAusgaben(double[][] matrix, int mitarbeiter, int aufgaben){
    System.out.print("\t\t");
    for (int aufgabe =0; aufgabe< aufgaben; aufgabe++){
      System.out.print("aufgabe" + (aufgabe+1)+ "\t");
    }
    System.out.println();
    for(int arbeiter =0; arbeiter< mitarbeiter; arbeiter++){
      System.out.print("Mitarbeiter "+(arbeiter+1)+ "\t");
      for (int aufgabe =0; aufgabe<aufgaben; aufgabe++){
        System.out.print( matrix[arbeiter][aufgabe] + "\t\t");
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
  public static double[][] auslastungGenerieren(int mitarbeiter, int aufgaben) {
    Random random = new Random();
    double[][] auslastung = new double[mitarbeiter][aufgaben];

    // Zufallswerte generieren und normalisieren
    for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
      double summe = 0.0;
      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        auslastung[arbeiter][aufgabe] = random.nextDouble();
        summe += auslastung[arbeiter][aufgabe];
      }


      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        auslastung[arbeiter][aufgabe] /= summe;
      }
    }

    // Iteration zur Normalisierung der Spalten
    for (int iteration = 0; iteration < 10; iteration++) {
      // Spalten normalisieren
      for(int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
        double summeSpalte = 0.0;
        for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
          summeSpalte += auslastung[arbeiter][aufgabe];
        }
        for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
          auslastung[arbeiter][aufgabe] /= summeSpalte;
        }
      }

      // Zeilen normalisieren
      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        double summeZeile = 0.0;
        for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
          summeZeile += auslastung[arbeiter][aufgabe];
        }
        for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
          auslastung[arbeiter][aufgabe] /= summeZeile;
        }
      }
    }

    return auslastung;
  }
}
