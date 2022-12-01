import java.util.*;

public class Main {

    public static class Playground {

        private Map<Integer, Map<Integer, Player>> playground = new HashMap<>();
        private int size;
        private String rowSeperator;

        public Playground(int size) {
            this.size = size;
            for (int i = 1; i <= size; i++) {
                Map<Integer, Player> list = new HashMap<Integer, Player>();
                for (int j = 1; j <= size; j++) {
                    list.put(j, null);
                }
                playground.put(i, list);
            }

            StringBuilder separatorBuilder = new StringBuilder();
            separatorBuilder.append("\n");
            for (int i = 0; i < (size * 4) -1; i++) {
                separatorBuilder.append("-");
            }
            separatorBuilder.append("\n");
            this.rowSeperator = separatorBuilder.toString();
        }

        public void setCell(int row, int col, Player player) {
            this.playground.get(row).put(col, player);
        }

        public Player getPlayer(int row, int col) {
            return playground.get(row).get(col);
        }

        public boolean isMoveValid(int row, int col) {
            if (row <= 0 || row > size || col <= 0 || col > size) {
                return false;
            }
            return this.getPlayer(row, col) == null;
        }

        public boolean hasWinner() {
            Player potentialWinner = getWinner();
            if (potentialWinner == null) {
                return false;
            }
            return true;
        }

        public boolean gameOver() {
            return hasWinner() || !movesLeft();
        }

        private Player getWinner() {
            // ROWS
            for (int i = 1; i <= size; i++) {
               if (allSame(getValuesFromRow(i))) {
                   return getPlayer(i, 1);
                }
            }

            // COLS
            for (int i = 1; i <= size; i++) {
                if (allSame(getValuesFromCol(i))) {
                    return getPlayer(1, i);
                }
            }

            // DIAGONALS
            if (allSame(getValuesFromDownDiagonal())) {
                return getPlayer(1, 1);
            }
            if (allSame(getValuesFromUpDiagonal())) {
                return getPlayer(size, 1);
            }

            return null;
        }

        private boolean movesLeft() {
            Iterator<Map<Integer, Player>> it = playground.values().iterator();
            while (it.hasNext()) {
                if (new LinkedList<>(it.next().values()).contains(null)) {
                    return true;
                }
            }
            return false;
        }

        private List<Player> getValuesFromRow(int row) {
            return new LinkedList<>(playground.get(row).values());
        }

        private List<Player> getValuesFromCol(int col) {
            List<Player> result = new LinkedList<>();
            for (int i = 1; i <= size; i++) {
                result.add(getPlayer(i, col));
            }
            return result;
        }

        private List<Player> getValuesFromDownDiagonal() {
            List<Player> result = new LinkedList<>();
            for (int i = 1; i <= size; i++) {
                result.add(getPlayer(i, i));
            }
            return result;
        }

        private List<Player> getValuesFromUpDiagonal() {
            List<Player> result = new LinkedList<>();
            for (int i = 1; i <= size; i++) {
                result.add(getPlayer(i, (size - i) + 1));
            }
            return result;
        }


        private boolean allSame(List<Player> fields) {
            if (!fields.isEmpty()) {
                Player comparatorField = fields.get(0);
                for (Player field : fields) {
                    if (field == null || !field.equals(comparatorField)) {
                        return false;
                    }
                }
            }
            return true;
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
                        output.append(" " + currentPlayer + " ");
                    }
                    if (it.hasNext()) {
                        output.append("|");
                    }
                }
                if (iterator.hasNext()) {
                    output.append(rowSeperator);
                }
            }
            return output.toString();
        }
    }

    private enum Player {
        X, O;
    }

    public static void main(String[] args) {
        Playground playground = new Playground(3);

        Scanner in = new Scanner(System.in);
        Player currentPlayer = Player.X;
        System.out.println(playground);

        while (!playground.gameOver()) {
            System.out.println("Player " + currentPlayer + ":");
            int row = playground.size + 1;
            int col = playground.size + 1;
            do {
                System.out.print("  row: ");
                row = in.nextInt();
                System.out.print("  col: ");
                col = in.nextInt();
                if (!playground.isMoveValid(row, col)) {
                    System.out.println(" Move isn't valid. Please insert valid input:");
                }
            } while (!playground.isMoveValid(row, col));
            playground.setCell(row, col, currentPlayer);
            System.out.println(playground);
            currentPlayer = currentPlayer.equals(Player.X) ? Player.O : Player.X;
        }

        Player winner = playground.getWinner();

        if (winner == null) {
            System.out.println("GAME OVER! Nobody won :(");
        } else {
            System.out.println("Got an Winner: " + winner + " :)");
        }

        in.close();
    }
}