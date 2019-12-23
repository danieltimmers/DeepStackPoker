package deepstack;

import java.util.*;

public class PokerTable {
    private Dealer dealer;
    private LinkedList<Player> players;
    private int pot;
    private int smallB;
    private int bigB;

    private int turnIndex;

    public PokerTable() {
        this.dealer = new Dealer("Bobby");

    }

}