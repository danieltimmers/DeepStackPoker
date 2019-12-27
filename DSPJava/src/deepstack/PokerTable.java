package deepstack;

import java.util.*;

public class PokerTable {
    private LinkedList<Card> communityCards;
    private int pot;
    private int smallB;
    private int bigB;

    public PokerTable(int smallB, int bigB) {

        this.communityCards = new LinkedList<Card>();
        this.pot = 0;
        this.smallB = smallB;
        this.bigB = bigB;
    }

    public int getPot() {
        return this.pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getSmallB() {
        return this.smallB;
    }

    public void setSmallB(int smallB) {
        this.smallB = smallB;
    }

    public int getBigB() {
        return this.bigB;
    }

    public void setBigB(int bigB) {
        this.bigB = bigB;
    }

    public LinkedList<Card> communityCards() {
        return this.communityCards;
    }

    public void addCommunityCard(Card c) {
        this.communityCards.add(c);
    }

    public void showCommunityCards() {
        for (Card c : communityCards) {
            System.out.println(c);
        }
    }
}