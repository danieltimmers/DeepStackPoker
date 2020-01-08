package deepstack;

import java.util.*;

class PlayerHandComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.bestHand().handStrength() < p2.bestHand().handStrength()) {
            return 1;
        } else {
            return -1;
        }
    }
}