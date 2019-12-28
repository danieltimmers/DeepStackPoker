package deepstack;

//import java.util.*;

public class TempTester {
    public static void main(String[] args) {

        /*
         * Player player1 = new Player("Daniel", 100); Player player2 = new
         * Player("John", 100); Player player3 = new Player("Robert", 100); Player p4 =
         * new Player("P4", 100); Player p5 = new Player("P5", 100); Player p6 = new
         * Player("P6", 100); Player p7 = new Player("P7", 100);
         * 
         * Game g1 = new Game(); player1.joinGame(g1); player2.joinGame(g1);
         * player3.joinGame(g1); p4.joinGame(g1); p5.joinGame(g1); p6.joinGame(g1);
         * p7.joinGame(g1);
         */
        Game g1 = new Game();
        for (int ii = 0; ii < 10; ii++) {
            g1.players().add(new Player());
        }

        g1.dealer().dealPockets(g1);

        g1.dealer().dealFlop(g1);
        g1.dealer().dealTurn(g1);
        g1.dealer().dealRiver(g1);

        System.out.println();
        for (Player p : g1.players()) {
            g1.dealer().checkHandStrength(p);
            p.showHand();
            System.out.println("\n" + p.bestHand() + "\n");
        }
    }
}