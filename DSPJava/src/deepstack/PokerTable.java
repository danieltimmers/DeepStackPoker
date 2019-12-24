package deepstack;

import java.util.*;

public class PokerTable {
    private Dealer dealer;
    private LinkedList<Player> players;
    private LinkedList<Card> communityCards;
    private int pot;
    private int smallB;
    private int bigB;

    public PokerTable(int smallB, int bigB) {
        this.dealer = new Dealer();
        this.players = new LinkedList<Player>();
        this.communityCards = new LinkedList<Card>();
        this.pot = 0;
        this.smallB = smallB;
        this.bigB = bigB;
    }

    public void rotateButton() {
        Collections.rotate(players, -1);
    }

    public void addCommunityCard(Card c) {
        this.communityCards.add(c);
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void removePlayer(int i) {
        this.players.remove(i);
    }

    public Dealer dealer() {
        return this.dealer;
    }

    public LinkedList<Player> players() {
        return this.players;
    }
}