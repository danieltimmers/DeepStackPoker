package deepstack;

//import java.util.*;

public class TempTesterMacro {
    public static void main(String[] args) {

        Game gg = new Game(5);
        int[] nPerHand = new int[11];
        int nOfRounds = 1000;
        double totalHands = nOfRounds * gg.players().size();
        String[] handType = { "0", "High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush",
                "Full House", "Four of a Kind", "Straight-Flush", "Royal Flush" };

        for (int i = 0; i < nOfRounds; i++) {
            // gg.runToFlopQuiet();
            gg.runToShowDownQuiet();
            gg.setPlayersBestHands();
            for (Player p : gg.players()) {
                nPerHand[p.bestHand().handStrength()]++;

                // INSERT EXTRA CODE HERE
                if (p.bestHand().handStrength() == 6) {
                    System.out.println();
                    for (Card cc : p.bestHand().hand()) {
                        System.out.println(cc);
                    }
                    System.out.println(p.bestHand().handStrength());
                }
            }
            gg.newRound();
        }

        double[] percent = new double[11];
        for (int d = 0; d < 11; d++) {
            percent[d] = (nPerHand[d] / totalHands) * 100;
        }

        for (int ii = 10; ii > 0; ii--) {
            System.out.printf("%1$-18s : %2$10d : %3$10.6f %% %n", handType[ii], nPerHand[ii], percent[ii]);
        }
        System.out.println();

    }

    public void extraCode() {
        Player p = new Player();

        if (p.bestHand().handStrength() == 2) {
            for (Card c : p.bestHand().usableCards()) {
                System.out.println(c);
            }
            System.out.println();
            System.out.println(p.bestHand().handStrength());
            System.out.println();
        }

        if (p.bestHand().handStrength() == 5) {
            for (Card c : p.bestHand().usableCards()) {
                System.out.println(c);
            }
            System.out.println();
            for (Card cc : p.bestHand().hand()) {
                System.out.println(cc);
            }
            System.out.println(p.bestHand().handStrength());
        }

        int[] highCard = new int[15];

        if (p.bestHand().handStrength() == 5) {
            highCard[p.bestHand().hand().getFirst().rankID()]++;
        }

        for (int hc = 0; hc < 15; hc++) {
            System.out.print(highCard[hc] + " | ");
        }
        System.out.println();

    }
}