package game;

import enums.GameStatus;
import enums.Player;

import java.util.Optional;
import java.util.Scanner;

public class Game {

    private final Playground playground;
    private GameStatus gameStatus;
    private Player currentPlayer;


    public Game() {
        playground = new Playground();
        currentPlayer = Player.X;
    }

    public void start() {
        while (!this.gameOver()) {
            setStatus(GameStatus.MOVE_EXECUTED);
        }
        setStatus(GameStatus.GAME_OVER);
    }

    private void setStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        executeStatus();
    }

    private boolean gameOver() {
        return this.getWinner().isPresent() || !this.playground.emptyFieldsAvailable();
    }

    private Optional<Player> getWinner() {
        return Optional.ofNullable(this.playground.findMatchInPlayground());
    }

    private void executeStatus() {
        printStatus();
        switch (this.gameStatus) {
            case INPUT_EXPECTED:
                int row = getInput(true);
                int col = getInput(false);
                executeMove(row, col);
                break;
            case MOVE_EXECUTED:
            case WRONG_INPUT:
                setStatus(GameStatus.INPUT_EXPECTED);
                break;
            case GAME_OVER:
                break;
        }
    }

    private void printStatus() {
        switch (this.gameStatus) {
            case MOVE_EXECUTED:
                System.out.println();
                System.out.println(playground);
                System.out.println("Player " + currentPlayer + ":");
                break;
            case INPUT_EXPECTED:
                System.out.println(" Please insert valid input:");
                break;
            case WRONG_INPUT:
                System.out.println(" Move isn't valid. ");
                break;
            case GAME_OVER:
                System.out.println();
                System.out.println("GAME OVER!");
                System.out.println(this.playground);
                System.out.println((getWinner().isEmpty() ? "Nobody" : "Player " + getWinner().get()) + " won.");
        }
    }

    private int getInput(boolean row) {
        Scanner console = new Scanner(System.in);
        System.out.print("  " + (row ? "row" : "col") + ": ");
        return console.nextInt();
    }

    private void executeMove(int row, int col) {
        if (this.playground.setCell(row, col, currentPlayer)) {
            changePlayer();
        } else {
            setStatus(GameStatus.WRONG_INPUT);
        }
    }

    private void changePlayer() {
        this.currentPlayer = currentPlayer.equals(Player.X) ? Player.O : Player.X;
    }




}
