class Game {
    private Board board;
    private Player playerX;
    private Player playerO;
    private GameState state;
    private Player winner;
    private Player currentPlayer;


    public Game(Player playerX, Player playerO) {
        this.board = new Board();
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;     // X always starts
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
    }

    public boolean makeMove(int row, int col) {
        if (state != GameState.IN_PROGRESS) {
            return false;
        }

        if(!board.isValidMove(row, col)) {
            throw new IllegalStateException("Not a valid move");
        }

        board.placeMark(row, col, currentPlayer.getMark());

        if(board.checkWin(row, col, currentPlayer.getMark())) {
            state = GameState.WON;
            winner = currentPlayer;
            return;
        }

        if(board.isFull()) {
            state = GameState.DRAW;
            return true;
        }

        switchTurn();
        return true;
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

}
public enum GameState {
    IN_PROGRESS,
    WON,
    DRAW
}