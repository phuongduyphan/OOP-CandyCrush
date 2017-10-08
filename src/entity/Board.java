package entity;

import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;
import tools.Coordinate;
import tools.Drawer;


/**
 * @initialize()
 * Initialize the game field: 
 * Fill, validate and show grid
 * 
 * @isValid()
 * Check for at least one possible combo, 
 * i.e player can make at least one more move.
 * 		
 * @checkSequenceCandy()
 * Check for uncrushed combos on the screen. 
 * Return comboList
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2)
 * Exchange the coordinates of two candies.
 * Check the swap: if not generate combo, swap back
 * 
 * @crushCandies(ArrayList<Coordinate> comboList)
 * Set null(?) values to candies with coordinates from comboList. 
 * 
 * @moveCandies()
 * Move candies downwards to fill the empty cells after crushing.
 * Update the positions of candies.
 *  
 * @newFall(ArrayList<Coordinate> blankList)
 * Fill the unoccupied cells with randomly-generated candies. 
 * 
 * @updateBoard()
 * Update the board after each valid swap until stability is attained.
 * 
 */

public class Board {
	private static int NUM_OF_ROWS = 5, NUM_OF_COLS = 5; //TODO FIXED SIZE?
	private static int CELL_HEIGHT = 10, CELL_WIDTH = 10; //TODO FIXED SIZE?
	private static ArrayList<Coordinate> comboList, oldCoorList, newCoorList, blankList, fallColList;
	private static ArrayList<Integer> newCandyList;
	private static int score;
	private Coordinate candy1, candy2;
	private Drawer drawer;
	private static Integer grid[][];
	
	public Board() {
		grid = new Integer[NUM_OF_ROWS][NUM_OF_COLS];
		drawer = new Drawer();
		comboList = new ArrayList<Coordinate>();
		oldCoorList = new ArrayList<Coordinate>();
		newCoorList = new ArrayList<Coordinate>();
		blankList = new ArrayList<Coordinate>();
		fallColList = new ArrayList<Coordinate>();
	}
	
	/*public void initialize() {
		fillGrid();
		
		 while (!isValid() || !isStable())
			generateBoard(); //or fill new grid?
		 
		drawer.drawBoard();
	}*/
	
	public static ArrayList<Coordinate> checkSequenceCandy() {
		comboList.clear(); //Reset comboList.
		
		for (int i = 0; i < NUM_OF_ROWS - 2; i++) {
			for (int j = 0; j < NUM_OF_COLS - 2; j++) {
				
				//CHECK COLUMN
				if ( (grid[i][j] == grid[i+ 1][j]) && (grid[i][j] == grid[i+2][j]) ) {
					comboList.add(new Coordinate(i, j));
					comboList.add(new Coordinate(i + 1, j));
					comboList.add(new Coordinate(i + 2, j));
				}
				
				//CHECK ROW
				if ( (grid[i][j] == grid[i][j + 1]) && (grid[i][j] == grid[i][j + 2]) ) {
					comboList.add(new Coordinate(i, j));
					comboList.add(new Coordinate(i, j + 1));
					comboList.add(new Coordinate(i, j + 2));
				}
			}
		}
		
		return comboList;
	}
	
	public static void updateScore() {
		score += comboList.size() * 100;
		
		headerBoardController(toString(score)); //????
		
	}
	
	public static void swap(Coordinate candy1, Coordinate candy2) {
		//TODO
		Integer temp;
		temp = grid[candy1.getKey()][candy1.getValue()];
		grid[candy1.getKey()][candy1.getValue()]
									= grid[candy2.getKey()][candy2.getValue()];
		grid[candy2.getKey()][candy2.getValue()] = temp;
		
	}

	public static boolean swapCandies(Coordinate candy1, Coordinate candy2) {
		
		swap(candy1, candy2);
		if (checkSequenceCandy().size() == 0)
			swap(candy1, candy2);
		
		return !(checkSequenceCandy().size() == 0);
	}
	

	public void crushCandies(ArrayList<Coordinate> comboList) {
		//TODO
		//update board
		
		drawer.crush(comboList);
		
	}
	
	public void moveCandies() {
		/* PARAMETERS:
			 * oldCoorList & newCoorList store positions before and after the move. 
			 * blankList stores positions of unoccupied cells after the move. 
		 
		 * TODO
			 * Append coordinates to oldCoorList, newCoorList and blankList. 
			 * Set new coordinates for the candies and null values for blank cells.
		 */
		
		drawer.move(oldCoorList, newCoorList);
	}
	
	public void newFall(ArrayList<Coordinate> blankList) {
		/* PARAMETERS:
			 * blankList stores positions of unoccupied cells after the move.
			 * 
			 * fallColList stores coordinates for the fall of ONE column. 
			 * newCandyList stores randomly generated candies to fill cells with coors in fallColList. 
			 * ORDER: FROM BOTTOM TO TOP.
		 
		 * TODO
		 	* Update fallColList with coordinates with same column from blankList. 
		 	* Append newCandyList with candies returned from getRandCandy(). 
		 */
		
		drawer.newFall(fallColList, newCandyList);
		
	}
	
	public void updateBoard() {

		if (swapCandies(candy1, candy2)) {
			do {
				crushCandies(comboList);
				updateScore();
				moveCandies();
				newFall(blankList);
			} while (checkSequenceCandy().size() > 0) ;  
		}
			
	}
	
	
		
}