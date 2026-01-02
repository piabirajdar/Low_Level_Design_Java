
 class Board {
    - int rows
    - int columns
    - DiscColor[][] grid
    
    + Board(int rows, int columns)
    + getRows()
    + getColumns()
    + canPlaceDisc(int column) -> boolean
    + placeDisc(int column, DiscColor color) -> rowIndex
    + isFull() -> boolean
    + getCell(int row, int column) -> DiscColor
 }




private boolean checkWin(int row, int column, DiscColor color) {
    return checkDirection(row, column, color, 1, 0) || // Vertical
           checkDirection(row, column, color, 0, 1) || // Horizontal
           checkDirection(row, column, color, 1, 1) || // Diagonal /
           checkDirection(row, column, color, 1, -1);   // Diagonal \
}

private boolean checkDirection(int row, int column, DiscColor color, int deltaRow, int deltaCol) {
    int count = 1;
    count += countDiscs(row, column, color, deltaRow, deltaCol);
    count += countDiscs(row, column, color, -deltaRow, -deltaCol);
    return count >= 4;
}

private int countDiscs(int row, int column, DiscColor color, int deltaRow, int deltaCol) {
    int r = row + deltaRow;
    int c = column + deltaCol;
    int count = 0;
    while(r >= 0 && r < board.getRows() && c >= 0 && c < board.getColumns() && board.getCell(r, c) == color) {
        count++;
        r += deltaRow;
        c += deltaCol;
    }
    return count;
}
private boolean isFull() {
    for(int c = 0; c < board.getColumns(); c++) {
        if(board.canPlaceDisc(c)) {
            return false;
        }
    }
    return true;
}

public int placeDisc(int column, DiscColor color) {
       
     for (int r = rows-1; r >=0; r--) {
            if (grid[r][column] == DiscColor.EMPTY) {
                grid[r][column] = color;
                return r;
            }
        }
        return -1;
 }
 
public boolean canPlaceDisc(int column) {
    if (column < 0 || column >= columns) {
        throw new IllegalArgumentException("Invalid column index.");
    }
    return grid[0][column] == DiscColor.EMPTY;
}
