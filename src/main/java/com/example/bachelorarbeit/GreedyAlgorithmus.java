package com.example.bachelorarbeit;

import java.util.Random;
import java.util.Scanner;

public class GreedyAlgorithmus {


    public static void main(String[] args) {

      Scanner scanner = new Scanner(System.in);

      System.out.print("Bitte geben Sie die Anzahl der Mitarbeiter ein: ");

      int anzahlDerArbeiter = scanner.nextInt();

      System.out.print("Bitte geben Sie die Anzahl der Aufgaben ein: ");

      int anzahlDerAufgaben = scanner.nextInt();
      System.out.print("Bitte geben Sie die Anzahl der Tage ein: ");

      int tage = scanner.nextInt();
      scanner.close();



      double[][] auslastung = auslastungGenerieren(anzahlDerArbeiter, anzahlDerAufgaben);
      double[] angesammlteArbeit = new double[anzahlDerAufgaben];

      for (int tag = 1; tag <= tage; tag++) {
        System.out.println("Tag " + tag + ":");

        boolean[] aufgabeZuweisen = new boolean[anzahlDerAufgaben];
        for (int arbeiter = 0; arbeiter < anzahlDerArbeiter; arbeiter++) {
          int bevorzugteAufgabe = -1;
          double maxAuslastung = -1;

          for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) {
            if (!aufgabeZuweisen[aufgabe] && auslastung[arbeiter][aufgabe] > maxAuslastung) { // 0.2
              maxAuslastung = auslastung[arbeiter][aufgabe];
              bevorzugteAufgabe = aufgabe; //
              // System.out.println("Testing bevorzugteAufgabe: " + bevorzugteAufgabe);
            }
          }

          if (bevorzugteAufgabe != -1) {
            aufgabeZuweisen[bevorzugteAufgabe] = true;
            angesammlteArbeit[bevorzugteAufgabe] += 1.0;
            System.out.println("[*] Arbeiter " + (arbeiter + 1) + " -> Aufgabe " + (bevorzugteAufgabe + 1) + " (Gewicht: " + String.format("%.3f", maxAuslastung) +")");
          }
        }
        System.out.println();
      }

      System.out.println("Ergebnisse:");
      for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) {
        System.out.println("[+] Aufgabe " + (aufgabe + 1) + ": Gesamte angesammelte Arbeit = " + angesammlteArbeit[aufgabe]);
      }
    }


    public static double[][] auslastungGenerieren(int anzahlDerArbeiter, int anzahlDerAufgaben) {
      Random random = new Random();
      double[][] auslastung = new double[anzahlDerArbeiter][anzahlDerAufgaben];

      for (int arbeiter = 0; arbeiter < anzahlDerArbeiter; arbeiter++) {
        double summe = 0.0;
        for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) {
          auslastung[arbeiter][aufgabe] = random.nextDouble();
          // System.out.println("Testing auslastung[arbeiter][aufgabe]: " + auslastung[arbeiter][aufgabe]);
          summe += auslastung[arbeiter][aufgabe];
          // System.out.println("Testing summe: " + summe);

        }

        for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) { // Die Summe ändert sich hier nicht!!!!!
          auslastung[arbeiter][aufgabe] /= summe;
        }


      }

      // Die Matrix Tabelle:
      System.out.println("Die generierte Gewichtsmatrix:");
      System.out.print("\t\t");
      for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) {
        System.out.print("Arbeiter" + (aufgabe + 1) + "\t");
      }
      System.out.println();

      for (int arbeiter = 0; arbeiter < anzahlDerArbeiter; arbeiter++) {
        System.out.print("Aufgabe" + (arbeiter + 1) + "\t");
        for (int aufgabe = 0; aufgabe < anzahlDerAufgaben; aufgabe++) {
          System.out.printf("%.3f\t\t", auslastung[arbeiter][aufgabe]);
        }
        System.out.println();
      }

      System.out.println();
      return auslastung;
    }
  }


