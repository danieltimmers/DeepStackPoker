package deepstack;

private enum Suite {
    CLUBS(0, "of Clubs"), DIAMONDS(1, "of Diamonds"), HEARTS(2, "of Hearts"), SPADES(3, "of Spades");

    private final int suiteID;
    private final String ofSuite;

    Suite(int suiteID, String ofSuite) {
        this.suiteID = suiteID;
        this.ofSuite = ofSuite;
    }

    private int suiteID() {
        return this.suiteID;
    }

    private String ofSuite() {
        return this.ofSuite;
    }
}

private enum Rank {
    TWO(2, "Two"), THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"), SIX(6, "Six"), SEVEN(7, "Seven"),
    EIGHT(8, "Eight"), NINE(9, "Nine"), TEN(10, "Ten"), JACK(11, "Jack"), QUEEN(12, "Queen"), KING(13, "King"),
    ACE(14, "Ace");

    private final int rankID;
    private final String rank;

    Rank(int rankID, String rank) {
        this.rankID = rankID;
        this.rank = rank;
    }

    private int rankID() {
        return this.rankID;
    }

    private String rank() {
        return this.rank;
    }
}

public class Card {
    private int cardID;
    private int rankID;
    private int suiteID;
    private String rank;
    private String suite;

    public Card(int cardID, int rankID, int suiteID, String rank, String suite) {
        this.cardID = cardID;
        this.rankID = rankID;
        this.suiteID = suiteID;
        this.rank = rank;
        this.suite = suite;
    }

    public String getName() {
        return this.rank + this.suite;
    }

    public int getCardID() {
        return this.cardID;
    }

    public int getRankID() {
        return this.rankID;
    }

    public int getSuiteID() {
        return this.suiteID;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuite() {
        return this.suite;
    }

    @Override
    public String toString() {
        return "{" + " cardID='" + getCardID() + "'" + ", rankID='" + getRankID() + "'" + ", suiteID='" + getSuiteID()
                + "'" + ", rank='" + getRank() + "'" + ", suite='" + getSuite() + "'" + "}";
    }

}