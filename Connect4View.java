package csc335project5;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/** 
 * Launches and facilitates a GUI view for the game of Connect4.
 * 
 * This class holds the methods to create a GUI interface for a user
 * to play Connect 4 on. The user takes their turn by clicking a column
 * on the board, which places a piece at the next empty spot at the 
 * bottom  of that column. Then, the computer will take a turn by placing
 * a piece in a random column with an available spot. The game continues
 * until either the user or the computer successfully get 4 pieces in a
 * row.
 * 
 * @author Jesse Catterton
 */
public class Connect4View extends Application implements Observer {

	private BorderPane window = new BorderPane();
	private GridPane board = new GridPane();
	private Circle[][] grid = new Circle[6][7];
	private Connect4Controller controller = new Connect4Controller();
	
	/**
	 * Starts the Connect 4 GUI application.
	 * 
	 * This method launches the GUI application that simulates a game of Connect
	 * 4. It creates a BorderPane to act as a window for the application, which
	 * houses a GridPane to represent the Connect 4 board. The grid consists of
	 * Circle objects whose fill colors are changed to represent moves made/pieces
	 * placed on the board. This method also sets a mouse-click handler method 
	 * to read in user input when the player wants to make a move.
	 * 
	 * @param stage is a Stage object that holds our GUI applcation.
	 * @throws Exception if an error occurs in launching the GUI. Caught in main.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Connect 4");
		window.setStyle("-fx-background-color: blue;");
		window.setCenter(board);
		board.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		board.setHgap(8);
		board.setVgap(8);
		board.setPadding(new Insets(8, 8, 8, 8));

		for (int row = 0; row < 6; row++) { // loops to add each row of Circles
			for (int col = 0; col < 7; col++) { // loops to add a Circle in each column of the row
				Circle circle = new Circle(20);
				circle.setFill(Color.WHITE);
				board.add(circle, col, row);
				grid[row][col] = circle; // stores a reference to each board spot in grid for easy access
			}
		}
	
		board.setOnMouseClicked(event -> handleMouseClick(event)); 
		controller.getModel().addObserver(this); // adds an observer to the model so it can call update	
		Scene scene = new Scene(window, 344, 296);
		stage.setScene(scene);
		stage.show(); 
	}
	
	/**
	 * Handles the event that a user clicks the grid.
	 * 
	 * This method is a handler for when the user performs a mouse click. It
	 * extracts the x coordinate from where the user clicked the grid and calls
	 * a method to determine which column was clicked. If the player's turn was
	 * successful and valid, then it checks to see if the player has won. If so,
	 * it calls sendResultMsg() to end the game. Otherwise, the computer takes
	 * its turn and we check to see if the computer has won or if the game was
	 * tied. If either is true, we call sendResultMsg() to end the game. If the
	 * player turn was unsuccessful, then we send a column error message.
	 * 
	 * @param click is a MouseEvent contatining information about a user's mouse click.
	 */
	public void handleMouseClick(MouseEvent click) {
		double x = click.getX(); 
		if (controller.humanTurn(getColumn(x))) { // runs if human turn was successful
			if (controller.checkWinner() == Connect4MoveMessage.YELLOW) { // checks if player has won
				sendResultMsg("You won!");
				return;
			} 
			
			controller.computerTurn();
			if (controller.checkWinner() == Connect4MoveMessage.RED) { // checks if computer has won
				sendResultMsg("You lost!");
				return;
			} 
			
			// checks for a tie (we only check this after the computer has taken their turn because since the
			// player goes first, it's impossible for the player to have the final move that fills the board
			if (controller.checkWinner() == 0) {
				sendResultMsg("You tied!");
				return;
			}			
		} else { // if the user clicks a full column, human turn will return false, enters this to send error
			Alert msg = new Alert(Alert.AlertType.ERROR);
			msg.setTitle("Error"); 
			msg.setHeaderText("Error");
			msg.setContentText("Column full, pick somewhere else!");
			msg.showAndWait(); // shows error message when a user clicks on a full column
		}
	}
	
	/**
	 * Returns the column that was clicked.
	 * 
	 * This method takes in the x coordinate where the mouse click was made
	 * by the user. Given that location, the method returns the column that
	 * was clicked based on the width of each column.
	 * 
	 * @param xCoord is the x coordinate where the user clicked the grid.
	 * @return the column that was clicked by the user.
	 */
	public int getColumn(double xCoord) {
		if (xCoord <= 52) return 0;
		if (xCoord <= 100) return 1;
		if (xCoord <= 148) return 2;
		if (xCoord <= 196) return 3;
		if (xCoord <= 244) return 4;
		if (xCoord <= 292) return 5;
		if (xCoord <= 344) return 6;
		return -1;
	}
	
	/**
	 * Displays result message to the user.
	 * 
	 * This method can display a popup window with a message for the 
	 * user. It is used to indicate when the user has won or lost, or
	 * when a tie has occured. It creates an Alert object with either 
	 * the message "You won!" if the player connected 4 of their 
	 * pieces, "You lost!" if the computer connected 4 of theirs, or
	 * "You tied!" if the board is full and neither has 4 in a row. It
	 * also prevents the user from making moves once the game is over.
	 * 
	 * @param message is string to be displayed in the alert.
	 */
	public void sendResultMsg(String message) {
		Alert msg = new Alert(Alert.AlertType.INFORMATION);
		msg.setTitle("Message"); 
		msg.setHeaderText("Message");
		msg.setContentText(message);
		board.setOnMouseClicked(null); // disables mouse clicks
		msg.showAndWait(); // shows win or loss message
	}

	/**
	 * Observes a change in the model and updates the view.
	 * 
	 * This method observes the model and detects when it calls the notifyObservers
	 * method, which sends an instance of the Connect4MoveMessage class. The instance
	 * contains information about what has been updated in the model when a move is
	 * made. It delivers the row and column a piece has been placed along with the
	 * color of that piece so that the view can be updated accordingly.
	 * 
	 * @param o is an Observable object that this method observes for changes.
	 * @param message is an Object containing information about the change.
	 */
	@Override
	public void update(Observable o, Object message) {
		if (!(message instanceof Connect4MoveMessage)) return;
		
		int row = ((Connect4MoveMessage) message).getRow();
		int col = ((Connect4MoveMessage) message).getColumn();
		int colorNum = ((Connect4MoveMessage) message).getColor();
		Color color = colorNum == Connect4MoveMessage.YELLOW ? Color.YELLOW : Color.RED; // sets color based on integer (1 = yellow, 2 = red)
		grid[5 - row][col].setFill(color); // sets the corresponding spot on game board to the piece's color
	}
	
	/**
	 * Launches a game of Connect 4.
	 * 
	 * This method launches the GUI view of Connect 4 by calling launch
	 * with the argument args, which calls the start method.
	 * 
	 * @param args is is a String array of the command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
