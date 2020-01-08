package deepstack;

import java.util.*;

class RankComparator implements Comparator<Card> {

    @Override
    public int compare(Card c1, Card c2) {
        if (c1.rankID() < c2.rankID()) {
            return 1;
        } else {
            return -1;
        }
    }
}