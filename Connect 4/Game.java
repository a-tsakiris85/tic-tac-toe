/**
 * Console based display of the Connect 4 Game
 * 
 * @author Andrew
 *
 */
public class Game {

	final int BOARD_ROWS = 6;
	final int BOARD_COLS = 7;

	Board board = new Board(BOARD_ROWS, BOARD_COLS); // assumes standard size board.
	boolean playerOneTurn = true;

	// Can support AI v AI play, as well as 2 player play.
	Player player1 = new CliPlayer(Piece.RED); // these players can be interchanged for any type of player.
	Player player2 = new StrongAI(Piece.YELLOW);

	/**
	 * Checks if a player has won the game (4 in a row) and returns that player if
	 * so.
	 * 
	 * @return the winning player, null otherwise.
	 */
	public Player checkWin() {
		// Check horizontals
		for (int row = BOARD_ROWS - 1; row > -1; row--) {
			for (int startCol = 0; startCol < BOARD_COLS - 3; startCol++) {
				int streak = 1;
				int col = startCol;
				while (col < BOARD_COLS - 1 && board.getPiece(row, col) == board.getPiece(row, col + 1)) {
					streak++;
					col++;
				}
				if (streak > 3) {
					if (board.getPiece(row, startCol) == player1.getPieceType())
						return player1;
					else if (board.getPiece(row, startCol) == player2.getPieceType())
						return player2;
				}
			}
		}

		// Check verticals
		for (int col = 0; col < BOARD_COLS; col++) {
			for (int startRow = 0; startRow < BOARD_ROWS - 3; startRow++) {
				int streak = 1;
				int row = startRow;
				while (row < BOARD_ROWS - 1 && board.getPiece(row, col) == board.getPiece(row + 1, col)) {
					streak++;
					row++;
				}
				if (streak > 3) {
					if (board.getPiece(startRow, col) == player1.getPieceType())
						return player1;
					else if (board.getPiece(startRow, col) == player2.getPieceType())
						return player2;
				}
			}
		}

		// Check diagonals
		// bottom left corner + up/right
		int startRow;
		int startCol;
		for (startRow = 4; startRow < BOARD_ROWS; startRow++) {
			for (startCol = 0; startCol < 4; startCol++) {
				if (board.getPiece(startRow, startCol) != Piece.EMPTY
						&& board.getPiece(startRow, startCol) == board.getPiece(startRow - 1, startCol + 1)
						&& board.getPiece(startRow - 1, startCol + 1) == board.getPiece(startRow - 2, startCol + 2)
						&& board.getPiece(startRow - 2, startCol + 2) == board.getPiece(startRow - 3, startCol + 3)) {
					if (player1.getPieceType() == board.getPiece(startRow, startCol)) {
						return player1;
					}
					return player2;
				}
			}
		}
		// top left corner and down/right
		for (startRow = 0; startRow < 3; startRow++) {
			for (startCol = 0; startCol < 4; startCol++) {
				if (board.getPiece(startRow, startCol) != Piece.EMPTY
						&& board.getPiece(startRow, startCol) == board.getPiece(startRow + 1, startCol + 1)
						&& board.getPiece(startRow + 1, startCol + 1) == board.getPiece(startRow + 2, startCol + 2)
						&& board.getPiece(startRow + 2, startCol + 2) == board.getPiece(startRow + 3, startCol + 3)) {
					if (player1.getPieceType() == board.getPiece(startRow, startCol)) {
						return player1;
					}
					return player2;
				}
			}
		}

		return null;
	}

	/**
	 * Makes a complete set of moves, one from each player if possible.
	 */
	public void makeMoves() {
		if (playerOneTurn) {
			while (!board.placePiece(player1.getMove(board), player1.getPieceType())) {
			}
			;
		} else {
			while (!board.placePiece(player2.getMove(board), player2.getPieceType())) {
				System.out.println("Try again player 2");
			}
			;
		}
	}

	/**
	 * Runs console game.
	 */
	public void playConsoleGame() {
		Player winner = null;
		while (winner == null && board.movesRemaining()) {
			makeMoves();
			playerOneTurn = !playerOneTurn;
			winner = checkWin();
			System.out.println(board);
			System.out.println("-----------------------------");
		}
		if (winner != null) {
			System.out.println(winner + " wins!");
		} else {
			System.out.println("Tie!");
		}
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.playConsoleGame();

	}
}
