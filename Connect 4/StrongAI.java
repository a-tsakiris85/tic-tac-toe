/**
 * A player that uses minimax algorithm and still-in-development heuristic to
 * determine best possible moves. Connect 4 is complicated enough that the
 * machine cannot achieve full depth, so this parameter can be changed to
 * provide more intelligent moves at the cost of computation time.
 * 
 * Currently, this AI will almost always tie or beat the average human player.
 * It will win when presented with the opportunity, but it is not smart enough
 * to try to "trick" the player into losing, and therefore the AI is good at not
 * losing, but not particularly good at winning either.
 * 
 * @author Andrew
 *
 */
public class StrongAI extends Player {

	public StrongAI(Piece p) {
		super(p);
	}

	/**
	 * Evaluates the state of the board, with a premium on winning situations and 3
	 * in a row situations. This heuristic could be developed further to better
	 * reflect the value of a board.
	 * 
	 * Precondition: board is a standard 7 by 6 connect 4 Board.
	 * 
	 * @param board
	 * @return the value
	 */
	public int evaluateBoardDumb(Board board) {
		int BOARD_ROWS = board.numRows;
		int BOARD_COLS = board.numCols;
		// Check horizontals for win
		for (int row = BOARD_ROWS - 1; row > -1; row--) {
			for (int startCol = 0; startCol < BOARD_COLS - 3; startCol++) {
				int streak = 1;
				int col = startCol;
				while (col < BOARD_COLS - 1 && board.getPiece(row, col) == board.getPiece(row, col + 1)) {
					streak++;
					col++;
				}
				if (streak > 3) {
					if (board.getPiece(row, startCol) == super.piece)
						return 1000;
					else if (board.getPiece(row, startCol) != Piece.EMPTY)
						return -1000;
				}
			}
		}

		// Check verticals for win
		for (int col = 0; col < BOARD_COLS; col++) {
			for (int startRow = 0; startRow < BOARD_ROWS - 3; startRow++) {
				int streak = 1;
				int row = startRow;
				while (row < BOARD_ROWS - 1 && board.getPiece(row, col) == board.getPiece(row + 1, col)) {
					streak++;
					row++;
				}
				if (streak > 3) {
					if (board.getPiece(startRow, col) == super.piece)
						return 1000;
					else if (board.getPiece(startRow, col) != Piece.EMPTY)
						return -1000;
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
					if (board.getPiece(startRow, startCol) == super.piece) {
						return 1000;
					}
					return -1000;
				}
			}
		}
		// top left corner and down/right for wins
		for (startRow = 0; startRow < 3; startRow++) {
			for (startCol = 0; startCol < 4; startCol++) {
				if (board.getPiece(startRow, startCol) != Piece.EMPTY
						&& board.getPiece(startRow, startCol) == board.getPiece(startRow + 1, startCol + 1)
						&& board.getPiece(startRow + 1, startCol + 1) == board.getPiece(startRow + 2, startCol + 2)
						&& board.getPiece(startRow + 2, startCol + 2) == board.getPiece(startRow + 3, startCol + 3)) {
					if (board.getPiece(startRow, startCol) == super.piece) {
						return 1000;
					}
					return -1000;
				}
			}
		}
		int value = 0;
		// horizontal check
		for (int row = 0; row < board.numRows; row++) {
			for (int col = 0; col < 5; col++) {
				if (board.getPiece(row, col) != Piece.EMPTY && board.getPiece(row, col) == board.getPiece(row, col + 1)
						&& board.getPiece(row, col + 1) == board.getPiece(row, col + 2)) {
					if (board.getPiece(row, col - 1) == Piece.EMPTY) {
						if (board.getPiece(row, col) == super.piece) {
							value += 50;
						} else {
							value -= 50;
						}
					}
					if (board.getPiece(row, col + 3) == Piece.EMPTY) {
						if (board.getPiece(row, col + 2) == super.piece) {
							value += 50;
						} else {
							value -= 50;
						}
					}
				}
			}
		}
		return value;
	}

	/**
	 * returns a value of a certain state based on all possible consequent states,
	 * assuming perfect play Using the minimax algorithm
	 * 
	 * @param b
	 * @param depth
	 * @param isMaximizingPlayer
	 * @return
	 */
	public int minimax(Board b, int depth, boolean isMaximizingPlayer) {
		int value = evaluateBoardDumb(b);
		if (value == 1000)
			return 1000 - depth + value;
		if (value == -1000)
			return -1000 + depth + value;
		if (depth > 5)
			return value;
		int col = -1;

		if (isMaximizingPlayer) {
			int newValue = Integer.MIN_VALUE;
			int bestValue = Integer.MIN_VALUE;
			for (col = 0; col < b.numCols; col++) {
				if (b.isValidMove(col)) {
					b.placePiece(col, super.piece);
					newValue = minimax(b, depth + 1, false);
					b.undoMove(col);
					if (newValue > bestValue) {
						bestValue = newValue;
					}

				}
			}
			return bestValue;
		} else {
			Piece pieceToPlace = Piece.RED;
			if (getPieceType() == Piece.RED) {
				pieceToPlace = Piece.YELLOW;
			}
			int newValue = Integer.MAX_VALUE;
			int bestValue = Integer.MAX_VALUE;
			for (col = 0; col < b.numCols; col++) {
				if (b.isValidMove(col)) {
					b.placePiece(col, pieceToPlace);
					newValue = minimax(b, depth + 1, true);
					b.undoMove(col);
					if (newValue < bestValue) {
						bestValue = newValue;
					}

				}
			}

			return bestValue;
		}

	}

	@Override
	public int getMove(Board b) {
		System.out.println("Getting AI move");
		int bestValue = Integer.MIN_VALUE;
		int bestMove = -1;
		for (int col = 0; col < b.numCols; col++) {
			if (b.isValidMove(col)) {
				b.placePiece(col, super.piece);
				int value = minimax(b, 0, false);
				if (value > bestValue) {
					bestValue = value;
					bestMove = col;
				}
				b.undoMove(col);
			}
		}

		return bestMove;
	}

}
