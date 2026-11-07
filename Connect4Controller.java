package csc335project5;

import java.util.Random;
import java.util.ArrayList;

/**
 * Represents the Connect 4 game behavior.
 * 
 * This method contains the methods that allow the
 * view and model class to communicate with each other
 * to facilitate a game of Connect 4. It connects the
 * model to the view using the setObserver method and 
 * passes actions on the view to the model using the
 * humanTurn() and computerTurn() methods.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 */
public class Connect4Controller {

	private Connect4Model model;
	
	/**
	 * Creates a new instance of Connect4Controller.
	 * 
	 * This method is a constructor for the Connect4Controller class. It
	 * creates a new instance of Connect4Model to hold the game state of
	 * the game of Connect4.
	 */
	public Connect4Controller() {
		model = new Connect4Model();
	}
	
	/**
	 * Returns the controller's instance of the model.
	 * 
	 * This is a getter method for the controller's model that returns
	 * the controller's current insance of Connect4Model. This allows the
	 * view to add an observer to the model so it can be notified when
	 * changes are made.
	 * 
	 * @return model is an instance of Connect4Model.
	 */
	public Connect4Model getModel() {
		return model;
	}
	
	/**
	 * Completes one human/player turn.
	 * 
	 * This method is called when the player clicks on a column in
	 * the GUI view to complete their turn. The method is passed
	 * the column that the player clicked and passes it into the 
	 * model's isValidMove() function which checks if the move is valid,
	 * and then places the piece if it is. It returns true if the piece 
	 * was placed and false if the placement is invalid.
	 * 
	 * @param col is the column that player wants to place a piece in.
	 * @return a boolean representing whether the attempt to place the token was successful.
	 */
	public boolean humanTurn(int col) {
		// Check if a move is valid, and update model if it is
		if (model.isValidMove(col)) {
			model.placeToken(col, Connect4MoveMessage.YELLOW);
			updateGameState();
			return true;
		} else return false;
	}
	
	/**
	 * Completes one computer turn.
	 * 
	 * This method is called after a player turn as long as the 
	 * player did not win. It completes the computer's turn by 
	 * generating a pre-validated random column number and passing 
	 * it into the model's placeToken() method, which places a red 
	 * piece in the column specified. 
	 */
	public void computerTurn() {
		// Get list of valid column indices
		ArrayList<Integer> validColumns = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			if (model.isValidMove(i)) validColumns.add(i);
		}
		
		if (!validColumns.isEmpty()) {
			// Choose random pre-validated column index
			Random random = new Random();
			int randomIndex = random.nextInt(validColumns.size()); // generates the index of a random valid columns
			int column = validColumns.get(randomIndex);
			
			// Place token at random index
			model.placeToken(column, Connect4MoveMessage.RED);
		}
		
		updateGameState();
	}
	
	/**
	 * Updates the current state of the game.
	 * 
	 * This method is called after each turn, it checks for wins or ties and
	 * updates the model accordingly.
	 */
	private void updateGameState() {
		// Check if the game is tied
		if (model.isTied()) {
			model.setIsGameOver(true);
			model.setWinner(0);
			return;
		}

		// Check for winners
		int winner = model.checkWin();
		if (winner != 0) {
			model.setIsGameOver(true);
			model.setWinner(winner);
		}
	}
	
	/**
	 * Returns the winner if the game is over.
	 * 
	 * This method allows the view to access the model's data
	 * on the winner of the game. It first checks to see if the
	 * game is over, then returns the winner as an integer. A 1
	 * means that yellow won, a 2 means that red won, and a 0
	 * means that it was a tie. The integer -1 will be returned
	 * if the game isn't over.
	 * 
	 * @return an integer representing the winner, (1 = yellow, 2 = red, 0 = tie).
	 */
	public int checkWinner() {
		if (model.getIsGameOver()) return model.getWinner(); // returns winner if game is over
		return -1;
	}
 }
