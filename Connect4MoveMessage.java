package csc335project5;

/**
 * Used to indicate a move has been made.
 * 
 * This class is utilized to notify the view that the model 
 * has been updated because a move has been made. The data
 * that it passes includes the row and column that the piece
 * is being placed along with the color of that piece. It 
 * also contains getter methods for each of those fields.
 * 
 * @author Project Spec
 */
public class Connect4MoveMessage {
	
	/** integer representation of yellow piece */
	public static int YELLOW = 1;
	/** integer representation of red piece */
	public static int RED = 2;
	
	private int row;
	private int col;
	private int color;
	
	/**
	 * Constructs an instance of Connect4MoveMessage.
	 * 
	 * This constructor is used to created a new instance of 
	 * Connect4MoveMessage. This instance will be used to notify
	 * the view when the model has been updated. When a turn is 
	 * taken by the player or computer, the model must be changed
	 * to reflect that turn. Upon changing, it notifies the view
	 * so it can be updated on the view.
	 * 
	 * @param row is the row where the piece is being placed
	 * @param col is the column where the piece is being placed
	 * @param color is the color of the piece being placed
	 */
	public Connect4MoveMessage(int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}

	/** 
	 * Getter method for row field.
	 * 
	 * @return an integer row where the piece is being placed.
	 */
	public int getRow() { return row; }
	
	/** 
	 * Getter method for column field.
	 * 
	 * @return an integer column where the piece is being placed.
	 */
	public int getColumn() { return col; }
	
	/** 
	 * Getter method for color field.
	 * 
	 * @return an integer color of the piece being placed.
	 */
	public int getColor() { return color; }

}
