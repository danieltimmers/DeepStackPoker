package deepstack;

//import java.util.*;

public class TempTester {
    public static void main(String[] args) {

        Game gg = new Game(10);
        int[] nPerHand = new int[11];
        int nOfRounds = 1000000;
        double totalHands = nOfRounds * gg.players().size();
        String[] handType = { "0", "High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush",
                "Full House", "Four of a Kind", "Straight-Flush", "Royal Flush" };

        for (int i = 0; i < nOfRounds; i++) {
            gg.runToShowDownQuiet();
            for (Player p : gg.players()) {
                nPerHand[p.bestHand().handStrength()]++;
            }
            gg.newRound();
        }

        double[] percent = new double[11];
        for (int d = 0; d < 11; d++) {
            percent[d] = (nPerHand[d] / totalHands) * 100;
        }

        for (int i = 10; i > 0; i--) {
            System.out.printf("%1$-18s : %2$10d : %3$10.6f %% %n", handType[i], nPerHand[i], percent[i]);
        }
    }
}