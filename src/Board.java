import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The Board class includes methods to handle events on the game board, 
 * i.e searching for sequences of candies, updating positions of candies after a move, 
 * swapping two candies...
 */

public class Board {
	private int numberOfRow, numberOfColumn;
	private Candy[][] grid;
	private int score;

	public Board(int numberOfRow, int numberOfColumn, int numberOfCandyColor) {
		this.numberOfRow = numberOfRow;
		this.numberOfColumn = numberOfColumn;
		grid = new Candy[numberOfRow + 1][numberOfColumn + 1];
		score = 0;
		Main.getHeaderBoard().setScoreValue(score);
	}
	
	/**
	 * Check whether the grid is full of candies
	 * @return
	 */
	private boolean checkFullCandy() {
		for (int i= 0; i< numberOfRow; i++) {
			for (int j=0; j < numberOfColumn; j++) {
				if (grid[i][j] == null) return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if a given coordinate is within the board range (for using inside
	 * isValid function)
	 * 
	 * @param curRow
	 *            row position
	 * @param curCol
	 *            column position
	 * @return <b>true</b> if the coordinate is within range
	 * 
	 * @see isValid
	 */
	private boolean checkInside(int curRow, int curCol) {
		if (0 <= curRow && curRow < numberOfRow && 0 <= curCol && curCol < numberOfColumn)
			return true;
		return false;
	}

	/**
	 * Check if there are sequences of three or more candies and process those sequences
	 * 
	 * @return a list of coordinates of candies in sequences
	 */
	public ArrayList<Coordinate> checkSequenceCandy() {
		int[][] hrow = new int[numberOfRow + 5][numberOfColumn + 5],
				hcol = new int[numberOfRow + 5][numberOfColumn + 5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>(); //stores coordinates of candies in sequences 
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;

		/**
		 *  SEARCH FOR SEQUENCES
		 *  
		 *  @param hrow 
		 *  			array dedicates the number of times a specific color appears w.r.t rows
		 *  @param hcol 
		 *  			array dedicates the number of times a specific color appears w.r.t columns
		 */
		//Traverse the board
		//If a candy in a row/column (at position [i][j]) has the same color with the preceding one, 
		//increment the times the preceding one appears in the sequence by 1 and set it as hrow[i][j]/hcol[i][j]
		for (int i = 0; i < numberOfRow; i++)
			for (int j = 0; j < numberOfColumn; j++) {
				if (j > 0 && grid[i][j].getColor() == grid[i][j - 1].getColor())
					hrow[i][j] = hrow[i][j - 1] + 1;
				else
					hrow[i][j] = 1;

				if (i > 0 && grid[i][j].getColor() == grid[i - 1][j].getColor())
					hcol[i][j] = hcol[i - 1][j] + 1;
				else
					hcol[i][j] = 1;

			}

		System.out.println("Check Sequence:");
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j)
				System.out.print(hrow[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j)
				System.out.print(hcol[i][j] + " ");
			System.out.println();
		}
		System.out.println("/***************************/");

		/**
		 *  PROCESS SEQUENCES
		 *  
		 *  @param specialPos
		 *  			the position of candy with formed by special sequence
		 *  @param visitRowSeq
		 *  			array dedicates whether a cell has been processed in consequence of a row sequence
		 *  @param visitColSeq 
		 *  			array dedicates whether a cell has been processed in consequence of a column sequence
		 */
		int specialPos = 0; 
		boolean[][] visitRowSeq = new boolean[numberOfRow + 5][numberOfColumn + 5],
					visitColSeq = new boolean[numberOfRow + 5][numberOfColumn + 5];
		
		for (int i = numberOfRow - 1; i >= 0; i--)
			for (int j = numberOfColumn - 1; j >= 0; j--) {
				/**PROCESS ROW SEQUENCE**/
				//if there is a row sequence & the candy at the position has not been processed
				if (!visitRowSeq[i][j] && hrow[i][j] >= 3) {
					specialPos = -1;						
					
					if (hrow[i][j] >= 4) {
						
						specialPos = ((j - hrow[i][j] + 1) + j)/2; //supposed special position of the row
						
						//If the candy at the special position is of type Normal,
						//turn it into a candy of type Horizontal Bomb with the same color.
						//Otherwise, set specialPos back to -1
						if (grid[i][specialPos] instanceof CandyNormal) {
							grid[i][specialPos] = new CandyHorizontalBomb(grid[i][j].getColor());
						}
						else specialPos = -1;
					}
					
					//traverse the row sequence, 
					//mark visitRowSeq at the corresponding position true to avoid repeated process,
					//append the coordinates (except which includes specialPos) to setList.
					for (int p = 0; p < hrow[i][j]; p++) {
						visitRowSeq[i][j-p] = true; 
						if (j-p != specialPos) {
							coor = new Coordinate(i, j - p);
							SetList.add(coor);
						}
					}
				}
				
				/**PROCESS COLUMN SEQUENCE**/
				//if there is a column sequence & the candy at the position has not been processed
				if (!visitColSeq[i][j] && hcol[i][j] >= 3) {
					specialPos = -1;
			
					if (hcol[i][j] >= 4) {
						
						specialPos = ((i - hcol[i][j] + 1) + i)/2; //supposed special position of the column
						
						//If the candy at the special position is of type Normal,
						//turn it into a candy of type Vertical Bomb with the same color.
						//Otherwise, set specialPos back to -1
						if (grid[specialPos][j] instanceof CandyNormal) {
							grid[specialPos][j] = new CandyVerticalBomb(grid[i][j].getColor());
						}
						else specialPos = -1;
					}
					
					//traverse the column sequence, 
					//mark visitColSeq at the corresponding position true to avoid repeated process,
					//append the coordinates (except which includes specialPos) to setList.
					for (int p = 0; p < hcol[i][j]; p++) {
						visitColSeq[i-p][j] = true;
						if (i-p != specialPos) {
							coor = new Coordinate(i - p, j);
							SetList.add(coor);
						}
					}
				}
			}
		System.out.println(SetList.toString());
		// append the coordinates to a list
		Iterator<Coordinate> it = SetList.iterator();
		while (it.hasNext()) {
			coor = it.next();
			list.add(coor);
		}

		return list;
	}

	/**
	 * Set values at coordinates to 0 and check for special explode
	 * 
	 * @param coorList list of coordinates of candies to be exploded
	 */
	private void crush(ArrayList<Coordinate> coorList) {
		for (Coordinate coor : coorList) {
			crush(coor);
		}
		debugGrid();
		Main.getGameBoard().updateBoard();
	}

	/**
	 * Set values at coordinates to 0 and check for special explode
	 * 
	 * @param coor the coordinate of the candy to be exploded
	 */
	private void crush(Coordinate coor) {
		Candy candy = grid[coor.getRow()][coor.getColumn()];
		if (candy == null)
			return;
		grid[coor.getRow()][coor.getColumn()] = null;
		for (Coordinate coor2 : candy.specialExplode(coor)) {
			crush(coor2);
		}
	}

	private void debugGrid() {
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j)
				if (grid[i][j] == null)
					System.out.print('X' + " ");
				else
					System.out.print(grid[i][j].getColor() + " ");
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Update positions of candies after a valid swap,
	 * i.e move the candies downwards to fill vacant cells and then randomly generate new candies to fill the board
	 */
	public void dropNewCandy() {		
		boolean checkTopCandy; //is true if there is at least one vacant cell in-between
		
		//For one loop, in any particular column, 
		//either the candies will be moved downwards to fill vacant cells (checkTopCandy == true)
		//or new candies will be randomly generated and filled in (checkTopCandy == false).
		//The main process will be done when all cells in the board is filled.
		while (!checkFullCandy()) {
			for (int j = 0; j <= numberOfColumn; j++) {
				if (j == numberOfColumn) { 
					System.out.println("fallCandy");
					debugGrid();
					Main.getGameBoard().updateBoard();
					continue;
				}
				
				checkTopCandy = false;
				for (int i=numberOfRow-1; i>=0; i--) {
					//If the cell is vacant, replace it with the nearest candy in the column
					if (grid[i][j] == null) {
						for (int k = i-1; k>=0; k--) {
							if (grid[k][j] != null) {
								grid[i][j] = grid[k][j];
								grid[k][j] = null;
								checkTopCandy = true;
								break;
							}
						}
						
						//if there is no candy on the cells above, randomly generate a candy for the current cell
						if (!checkTopCandy) grid[i][j] = Candy.getRandCandy();
						break;
					}	
				}
			}
		}
	}

	/**
	 * Create board with random candies and modify to avoid sequences if necessary
	 */
	public void generateBoard() {
		/** RANDOMLY GENERATE BOARD **/
		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {
				grid[i][j] = Candy.getRandCandy();
			}
		}

		/** ELIMINATE SEQUENCES
		 * 	Traverse the board, if a candy has the same color with its two preceding candies, 
		 *  either in a row or in a column, change its color to a different color.
		 */
		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {
				// CASE 1: where column sequences can be randomly formed
				if ((i >= 2) && (j < 2)) {
					if ((grid[i - 1][j].getColor() == grid[i - 2][j].getColor())) {
						while (grid[i][j].getColor() == grid[i - 1][j].getColor())
							grid[i][j] = Candy.getRandCandy();
					}
				}

				// CASE 2: where row sequences can be randomly formed
				if ((i < 2) && (j >= 2)) {
					if ((grid[i][j - 1].getColor() == grid[i][j - 2].getColor())) {
						while (grid[i][j].getColor() == grid[i][j - 1].getColor())
							grid[i][j] = Candy.getRandCandy();
					}
				}

				// CASE 3: where row or column sequences can be randomly formed
				if ((i >= 2) && (j >= 2)) {
					if ((grid[i - 1][j].getColor() == grid[i - 2][j].getColor())
							&& (grid[i][j - 1].getColor() == grid[i][j - 2].getColor())) {
						while ((grid[i][j].getColor() == grid[i - 1][j].getColor())
								|| (grid[i][j].getColor() == grid[i][j - 1].getColor()))
							grid[i][j] = Candy.getRandCandy();
					} else if ((grid[i - 1][j].getColor() == grid[i - 2][j].getColor())
							&& (grid[i][j - 1].getColor() != grid[i][j - 2].getColor())) {
						while (grid[i][j].getColor() == grid[i - 1][j].getColor())
							grid[i][j] = Candy.getRandCandy();
					} else if ((grid[i - 1][j].getColor() != grid[i - 2][j].getColor())
							&& (grid[i][j - 1].getColor() == grid[i][j - 2].getColor())) {
						while (grid[i][j].getColor() == grid[i][j - 1].getColor())
							grid[i][j] = Candy.getRandCandy();
					}
				}
			}
		}
		System.out.println("genBoard");
		debugGrid();
		Main.getGameBoard().updateBoard();
	}

	public Candy[][] getGrid() {
		return grid;
	}

	/**
	 * Check if player can make at least one more move
	 * 
	 * @return <b>true</b> if the player can make at least one more move
	 */
	public boolean isValid() {
		int curRow, curCol, cnt;

		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {

				/** CASE 1: Row sequence created by an upward swap **/
				curRow = i + 1;
				cnt = 0;

				// Check RHS positions
				curCol = j + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curCol++;
					cnt++;
				}

				// Check LHS positions
				curCol = j - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i, j);
					return true;
				}

