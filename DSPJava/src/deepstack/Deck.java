package deepstack;

import java.util.*;

/**
 * This object is the deck of cards from which the game is played. The deck of
 * cards contains 52 unique cards. There are 13 ranks of each 4 suits.
 */
public class Deck {

    private LinkedList<Card> deck;
    private int n = 0;

    public Deck() {
        this.deck = new LinkedList<Card>();
        for (Suite s : Suite.values()) {
            for (Rank r : Rank.values()) {
                Card card = new Card(n, r, s);
                deck.add(card);
                n++;
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public Card[] getNCards(int n) {
        Card[] nCards = new Card[n];
        int i = 0;
        while (i < n) {
            nCards[i] = this.deck.get(i);
            i++;
        }
        return nCards;
    }

    public void buryOne() {
        Collections.rotate(this.deck, -1);
    }

    public void buryOne(int n) {
        Collections.rotate(this.deck, -n);

    }

}
