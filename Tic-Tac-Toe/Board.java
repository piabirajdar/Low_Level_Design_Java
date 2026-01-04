public class Board {
    private static final int SIZE = 3;
    private final CellState[][] grid;
    private int moveCount;

    public Board() {
        grid = new CellState[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = CellState.EMPTY;
            }
        }
        moveCount = 0;
    }

    public boolean isValidMove(int row, int col) {
        return row>=0 && row < grid.length && col >=0 && col < grid[0].length && grid[row][col] == CellState.EMPTY;
    }

    public void placeMark(int row, int col, Mark mark) {
        if (!isValidMove(row, col)) {
            throw new IllegalArgumentException("Invalid move");
        }

        grid[row][col] = mark == Mark.X ? CellState.X : CellState.O;
        moveCount++;
    }

    public boolean isFull() {
        return moveCount == SIZE * SIZE;
    }

    public boolean checkWin(int row, int col, Mark mark){
        CellState target = (mark == Mark.X) ? CellState.X : CellState.O;
        int[][] directions = {
            {1, 0},   // vertical
            {0, 1},   // horizontal
            {1, 1},   // diagonal \
            {1, -1}   // diagonal /
        };


        for(int[] dir : directions) {
            int count = 1;
            count += countInDirection(row, col, dir[0], dir[1], target);
            count+= countInDirection(row, col, -dir[0], -dir[1], target)

            if(count > SIZE){
                return true
            }
        }
        return false;
    }

    public int countInDirection(int row, int col, int dr, int dc, CellState target) {
        int count = 0;
        int newdr = row + dr;
        int newdc = col + dc;

        while(newdr > 0 && newdc > 0 && newdr <= grid.length && newdc <= grid[0].length && grid[newdr][newdc] == target) {
            count++;
            newdr += dr;
            newdc += dc;
        }
        return count;
    }



}
