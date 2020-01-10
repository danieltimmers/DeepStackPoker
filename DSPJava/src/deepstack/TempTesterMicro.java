package deepstack;

//import java.util.*;

public class TempTesterMicro {
    public static void main(String[] args) {

        Game g5 = new Game();

        Player dan = new Player("Dan", 100);
        dan.joinGame(g5);

        Player whit = new Player("Whitney", 100);
        whit.joinGame(g5);

        Player porter = new Player("Porter", 100);
        porter.joinGame(g5);

        Player ryder = new Player("Ryder", 100);
        ryder.joinGame(g5);

        Player scout = new Player("Scout", 100);
        scout.joinGame(g5);

        Player rhone = new Player("Rhone", 100);
        rhone.joinGame(g5);

        g5.runToShowDownQuiet();
        g5.setPlayersBestHands();

        // g5.sortPlayersByHandStr(g5.players());

        for (Player p : g5.players()) {
            System.out.println(p.getName());
            for (Card c : p.bestHand().hand()) {
                System.out.println(c);
            }
            System.out.println(p.bestHand().handStrength() + " | \t\t" + p.bestHand().handName() + "\n");
        }

        System.out.println("\n Top Hands:");
        g5.getWinner();

        // END MAIN
    }

    public void microExtraCode() {

        Player p = new Player();
        for (Card c : p.bestHand().usableCards()) {
            System.out.println(c);
        }
    }
}