package deepstack;

public class TempTester {
    public static void main(String[] args) {

        DeckOfCards deck2 = new DeckOfCards();

        System.out.println(deck2.getDeck());
        System.out.println(deck2.getCard(0));
        System.out.println(deck2.getLength());
        System.out.println(deck2.getCards(3));
        System.out.println(deck2.getDeck());

    }
}