				/** CASE 2: Row sequence created by a downward swap **/
				curRow = i - 1;
				cnt = 0;

				// Check RHS positions
				curCol = j + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curCol++;
					cnt++;
				}
				// Check LHS positions
				curCol = j - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curCol--;
					cnt++;
				}

				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i, j);
					return true;
				}

				/** CASE 3: Column sequence created by a leftward swap **/
				curCol = j - 1;
				cnt = 0;

				// Check upper positions
				curRow = i - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curRow--;
					cnt++;
				}
				// Check lower positions
				curRow = i + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curRow++;
					cnt++;
				}

				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i, j);
					return true;
				}

				/** CASE 4: Column sequence created by a rightward swap **/
				curCol = j + 1;
				cnt = 0;

				// Check upper positions
				curRow = i - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curRow--;
					cnt++;
				}
				// Check lower positions
				curRow = i + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol].getColor() == grid[i][j].getColor()) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i, j);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Exchange the positions of two candies (for using inside swapCandies)
	 * 
	 * @param candy1
	 *            the coordinate of the first candy
	 * @param candy2
	 *            the coordinate of the second candy
	 * 
	 * @see swapCandies
	 */
	private void swap(Coordinate candy1, Coordinate candy2) {
		Integer row1, col1, row2, col2;
		Candy temp;
		row1 = candy1.getRow();
		col1 = candy1.getColumn();
		row2 = candy2.getRow();
		col2 = candy2.getColumn();
		temp = grid[row1][col1];
		grid[row1][col1] = grid[row2][col2];
		grid[row2][col2] = temp;
		System.out.println("swap");
		debugGrid();
		Main.getGameBoard().updateBoard();
	}

	/**
	 * Exchange the positions of two candies. If the move does not generate any
	 * sequence, undo the move.
	 * 
	 * @param candy1
	 *            the coordinate of the first candy
	 * @param candy2
	 *            the coordinate of the second candy
	 * @return <b>true</b> if swapping two candies generates at least one sequence
	 *         of candies
	 */
	public boolean swapCandies(Coordinate candy1, Coordinate candy2) {
		swap(candy1, candy2);
		ArrayList<Coordinate> list = checkSequenceCandy();
		if (list.isEmpty() == false) {
			updateBoard(list);
		} else {
			swap(candy1, candy2);
		}
		Main.getGameBoard().play();
		Main.getHeaderBoard().setScoreValue(score);
		return (!list.isEmpty());
	}

	/**
	 * Update the positions of candies until there is no sequence left. If the
	 * player is out of move, generate new board
	 */
	public void updateBoard(ArrayList<Coordinate> curList) {
		ArrayList<Coordinate> list = curList;
		do {
			updateScore(list.size());
			System.out.println("Combo formed?: " + list.size());
			if (list.isEmpty() == false) {
				crush(list);
				Main.getGameBoard().pause();
			}
			dropNewCandy();
			list = checkSequenceCandy();
		} while (list.isEmpty() == false);

		if (!isValid()) {
			generateBoard();
		}
	}

	private void updateScore(int s) {
		score += s;
	}

}
