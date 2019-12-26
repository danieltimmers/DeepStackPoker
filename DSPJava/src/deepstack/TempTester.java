package deepstack;

import java.util.*;

public class TempTester {
    public static void main(String[] args) {

        Player player1 = new Player("Daniel", 100);
        Player player2 = new Player("John", 100);
        Player player3 = new Player("Robert", 100);

        Game g1 = new Game();
        g1.players.add(player1);
        g1.players.add(player2);
        g1.players.add(player3);

        g1.dealer.dealPockets(g1);

        for (Player p : g1.players) {
            System.out.println(p.showPocket());
        }

        System.out.println("\nNext, show flop\n");

        g1.dealer.dealFlop(g1);

        g1.tbl.showCommunityCards();

        g1.dealer.dealTurn(g1);
        g1.dealer.dealRiver(g1);
        System.out.println("\nNext show all Community Cards\n");

        g1.tbl.showCommunityCards();

        System.out.println("\n\nTest Test\n");

        for (Player p : g1.players) {
            p.seeTableCards(g1);
        }

        g1.players.get(0).showHand();

    }
}