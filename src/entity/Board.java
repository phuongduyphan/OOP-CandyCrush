package entity;

import java.util.ArrayList;
import java.util.Random;
import entity.Candy;
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
 * @isStable
 * Check for uncrushed combos on the screen. 
 * Return true if board is stable. 
 * Return false and append coors of combos to comboList. 
 * 
 * @shuffleBoard()
 * 
 * @fillGrid()
 * Fill grid with random candies.
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2)
 * Exchange the coordinates of two candies. 
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
	static private int NUM_OF_ROWS = 5, NUM_OF_COLS = 5;
	static private int CELL_HEIGHT = 10, CELL_WIDTH = 10;
	private ArrayList<Coordinate> comboList, oldCoorList, newCoorList, blankList, fallColList;
	private ArrayList<Candy> newCandyList;
	private Drawer drawer;
	//private CellSelectionHandler cellSelectionHandler;
	private Candy grid[][];
	//private boolean swapBack = false;
	
	public Board() {
		grid = new Candy[NUM_OF_ROWS][NUM_OF_COLS];
		drawer = new Drawer();
		comboList = new ArrayList<Coordinate>();
	}
	
	public void initialize() {
		fillGrid();
		
		/*TODO
		 *validate the grid
		 * 
		 * while (!isValid() || !isStable())
			shuffleBoard(); //or fill new grid?
		 */
		drawer.drawBoard();
	}
	
	public boolean isValid() {
		//TODO
		//check for validity
		
		return false;
	}
	
	public boolean isStable() {
		comboList.clear(); //Reset comboList.
		//TODO
		//check for stability and modify comboList if necessary
		
		return false;
	}
	
	public void shuffleBoard() {
		Random Rand = new Random();
		
		for (int i = NUM_OF_ROWS - 1; i > 0; i--) {
			for (int j = NUM_OF_COLS - 1; j > 0; j--) {
				int m = Rand.nextInt(i + 1);
				int n = Rand.nextInt(j + 1);
				
				/*SWAP TWO ELEMENTS (CANDIES) --> PUT IN ANOTHER METHOD MAYBE?
				
				Candy temp;
				temp = grid[i][j];
				grid[i][j] = grid[m][n];
				grid[m][n] = temp;
				
				*/
				
			}
		}
		
	}
	
	public void swapCandies(Coordinate candy1, Coordinate candy2) {
		//TODO
		//update coordinates of two candies
		
		drawer.swap(candy1, candy2);
		
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
		//TODO
		//swap and check:
		//if swapping doesn't create combo, swap back
			
		while (!(isStable())) {
			crushCandies(comboList);
			moveCandies();
			newFall(blankList);
		}
	}
	
	public void fillGrid() {
		for (int i = 0; i < NUM_OF_ROWS; i++)
			for (int j = 0; j < NUM_OF_COLS; j++)
				grid[i][j] = getRandCandy();
	}
	
	
		
}