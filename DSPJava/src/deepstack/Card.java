package deepstack;

public class Card {

    private final int ID;
    private final Rank R;
    private final Suite S;

    public Card(int ID, Rank R, Suite S) {
        this.ID = ID;
        this.R = R;
        this.S = S;
    }

    public Rank rank() {
        return this.R;
    }

    public Suite suite() {
        return this.S;
    }

    public int rankID() {
        return this.R.rankID();
    }

    public int suiteID() {
        return this.S.suiteID();
    }

    public int ID() {
        return this.ID;
    }

    public String name() {
        return this.R.rank() + this.S.ofSuite();
    }

    public String toString() {
        return name();
    }
}