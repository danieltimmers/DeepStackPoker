package deepstack;

public class TempTester {
    public static void main(String[] args) {

        // DeckOfCards deck2 = new DeckOfCards();
        /*
         * System.out.println(deck2.getDeck()); System.out.println(deck2.getCard(0));
         * System.out.println(deck2.getLength()); System.out.println(deck2.getCards(3));
         * System.out.println(deck2.getDeck());
         */
        Deck deck = new Deck();
        System.out.println(deck.getLength());
        System.out.println(deck.getCard(0).getName());
        deck.shuffleDeck();
        System.out.println(deck.getCard(0).getName());
        deck.buryOne();
        System.out.println(deck.getCard(0).getName());
        System.out.println(deck.getCard(0).getRankID());
        System.out.println(deck.getCard(0).getSuiteID());
        System.out.println(deck.getCard(0).getCardID());

    }
}