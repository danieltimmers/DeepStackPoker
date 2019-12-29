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

    public void checkHandStrength(Player p) {
        p.sortHand();
        int rankDuplicityA = 1;
        int rankDuplicityB = 1;
        int handStrength = 1;
        // int highCard = p.hand().get(0).rankID();

        for (int i = 0; i < p.hand().size() - 1; i++) {
            int rankDiff = p.hand().get(i).rankID() - p.hand().get(i + 1).rankID();
            if (rankDuplicityA == 1 && rankDiff != 0) {
                continue;
            }
            if (rankDiff != 0 && rankDuplicityA > 1 && rankDuplicityB > 1) {
                break;
            }
            if (rankDiff == 0) {
                rankDuplicityA++;
                if (rankDuplicityA > rankDuplicityB) {
                    // highCard = p.hand().get(i).rankID();
                }
                continue;
            }
            if (rankDuplicityA > 1 && rankDuplicityB == 1) {
                rankDuplicityB = rankDuplicityA;
                rankDuplicityA = 1;
            }

        }
        if (rankDuplicityA == 1 && rankDuplicityB == 1) {
            handStrength = 1;
        }
        if ((rankDuplicityB == 2 ^ rankDuplicityA == 2)) {
            handStrength = 2;
        }
        if ((rankDuplicityB == 2) && (rankDuplicityA == 2)) {
            handStrength = 3;
        }
        if ((rankDuplicityB == 3 && rankDuplicityA == 1) || (rankDuplicityB == 1 && rankDuplicityA == 3)) {
            handStrength = 4;
        }
        if (checkStraight(p)) {
            handStrength = 5;
        }
        if (checkFlush(p)) {
            handStrength = 6;
        }
        if ((rankDuplicityA >= 2 && rankDuplicityB == 3) || (rankDuplicityA == 3 && rankDuplicityB >= 2)) {
            handStrength = 7;
        }
        if (rankDuplicityB == 4 || rankDuplicityA == 4) {
            handStrength = 8;
        }
        if (checkStraightFlush(p)) {
            handStrength = 9;
        }
        setHandStrength(p, handStrength);

    }

    private boolean checkStraight(Player p) {

        boolean straight = false;
        LinkedList<Card> bestHand = new LinkedList<Card>();
        bestHand.add(p.hand().get(0));
        int nToStraight = 4;
        int rankDiff;
        // check for ace to consider low ace case
        if (p.hand().getFirst().rankID() == 14) {
            p.hand().addLast(p.hand().getFirst());
        }
        for (int i = 0; i < p.hand().size() - 1; i++) {
            // if straight is possible
            if (nToStraight <= (p.hand().size() - i)) {
                // check rank difference to nex card
                rankDiff = p.hand().get(i).rankID() - p.hand().get(i + 1).rankID();
                // if next rank differs by one or -12 for low ace case
                if (rankDiff == 1 || rankDiff == -12) {
                    bestHand.add(p.hand().get(i + 1));
                    nToStraight--;
                    if (nToStraight == 0) {
                        straight = true;
                        p.bestHand().setBestHand(bestHand);
                        break;
                    } else {
                        continue;
                    }
                }
                // if the next card is the same rank, do nothing but continue to next iteration
                if (rankDiff == 0) {
                    continue;
                }
                // if next card will kill current straight build,
                // reset nToStraight and bestHand with next card
                else {
                    bestHand.clear();
                    bestHand.add(p.hand().get(i + 1));
                    nToStraight = 4;
                }

            } else {
                break;
            }
        }
        // if low ace was added, remove it
        if (p.hand().getLast().rankID() == 14) {
            p.hand().removeLast();
        }
        return straight;
    }

    private boolean checkStraightFlush(Player p) {
        boolean straightFlush = false;
        for (int i = 0; i < p.bestHand().bestHand().size() - 1; i++) {
            if (p.bestHand().bestHand().get(i).suiteID() == p.bestHand().bestHand().get(i + 1).suiteID()) {
                if (i == 3) {
                    straightFlush = true;
                    break;
                }
            } else {
                break;
            }
        }
        return straightFlush;
    }

    private boolean checkFlush(Player p) {
        boolean flush = false;
        int spade = 0;
        int heart = 0;
        int diamond = 0;
        int club = 0;
        for (Card c : p.hand()) {
            switch (c.suiteID()) {
            case 0:
                club++;
                break;
            case 1:
                diamond++;
                break;
            case 2:
                heart++;
                break;
            case 3:
                spade++;
                break;
            }
        }
        if (club >= 5 || diamond >= 5 || heart >= 5 || spade >= 5) {
            flush = true;
        }

        return flush;
    }

    private void setHandStrength(Player p, int handStrength) {
        switch (handStrength) {
        case 1:
            p.bestHand().setHandStrength(1);
            p.bestHand().setHandName("High Card");
            break;
        case 2:
            p.bestHand().setHandStrength(2);
            p.bestHand().setHandName("Pair");
            break;
        case 3:
            p.bestHand().setHandStrength(3);
            p.bestHand().setHandName("Two Pair");
            break;
        case 4:
            p.bestHand().setHandStrength(4);
            p.bestHand().setHandName("Three of a kind");
            break;
        case 5:
            p.bestHand().setHandStrength(5);
            p.bestHand().setHandName("Straight");
            break;
        case 6:
            p.bestHand().setHandStrength(6);
            p.bestHand().setHandName("Flush");
            break;
        case 7:
            p.bestHand().setHandStrength(7);
            p.bestHand().setHandName("Full House");
            break;
        case 8:
            p.bestHand().setHandStrength(8);
            p.bestHand().setHandName("Four of a kind");
            break;
        case 9:
            if (p.bestHand().bestHand().get(0).rankID() == 14) {
                p.bestHand().setHandStrength(10);
                p.bestHand().setHandName("Royal-Flush");
            } else {
                p.bestHand().setHandStrength(9);
                p.bestHand().setHandName("Straight-Flush");
            }
            break;
        }

    }

}