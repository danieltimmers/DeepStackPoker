public class DeckOfCards {
    private String deckName;

    DeckOfCards() {
        this.deckName = "default";
    }

    public String getName() {
        return this.deckName;
    }

    public void setName(String deckName) {
        this.deckName = deckName;
    }
}
