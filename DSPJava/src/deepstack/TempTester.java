package deepstack;

public class TempTester {
    public static void main(String[] args) {

        Dealer dealer1 = new Dealer("Bob");

        System.out.println(dealer1.drawCard());
        dealer1.burnCard();
        System.out.println(dealer1.getDiscard());
        dealer1.burnCard();
        System.out.println(dealer1.discarded());
        dealer1.resetDeck();

        Deck sDeck = new Deck();
        Card[] cards = new Card[3];
        cards[0] = sDeck.getCard();
        cards[1] = sDeck.getCard();
        cards[2] = sDeck.getCard();

        cards[0] = sDeck.getCard(15);

        System.out.println(cards[0]);
    }
}