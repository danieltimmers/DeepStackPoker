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

    private void showTableCards(Game g) {
        for (Player p : g.players()) {
            p.seeTableCards(g);
        }
    }

    public void dealFlop(Game g) {
        burnCard();
        for (int i = 0; i < 3; i++) {
            giveCard(g.tbl());
        }
        showTableCards(g);
    }

    public void dealTurn(Game g) {
        burnCard();
        giveCard(g.tbl());
        showTableCards(g);
    }

    public void dealRiver(Game g) {
        burnCard();
        giveCard(g.tbl());
        showTableCards(g);
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

    private int checkMaxDuplicity(Player p) {
        int rankDuplicityI;
        int rankDuplicity = 1;
        int highCard = 0;
        for (Card c : p.hand()) {
            rankDuplicityI = 1;
            if (highCard < c.rankID()) {
                highCard = c.rankID();
            }
            for (int i = (p.hand().indexOf(c) + 1); i < p.hand().size(); i++) {
                if (c.rankID() == p.hand().get(i).rankID()) {
                    rankDuplicityI++;
                }
            }
            if (rankDuplicityI == 3 && rankDuplicity == 2) {
                rankDuplicity = 32;
                p.bestHand().setHighCard(c.rankID());
            }
            if ((rankDuplicityI == 2 && rankDuplicity == 3) && (c.rankID() != p.bestHand().highCard())) {
                rankDuplicity = 32;
            }
            if (rankDuplicityI == 2 && rankDuplicity == 2) {
                rankDuplicity = 22;
                if (c.rankID() > p.bestHand().highCard()) {
                    p.bestHand().setHighCard(c.rankID());
                }
            }
            if (rankDuplicityI > rankDuplicity) {
                rankDuplicity = rankDuplicityI;
                p.bestHand().setHighCard(c.rankID());
            }
        }
        if (rankDuplicity == 1) {
            p.bestHand().setHighCard(highCard);
        }
        return rankDuplicity;
    }

    public void setHandStrength(Player p) {
        switch (checkMaxDuplicity(p)) {
        case 1:
            p.bestHand().setHandStrength(1);
            p.bestHand().setHandName("High Card");
            break;
        case 2:
            p.bestHand().setHandStrength(2);
            p.bestHand().setHandName("Pair");
            break;
        case 22:
            p.bestHand().setHandStrength(3);
            p.bestHand().setHandName("Two Pair");
            break;
        case 3:
            p.bestHand().setHandStrength(4);
            p.bestHand().setHandName("Three of a kind");
            break;
        case 32:
            p.bestHand().setHandStrength(7);
            p.bestHand().setHandName("Full House");
            break;
        case 4:
            p.bestHand().setHandStrength(8);
            p.bestHand().setHandName("Four of a kind");
            break;
        }
    }
}