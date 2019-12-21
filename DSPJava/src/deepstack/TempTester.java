package deepstack;

public class TempTester {
    public static void main(String[] args) {

        Deck deck = new Deck();

        Card[] testCards = deck.getNCards(3);
        for (Card c : testCards) {
            System.out.println(c);
        }
    }
}