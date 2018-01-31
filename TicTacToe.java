import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A graphical tic tac toe application in JAVA displaying the use of the minimax algorithm. 
 * The AI is a perfect player, meaning that it will always win or tie.
 * 
 * Note: My main interest in this program was creating the minimax AI, not the design of the code. I actually
 * first implemented the AI and then wrapped the graphics/display around it at the end. If I was creating a final
 * product, I would split concerns via the Model View Controller design pattern, but this is simply a demonstration
 * of minimax.
 * 
 * @author Andrew
 *
 */
public class TicTacToe {

	/**
	 * The board is represented as a 2D array of chars
	 */
	char[][] board =  {{'-', '-', '-'} ,
	   		  {'-', '-', '-'} , 
	   		  {'-', '-', '-'}};
	/**
	 * GUI components
	 */
	JFrame frame = new JFrame();
	DrawingCanvas mainPanel = new DrawingCanvas();
	JButton resetButton = new JButton("Reset");
	JPanel rightPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	
	int WINDOW_WIDTH = 1000;
	int SQUARE_SIZE = 200;
	
	/**
	 * Finds the best possible computer move given the state of the board.
	 * @return an int[] of length 2 given in the form {row, col} representing the coordinates of the best move
	 */
	public int[] findBestMove() {
		int bestValue = -1000;
		int[] bestMove = new int[2];
		//checks the value of all possible moves and chooses the resulting board with the highest value
		//does not actually make any moves
		for(int i =0; i < board.length; i++) {
			for(int j = 0;  j < board.length; j++) {
				if(board[i][j] == '-') {
					board[i][j] = 'X';
					int value = minimax(0, false);//finds the value of a given move
					board[i][j] = '-';
					if(value > bestValue) { //selects the move that yields the highest valued board.
						bestMove[0] = i;
						bestMove[1] = j;
						bestValue = value;
					}
					
				}
			}
		}
		return bestMove;
	}
	
