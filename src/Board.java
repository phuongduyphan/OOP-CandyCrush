import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;
import tools.Coordinate;
import tools.Drawer;


/**
 * @generateBoard()
 * PUBLIC
 * Initialize game field with randomly-generated and validated board
 * 
 * @haveCombo()
 * Return checkSequenceCandy().size() > 0
 * 
 * @updateScore()
 * Update score based on number of crushed candies
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2)
 * PUBLIC
 * Exchange the coordinates of two candies.
 * Check the swap: if not generate combo, swap back
 * 
 * @crushCandies()
 * Set 0 to candies with coordinates returned from checkSequenceCandy().  
 * 
 * @updateBoard()
 * PUBLIC
 * Update the board after each valid swap until stability is attained.
 * 
 */

public class Board {
	private static int NUM_OF_ROWS = 10, NUM_OF_COLS = 10; //TODO FIXED SIZE?
	private static int numType;
	private static Integer score;
	private static Coordinate candy1, candy2;
	//private static Drawer drawer;
	private static Integer grid[][];
	
	public Board() {
		grid = new Integer[NUM_OF_ROWS][NUM_OF_COLS];
		drawer = new Drawer();
	}
	
	public void generateBoard() {
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			for (int j = 0; j < NUM_OF_COLS; j++) {
				grid[i][j] = new Random().nextInt(numType) + 1;
			}
		}
		
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			for (int j = 0; j < NUM_OF_COLS; j++) {
				//CASE 1
				if ((i >= 2) && (j < 2)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) ) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}
				
				//CASE 2
				if ((i < 2) && (j >= 2)) {
					if ( (grid[i][j - 1] == grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}
				
				//CASE 3
				if ((i >= 2) && (j >= 2)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) 
							&& (grid[i][j - 1] == grid[i][j - 2]) ) {
						while ( (grid[i][j] == grid[i - 1][j]) 
								&& (grid[i][j] == grid[i][j - 1]) )
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
					else if ( (grid[i - 1][j] == grid[i - 2][j]) 
							&& (grid[i][j - 1] != grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i - 1][j]) 
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
					else if ( (grid[i - 1][j] != grid[i - 2][j]) 
							&& (grid[i][j - 1] == grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
					
				}
				
			}
		}
	}
	
	private static boolean haveCombo() {
		return (checkSequenceCandy().size() > 0);
	}
	
	private static void updateScore() {
		score += checkSequenceCandy().size() * 100;
		
		//headerBoardController(Integer.toString(score)); ????
		
	}	

	//swap PERFORMS SWAP ACTIONS
	//AND CAN BE REUSED IN ANY METHODS OTHER THAN swapCandies
	private static void swap(Coordinate candy1, Coordinate candy2) {
		Integer temp, xCandy1, yCandy1, xCandy2, yCandy2;
		xCandy1 = candy1.getKey(); yCandy1 = candy1.getValue();
		xCandy2 = candy2.getKey(); yCandy2 = candy2.getValue();
		
		temp = grid[xCandy1][yCandy1];
		grid[xCandy1][yCandy1] = grid[xCandy2][yCandy2];
		grid[xCandy2][yCandy2] = temp;
	}

	public boolean swapCandies(Coordinate candy1, Coordinate candy2) {
	
		swap(candy1, candy2);
		drawer.swap(candy1, candy2);
		
		if (haveCombo()) { //if move doesn't generate combo, swap back
			swap(candy1, candy2);
			drawer.swap(candy1, candy2);
		}
		
		return haveCombo();
	}
	

	private static void crushCandies() {
		for (Coordinate x : checkSequenceCandy())
			grid[x.getKey()][x.getValue()] = 0;		
		
		drawer.crush(checkSequenceCandy());
	}
	
	public void updateBoard() {
		do {
			crushCandies();
			updateScore();
			dropNewCandy();
		} while (haveCombo()) ;  
	}
		
}