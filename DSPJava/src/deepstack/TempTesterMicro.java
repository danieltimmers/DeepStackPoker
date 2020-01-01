package deepstack;

//import java.util.*;

public class TempTesterMicro {
    public static void main(String[] args) {

        Game g5 = new Game(5);
        g5.runToShowDownQuiet();

        for (Player p : g5.players()) {
            p.showHand();
            System.out.println(p.bestHand());
            System.out.println();
        }

    }
}