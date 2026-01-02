

class Game {
    - Board board
    - Player player1
    - Player player2
    - Player currentPlayer
    - GameState state
    - Player winner

    + Game(Player player1, Player player2)
    + boolean makeMove(Player player, int column)
    + Player getCurrentPlayer()
    + GameState getGameState()
    + Player getWinner()
    + Board getBoard()
}
enum GameState {
    INPROGRESS,
    DRAW,
    WIN
}

public void makeMove(Player player, int column) {
   if(state != GametState.INPROGRESS) {
         throw new IllegalStateException("Game is already over.");
   }
   if(player != currentPlayer) {
         throw new IllegalArgumentException("It's not your turn.");
   }
   if(!board.canPlaceDisc(column)) {
     throw new IllegalArgumentException("Column is full.");
   }
   int row = board.placeDisc(column, player.getColor());
   if(board.checkWin(row, column, player.getColor())) {
       state = GameState.WIN;
         winner = player;               
    }  else if (isFull()) {
        state = GameState.DRAW;
    } else {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }
}
