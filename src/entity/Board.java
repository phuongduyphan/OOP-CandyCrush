package entity;

import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;
import tools.Coordinate;
import tools.Drawer;


/**
 * @initialize()
 * PUBLIC
 * Initialize the game field: 
 * Fill, validate and show grid
 * 
 * @generateBoard()
 * Fill board with randomly-generated values
 * 
 * @isValid()
 * Check for at least one possible combo, 
 * i.e player can make at least one more move.
 * 		
 * @checkSequenceCandy()
 * Check for sequence of candies on the screen and modify comboList if necessary.
 * Return comboList
 * 
 * @haveCombo()
 * Return checkSequenceCandy().size() > 0
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2)
 * PUBLIC
 * Exchange the coordinates of two candies.
 * Check the swap: if not generate combo, swap back
 * 
 * @crushCandies()
 * Set 0 to candies with coordinates from comboList.  
 * 
 * @updateBoard()
 * PUBLIC
 * Update the board after each valid swap until stability is attained.
 * 
 * @updateScore()
 * PUBLIC
 * Update score based on number of crushed candies
 */

public class Board {
	private static int NUM_OF_ROWS = 10, NUM_OF_COLS = 10; //TODO FIXED SIZE?
	private static int CELL_HEIGHT = 10, CELL_WIDTH = 10; //TODO FIXED SIZE?
	private static int CANDY_TYPE;
	private static ArrayList<Coordinate> comboList;
	private static Integer score;
	private static Coordinate candy1, candy2;
	private Drawer drawer;
	private static Integer grid[][];
	
	public Board() {
		grid = new Integer[NUM_OF_ROWS][NUM_OF_COLS];
		drawer = new Drawer();
		comboList = new ArrayList<Coordinate>();
	}
	
	private static void generateBoard() {
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			for (int j = 0; j < NUM_OF_COLS; j++) {
				grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
		
		for (i = 0; i < NUM_OF_ROWS; i++) {
			for (j = 0; j < NUM_OF_COLS; j++) {
				//CASE 1
				if ((i >= 2) && (j < 2)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) ) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
					}
				}
				
				//CASE 2
				if ((i < 2) && (j >= 2)) {
					if ( (grid[i][j - 1] == grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
					}
				}
				
				//CASE 3
				if ((i >= 2) && (j >= 2)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) 
							&& (grid[i][j - 1] == grid[i][j - 2]) ) {
						while ( (grid[i][j] == grid[i - 1][j]) 
								&& (grid[i][j] == grid[i][j - 1]) )
							grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
					}
					else if ( (grid[i - 1][j] == grid[i - 2][j]) 
							&& (grid[i][j - 1] != grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i - 1][j]) 
							grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
					}
					else if ( (grid[i - 1][j] != grid[i - 2][j]) 
							&& (grid[i][j - 1] == grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(CANDY_TYPE) + 1;
					}
					
				}
				
			}
		}
	}
	
	private static boolean haveCombo() {
		return (checkSequenceCandy().size() > 0);
	}
	
	public static void updateScore() {
		score += comboList.size() * 100;
		
		//headerBoardController(Integer.toString(score)); ????
		
	}
	
	//swap PERFORMS SWAP ACTIONS
	//AND CAN BE REUSED IN ANY METHODS OTHER THAN swapCandies
	private static void swap(Coordinate candy1, Coordinate candy2) {
		Integer temp, xCandy1, yCandy1, xCandy2, yCandy2;
		xCandy1 = candy1.getKey(); yCandy1 = candy1.getValue();
		xCandy2 = candy1.getKey(); yCandy2 = candy2.getValue();
		
		temp = grid[xCandy1][yCandy1];
		grid[xCandy1][yCandy1] = grid[xCandy2][yCandy2];
		grid[xCandy2][yCandy2] = temp;
	}

	public static boolean swapCandies(Coordinate candy1, Coordinate candy2) {
		
		swap(candy1, candy2);
		drawer.swap(candy1, candy2);
		
		if (haveCombo()) { //if move doesn't generate combo, swap back
			swap(candy1, candy2);
			drawer.swap(candy1, candy2);
		}
		
		return haveCombo();
	}
	

	private static void crushCandies() {
		for (Coordinate x : comboList)
			grid[x.getKey()][x.getValue()] = 0;		
		
		drawer.crush(comboList);
	}
	
	public static void updateBoard() {
		do {
			crushCandies();
			dropNewCandy();
		} while (haveCombo()) ;  
	}
		
}