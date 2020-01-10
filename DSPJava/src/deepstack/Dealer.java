package deepstack;

import java.util.*;

public class Dealer {

    private LinkedList<Card> deck;
    private LinkedList<Card> discardPile;
    private LinkedList<Card> cardsDealt;

    public Dealer() {

        this.deck = buildDeck();
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
        Collections.shuffle(newDeck);
        return newDeck;
    }

    public LinkedList<Card> getDeck() {
        return this.deck;
    }

    private void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public void rotateButton(Game g) {
        Collections.rotate(g.players(), -1);
    }

    private void burnCard() {
        discardPile.add(deck.get(0));
        deck.remove(0);
    }

    private void giveCard(Player player) {
        player.acceptCard(deck.get(0));
        cardsDealt.add(deck.get(0));
        deck.remove(0);
    }

    private void giveCard(PokerTable tbl) {
        tbl.addCommunityCard(deck.get(0));
        cardsDealt.add(deck.get(0));
        deck.remove(0);
    }

    public void dealPockets(Game g) {
        for (int i = 0; i < 2; i++) {
            for (Player p : g.players()) {
                giveCard(p);
            }
        }
    }

    public void dealFlop(Game g) {
        burnCard();
        for (int i = 0; i < 3; i++) {
            giveCard(g.tbl());
        }
    }

    public void dealTurn(Game g) {
        burnCard();
        giveCard(g.tbl());
    }

    public void dealRiver(Game g) {
        burnCard();
        giveCard(g.tbl());
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

    public LinkedList<Card> cardsDealt() {
        return cardsDealt;
    }

    public void showCardsDealt() {
        for (Card c : cardsDealt) {
            System.out.println(c);
        }
    }

    public void showDiscardPile() {
        for (Card c : discardPile) {
            System.out.println(c);
        }
    }
}