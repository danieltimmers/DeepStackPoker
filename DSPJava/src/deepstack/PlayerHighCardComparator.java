package deepstack;

import java.util.*;

class PlayerHighCardComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.bestHand().hand().getFirst().rankID() < p2.bestHand().hand().getFirst().rankID()) {
            return 1;
        } else {
            return -1;
        }
    }
}