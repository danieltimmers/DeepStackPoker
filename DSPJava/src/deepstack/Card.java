package deepstack;

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