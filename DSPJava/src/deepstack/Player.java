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
    }

    public void acceptCard(Card c) {
        this.pocket.add(c);
        this.hand.add(c);
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
}