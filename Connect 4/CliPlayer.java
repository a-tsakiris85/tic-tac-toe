import java.util.Scanner;

/**
 * A Player that queries the user for a move via the console.
 * 
 * @author Andrew
 *
 */
public class CliPlayer extends Player{

	public CliPlayer(Piece p) {
		super(p);
		
	}

	@Override
	public int getMove(Board b) {
		boolean valid = false;
		int response = -1;
		while(!valid) {
			System.out.println(super.piece.toString() + " player: pick a column!");
			Scanner sc = new Scanner(System.in);
			response = Integer.parseInt(sc.nextLine());
			if(response > -1 && response < b.numCols) {
				valid = true;
			}
		}
		return response;
	}
	
	

}
