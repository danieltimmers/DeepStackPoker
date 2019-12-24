package deepstack;

import java.util.*;

public class TempTester {
    public static void main(String[] args) {

        Player player1 = new Player("Robert", 100);
        Player player2 = new Player("John", 100);
        Player player3 = new Player("Daniel", 100);
        Player player4 = new Player("Whitney", 100);

        /*
         * LinkedList<Player> players = new LinkedList<Player>(); players.add(player1);
         * players.add(player2); players.add(player3); players.add(player4);
         */

        PokerTable pt = new PokerTable(1, 2);
        pt.addPlayer(player1);
        pt.addPlayer(player2);
        pt.addPlayer(player3);
        pt.addPlayer(player4);

        pt.dealer().dealPockets(pt.players());

        System.out.println(player1.showPocket());

        System.out.println(player1.bet(200));

    }
}