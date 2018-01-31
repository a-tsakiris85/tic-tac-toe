/**
 * A representation of a connect 4 board that handles valid piece placement.
 * @author Andrew
 *
 */
public class Board {

	Piece[][] board;
	int numRows;
	int numCols;
	
	/**
	 * Create a board with specified dimensions. 
	 * @param rows
	 * @param cols
	 */
	public Board(int rows, int cols) {
		numRows = rows;
		numCols = cols;
		board = new Piece[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				board[i][j] = Piece.EMPTY;
			}
		}
	}
	
	/**
	 * Removes a single piece from the specified column.
	 * @param col
	 */
	public void undoMove(int col) {
		for(int row = 0; row < numRows; row++) {
			if(board[row][col] != Piece.EMPTY) {
				board[row][col] = Piece.EMPTY;
				return;
			}
		}
	}
	/**
	 * A quick constructor to create a board from scratch via an array. Chars should be either 'R', 'Y' or'-' in
	 * the array only.
	 * @param a
	 */
	public Board(char[][] a) {
		board = new Piece[a.length][a[0].length];
		numRows = a.length;
		numCols = a[0].length;
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				switch(a[i][j]) {
				case 'R': board[i][j] = Piece.RED;
				break;
				case 'Y': board[i][j] = Piece.YELLOW;
				break;
				case '-': board[i][j] = Piece.EMPTY;
				break;
				}
			}
		}
	}
	
	/**
	 * Clears board entirely of all pieces.
	 */
	public void clearBoard() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				board[i][j] = Piece.EMPTY;
			}
		}
	}
	
	/**
	 * Determines if a move at a given column is valid.
	 * @param col
	 * @return true if valid, false otherwise
	 */
	public boolean isValidMove(int col) {
		return board[0][col] == Piece.EMPTY && col > -1 && col < numCols;
	}
	
	/**
	 * Determines if given row,col coordinate is on the board at all.
	 * @param row
	 * @param col
	 * @return true if on board, false otherwise
	 */
	public boolean isOnBoard(int row, int col) {
		return -1 < row && row < numRows && -1 < col && col < numCols;
	}
	
	/**
	 * Places specified Piece at given col, if possible.
	 * @param col
	 * @param p
	 * @return false if piece was not able to be placed, true otherwise
	 */
	public boolean placePiece(int col, Piece p) {
		if(isValidMove(col)) {
			int row = numRows - 1;
			while (row > - 1 && board[row][col] != Piece.EMPTY) {
				row--;
			}
			board[row][col] = p;
			return true;
		}
		return false;
		
	}
	
	public String toString() {
		String str="";
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				str += board[i][j].toString() + " ";
			}
			str+= "\n";
		}
		return str;
	}
	/**
	 * 
	 * @return false if there are no more valid moves to make
	 */
	public boolean movesRemaining() {
		int col = 0;
		while(col < numCols ) {
			if(board[0][col] == Piece.EMPTY) {
				return true;
			}
			col++;
		}
		return false;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return the piece at the given row, col. Null if empty
	 */
	public Piece getPiece(int row, int col) {
		if(isOnBoard(row, col)) {
		return board[row][col];
		}
		return null;
	}
	

}
