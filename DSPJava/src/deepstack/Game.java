package deepstack;

import java.util.*;

public class Game {
    private Dealer dealer;
    private PokerTable tbl;
    private LinkedList<Player> players;

    public Game() {
        this.dealer = new Dealer();
        tbl = new PokerTable(1, 2);
        players = new LinkedList<Player>();
    }

    public Game(int nPlayers) {
        this.dealer = new Dealer();
        tbl = new PokerTable(1, 2);
        players = new LinkedList<Player>();

        for (int n = 0; n < nPlayers; n++) {
            this.players().add(new Player());
        }
    }

    public Dealer dealer() {
        return this.dealer;
    }

    public LinkedList<Player> players() {
        return players;
    }

    public PokerTable tbl() {
        return tbl;
    }

    public void runToShowDownQuiet() {

        this.dealer().dealPockets(this);
        this.dealer().dealFlop(this);
        this.dealer().dealTurn(this);
        this.dealer().dealRiver(this);

    }

    public void runToFlopQuiet() {
        this.dealer().dealPockets(this);
        this.dealer().dealFlop(this);
    }

    public void newRound() {
        this.tbl.resetTable();
        this.dealer.resetDeck();
        for (Player p : this.players) {
            p.resetHand();
        }
    }

    public void setPlayersBestHands() {
        for (Player p : this.players) {
            if (p.isActive()) {
                LinkedList<Card> usableCards = new LinkedList<Card>();
                usableCards.addAll(p.pocket());
                usableCards.addAll(tbl.communityCards());

                p.setBestHand(new BestHand(usableCards));

            }
        }
    }

    private void sortPlayersByHandStrength(LinkedList<Player> players) {
        Collections.sort(players, new PlayerHandStrengthComparator());
    }

    private void sortTopPlayersByHandType(LinkedList<Player> players, int handType) {
        switch (handType) {
        // For straight-flush, flush, and straight, only need to sort players by first
        // (high) card
        case (9):
        case (6):
        case (5):
            Collections.sort(players, new PlayerCardIndexComparator(0));
            break;

        // For Four of a kind and full house, sort players by fifth (last) card, then
        // first card
        case (8):
        case (7):
            Collections.sort(players, new PlayerCardIndexComparator(4));
            Collections.sort(players, new PlayerCardIndexComparator(0));
            break;

        // For three of a kind and two pair, sort players by fifth (last) card, then
        // fourth card, then first card
        case (4):
        case (3):
            Collections.sort(players, new PlayerCardIndexComparator(4));
            Collections.sort(players, new PlayerCardIndexComparator(3));
            Collections.sort(players, new PlayerCardIndexComparator(0));
            break;

        // for one pair, sort players by fifth, then fourth, then third, then first card
        case (2):
            for (int i = 4; i >= 2; i--) {
                Collections.sort(players, new PlayerCardIndexComparator(i));
            }
            Collections.sort(players, new PlayerCardIndexComparator(0));
            break;

        // for high card, sort players five times, starting from fifth card, 4th, 3rd,
        // 2nd, then the final sort will of the first card
        case (1):
            for (int i = 4; i >= 0; i--) {
                Collections.sort(players, new PlayerCardIndexComparator(i));
            }
            break;
        }
    }

    public void runShowDown() {

    }

    public void getWinner() {

        // create list of active players
        LinkedList<Player> activePlayers = new LinkedList<Player>();
        for (Player p : this.players) {
            if (p.isActive()) {
                activePlayers.add(p);
            }
        }

        // sort active players by their best hand strength (high to low)
        sortPlayersByHandStrength(activePlayers);

        // iterator for sorted active players
        ListIterator<Player> actIter = activePlayers.listIterator();

        // create list of Players who have same type of highest strength hands
        LinkedList<Player> topPlayers = new LinkedList<Player>();

        topPlayers.add(actIter.next());

        // if there are more than one player with equal highest hand strengths, add them
        // to topPlayers list
        if (actIter.hasNext()) {
            actIter.previous();
            int strengthDiff;
            while (actIter.nextIndex() < activePlayers.size() - 1) {
                strengthDiff = actIter.next().bestHand().handStrength() - actIter.next().bestHand().handStrength();
                if (strengthDiff > 0) {
                    break;
                }
                topPlayers.add(actIter.previous());
            }
        }

        if (topPlayers.size() > 1) {
            topPlayers = getBestTopPlayer(topPlayers); // DEBUG: STEP INTO

        }

        for (Player p : topPlayers) {
            System.out.println(p.getName() + " | " + p.bestHand().handName());
        }

    }