	/**
	 * The actual recursive minimax function, which is used to evaluate the current state of the board based
	 * on anticipation of future moves by an assumed-perfect player.
	 * @param depth the current depth of the algorithm. Used to add value to positions in which the AI can win quickly.
	 * @param isMaximizingPlayer a boolean that alternates with each increase in depth per form of the minimax algorithm
	 * @return the value
	 */
	public int minimax(int depth, boolean isMaximizingPlayer) {
		int score = evaluateBoard();
		//cases in which a player has won or lost.
		if(score == 10) return 10 - depth;
		if(score == -10) return -10 + depth;
		if(isEndState()) return 0;
		
		int bestValue = 0;
		
		if(isMaximizingPlayer) {
			bestValue = Integer.MIN_VALUE;
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(board[i][j] == '-') {
						board[i][j] = 'X';
						int value = minimax( depth + 1, false);
						board[i][j] = '-';
						if(value > bestValue) {
							bestValue = value;
						}
					}
				}
			}
			return bestValue;
		}
		else {
			bestValue = Integer.MAX_VALUE;
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(board[i][j] == '-') {
						board[i][j] = 'O';
						int value = minimax(depth + 1, true);
						board[i][j] = '-';
						if(value < bestValue) {
							bestValue = value;
						}
					}
				}
			}
			return bestValue;
		}
		
	}
	
	/**
	 * Takes care of the actual evaluation of the board (with no looking ahead to future moves).
	 * Since tic tac toe is a simple game, the minimax algorithm can always run to a complete depth, and
	 * therefore the only heuristic measure needed is when the game is won or lost, and no intermediates are needed. 
	 * @return the value of the board to be used in the minimax algorithm
	 */
	public int evaluateBoard() {
		//check horizontals for win
		boolean found = false;
		char winningChar = '-';
		for(int i = 0; i < board.length && !found; i++) {
			boolean stillInARow = true;
			int j = 0;
			while( j < board.length - 1 && stillInARow) {
				stillInARow = board[i][j] == board[i][++j];
			}
			if(stillInARow) {
				winningChar = board[i][j];
				if(winningChar != '-') {
					found = true;
				}
			}
		}
		
		//check Verticals for win
		if(!found) {
			for(int i = 0; i < board.length && !found; i++) {
				boolean stillInARow = true;
				int j = 0;
				while( j < board.length - 1 && stillInARow) {
					stillInARow = board[j][i] == board[++j][i];
				}
				if(stillInARow) {
					winningChar = board[j][i];
					if(winningChar != '-') {
						found = true;
					}
				}
			}
		}
		
		//check diagonals for win
		if(!found) {
			boolean stillInaRow = true;
			int i;
			for(i =0; i < board.length - 1 && stillInaRow; i++) {
				
				stillInaRow = board[i][i] == board[i+1][i+1];
			}
			if(stillInaRow) {
				
				winningChar = board[i][i];
			}
			if(winningChar == '-') {
				i = board.length - 1;
				stillInaRow = true;
				int j;
				for(j = 0; j < board.length -1 && stillInaRow; j++) {
					stillInaRow = board[i][j] == board[i - 1][j + 1];
					i--;
				}
				if(stillInaRow) {
					winningChar = board[i][j];
				}
			}
			
		}
		
		if(winningChar == 'X') return 10;
		if(winningChar == 'O') return -10;
		return 0;
	}
	
	/**
	 * 
	 * @return true if the board has empty squares, false if it does not.
	 */
	public boolean isEndState() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] != 'O' && board[i][j] != 'X') {
					return false;
				}
			}
		}
		return true;
	}
	
	private static String boardToString(char[][] board) {
		String str = "";
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				str += board[i][j] + " ";
			}
			if(i != board.length - 1)
			str += "\n";
		}
		
		return str;
	}
	public static String moveToString(int[] move) {
		return "(" + move[0] + "," + move[1] + ")";
	}
	
	/**
	 * Runs the GUI application
	 */
	public void launchGUI() {
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Tic Tac Toe");
		
		
		JLabel title = new JLabel("Tic Tac Toe");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
		JPanel titlePanel = new JPanel();
		titlePanel.add(title, BorderLayout.CENTER);
		
		rightPanel.setPreferredSize(new Dimension((WINDOW_WIDTH - SQUARE_SIZE * 3 -30)/2,100));
		leftPanel.setPreferredSize(new Dimension((WINDOW_WIDTH - SQUARE_SIZE * 3 -30)/2,100));
		
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		resetButton.setPreferredSize(new Dimension(100,100));
		frame.getContentPane().add(resetButton, BorderLayout.SOUTH);
		frame.setVisible(true);
		
		startGame();
	
	}
	
	/**
	 * Adds listeners to mouse and button to get ready for the game in graphics mode.
	 */
	public void startGame() {
		mainPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				 {
					System.out.println("Suc");
					makeMove(xyCoordsToBoardCoords(arg0.getX(), arg0.getY()));
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
		});
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < board.length; i++) {
					for(int j = 0; j < board.length; j++) {
						board[i][j] = '-';
					}
				}
				frame.repaint();
				mainPanel.requestFocusInWindow();
				
			}
			
		});
	}
	
	/**
	 * Executes a player move and the following computer move.
	 * @param move
	 */
	public void makeMove(int[] move) {
		System.out.println("Making Move: " + moveToString(move));
		int val = evaluateBoard();

		if(board[move[0]][move[1]] == '-' && val != 10 && val != -10) {
			board[move[0]][move[1]] = 'O';
			System.out.println("Success");
			val =evaluateBoard();
			if(!isEndState() && val != 10 && val != -10) {
			move = findBestMove();
			board[move[0]][move[1]] = 'X';
			System.out.println(boardToString(board));
			
			}
			frame.repaint();
			
		}
		
	}
	
	//converts the xy coordinates of a click into the coordinates on the board.
	private int[] xyCoordsToBoardCoords(int x, int y) {
		int[] result = new int[2];
		result[0] = y / SQUARE_SIZE;
		result[1] = x / SQUARE_SIZE;
		System.out.println("Board spot selected: " + moveToString(result));
		return result;
	}
	
	/**
	 * Class representing the canvas on which the board and pieces are drawn, which is in turn added
	 * to the outer JFrame's content pane.
	 * @author Andrew
	 *
	 */
	private class DrawingCanvas extends JPanel {
		public void paintComponent(Graphics g) {
			int squareSize = SQUARE_SIZE;
			int offset = 20;
			Graphics2D g2d =(Graphics2D) g;
			int x = 0;
			int y = 0;
			for(int i=0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					g2d.setColor(Color.BLACK);
					g2d.setStroke(new BasicStroke(1));
					g2d.drawRect(x, y, squareSize, squareSize);
					g2d.setStroke(new BasicStroke(10));
					if(board[j][i] == 'X' ) {
						System.out.println("X");
						g2d.setColor(Color.RED);
						g2d.drawLine(x + offset, y + offset, x + squareSize - offset, y + squareSize - offset);
						g2d.drawLine(x + offset, y + squareSize - offset, x + squareSize - offset, y + offset);
					}
					else if(board[j][i] == 'O') {
						g2d.setColor(Color.BLUE);
						 g2d.draw(new Ellipse2D.Double(x + offset, y+ offset, squareSize - offset*2, squareSize - offset*2));
					}
					y+= squareSize;
				}
				x += squareSize;
				y= 0;
			}
		}
	}
	/**
	 * To be called in main, runs the game in console mode.
	 */
	public void playGame() {
		
		System.out.println(boardToString(board));
		System.out.println("You are O's, AI is X's");
		
		while(true) {
			
			System.out.println("----------------------------------");
			Scanner sc = new Scanner(System.in);
			System.out.println("Your Move:");
			System.out.println("Enter row:");
			int row = Integer.parseInt(sc.nextLine());
			System.out.println("Enter col: ");
			int col = Integer.parseInt(sc.nextLine());
			board[row][col] = 'O';
			System.out.println(boardToString(board));
			System.out.println("Nice Move!");
			if(isEndState()) {
				break;
			}
		
			System.out.println("Computer is thinking");
			
			
			
			
			int[] move = findBestMove();
			System.out.println("Computer Moved!");
			board[move[0]][move[1]] = 'X';
			System.out.println(boardToString(board));
			if(isEndState()) {
				break;
			}
			
			
		}
		System.out.println("game over!");	
		}
	/**
	 * A Main method showing the game being launched as a graphical display.
	 * @param args none needed
	 */
	public static void main(String[] args) {
		
		TicTacToe t = new TicTacToe();
		t.launchGUI();
	}

}
