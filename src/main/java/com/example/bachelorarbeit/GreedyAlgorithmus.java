package com.example.bachelorarbeit;
import java.util.Random;
import java.util.Scanner;

public class GreedyAlgorithmus{
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
    double[][] gesamteAuslastung = new double[mitarbeiter][aufgaben];
    double[] angesammlteArbeit = new double[aufgaben];



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
      boolean[] aufgabeZuweisen = new boolean[aufgaben];

      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        int bevorzugteAufgabe = -1;
        double maxAuslastung = -1;


        for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
          if (!aufgabeZuweisen[aufgabe] && auslastung[arbeiter][aufgabe] > maxAuslastung) {
            maxAuslastung = auslastung[arbeiter][aufgabe];
            bevorzugteAufgabe = aufgabe;
          }
        }


        if (bevorzugteAufgabe != -1) {
          aufgabeZuweisen[bevorzugteAufgabe] = true;
          // angesammlteArbeit[bevorzugteAufgabe] += 1.0;
          System.out.println(
              "[*] Arbeiter " + (arbeiter + 1) + " -> Aufgabe " + (bevorzugteAufgabe + 1) +
                  " (Gewicht: " + String.format("%.3f", maxAuslastung) + ")");
          auslastung[arbeiter][bevorzugteAufgabe] = 0;
        }
      }

      System.out.println("Auslastungsmatrix nach der Zuweisung (Tag" +tag+"):");
      auslastungAusgaben(auslastung, mitarbeiter, aufgaben);





      for (int i = 0; i < mitarbeiter; i++){
        letzteAuslastung[i]= auslastung[i].clone();
      }
      /*for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
          gesamteAuslastung[arbeiter][aufgabe] += auslastung[arbeiter][aufgabe];
        }
      }*/

      System.out.println();
    }


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
  // Methode zum Generieren der Auslastungsmatrix
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

      // Normalisierung der Zeile (jedes Element in der Zeile durch die Zeilensumme teilen)
      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        auslastung[arbeiter][aufgabe] /= summe;
      }
    }

    // Iteration zur Normalisierung der Spalten
    for (int iteration = 0; iteration < 10; iteration++) {  // Wiederhole 10 Mal für Präzision
      // Spalten normalisieren
      for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
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
