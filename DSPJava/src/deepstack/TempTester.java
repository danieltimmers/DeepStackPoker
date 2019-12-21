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
    }
}