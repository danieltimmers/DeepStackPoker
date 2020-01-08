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

    public void sortPlayersByHandStr(LinkedList<Player> players) {
        Collections.sort(players, new PlayerHandComparator());
    }

    public void sortPlayersByHighCard(LinkedList<Player> players) {
        Collections.sort(players, new PlayerHighCardComparator());
    }

    public void sortPlayersByLowCard(LinkedList<Player> players) {
        Collections.sort(players, new PlayerLowCardComparator());
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
        sortPlayersByHandStr(activePlayers);

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
            topPlayers = getBestTopPlayer(topPlayers);

        }

        for (Player p : topPlayers) {
            System.out.println(p.getName() + " | " + p.bestHand().handName());
        }

    }

    public LinkedList<Player> getBestTopPlayer(LinkedList<Player> topPlayers) {

        ListIterator<Player> topIter = topPlayers.listIterator();
        int rankDiff;
        switch (topPlayers.getFirst().bestHand().handStrength()) {

        // in the case comparing multiple straights, check high card (use getFirst()),
        // return the best player(s) as winner
        case (9):
        case (5):
            sortPlayersByHighCard(topPlayers);
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

        default:
            sortPlayersByLowCard(topPlayers);
            sortPlayersByHighCard(topPlayers);
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
