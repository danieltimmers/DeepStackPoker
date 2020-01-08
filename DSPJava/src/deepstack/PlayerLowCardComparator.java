package deepstack;

import java.util.*;

class PlayerLowCardComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.bestHand().hand().getLast().rankID() < p2.bestHand().hand().getLast().rankID()) {
            return 1;
        } else {
            return -1;
        }
    }
}