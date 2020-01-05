package deepstack;

//import java.util.*;

public class TempTesterMicro {
    public static void main(String[] args) {

        Game g5 = new Game(9);
        g5.runToShowDownQuiet();
        g5.setPlayersBestHands();
        for (Player p : g5.players()) {
            for (Card c : p.bestHand().usableCards()) {
                System.out.println(c);
            }
            System.out.println("\n" + p.bestHand().handStrength() + " | " + p.bestHand().handName() + "\n");
        }
    }
}