package deepstack;

public enum Suite {
    CLUBS(0, " of Clubs"), DIAMONDS(1, " of Diamonds"), HEARTS(2, " of Hearts"), SPADES(3, " of Spades");

    private final int suiteID;
    private final String ofSuite;

    Suite(int suiteID, String ofSuite) {
        this.suiteID = suiteID;
        this.ofSuite = ofSuite;
    }

    public int suiteID() {
        return this.suiteID;
    }

    public String ofSuite() {
        return this.ofSuite;
    }
}