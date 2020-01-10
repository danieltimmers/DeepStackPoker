package deepstack;

import java.util.*;

class PlayerCardIndexComparator implements Comparator<Player> {

    private int index;

    public PlayerCardIndexComparator(int index) {
        this.index = index;
    }

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.bestHand().hand().get(this.index).rankID() < p2.bestHand().hand().get(this.index).rankID()) {
            return 1;
        } else {
            return -1;
        }
    }
}