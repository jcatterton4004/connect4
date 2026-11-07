/**
 * @author Jesse Catterton
 * @author Tyler Ohme
 * 
 * CSC 335 Spring 26 Project 5: Connect 4
 * Purpose: This program has the capability of running a game
 * of Connect 4 between a human player and a computer. Both
 * players drop yellow and red pieces respectively into a 6
 * by 7 grid and attempt to connect 4 of their pieces either
 * horizontally, vertically, or diagonally. The game board is
 * displayed to the player using a JavaFX GUI contained in the
 * Connect4View class. It communicates with the Connect4Model
 * class using a Connect4Controller, while the model class 
 * communicates with the view via an observer/observable
 * relationship. The game ends when either player connects 4
 * pieces or the board fills up.
 */

package csc335project5;
import javafx.application.Application;

/**
 * Launches a game of Connect 4.
 * 
 * This is the main class for the Connect 4 program. Its main method
 * is able to launch the JavaFX GUI application contained in the
 * Connect4View class. This class will launch a window containing the
 * board for Connect 4, which the player can interact with by clicking
 * on the columns. The player competes against a computer that makes
 * random moves until either of them have won or a tie occurs.
 * 
 * @author Jesse Catterton
 * @author Tyler Ohme
 */
public class Connect4 {
	
	/**
	 * Starts the Connect4 program.
	 * 
	 * This method starts a game of Connect 4 by launching a JavaFX GUI 
	 * application using the Connect4View class. It will catch any general
	 * Exception thrown when the application is launched.
	 * 
	 * @param args is a String array of the command line arguments.
	 */
	public static void main(String[] args) {
		try {
			Application.launch(Connect4View.class, args);
		} catch (Exception e) { // catches generic exception automatically thrown by start
			System.out.println("ERROR LAUNCHING GUI: " + e.getMessage());
		}
	}

}
