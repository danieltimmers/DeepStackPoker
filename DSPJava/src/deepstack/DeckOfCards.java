package deepstack;

import java.util.*;

/**
 * This object is the deck of cards from which the game is played
 */
public class DeckOfCards {

    private ArrayList<String> deck;

    public DeckOfCards() {
        String[] suite = { " of Clubs", " of Hearts", " of Diamonds", " of Spades" };
        String[] rank = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
        this.deck = new ArrayList<String>();

        for (String i : suite) {
            for (String k : rank) {
                deck.add(k + i);
            }
        }
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

    public String getCard(int n) {
        return deck.get(n);
    }

    public void burn() {
        deck.remove(0);
    }

}
