import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import javafx.util.Pair;

public class Board {
	private int row = 5, col = 5;
	private int[][] grid;
	private int numType;

	/**
	 * Set initial size
	 *
	 * @param row
	 *            the number of rows
	 * @param col
	 *            the number of columns
	 */
	public void setSize(int row, int col) {
		this.row = row;
		this.col = col;
		grid = new int[row + 1][col + 1];
	}

	/**
	 * Set number of candy types
	 * 
	 * @param numType
	 *            the number of candy types
	 */
	public void setNumType(int numType) {
		this.numType = numType;
	}

	/****
	 * PRINT FOR TESTING public void printBoard() { for (int i=1; i<= row; i++) {
	 * for (int j=1; j<= col; j++) { System.out.printf("%d ", grid[i][j]); }
	 * System.out.println(""); } System.out.println(""); }
	 * 
	 * // print ArrayList of Old and New Coordinate when moving candies (for
	 * testing) private void printListMove(ArrayList<Pair<Coordinate,Coordinate>>
	 * list) { for (int i=0; i< list.size(); i++) { System.out.printf("Old Coor: %d
	 * %d ",
	 * ((list.get(i)).getKey()).getRow(),((list.get(i)).getKey()).getColumn());
	 * System.out.printf("New Coor: %d %d\n",
	 * ((list.get(i)).getValue()).getRow(),((list.get(i)).getValue()).getColumn());
	 * } System.out.println(""); }
	 *****/

	/**
	 * Shift the candies downward to fill the holes after crushing the candies (for
	 * using inside DropNewCandy)
	 * 
	 * @see DropNewCandy
	 */
	private void moveCandy() {
		int startRow = 0, curRow = 0;
		ArrayList<Pair<Coordinate, Coordinate>> list = new ArrayList<Pair<Coordinate, Coordinate>>();
		Coordinate coor1, coor2;
		Pair<Coordinate, Coordinate> tmpPair;

		for (int j = 0; j < col; j++) {
			startRow = -1;
			for (int i = row - 1; i >= 0; i--) {
				if (grid[i][j] == 0) {
					startRow = i; // The first Row whose value is 0
					break;
				}
			}
			
			if (startRow == -1) continue;
			curRow = startRow; // holds the lowest position of 0 in a column
			for (int i = startRow; i >= 0; i--) {
				if (grid[i][j] != 0) {
					grid[curRow][j] = grid[i][j];

					coor1 = new Coordinate(i, j);
					coor2 = new Coordinate(curRow, j);
					tmpPair = new Pair<Coordinate, Coordinate>(coor1, coor2);
					list.add(tmpPair);

					grid[i][j] = 0;
					curRow--;
				}
			}
				System.out.println("moveCandy");
				debugGrid();
				Main.getGameBoard().updateBoard();
		}
//		Main.getGameBoard().swap(list);
	}

	/****
	 * PRINTING FOR TESTING // printArrayList of Coordinate and Type of new Candies
	 * Fall (for testing) private void printListFall(ArrayList<Coordinate>
	 * listCoor,ArrayList<Integer> listCandy) { for (int i=0; i<listCoor.size();
	 * i++) { System.out.printf("Coor: %d %d Type: %d\n", listCoor.get(i).getRow(),
	 * listCoor.get(i).getColumn(), listCandy.get(i)); } System.out.println(""); }
	 *****/

	/**
	 * Generate new random candies to fill the holes after shifting candies
	 * downwards (for using inside DropNewCandy)
	 * 
	 * @see DropNewCandy
	 */
	private void fallCandy() {
		for (int i = row - 1; i >= 0; i--) {
			ArrayList<Coordinate> coorList = new ArrayList<Coordinate>();
			ArrayList<Integer> typeList = new ArrayList<Integer>();
			for (int j = 0; j < col; j++) {
				if (grid[i][j] == 0) {
					grid[i][j] = new Random().nextInt(numType) + 1;
					coorList.add(new Coordinate(i, j));
					typeList.add(grid[i][j]);
				}
			}
			if(coorList.isEmpty() == false) {
				System.out.println("fallCandy");
				debugGrid();
				Main.getGameBoard().updateBoard();
//				Main.getGameBoard().setCandy(coorList, typeList);
			}
		}
	}

	/**
	 * Update positions of candies after a valid swap
	 */
	public void dropNewCandy() {
		moveCandy();
		Main.getGameBoard().pause();
		fallCandy();
	}

	/****
	 * PRINTING FOR TESTING public void printCrushCandy(ArrayList<Coordinate> list)
	 * { for (int i=0; i < list.size(); i++) { System.out.printf("Coor: %d %d \n",
	 * list.get(i).getRow(), list.get(i).getColumn()); } System.out.println(""); }
	 *****/

