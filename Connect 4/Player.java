/**
 * Abstract parent class that handles move input from a player. It doesn't matter how this move is obtained.
 * Current implementation allows a strong AI move player and a console based player. This class can be extended
 * to include input via a GUI and different AI's that may be more or less intelligent in the future.
 * @author Andrew
 *
 */
public abstract class Player {
	/**
	 * The piece type for this player.
	 */
	Piece piece;
	/**
	 * CTOR
	 * @param p the piece type for this player
	 */
	public Player(Piece p) {
		piece = p;
	}
	
	/**
	 * Abstract method that quieries and obtains a move from a player given a certain board state.
	 * @param b
	 * @return the column to move in.
	 */
	public abstract int getMove(Board b);
	
	public Piece getPieceType() {
		return piece;
	}
	public String toString() {
		return piece.toString();
	}
}
