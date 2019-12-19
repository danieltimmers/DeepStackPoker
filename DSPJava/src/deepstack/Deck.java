package deepstack;

import java.util.*;

/**
 * This object is the deck of cards from which the game is played. The deck of
 * cards contains 52 unique cards. There are 13 ranks of each 4 suits.
 */
public class Deck {

    private final String[] SUITES = { " of Clubs", " of Diamonds", " of Hearts", " of Spades" };
    private final String[] RANK = { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
            "Queen", "King", "Ace" };
    private final LinkedList<Card> deck;

    public Deck() {
        this.deck = this.deckBuilder();
    }

    private LinkedList<Card> deckBuilder() {

        LinkedList<Card> builderDeck = new LinkedList<Card>();
        int cardID = 1;
        int rankID;
        int suiteID = 1;
        for (String i : this.SUITES) {
            rankID = 2;
            for (String k : this.RANK) {
                Card aCard = new Card(cardID, rankID, suiteID, k, i);
                cardID++;
                rankID++;
                builderDeck.add(aCard);
            }
            suiteID++;
        }
        Collections.shuffle(builderDeck);
        return builderDeck;
    }

    /**
     * this method contains the shuffle() method under the Collections Object
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public int getLength() {
        return this.deck.size();
    }

    public Card getCard(int n) {
        return this.deck.get(n);
    }

    /*
     * public LinkedList<String> getCards(int n) { LinkedList<String> nCards = new
     * LinkedList<String>(); int i = 0; while (i < n) {
     * nCards.add(this.deck.get(i)); i++; } return nCards; }
     */

    /**
     * removes the top card in the deck and shifts the remaining cards to the left
     * by 1
     */
    /*
     * public void burn() { deck.remove(0); }
     */
    /**
     * moves the top card of the deck to the bottom and shifts the other cards
     * appropriately (card at index 1 is now 0 etc.)
     */
    public void buryOne() {
        Collections.rotate(this.deck, -1);
    }

    public void buryOne(int n) {
        Collections.rotate(this.deck, -n);

    }

}
