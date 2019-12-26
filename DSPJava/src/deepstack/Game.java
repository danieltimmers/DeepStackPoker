package deepstack;

import java.util.*;

public class Game {
    public Dealer dealer;
    public PokerTable tbl;
    public LinkedList<Player> players;

    public Game() {
        dealer = new Dealer();
        tbl = new PokerTable(1, 2);
        players = new LinkedList<Player>();
    }

}