package deepstack;

import java.util.*;

public class Player {
    private String playerName;
    private LinkedList<Card> pocket;
    private LinkedList<Card> hand;
    private int chipStack;

    public Player(String playerName, int chipStack) {
        this.playerName = playerName;
        this.chipStack = chipStack;
        this.pocket = new LinkedList<Card>();
        this.hand = new LinkedList<Card>();
    }

    public void acceptCard(Card c) {
        this.hand.add(c);
        if (this.pocket.size() <= 2) {
            this.pocket.add(c);
        }
    }

    public void seeTableCards(Game g) {
        for (Card c : g.tbl.communityCards()) {
            if (!c.equals(hand.get(0))) {
                this.hand.add(c);
            }
        }
    }

    public void fold() {

    }

    public int bet(int b) {
        if (checkStack(b)) {
            this.chipStack -= b;
            return b;
        } else {
            return 0;
        }
    }

    public int raise(int r) {
        return bet(r);
    }

    public void check() {

    }

    public void call() {

    }

    public boolean checkStack(int bet) {
        return bet < chipStack;
    }

    public int getChips() {
        return this.chipStack;
    }

    public String getName() {
        return this.playerName;
    }

    public String showPocket() {
        return this.pocket.get(0) + "\n" + this.pocket.get(1) + "\n";
    }

    public void showHand() {
        for (Card c : this.hand) {
            System.out.println(c);
        }
    }
}