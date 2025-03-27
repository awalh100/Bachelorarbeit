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

    double[][] auslastung = auslastungGenerieren(mitarbeiter, aufgaben);
    double[][] gesamteAuslastung = new double[mitarbeiter][aufgaben];
    double[] angesammlteArbeit = new double[aufgaben];

    boolean[] aufgabeZuweisen = new boolean[aufgaben];

    for (int tag = 1; tag <= tage; tag++) {
      System.out.println("Tag " + tag + ":");
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
          angesammlteArbeit[bevorzugteAufgabe] += 1.0;
          System.out.println(
              "[*] Arbeiter " + (arbeiter + 1) + " -> Aufgabe " + (bevorzugteAufgabe + 1) +
                  " (Gewicht: " + String.format("%.3f", maxAuslastung) + ")");
          auslastung[arbeiter][bevorzugteAufgabe] = 0;
        }
      }


      for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
        for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
          gesamteAuslastung[arbeiter][aufgabe] += auslastung[arbeiter][aufgabe];
        }
      }


      System.out.println("Ergebnisse:");
      for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
        System.out.println("[+] Aufgabe " + (aufgabe + 1) + ": Gesamte angesammelte Arbeit = " +
            angesammlteArbeit[aufgabe]);
      }


      if (tag < tage) {
        auslastung = auslastungGenerieren(mitarbeiter, aufgaben);
        aufgabeZuweisen = new boolean[aufgaben];
      }
    }

    // Gesamtauslastung nach t Tagen
    System.out.println("\n Gesamtauslastung nach " + tage + " Tagen:");
    for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
      System.out.println("Arbeiter " + (arbeiter + 1) + ":");
      for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
        System.out.printf("%.3f\t", gesamteAuslastung[arbeiter][aufgabe]);
      }
      System.out.println();
    }
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

    // Die Matrix Tabelle ausgeben
    System.out.println("Die generierte Gewichtsmatrix:");
    System.out.print("\t\t");
    for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
      System.out.print("Aufgabe" + (aufgabe + 1) + "\t");
    }
    System.out.println();

    for (int arbeiter = 0; arbeiter < mitarbeiter; arbeiter++) {
      System.out.print("Arbeiter" + (arbeiter + 1) + "\t");
      for (int aufgabe = 0; aufgabe < aufgaben; aufgabe++) {
        System.out.printf("%.3f\t\t", auslastung[arbeiter][aufgabe]);
      }
      System.out.println();
    }

    System.out.println();
    return auslastung;
  }
}
