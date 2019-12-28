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

    public void sortHand() {
        Collections.sort(hand, new RankComparator());
    }

    public class BestHand {

        private int handStrength;
        private String handName;
        private int highCard;

        /*
         * private int pairRankA; private int pairRankB; private int tripleRankA;
         * private int tripleRankB; private int fourOKRank; private int flushSuite;
         * private int straightRank;
         */

        public BestHand() {
            this.handStrength = 0;
            this.highCard = 0;
        }

        public void setHandStrength(int handStrength) {
            this.handStrength = handStrength;
        }

        public void setHandName(String handName) {
            this.handName = handName;
        }

        public void setHighCard(int highCard) {
            this.highCard = highCard;
        }

        public int handStrength() {
            return this.handStrength;
        }

        public String handName() {
            return this.handName;
        }

        public int highCard() {
            return this.highCard;
        }

        public String toString() {
            return "Hand: " + this.handName + "  |  High Card: " + this.highCard + "  |  Strength: "
                    + this.handStrength;
        }
    }
}