	/**
	 * Check if there are sequences of three or more candies, and set them to 0
	 * 
	 * @return a list of coordinates of candies in sequences
	 */
	public ArrayList<Coordinate> checkSequenceCandy() {
		int[][] hrow = new int[row + 5][col + 5], hcol = new int[row + 5][col + 5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;

		// search for sequences and store row/column sequences in hrow/hcol
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (j > 0 && grid[i][j] == grid[i][j - 1])
					hrow[i][j] = hrow[i][j - 1] + 1;
				else
					hrow[i][j] = 1;

				if (i > 0 && grid[i][j] == grid[i - 1][j])
					hcol[i][j] = hcol[i - 1][j] + 1;
				else
					hcol[i][j] = 1;

			}
		}
		System.out.println("Check Sequence:");
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j)
				System.out.print(hrow[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j)
				System.out.print(hcol[i][j] + " ");
			System.out.println();
		}
		System.out.println("/***************************/");

		// add candies in sequences coordinates to TreeSet
		for (int i = row - 1; i >= 0; i--) {
			for (int j = col - 1; j >= 0; j--) {
				if (hrow[i][j] >= 3) {
					for (int p = 0; p < hrow[i][j]; p++) {
						coor = new Coordinate(i, j - p);
						SetList.add(coor);
					}
				}

				if (hcol[i][j] >= 3) {
					for (int p = 0; p < hcol[i][j]; p++) {
						coor = new Coordinate(i - p, j);
						SetList.add(coor);
					}
				}
			}
		}

		// append the coordinates to a list
		Iterator<Coordinate> it = SetList.iterator();
		while (it.hasNext()) {
			coor = it.next();
			list.add(coor);
		}

		return list;
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
		if (1 <= curRow && curRow <= row && 1 <= curCol && curCol <= col)
			return true;
		return false;
	}

	/**
	 * Check if player can make at least one more move
	 * 
	 * @return <b>true</b> if the player can make at least one more move
	 */
	public boolean isValid() {
		int curRow, curCol, cnt;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {

				/** CASE 1: Row sequence created by an upward swap **/
				curRow = i + 1;
				cnt = 0;

				// Check RHS positions
				curCol = j + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol++;
					cnt++;
				}

				// Check LHS positions
				curCol = j - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
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
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol++;
					cnt++;
				}
				// Check LHS positions
				curCol = j - 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
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
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow--;
					cnt++;
				}
				// Check lower positions
				curRow = i + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
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
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow--;
					cnt++;
				}
				// Check lower positions
				curRow = i + 1;
				while (checkInside(curRow, curCol) && grid[curRow][curCol] == grid[i][j]) {
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
	 * Create board with random candies and modify to avoid sequences if necessary
	 */
	public void generateBoard() {
		/** Randomly generate board **/
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				grid[i][j] = new Random().nextInt(numType) + 1;
			}
		}

		/** Eliminate sequences **/
		for (int i = 0; i < row; i++) {
			ArrayList<Coordinate> coorList = new ArrayList<Coordinate>();
			ArrayList<Integer> typeList = new ArrayList<Integer>();
			for (int j = 0; j < col; j++) {
				// CASE 1
				if ((i >= 2) && (j < 2)) {
					if ((grid[i - 1][j] == grid[i - 2][j])) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}

				// CASE 2
				if ((i < 2) && (j >= 2)) {
					if ((grid[i][j - 1] == grid[i][j - 2])) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}

				// CASE 3
				if ((i >= 2) && (j >= 2)) {
					if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
						while ((grid[i][j] == grid[i - 1][j]) || (grid[i][j] == grid[i][j - 1]))
							grid[i][j] = new Random().nextInt(numType) + 1;
					} else if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] != grid[i][j - 2])) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numType) + 1;
					} else if ((grid[i - 1][j] != grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}
				coorList.add(new Coordinate(i, j));
				typeList.add(new Integer(grid[i][j]));
			}
//			Main.getGameBoard().setCandy(coorList, typeList);
		}
		System.out.println("genBoard");
		debugGrid();
		Main.getGameBoard().updateBoard();
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
		Integer temp, row1, col1, row2, col2;
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
//			Main.getGameBoard().swap(candy1, candy2);
			updateBoard();
		} else {
			swap(candy1, candy2);
//			Main.getGameBoard().flipFlop(candy1, candy2);
		}
		Main.getGameBoard().play();
		return (!list.isEmpty());
	}

	/**
	 * Set values at coordinates to 0
	 * 
	 * @param coorList
	 */
	private void crush(ArrayList<Coordinate> coorList) {
		for (Coordinate coor : coorList)
			grid[coor.getRow()][coor.getColumn()] = 0;
//		Main.getGameBoard().crush(coorList);
		debugGrid();
		Main.getGameBoard().updateBoard();
	}

	/**
	 * Update the positions of candies until there is no sequence left. If the
	 * player is out of move, generate new board
	 */
	public void updateBoard() {
		ArrayList<Coordinate> list = checkSequenceCandy();
		do {
			// updateScore
			System.out.println("Combo formed?: " + list.size());
			if (list.isEmpty() == false) {
				crush(list);
			}
			dropNewCandy();
			list = checkSequenceCandy();
		} while (list.isEmpty() == false);

		if (!isValid()) {
			generateBoard();
		}
	}

	private void debugGrid() {
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	public int[][] getGrid() {
		return grid;
	}

}
