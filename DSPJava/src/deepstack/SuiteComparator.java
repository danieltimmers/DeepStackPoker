package deepstack;

import java.util.*;

class SuiteComparator implements Comparator<Card> {

    @Override
    public int compare(Card c1, Card c2) {
        if (c1.suiteID() > c2.suiteID()) {
            return 1;
        } else {
            return -1;
        }
    }
}