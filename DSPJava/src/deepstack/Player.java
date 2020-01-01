package deepstack;

import java.util.*;

public class Player {
    private String playerName;
    private LinkedList<Card> pocket;
    private LinkedList<Card> hand;
    private int chipStack;
    private BestHand bestHand;

    public Player(String playerName, int chipStack) {
        this.playerName = playerName;
        this.chipStack = chipStack;
        this.pocket = new LinkedList<Card>();
        this.hand = new LinkedList<Card>();
        this.bestHand = new BestHand();
    }

    public Player() {
        this.playerName = "default";
        this.chipStack = 100;
        this.pocket = new LinkedList<Card>();
        this.hand = new LinkedList<Card>();
        this.bestHand = new BestHand();
    }

    public void joinGame(Game g) {
        g.players().add(this);
    }

    public void resetHand() {
        this.pocket.clear();
        this.hand.clear();
        this.bestHand = new BestHand();
    }

    public void acceptCard(Card c) {
        this.hand.add(c);
        if (this.pocket.size() <= 2) {
            this.pocket.add(c);
        }
    }

    public void seeTableCards(Game g) {
        for (Card c : g.tbl().communityCards()) {
            if (!hand.contains(c)) {
                this.hand.add(c);
            }
        }
    }

    public void fold() {

    }

    public int bet(int b) {
        if (checkStack(b)) {
            this.chipStack -= b;
            return b;
        } else {
            return 0;
        }
    }

    public int raise(int r) {
        return bet(r);
    }

    public void check() {

    }

    public void call() {

    }

    public boolean checkStack(int bet) {
        return bet < chipStack;
    }

    public int getChips() {
        return this.chipStack;
    }

    public String getName() {
        return this.playerName;
    }

    public void showPocket() {
        System.out.println(this.pocket.get(0) + "\n" + this.pocket.get(1) + "\n");
    }

    public LinkedList<Card> hand() {
        return this.hand;
    }

    public BestHand bestHand() {
        return this.bestHand;
    }

    public void showHand() {
        for (Card c : this.hand) {
            System.out.println(c);
        }
    }

    public void sortCardsByRank(LinkedList<Card> cards) {
        Collections.sort(cards, new RankComparator());
    }

    public void sortCardsBySuite(LinkedList<Card> cards) {
        Collections.sort(cards, new SuiteComparator());
    }

    public class BestHand {

        private int handStrength;
        private String handName;
        private String handSlang;
        private Card highCardA;
        private Card highCardB;
        private LinkedList<Card> hand;

        public BestHand() {
            this.handStrength = 0;
            this.hand = new LinkedList<Card>();
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
            return "Hand: " + this.handName + "  |  High Card: " + this.highCardA + "  |  Strength: "
                    + this.handStrength;
        }
    }
}