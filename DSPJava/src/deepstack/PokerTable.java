package deepstack;

import java.util.*;

public class PokerTable {
    private Dealer dealer;
    private LinkedList<Player> players;
    private int pot;
    private int smallB;
    private int bigB;

    public PokerTable(int smallB, int bigB) {
        this.dealer = new Dealer();
        this.players = new LinkedList<Player>();
        this.pot = 0;
        this.smallB = smallB;
        this.bigB = bigB;

    }

    public void rotateButton() {
        Collections.rotate(players, -1);
    }
}