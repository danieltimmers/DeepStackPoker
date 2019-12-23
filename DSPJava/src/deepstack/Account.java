package deepstack;

import java.util.*;

public class Account {
    private final String ACCOUNT_NAME;
    private String password;
    private double wallet;
    private LinkedList<Player> inGamePlayers;

    public Account(final String ACCOUNT_NAME, String password) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
        this.inGamePlayers = new LinkedList<Player>();
        this.password = password;
    }

    public void newGame(double buyIn) {
        if (checkFunds(buyIn)) {
            this.wallet -= buyIn;
            Player newPlayer = new Player(this.ACCOUNT_NAME + "(1)", (int) buyIn);
            inGamePlayers.add(newPlayer);
        } else {
            System.out.println("Insufficient money in WALLET. Add more funds and try again.");
        }
    }

    /**
     * returns true if Account.wallet has enough funds to join game
     * 
     * @param tryMoney
     * @return
     */
    private boolean checkFunds(double tryMoney) {
        return tryMoney < this.wallet;
    }

}