package deepstack;

import java.util.*;

/**
 * This object is the deck of cards from which the game is played. The deck of
 * cards contains 52 unique cards. There are 13 ranks of each 4 suits.
 */
public class Deck {

    private final LinkedList<Card> deck;
    private int id = 0;

    public Deck() {
        this.deck = new LinkedList<Card>();
        for (final Suite s : Suite.values()) {
            for (final Rank r : Rank.values()) {
                final Card card = new Card(id, r, s);
                deck.add(card);
                id++;
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public Card getCard() {
        return this.deck.get(0);
    }

    public Card getCard(final int n) {
        return this.deck.get(n);
    }

    public Card[] getNCards(final int n) {
        final Card[] nCards = new Card[n];
        int i = 0;
        while (i < n) {
            nCards[i] = this.deck.get(i);
            i++;
        }
        return nCards;
    }

    public void addCard(Card c) {
        this.deck.add(c);
    }

    public void removeCard() {
        this.deck.remove(0);
    }

    public void buryOne() {
        Collections.rotate(this.deck, -1);
    }

    public void buryOne(final int n) {
        Collections.rotate(this.deck, -n);

    }

    public int cardsLeft() { 
        return this.deck.size();
    }

}
