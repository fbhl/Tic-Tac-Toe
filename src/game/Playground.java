package game;

import enums.Player;
import helper.ListHelper;

import java.util.*;

public class Playground {

    private final Map<Integer, Map<Integer, Player>> playground = new HashMap<>();
    private static final int SIZE = 3;

    public Playground() {
        fillPlaygroundInitial();
    }

    public boolean setCell(int row, int col, Player player) {
        if (this.isMoveValid(row, col)) {
            this.playground.get(row).put(col, player);
            return true;
        }
        return false;
    }

    public boolean emptyFieldsAvailable() {
        for (List<Player> row : getRows()) {
            if (row.contains(null)) {
                return true;
            }
        }
        return false;
    }

    public Player findMatchInPlayground() {
        List<List<List<Player>>> rowsColsAndDiagonals = new LinkedList<>();
        rowsColsAndDiagonals.add(getRows());
        rowsColsAndDiagonals.add(getCols());
        rowsColsAndDiagonals.add(getDiagonals());

        for (List<List<Player>> rowsColsOrDiagonals : rowsColsAndDiagonals) {
            if (findMatchIn(rowsColsOrDiagonals) != null) {
                return findMatchIn(rowsColsOrDiagonals);
            }
        }

        return null;
    }

    private Player findMatchIn(List<List<Player>> rowsColsOrDiagonals) {
        for (List<Player> rowColOrDiagonal : rowsColsOrDiagonals) {
            if (ListHelper.allValuesEqual(rowColOrDiagonal)) {
                return rowColOrDiagonal.get(0);
            }
        }
        return null;
    }

    private Player getPlayer(int row, int col) {
        return playground.get(row).get(col);
    }

    private boolean isMoveValid(int row, int col) {
        if (row <= 0 || row > SIZE || col <= 0 || col > SIZE) {
            return false;
        }
        return this.getPlayer(row, col) == null;
    }

    private List<List<Player>> getRows() {
        List<List<Player>> rows = new LinkedList<>();
        for (Map<Integer, Player> row : playground.values()) {
            rows.add(new LinkedList<>(row.values()));
        }
        return rows;
    }

    private List<List<Player>> getCols() {
        List<List<Player>> cols = new LinkedList<>();
        for (int colIndex = 1; colIndex <= SIZE; colIndex++) {
            cols.add(getCol(colIndex));
        }
        return cols;
    }

    private List<Player> getCol(int colIndex) {
        List<Player> col = new LinkedList<>();
        for (int rowIndex = 1; rowIndex <= SIZE; rowIndex++) {
            col.add(getPlayer(rowIndex, colIndex));
        }
        return col;
    }

    private List<List<Player>> getDiagonals() {
        List<List<Player>> diagonals = new LinkedList<>();
        diagonals.add(getDiagonal(true));
        diagonals.add(getDiagonal(false));
        return diagonals;
    }

    private List<Player> getDiagonal(boolean up) {
        List<Player> diagonal = new LinkedList<>();
        for (int rowIndex = 1; rowIndex <= SIZE; rowIndex++) {
            int colIndex = up ? (SIZE - rowIndex) + 1 : rowIndex;
            diagonal.add(getPlayer(rowIndex, colIndex));
        }
        return diagonal;
    }

    private String getSeparator() {
        return "\n" + "-".repeat((SIZE * 4) - 1) + "\n";
    }

    public void fillPlaygroundInitial() {
        for (int row = 1; row <= SIZE; row++) {
            Map<Integer, Player> list = new HashMap<>();
            for (int j = 1; j <= SIZE; j++) {
                list.put(j, null);
            }
            playground.put(row, list);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        Iterator<Map<Integer, Player>> iterator = playground.values().iterator();

        while (iterator.hasNext()) {
            Iterator<Player> it = iterator.next().values().iterator();
            while (it.hasNext()) {
                Player currentPlayer = it.next();
                if (currentPlayer == null) {
                    output.append("   ");
                } else {
                    output.append(" ").append(currentPlayer).append(" ");
                }
                if (it.hasNext()) {
                    output.append("|");
                }
            }
            if (iterator.hasNext()) {
                output.append(getSeparator());
            }
        }
        return output.toString();
    }

}
