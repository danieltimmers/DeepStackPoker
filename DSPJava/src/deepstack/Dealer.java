package deepstack;

import java.util.*;

public class Dealer {

    private LinkedList<Card> deck;
    private final String NAME;
    private LinkedList<Card> discardPile;
    private LinkedList<Card> cardsDealt;

    public Dealer(String name) {
        this.deck = buildDeck();
        this.NAME = name;
        this.discardPile = new LinkedList<Card>();
        this.cardsDealt = new LinkedList<Card>();
    }

    private LinkedList<Card> buildDeck() {
        LinkedList<Card> newDeck = new LinkedList<Card>();
        int id = 0;
        for (final Suite s : Suite.values()) {
            for (final Rank r : Rank.values()) {
                final Card card = new Card(id, r, s);
                newDeck.add(card);
                id++;
            }
        }
        shuffleDeck();
        return newDeck;
    }

    private void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public void burnCard() {
        discardPile.add(deck.get(0));
        deck.remove(0);
    }

    private void giveCard(Player player) {
        player.acceptCard(deck.get(0));
        cardsDealt.add(deck.get(0));
        deck.remove(0);
    }

    public void dealPockets(LinkedList<Player> players) {
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                giveCard(p);
            }
        }
    }

    public void callWinner() {

    }

    public void resetDeck() {
        for (Card c : discardPile) {
            deck.add(c);
        }
        for (Card cc : cardsDealt) {
            deck.add(cc);
        }
        discardPile.clear();
        cardsDealt.clear();
        shuffleDeck();
    }

    public String getName() {
        return this.NAME;
    }

}