    private LinkedList<Player> getBestTopPlayer(LinkedList<Player> topPlayers) {

        int handType = topPlayers.getFirst().bestHand().handStrength();
        ListIterator<Player> topIter = topPlayers.listIterator();
        int rankDiff;

        // sort topPlayers for algorithm that chooses top player(s)
        sortTopPlayersByHandType(topPlayers, handType);
        switch (handType) {

        // algorithm for choosing top player(s) for cases: Straight-Flush, Flush, and
        // Straight
        case (9):
        case (6):
        case (5):
            while (topIter.nextIndex() < topPlayers.size() - 1) {
                rankDiff = topIter.next().bestHand().hand().getFirst().rankID()
                        - topIter.next().bestHand().hand().getFirst().rankID();
                if (rankDiff > 0) {
                    topIter.remove();
                    topIter.previous();
                    continue;
                }
                topIter.previous();
            }
            break;

        // algorithm for choosing top player(s) for cases: Four of a Kind, Full House,
        // Three of a kind, Two Pair, One Pair, and High Card
        default:
            if (topPlayers.getFirst().bestHand().handStrength() == 3) {
                // sort
            }

            while (topIter.nextIndex() < topPlayers.size() - 1) {

                ListIterator<Card> topHandA = topIter.next().bestHand().hand().listIterator();
                ListIterator<Card> topHandB = topIter.next().bestHand().hand().listIterator();

                while (topHandA.hasNext()) {
                    rankDiff = topHandA.next().rankID() - topHandB.next().rankID();
                    if (rankDiff == 0) {
                        continue;
                    }
                    if (rankDiff > 0) {
                        topIter.remove();
                        topIter.previous();
                        break;
                    }
                    if (rankDiff < 0) {
                        topIter.previous();
                        topIter.previous();
                        topIter.remove();
                        break;
                    }
                }
            }
            break;
        }
        return topPlayers;
    }
    /*
     * // in the case of quads, compare high card, then compare the kicker card (use
     * // getLast()), return the best player(s) case (8):
     * sortPlayersByHighCard(topPlayers); while (topIter.nextIndex() <
     * topPlayers.size() - 1) { rankDiff =
     * topIter.next().bestHand().hand().getFirst().rankID() -
     * topIter.next().bestHand().hand().getFirst().rankID(); if (rankDiff > 0) {
     * topIter.remove(); topIter.previous(); continue; } topIter.previous(); }
     * 
     * // if more than one player remains in topPlayers, go to start of the list and
     * // compare kicker (getLast()) if (topPlayers.size() > 1) { while
     * (topIter.hasPrevious()) { topIter.previous(); } while (topIter.nextIndex() <
     * topPlayers.size() - 1) { rankDiff =
     * topIter.next().bestHand().hand().getLast().rankID() -
     * topIter.next().bestHand().hand().getLast().rankID(); if (rankDiff > 0) {
     * topIter.remove(); topIter.previous(); continue; } if (rankDiff < 0) {
     * topIter.previous(); topIter.previous(); topIter.remove(); continue; }
     * topIter.previous(); } } break;
     * 
     * // in the case of full house, compare trips then pair (use getFirst() then //
     * getLast()), return the best player(s) case (7):
     * sortPlayersByHighCard(topPlayers); while (topIter.nextIndex() <
     * topPlayers.size() - 1) { rankDiff =
     * topIter.next().bestHand().hand().getFirst().rankID() -
     * topIter.next().bestHand().hand().getFirst().rankID(); if (rankDiff > 0) {
     * topIter.remove(); topIter.previous(); continue; } topIter.previous(); } // if
     * more than one player remains in topPlayers, go to start of the list and //
     * compare pairs (getLast()) if (topPlayers.size() > 1) { while
     * (topIter.hasPrevious()) { topIter.previous(); } while (topIter.nextIndex() <
     * topPlayers.size() - 1) { rankDiff =
     * topIter.next().bestHand().hand().getLast().rankID() -
     * topIter.next().bestHand().hand().getLast().rankID(); if (rankDiff > 0) {
     * topIter.remove(); topIter.previous(); continue; } if (rankDiff < 0) {
     * topIter.previous(); topIter.previous(); topIter.remove(); continue; }
     * topIter.previous(); } } break;
     * 
     * // in the case of three of a kind, compare trips then the two kicker cards
     * with // iterator // in the case of one pair, compare the pair then three
     * kicker cards // in the case of two pair, compare high pair, then second pair,
     * then kicker // card case (4): case (3): case (2):
     * sortPlayersByHighCard(topPlayers); while (topIter.nextIndex() <
     * topPlayers.size() - 1) {
     * 
     * ListIterator<Card> topHandA =
     * topIter.next().bestHand().hand().listIterator(); ListIterator<Card> topHandB
     * = topIter.next().bestHand().hand().listIterator();
     * 
     * while (topHandA.hasNext()) { rankDiff = topHandA.next().rankID() -
     * topHandB.next().rankID(); if (rankDiff == 0) { continue; } if (rankDiff > 0)
     * { topIter.remove(); topIter.previous(); break; } if (rankDiff < 0) {
     * topIter.previous(); topIter.previous(); topIter.remove(); break; } } } break;
     */
    // END getTopBestPlayer()
}
