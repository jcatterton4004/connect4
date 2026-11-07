package csc335project5;
import java.util.Observable;

/**
 * Represents Connect 4's game state.
 * 
 * This class contains the methods to manage Connect 4's
 * game state. This includes the board of pieces, whether
 * the game is over or not, and the winner. It creates a
 * new, blank 6 by 7 board upon construction and contains
 * the methods to add pieces to the board, check that a 
 * move is valid, check whether a player has won, and get/
 * set the isGameOver and winner fields.
 * 
 * @author Tyler Ohme
 */
public class Connect4Model extends Observable {
	/**
	 * The 2D array that represents the grid of colored tokens.
	 * 
	 * Each color value in the grid is represented by an integer. 0 means
	 * empty/white, 1 means yellow, 2 means red.
	 */
	private int[][] board;
	
	/** The tracker for the current game state, checks if the game is over. */
	private boolean isGameOver;
	
	/** Indicator for game winner. If 0, there is no winner (tie/game not over). If 1 then player wins, if 2 then computer wins.*/
	private int winner;
	
	/**
	 * The class constructor for Connect4Model.
	 * 
	 * Each field in the grid is initially populated to a blank value, and the
	 * game state is set to not over.
	 */
	public Connect4Model() {
		isGameOver = false;
		winner = 0;
		board = new int[6][7];
		
		// Populate rows with columns
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				board[row][col] = 0;
			}
		}
	}
	
	/**
	 * Validates whether a placement in a certain grid column is a valid move.
	 * 
	 * A move is considered valid if the index is within bounds (column 0-6) and
	 * at least one slot in the column is unoccupied.
	 * 
	 * @param col the column index to check, where 0 is the leftmost column
	 * @return true if the move is valid, false if it is not
	 */
	public boolean isValidMove(int col) {
		if (col < 0 || col > 6) return false; // Handle out of bounds
		
		// Check if there is an open slot
		return board[5][col] == 0;
	}
	
	/**
	 * Places a new piece in a specified column on the board.
	 * 
	 * Places the provided color on the lowest available space on the input column,
	 * then notifies observers.
	 * 
	 * @param col the column index to attempt a placement, where 0 is the leftmost column
	 * @param color the color of the chip being placed
	 */
	public void placeToken(int col, int color) {
		// Place new token on top
		for (int i = 0; i < 6; i++) {
			if (board[i][col] == 0) {
				board[i][col] = color;
				setChanged();
				notifyObservers(new Connect4MoveMessage(i, col, color));
				break;
			}
		}
	}
	
	/**
	 * Checks if a player has won the game.
	 * 
	 * The return value indicates whether a player won, and if so, which player.
	 * It checks if there is a row of 4 horizontally, vertically, and diagonally.
	 * 
	 * @return 0 if nobody won, 1 if player 1 won, 2 if player 2 won.
	 */
	public int checkWin() {
		// Iterate through each grid space in the board
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				// Record current space color
				int currentColor = getToken(col, row);
				if (currentColor == 0) continue;
				
				// Check horizontal win
				for (int i = 1; i < 4; i++) {
					if (col >= 4) break; // Handle out of bounds
					
					// Perform checks
					if (getToken(col + i, row) == currentColor) {
						if (i == 3) return currentColor;
					} else break;
				}
				
				// Check vertical win
				for (int i = 1; i < 4; i++) {
					if (row >= 3) break; // Handle out of bounds
					
					// Perform checks
					if (getToken(col, row + i) == currentColor) {
						if (i == 3) return currentColor;
					} else break;
				}
				
				// Check diagonal win 
				for (int i = 1; i < 4; i++) {
					if (col >= 4 || row >= 3) break; // Handle out of bounds
					
					// Perform checks
					if (getToken(col + i, row + i) == currentColor) {
						if (i == 3) return currentColor;
					} else break;
				}
				
				// Check other diagonal win
				for (int i = 1; i < 4; i++) {
				    if (col >= 4 || row < 3) break; // Handle out of bounds
				    
				    // Perform checks
				    if (getToken(col + i, row - i) == currentColor) {
				        if (i == 3) return currentColor;
				    } else break;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * Checks if the current gamestate is tied.
	 * 
	 * Checks each column to see if there is an available space to still place
	 * tokens. If there are no open spaces, then the game is tied.
	 * 
	 * @return true if the game is tied, false otherwise
	 */
	public boolean isTied() {
		// Check each column for open spaces
		for (int col = 0; col < 7; col++) {
			if (board[5][col] == 0) return false;
		}
		return true;
	}
	
	/**
	 * Gets the value of the token at the given row and column.
	 * 
	 * The col index represents the x coordinate, while the row index is the y
	 * coordinate. Will return a number value that corresponds to a color.
	 * 
	 * @param col the column index to get, where 0 is the leftmost column
	 * @param row the row index to get, where 0 is the bottommost row
	 * @return 0 if the token is empty, 1 if it is yellow, 2 if it is red
	 */
	public int getToken(int col, int row) {
		return board[row][col];
	}
	
	/**
	 * Sets the isGameOver instance variable.
	 * 
	 * Used to update the game state so classes using the model can check.
	 * 
	 * @param input the new value of isGameOver
	 */
	public void setIsGameOver(boolean input) {
		isGameOver = input;
	}
	
	/**
	 * Gets the value of the isGameOver instance variable.
	 * 
	 * Used to check the current game state by any class using the model.
	 * 
	 * @return true if the game is over, false if the game is ongoing
	 */
	public boolean getIsGameOver() {
		return isGameOver;
	}
	
	/**
	 * Sets the value of the winner instance variable.
	 * 
	 * Used to set the current winner according to the following values. 0 means
	 * a tie or unfinished game, 1 means the human player, 2 means the computer.
	 * 
	 * @param input the new value of winner
	 */
	public void setWinner(int input) {
		winner = input;
	}
	
	/**
	 * Gets the value of the winner instance variable.
	 * 
	 * Used to check the current value of the winner. 0 means a tie or unfinished 
	 * game, 1 means the human player, 2 means the computer.
	 * 
	 * @return 0 if no winner, 1 if human won, 2 if computer won.
	 */
	public int getWinner() {
		return winner;
	}
}
