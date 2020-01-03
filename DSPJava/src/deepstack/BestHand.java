package deepstack;

import java.util.*;

public class BestHand {

    private int handStrength;
    private String handName;
    private String handSlang;
    private Card highCardA;
    private Card highCardB;
    private LinkedList<Card> hand;
    private LinkedList<Card> usableCards;

    public BestHand(LinkedList<Card> usableCards) {
        this.usableCards = usableCards;
        buildBestHand();
    }

    public BestHand(int handStrength, LinkedList<Card> hand) {
        this.handStrength = handStrength;
        this.hand = hand;
    }

    public BestHand(int handStrength, String handName, String handSlang, Card highCardA, Card highCardB,
            LinkedList<Card> hand) {
        this.handStrength = handStrength;
        this.handName = handName;
        this.handSlang = handSlang;
        this.highCardA = highCardA;
        this.highCardB = highCardB;
        this.hand = hand;
    }

    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    public LinkedList<Card> hand() {
        return this.hand;
    }

    public void setHandStrength(int handStrength) {
        this.handStrength = handStrength;
    }

    public void setHandName(String handName) {
        this.handName = handName;
    }

    public void setHandSlang(String handSlang) {
        this.handSlang = handSlang;
    }

    public void setHighCardA(Card card) {
        this.highCardA = card;
    }

    public void setHighCardB(Card card) {
        this.highCardB = card;
    }

    public int handStrength() {
        return this.handStrength;
    }

    public String handName() {
        return this.handName;
    }

    public String handSlang() {
        return this.handSlang;
    }

    public Card highCardA() {
        return this.highCardA;
    }

    public Card highcardB() {
        return this.highCardB;
    }

    public String toString() {
        return "Hand: " + this.handName + "  |  High Card: " + this.highCardA + "  |  Strength: " + this.handStrength;
    }

    public void sortCardsByRank(LinkedList<Card> cards) {
        Collections.sort(cards, new RankComparator());
    }

    public void sortCardsBySuite(LinkedList<Card> cards) {
        Collections.sort(cards, new SuiteComparator());
    }

    private void buildBestHand() {

        if (checkFlush()) {
            if (checkStraightFlush()) {
                if (hand.getFirst().rankID() == 14) {
                    handStrength = 10;
                    handName = "Royal Flush";
                    handSlang = "ROYAL FLUSH!";
                    highCardA = hand.getFirst();
                } else {
                    handStrength = 9;
                    handName = "Straight Flush";
                    handSlang = hand.getFirst().rank().rank() + " high, Straight Flush";
                    highCardA = hand.getFirst();

                }

            } else {
                checkDupes();
            }
        }

    }

    /**
     * checkFlush checks players usable cards and determines if there exists a flush
     * 
     * @param usableCards
     * @return
     */
    private boolean checkFlush() {
        boolean flush = false;
        // sort players usableCards by suite for easiest flush detection
        sortCardsBySuite(usableCards);

        // Create list for possible flush hand
        LinkedList<Card> flushHand = new LinkedList<Card>();

        // iterate through usable cards to possibly add to flushHand
        ListIterator<Card> usableIter = usableCards.listIterator();

        // add first card from suite-sorted usableCards
        flushHand.add(usableCards.getFirst());

        // loop will break if enough cards have been checked to determine that flush is
        // not possible
        while (usableIter.hasNext() && (usableCards.size() - usableIter.nextIndex() > 5 - flushHand.size())) {
            if (usableIter.next().suiteID() == usableIter.next().suiteID()) {
                flushHand.add(usableIter.previous());
                continue;
            } else {
                if (flushHand.size() >= 5) {
                    flush = true;
                    hand = flushHand;

                } else {
                    flushHand.clear();
                    flushHand.add(usableIter.previous());
                    continue;
                }
            }
        }
        return flush;
    }

