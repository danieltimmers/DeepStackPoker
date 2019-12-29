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

    public Game(int nPlayers) {
        this.dealer = new Dealer();
        tbl = new PokerTable(1, 2);
        players = new LinkedList<Player>();

        for (int n = 0; n < nPlayers; n++) {
            this.players().add(new Player());
        }
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

    public void runToShowDownQuiet() {

        this.dealer().dealPockets(this);
        this.dealer().dealFlop(this);
        this.dealer().dealTurn(this);
        this.dealer().dealRiver(this);

        for (Player p : this.players()) {
            this.dealer().checkHandStrength(p);
        }
    }

    public void newRound() {
        this.tbl.resetTable();
        this.dealer.resetDeck();
        for (Player p : this.players) {
            p.resetHand();
        }
    }

}