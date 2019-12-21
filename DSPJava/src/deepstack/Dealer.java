package deepstack;

import java.util.*;

public class Dealer {

    private final Deck DECK;
    private final String NAME;
    private LinkedList<Card> discardPile;
    private LinkedList<Card> cardsDealt;

    public Dealer(String name) {
        this.DECK = new Deck();
        this.NAME = name;
        this.discardPile = new LinkedList<Card>();
    }

    public int discarded() {
        return this.discardPile.size();
    }

    public Card getDiscard() {
        return this.discardPile.get(0);
    }

    public Card getDiscard(int i) {
        return this.discardPile.get(i);
    }

    public void burnCard() {
        discardPile.add(DECK.getCard());
        DECK.removeCard();
    }

    public Card drawCard() {
        return this.DECK.getCard();
    }

    public Card[] drawNCards(int n) {
        return this.DECK.getNCards(n);
    }

    public void callWinner() {

    }

    public void resetDeck() {
        for (Card c : discardPile) {
            DECK.addCard(c);
        }
        discardPile.clear();
        DECK.shuffleDeck();
    }

    public String getName() {
        return this.NAME;
    }

}