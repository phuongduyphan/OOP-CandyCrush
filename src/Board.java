import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import javafx.util.Pair;

public class Board {
	private int numberOfRow, numberOfColumn;
	private int[][] grid;
	private int numberOfCandyColor;
	private int score;

	public Board(int numberOfRow, int numberOfColumn, int numberOfCandyColor) {
		this.numberOfRow = numberOfRow;
		this.numberOfColumn = numberOfColumn;
		this.numberOfCandyColor = numberOfCandyColor;
		grid = new int[numberOfRow + 1][numberOfColumn + 1];
		score = 0;
		Main.getHeaderBoard().setScoreValue(score);
	}

	/**
	 * Shift the candies downward to fill the holes after crushing the candies (for
	 * using inside DropNewCandy)
	 * 
	 * @see dropNewCandy
	 */
	private void moveCandy() {
		ArrayList<ArrayList<Pair<Coordinate, Coordinate>>> dropLists = new ArrayList<ArrayList<Pair<Coordinate, Coordinate>>>();
		int startRow = 0, curRow = 0;
		Coordinate coor1, coor2;
		for (int i = 0; i < Main.getNumberofrow(); ++i)
			dropLists.add(new ArrayList<Pair<Coordinate, Coordinate>>());
		for (int j = 0; j < numberOfColumn; j++) {
			startRow = -1;
			for (int i = numberOfRow - 1; i >= 0; i--) {
				if (grid[i][j] == 0) {
					startRow = i; // The first Row whose value is 0
					break;
				}
			}
			if (startRow == -1)
				continue;
			curRow = startRow; // holds the lowest position of 0 in a column
			for (int i = startRow; i >= 0; i--) {
				if (grid[i][j] != 0) {
					coor1 = new Coordinate(i, j);
					coor2 = new Coordinate(curRow, j);
					dropLists.get(coor2.getRow()).add(new Pair<Coordinate, Coordinate>(coor1, coor2));
					curRow--;
				}
			}
		}
		for (int i = numberOfRow - 1; i >= 0; --i) {
			ArrayList<Pair<Coordinate, Coordinate>> dropList = dropLists.get(i);
			for (Pair<Coordinate, Coordinate> p : dropList) {
				int tmp = grid[p.getKey().getRow()][p.getKey().getColumn()];
				grid[p.getKey().getRow()][p.getKey().getColumn()] = grid[p.getValue().getRow()][p.getValue()
						.getColumn()];
				grid[p.getValue().getRow()][p.getValue().getColumn()] = tmp;
			}
			System.out.println("moveCandy");
			debugGrid();
			Main.getGameBoard().updateBoard();
		}
	}

	/**
	 * Generate new random candies to fill the holes after shifting candies
	 * downwards (for using inside dropNewCandy)
	 * 
	 * @see dropNewCandy
	 */
	private void fallCandy() {
		boolean haveChange = false;
		for (int i = numberOfRow - 1; i >= 0; i--) {
			for (int j = 0; j < numberOfColumn; j++) {
				if (grid[i][j] == 0) {
					grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					haveChange = true;
				}
			}
			if (haveChange) {
				System.out.println("fallCandy");
				debugGrid();
				Main.getGameBoard().updateBoard();
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

	/**
	 * Check if there are sequences of three or more candies, and set them to 0
	 * 
	 * @return a list of coordinates of candies in sequences
	 */
	public ArrayList<Coordinate> checkSequenceCandy() {
		int[][] hrow = new int[numberOfRow + 5][numberOfColumn + 5],
				hcol = new int[numberOfRow + 5][numberOfColumn + 5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;

		// search for sequences and store row/column sequences in hrow/hcol
		for (int i = 0; i < numberOfRow; i++)
			for (int j = 0; j < numberOfColumn; j++) {
				if (j > 0 && grid[i][j] == grid[i][j - 1])
					hrow[i][j] = hrow[i][j - 1] + 1;
				else
					hrow[i][j] = 1;

				if (i > 0 && grid[i][j] == grid[i - 1][j])
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

		// add candies in sequences coordinates to TreeSet
		for (int i = numberOfRow - 1; i >= 0; i--)
			for (int j = numberOfColumn - 1; j >= 0; j--) {
				if (hrow[i][j] >= 3) {
					for (int p = 0; p < hrow[i][j]; p++) {
						coor = new Coordinate(i, j - p);
						SetList.add(coor);
					}
				}

				if (hcol[i][j] >= 3)
					for (int p = 0; p < hcol[i][j]; p++) {
						coor = new Coordinate(i - p, j);
						SetList.add(coor);

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
		if (1 <= curRow && curRow <= numberOfRow && 1 <= curCol && curCol <= numberOfColumn)
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

		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {

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
		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {
				grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
			}
		}

		/** Eliminate sequences **/
		for (int i = 0; i < numberOfRow; i++) {
			ArrayList<Coordinate> coorList = new ArrayList<Coordinate>();
			ArrayList<Integer> typeList = new ArrayList<Integer>();
			for (int j = 0; j < numberOfColumn; j++) {
				// CASE 1
				if ((i >= 2) && (j < 2)) {
					if ((grid[i - 1][j] == grid[i - 2][j])) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					}
				}

				// CASE 2
				if ((i < 2) && (j >= 2)) {
					if ((grid[i][j - 1] == grid[i][j - 2])) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					}
				}

				// CASE 3
				if ((i >= 2) && (j >= 2)) {
					if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
						while ((grid[i][j] == grid[i - 1][j]) || (grid[i][j] == grid[i][j - 1]))
							grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					} else if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] != grid[i][j - 2])) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					} else if ((grid[i - 1][j] != grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numberOfCandyColor) + 1;
					}
				}
				coorList.add(new Coordinate(i, j));
				typeList.add(new Integer(grid[i][j]));
			}
			// Main.getGameBoard().setCandy(coorList, typeList);
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
			updateBoard();
		} else {
			swap(candy1, candy2);
		}
		Main.getGameBoard().play();
		Main.getHeaderBoard().setScoreValue(score);
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
			updateScore(list.size());
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

	private void updateScore(int s) {
		score += s;
	}

	private void debugGrid() {
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	public int[][] getGrid() {
		return grid;
	}

}
