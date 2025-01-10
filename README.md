# Problem Darstellung:

Es gibt n Arbeiter N und n Aufgaben J. Jede Aufgabe j hat für jeden Arbeiter i eine Auslastung w_i,j in [0, 1], sodass sum_j w_i,j = 1. Jeder Arbeiter kann jeden Tag eine Aufgabe bearbeiten, aber jede Aufgabe kann nur jeweils von einem Arbeiter gleichzeitig bearbeitet werden. Dabei bearbeitet jeder Arbeiter pro Tag 1 Einheit Arbeit.


- Gegeben ein einfacher Algorithmus, z.B. der Greedy-Algorithmus unten, wieviel Arbeit kann sich hier nach t Tagen maximal ansammeln?

- Wenn die Zuordnung von Arbeitern auf Aufgaben jeden Tag ein Matching darstellt, wieviele solcher Matchings werden in einem optimalen Algorithmus benötigt? Wie lang ist eine solche Sequenz von Matchings bevor sie sich wiederholt?

- Wieviel Arbeit kann sich höchstens/nach t vielen Tagen in einer Aufgabe ansammeln?
  
Greedy-Algorithmus:
- an jedem Tage:
    - bis alle Arbeiter eine Aufgabe haben
        - wähle eine Arbeiter-Aufgaben Kombination mit maximaler Arbeit