    /**
     * This method is called only if a flush is detected in the first check of
     * setBestHand() method. If a flush is detected, "checkStraightFlush() will
     * determine if a straight flush exists"
     * 
     * @param bestHand
     * @return
     */
    public boolean checkStraightFlush() {
        boolean straightFlush = false;

        // sort flush by rank for easiest straight detection
        sortCardsByRank(hand);

        // check for case of low ace straight. If case is possible, add ace to the end
        // of the list
        if (hand.getFirst().rankID() == 14 && hand.getLast().rankID() == 2) {
            hand.addLast(hand.getFirst());
        }

        // listIterator for flush stored in bestHand.hand() that is to be checked for
        // straight
        ListIterator<Card> handIter = hand.listIterator();

        // create list for possible straighFlush that will be filled from
        // p.bestHand().hand()
        LinkedList<Card> straightFlushHand = new LinkedList<Card>();

        straightFlushHand.add(hand.getFirst());

        // while iterator hasNext && straight-flush is possible -> possibility changes
        // based on how many cards are left to check and straightFlush.size()
        while (handIter.hasNext() && (hand.size() - handIter.nextIndex() > 5 - straightFlushHand.size())) {
            // if each two card ranks differ by 1, add to straightFlush
            if (handIter.next().rankID() - handIter.next().rankID() == 1) {
                straightFlushHand.add(handIter.previous());
                // loop will stop once the highest straight is detected, if one exists
                if (straightFlushHand.size() == 5) {
                    straightFlush = true;
                    hand = straightFlushHand;
                    break;
                } else {
                    continue;
                }
            } else { // if the two cards ranks do not differ by 1, check to see if last card is ace
                     // to complete a baby straight. loop will stop after this
                if (handIter.nextIndex() == hand.size()) {
                    if (handIter.previous().rankID() - handIter.previous().rankID() == 12) {
                        handIter.next();
                        straightFlushHand.add(handIter.next());
                        straightFlush = true;
                        hand = straightFlushHand;
                        break;
                    } else {
                        break;
                    }
                } else {// if the two cards ranks do not differ by 1, and baby straight is not detected,
                        // start over check loop
                    straightFlushHand.clear();
                    straightFlushHand.add(handIter.previous());
                }
            }
        }

        return straightFlush;
    }

    public boolean checkDupes() {

        boolean highDupe = false;
        boolean lowDupe = false;

        sortCardsByRank(usableCards);

        // Lists for cards to hold 4/3/2/2 of a kind
        LinkedList<Card> dupeHandA = new LinkedList<Card>();
        LinkedList<Card> dupeHandB = new LinkedList<Card>();

        ListIterator<Card> usableIter = usableCards.listIterator();

        // add first card
        dupeHandA.add(usableCards.getFirst());

        // loop should check all cards
        while (usableIter.hasNext()) {
            // if two cards have the same rank, add card to dupeHandA
            if (usableIter.next().rankID() == usableIter.next().rankID()) {
                dupeHandA.add(usableIter.previous());
                // if four of a kind, add the best kicker card
                if (dupeHandA.size() == 4) {
                    if (usableCards.getFirst().rankID() > dupeHandA.getFirst().rankID()) {
                        dupeHandA.add(usableCards.getFirst());
                        highDupe = true;
                        hand = dupeHandA;
                        break;
                    } else {
                        dupeHandA.add(usableIter.next());
                        highDupe = true;
                        hand = dupeHandA;
                        break;
                    }
                }
                // if full house, set hand to full house
                if ((dupeHandA.size() == 3 && dupeHandB.size() == 2)
                        || (dupeHandA.size() == 2 && dupeHandB.size() == 3)) {
                    highDupe = true;
                    hand = dupeHandA;
                    hand.addAll(dupeHandB);
                    break;
                }
            } else {
                if (dupeHandA.size() <= dupeHandB.size()) {

                }
                if (dupeHandB.size() < 2 && dupeHandA.size() > 1) {
                    dupeHandB = dupeHandA;
                    dupeHandA.clear();
                    dupeHandA.add(usableIter.previous());
                    continue;
                }
                if (dupeHandA.size() < 2) {
                    dupeHandA.clear();
                    dupeHandA.add(usableIter.previous());
                }

            }
        }

        return highDupe || lowDupe;
    }

    public boolean checkStraight(LinkedList<Card> hand) {
        boolean straight = false;
        return straight;
    }

}