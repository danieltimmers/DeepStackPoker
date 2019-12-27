package deepstack;

import java.util.*;

public class Game {
    private Dealer dealer;
    private PokerTable tbl;
    private LinkedList<Player> players;

    public Game() {
        this.dealer = new Dealer();
        tbl = new PokerTable(1, 2);
        players = new LinkedList<Player>();
    }

    public Dealer dealer() {
        return this.dealer;
    }

    public LinkedList<Player> players() {
        return players;
    }

    public PokerTable tbl() {
        return tbl;
    }

}