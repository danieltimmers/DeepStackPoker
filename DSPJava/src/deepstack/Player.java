package deepstack;

import java.util.*;

public class Player {
    private String playerName;
    private LinkedList<Card> pocket;
    private int chipStack;
    private BestHand bestHand;
    private boolean active;

    public Player(String playerName, int chipStack) {
        this.playerName = playerName;
        this.chipStack = chipStack;
        this.pocket = new LinkedList<Card>();
        this.active = true;
    }

    public Player() {
        this.playerName = "default";
        this.chipStack = 100;
        this.pocket = new LinkedList<Card>();
        this.active = true;
    }

    public void joinGame(Game g) {
        g.players().add(this);
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean tf) {
        this.active = tf;
    }

    public LinkedList<Card> pocket() {
        return this.pocket;
    }

    public void resetHand() {
        this.pocket.clear();
        this.bestHand = null;
    }

    public void acceptCard(Card c) {
        this.pocket.add(c);
    }

    public void fold() {
        this.active = false;
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

    public void showPocket() {
        System.out.println(this.pocket.get(0) + "\n" + this.pocket.get(1) + "\n");
    }

    public void setBestHand(BestHand bestHand) {
        this.bestHand = bestHand;
    }

    public BestHand bestHand() {
        return this.bestHand;
    }

}