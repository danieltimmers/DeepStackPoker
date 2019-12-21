package deepstack;

public class TempTester {
    public static void main(String[] args) {

        Deck deck = new Deck();

        System.out.println(deck.getCard(0));
        System.out.println(deck.getCard(0).rank());
        System.out.println(deck.getCard(0).ID());
        System.out.println(deck.getCard(6));
    }
}