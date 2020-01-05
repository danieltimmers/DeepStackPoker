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
    private boolean straightFlush;
    private boolean fourOK;
    private boolean fullHouse;
    private boolean flush;
    private boolean straight;
    private boolean threeOK;
    private boolean twoPair;
    private boolean pair;

    public BestHand(LinkedList<Card> usableCards) {
        this.hand = new LinkedList<Card>();
        this.usableCards = usableCards;
        buildBestHand();
        sortCardsByRank(this.usableCards);
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
        this.hand.addAll(hand);
    }

    public LinkedList<Card> hand() {
        return this.hand;
    }

    public LinkedList<Card> usableCards() {
        return this.usableCards;
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

    public void showHand() {
        for (Card c : this.hand) {
            System.out.println(c);
        }
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

        checkFlush();
        if (this.flush) {
            checkStraightFlush();
            if (this.straightFlush) {
                // sortCardsByRank(this.hand);
                if (this.hand.getFirst().rankID() == 14 && this.hand.getLast().rankID() == 10) {
                    this.handStrength = 10;
                    this.handName = "Royal Flush";
                    this.handSlang = "ROYAL FLUSH!";
                    return;
                } else {
                    this.handStrength = 9;
                    this.handName = "Straight Flush";
                    this.handSlang = hand.getFirst().rank().rank() + " high, Straight Flush";
                    return;
                }
            } else {
                checkDupes();
                if (this.fourOK) {
                    return;
                }
                if (this.fullHouse) {
                    return;
                }
                while (this.hand.size() > 5) {
                    this.hand.removeLast();
                }
                this.handStrength = 6;
                this.handName = "Flush";
                this.handSlang = "Flush" + hand.getFirst().suite().ofSuite();
                return;
            }

        } else {
            checkDupes();
            if (this.fourOK) {
                return;
            }
            if (this.fullHouse) {
                return;
            }
            checkStraight();
            if (this.straight) {
                this.handStrength = 5;
                this.handName = "Straight";
                this.handSlang = this.hand.getFirst().rank().rank() + " high, Straight";
                return;
            } else {
                if (this.threeOK) {
                    return;
                }
                if (this.twoPair) {
                    return;
                }
                if (this.pair) {
                    return;
                }
                this.hand.clear();
                this.hand.addAll(this.usableCards);
                while (this.hand.size() > 5) {
                    this.hand.removeLast();
                }
                this.handStrength = 1;
                this.handName = "High Card";
                return;
            }
        }
    }

    /**
     * checkFlush checks players usable cards and determines if there exists a flush
     * 
     * @param usableCards
     * @return
     */
    private void checkFlush() {
        this.flush = false;
        // sort players usableCards by suite for easiest flush detection
        sortCardsBySuite(this.usableCards);

        // Create list for possible flush hand
        LinkedList<Card> flushHand = new LinkedList<Card>();

        // iterate through usable cards to possibly add to flushHand
        ListIterator<Card> usableIter = this.usableCards.listIterator();

        // add first card from suite-sorted usableCards
        flushHand.add(this.usableCards.getFirst());

        // loop will break if enough cards have been checked to determine that flush is
        // not possible
        while ((usableIter.nextIndex() < this.usableCards.size() - 1)
                && (this.usableCards.size() - usableIter.nextIndex() > 5 - flushHand.size())) {
            if (usableIter.next().suiteID() == usableIter.next().suiteID()) {
                flushHand.add(usableIter.previous());
                continue;
            } else {
                // check early flush (index: 0-4)
                if (flushHand.size() >= 5) {
                    break;

                } else {
                    flushHand.clear();
                    flushHand.add(usableIter.previous());
                    continue;
                }
            }
        }
        // check flush if flush was not detected in "check early flush"
        if (flushHand.size() >= 5) {
            this.flush = true;
            this.hand.addAll(flushHand);
        }

    }

    public void checkStraightFlush() {
        this.straightFlush = false;

        // sort flush by rank for easiest straight detection
        sortCardsByRank(this.hand);

        // check for case of low ace straight. If case is possible, add ace to the end
        // of the hand list
        if (this.hand.getFirst().rankID() == 14 && this.hand.getLast().rankID() == 2) {
            this.hand.addLast(this.hand.getFirst());
        }

        // listIterator for flush stored in bestHand.hand() that is to be checked
        // for
        // straight
        ListIterator<Card> handIter = this.hand.listIterator();

        // create list for possible straigh that will be filled from
        // hand
        LinkedList<Card> straightHand = new LinkedList<Card>();

        straightHand.add(this.hand.getFirst());

        // while iterator hasNext && straight-flush is possible -> possibility changes
        // based on how many cards are left to check and straight.size()
        while ((handIter.nextIndex() < this.hand.size() - 1)
                && (this.hand.size() - handIter.nextIndex() > 5 - straightHand.size())) {
            // if each two card ranks differ by 1, add to straight
            if (handIter.next().rankID() - handIter.next().rankID() == 1) {
                straightHand.add(handIter.previous());
                // loop will stop once the highest straight is detected, if one exists
                if (straightHand.size() == 5) {
                    this.straightFlush = true;
                    this.hand.clear();
                    this.hand.addAll(straightHand);
                    break;
                } else {
                    continue;
                }
            } else { // if the two cards ranks do not differ by 1, check to see if last card is ace
                     // to complete a baby straight. loop will stop after this
                if (handIter.nextIndex() == hand.size()) {
                    if (handIter.previous().rankID() - handIter.previous().rankID() == 12) {
                        handIter.next();
                        straightHand.add(handIter.next());
                        this.straightFlush = true;
                        this.hand.clear();
                        this.hand.addAll(straightHand);
                        break;
                    } else {
                        break;
                    }
                } else {// if the two cards ranks do not differ by 1, and baby straight is not detected,
                        // start over check loop
                    straightHand.clear();
                    straightHand.add(handIter.previous());
                }
            }
        }
    }

    /**
     * This method is called only if a flush is detected in the first check of
     * setBestHand() method. If a flush is detected, "checkStraightFlush() will
     * determine if a straight flush exists"
     * 
     * @param bestHand
     * @return
     */
    public void checkStraight() {
        this.straight = false;

        // sort this.usableCards by rank for easiest straight detection
        sortCardsByRank(this.usableCards);

        // check for case of low ace straight. If case is possible, add ace to the end
        // of the usableCards list
        if (this.usableCards.getFirst().rankID() == 14 && this.usableCards.getLast().rankID() == 2) {
            this.usableCards.addLast(this.usableCards.getFirst());
        }

        // listIterator for seven cards stored in this.usableCards that is to be checked
        // for straight
        ListIterator<Card> usableIter = this.usableCards.listIterator();

        // create list for possible straigh that will be filled from
        // usableCards
        LinkedList<Card> straightHand = new LinkedList<Card>();

        straightHand.add(this.usableCards.getFirst());
        int rankDiff;
        // while iterator hasNext && straight is possible -> possibility changes
        // based on how many cards are left to check and straight.size()
        while ((usableIter.nextIndex() < this.usableCards.size() - 1)
                && (this.usableCards.size() - usableIter.nextIndex() > 5 - straightHand.size())) {

            rankDiff = usableIter.next().rankID() - usableIter.next().rankID();

            if (rankDiff == 0) {
                usableIter.previous();
                continue;
            }
            // if each two card ranks differ by 1, add to straight
            if (rankDiff == 1) {
                straightHand.add(usableIter.previous());
                // loop will stop once the highest straight is detected, if one exists
                if (straightHand.size() == 5) {
                    this.straight = true;
                    this.hand.clear();
                    this.hand.addAll(straightHand);
                    break;
                }
                continue;
            }
            // if rankDiff is 2 to Ace (last two cards of baby straight), set straight to
            // true and break out of loop
            if (rankDiff == -12) {
                straightHand.add(usableIter.previous());
                this.straight = true;
                this.hand.clear();
                this.hand.addAll(straightHand);
                break;
            } else { // if the rank difference is such that the next card does not contribute to a
                     // straight start over check loop
                straightHand.clear();
                straightHand.add(usableIter.previous());
            }
        }

        // if an ace was added to the end, remove it
        if (this.usableCards.getLast().rankID() == 14) {
            this.usableCards.removeLast();
        }

        return;
    }

    public void checkDupes() {

        this.fourOK = false;
        this.fullHouse = false;
        this.threeOK = false;
        this.twoPair = false;
        this.pair = false;

        sortCardsByRank(this.usableCards);

        // Lists for cards to hold 4/3/2/2 of a kind
        LinkedList<Card> dupeHandA = new LinkedList<Card>();
        LinkedList<Card> dupeHandB = new LinkedList<Card>();
        LinkedList<Card> dupeHandC = new LinkedList<Card>();

        ListIterator<Card> usableIter = usableCards.listIterator();

        // add first card
        dupeHandA.add(this.usableCards.getFirst());

        // loop should check all cards
        while (usableIter.nextIndex() < this.usableCards.size() - 1) {

            // if two cards have the same rank, add card to dupeHandA
            if (usableIter.next().rankID() == usableIter.next().rankID()) {
                dupeHandA.add(usableIter.previous());

                // if four of a kind, add the best kicker card, then quit checkDupes method and
                // return to buildBestHand()
                if (dupeHandA.size() == 4) {
                    if (this.usableCards.getFirst().rankID() > dupeHandA.getFirst().rankID()) {
                        dupeHandA.add(this.usableCards.getFirst());
                    } else {
                        usableIter.next();
                        dupeHandA.add(usableIter.next());
                    }

                    // set BestCard variables to four of a kind, then return to buildBestHand()
                    this.fourOK = true;
                    this.hand.clear();
                    this.hand.addAll(dupeHandA);
                    this.handStrength = 8;
                    this.handName = "Four of a Kind";
                    this.handSlang = "Quad " + this.hand.getFirst().rank().rank() + "'s";
                    return;
                }

            } else { // else, two compared cards are not the same rank

                if (dupeHandA.size() > 1) {
                    if (dupeHandB.size() > 1) {
                        if (dupeHandC.size() > 1) {
                            break;
                        } else {
                            dupeHandC.addAll(dupeHandA);
                        }
                    } else {
                        dupeHandB.addAll(dupeHandA);
                    }
                }
                dupeHandA.clear();
                dupeHandA.add(usableIter.previous());
            }
        }

        // if "B AND C" full house, set hand to trips+pair. set BestHand variables to
        // full house
        if ((dupeHandB.size() == 3 && dupeHandC.size() >= 2) || (dupeHandB.size() == 2 && dupeHandC.size() == 3)) {

            this.hand.clear();
            switch (dupeHandB.size()) {

            case (2):
                this.hand.addAll(dupeHandC);
                this.hand.addAll(dupeHandB);
                break;
            case (3):
                this.hand.addAll(dupeHandB);
                this.hand.add(dupeHandC.getFirst());
                this.hand.add(dupeHandC.getLast());
                break;
            }

            this.handStrength = 7;
            this.fullHouse = true;
            this.handName = "Full House";
            this.handSlang = this.hand.getFirst().rank().rank() + "'s full of " + this.hand.getLast().rank().rank()
                    + "'s";

            return;
        }

        // if "B AND A" full house, set hand to trips+pair. set BestHand variables to
        // full house
        if ((dupeHandB.size() == 3 && dupeHandA.size() >= 2) || (dupeHandB.size() == 2 && dupeHandA.size() == 3)) {

            this.hand.clear();
            switch (dupeHandB.size()) {

            case (2):
                this.hand.addAll(dupeHandA);
                this.hand.addAll(dupeHandB);
                break;
            case (3):
                this.hand.addAll(dupeHandB);
                this.hand.add(dupeHandA.getFirst());
                this.hand.add(dupeHandA.getLast());
                break;
            }

            this.handStrength = 7;
            this.fullHouse = true;
            this.handName = "Full House";
            this.handSlang = this.hand.getFirst().rank().rank() + "'s full of " + this.hand.getLast().rank().rank()
                    + "'s";

            return;
        }

        if (dupeHandA.size() == 3 || dupeHandB.size() == 3) {
            switch (dupeHandA.size()) {
            case (3):
                this.hand.addAll(dupeHandA);
                break;
            default:
                this.hand.addAll(dupeHandB);

            }
            this.handStrength = 4;
            this.threeOK = true;
            this.handName = "Three of a Kind";
            return;
        }

        if ((dupeHandA.size() == 2 && dupeHandB.size() == 2) || (dupeHandB.size() == 2 && dupeHandC.size() == 2)) {
            switch (dupeHandA.size()) {
            case (1):
                this.hand.addAll(dupeHandB);
                this.hand.addAll(dupeHandC);
                break;
            case (2):
                this.hand.addAll(dupeHandB);
                this.hand.addAll(dupeHandA);
                break;
            }
            this.handStrength = 3;
            this.twoPair = true;
            this.handName = "Two Pair";
            return;
        }

        if (dupeHandA.size() == 2 ^ dupeHandB.size() == 2) {
            switch (dupeHandA.size()) {
            case (1):
                this.hand.addAll(dupeHandB);
                break;
            case (2):
                this.hand.addAll(dupeHandA);
                break;
            }
            this.handStrength = 2;
            this.pair = true;
            this.handName = "One Pair";
            return;
        }
    }
}