package deepstack;

import java.util.*;

/**
 * This object is the deck of cards from which the game is played. The deck of
 * cards contains 52 unique cards. There are 13 ranks of each 4 suits.
 */
public class DeckOfCards {

    private LinkedList<String> deck;

    public DeckOfCards() {
        String[] suite = { " of Spades", " of Hearts", " of Diamonds", " of Clubs" };
        String[] rank = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
        this.deck = new LinkedList<String>(); // TODO determine best data structure for 52 card deck

        for (String i : suite) {
            for (String k : rank) {
                deck.add(k + i); // TODO Not sure if this is BP for string concantenation
            }
        }
        shuffleDeck();
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

    public LinkedList<String> getDeck() {
        return this.deck;
    }

    public String getCard(int n) {
        return deck.get(n);
    }

    public LinkedList<String> getCards(int n) {
        LinkedList<String> nCards = new LinkedList<String>();
        int i = 0;
        while (i < n) {
            nCards.add(this.deck.get(i));
            i++;
        }
        return nCards;
    }

    /**
     * removes the top card in the deck and shifts the remaining cards to the left
     * by 1
     */
    public void burn() {
        deck.remove(0);
    }

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
