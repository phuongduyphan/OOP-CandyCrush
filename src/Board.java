import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 * @initialize() PUBLIC Initialize the game field: Fill, validate and show grid
 * 
 * @generateBoard() Fill board with randomly-generated values
 * 
 * @isValid() Check for at least one possible combo, i.e player can make at
 *            least one more move.
 * 
 * @checkSequenceCandy() Check for sequence of candies on the screen and modify
 *                       comboList if necessary. Return comboList
 * 
 * @haveCombo() Return checkSequenceCandy().size() > 0
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2) PUBLIC Exchange the
 *                         coordinates of two candies. Check the swap: if not
 *                         generate combo, swap back
 * 
 * @crushCandies() Set 0 to candies with coordinates from comboList.
 * 
 * @updateBoard() PUBLIC Update the board after each valid swap until stability
 *                is attained.
 * 
 * @updateScore() PUBLIC Update score based on number of crushed candies
 */

public class Board {

	private int numberOfRow = 5, numberOfColumn = 5;
	private int[][] grid;
	private static ArrayList<Coordinate> comboList;
	private static int score;

	public Board() {
		grid = new int[numberOfRow][numberOfColumn];
		comboList = new ArrayList<Coordinate>();
	}

	private void generateBoard() {
		for (int i = 0; i < numberOfRow; i++) {
			for (int j = 0; j < numberOfColumn; j++) {
				grid[i][j] = Candy.getRandCandy();

				for (i = 0; i < numberOfRow; i++) {
					for (j = 0; j < numberOfColumn; j++) {
						// CASE 1
						if ((i >= 2) && (j < 2)) {
							if ((grid[i - 1][j] == grid[i - 2][j])) {
								while (grid[i][j] == grid[i - 1][j])
									grid[i][j] = Candy.getRandCandy();
							}
						}

						// CASE 2
						if ((i < 2) && (j >= 2)) {
							if ((grid[i][j - 1] == grid[i][j - 2])) {
								while (grid[i][j] == grid[i][j - 1])
									grid[i][j] = Candy.getRandCandy();
							}
						}

						// CASE 3
						if ((i >= 2) && (j >= 2)) {
							if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
								while ((grid[i][j] == grid[i - 1][j]) && (grid[i][j] == grid[i][j - 1]))
									grid[i][j] = Candy.getRandCandy();
							} else if ((grid[i - 1][j] == grid[i - 2][j]) && (grid[i][j - 1] != grid[i][j - 2])) {
								while (grid[i][j] == grid[i - 1][j])
									grid[i][j] = Candy.getRandCandy();
							} else if ((grid[i - 1][j] != grid[i - 2][j]) && (grid[i][j - 1] == grid[i][j - 2])) {
								while (grid[i][j] == grid[i][j - 1])
									grid[i][j] = Candy.getRandCandy();
							}

						}
					}
				}
			}
		}
	}

	private boolean haveCombo() {
		return (checkSequenceCandy().size() > 0);
	}

	public static void updateScore() {
		score += comboList.size() * 100;
		Main.headerBoard.setScoreValue(score);
	}

	// swap PERFORMS SWAP ACTIONS
	// AND CAN BE REUSED IN ANY METHODS OTHER THAN swapCandies
	private void swap(Coordinate candy1, Coordinate candy2) {
		Integer temp, xCandy1, yCandy1, xCandy2, yCandy2;
		xCandy1 = candy1.getKey();
		yCandy1 = candy1.getValue();
		xCandy2 = candy1.getKey();
		yCandy2 = candy2.getValue();
		temp = grid[xCandy1][yCandy1];
		grid[xCandy1][yCandy1] = grid[xCandy2][yCandy2];
		grid[xCandy2][yCandy2] = temp;
	}

	public boolean swapCandies(Coordinate candy1, Coordinate candy2) {

		swap(candy1, candy2);
		Main.gameBoard.swap(candy1, candy2);

		if (haveCombo()) { // if move doesn't generate combo, swap back
			swap(candy1, candy2);
			Main.gameBoard.swap(candy1, candy2);
		}

		return haveCombo();
	}

	private void crushCandies() {
		for (Coordinate x : comboList)
			grid[x.getKey()][x.getValue()] = 0;

		Main.gameBoard.crush(comboList);
	}

	public void updateBoard() {
		do {
			crushCandies();
			dropNewCandy();
		} while (haveCombo());
	}

	/**
	 * TODO
	 * @author DUY
	 */
	// set initial size
	public void setSize(int row, int col) {
		this.numberOfRow = row;
		this.numberOfColumn = col;
		grid = new int[row + 1][col + 1];
	}

	// Set initial Board
	public void setBoard(int[][] currentBoard) {
		for (int i = 1; i <= numberOfRow; i++) {
			for (int j = 1; j <= numberOfColumn; j++) {
				grid[i][j] = currentBoard[i][j];
			}
		}
	}

	// print for testing
	public void printBoard() {
		for (int i = 1; i <= numberOfRow; i++) {
			for (int j = 1; j <= numberOfColumn; j++) {
				System.out.printf("%d ", grid[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// print ArrayList of Old and New Coordinate when moving candies (for testing)
	private void printListMove(ArrayList<Pair<Coordinate, Coordinate>> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("Old Coor: %d %d ", ((list.get(i)).getKey()).getRow(),
					((list.get(i)).getKey()).getColumn());
			System.out.printf("New Coor: %d %d\n", ((list.get(i)).getValue()).getRow(),
					((list.get(i)).getValue()).getColumn());
		}
		System.out.println("");
	}

	// for using inside DropNewCandy
	private void moveCandy(int[][] currentBoard) {

		int startRow = 0, curRow = 0;
		ArrayList<Pair<Coordinate, Coordinate>> list = new ArrayList<Pair<Coordinate, Coordinate>>();
		Coordinate coor1, coor2;
		Pair<Coordinate, Coordinate> tmpPair;

		for (int j = 1; j <= numberOfColumn; j++) {
			for (int i = numberOfRow; i >= 1; i--) {
				if (currentBoard[i][j] == 0) {
					startRow = i; // The first Row whose value is 0
					break;
				}
			}

			curRow = startRow;
			for (int i = startRow; i >= 1; i--) {
				if (currentBoard[i][j] != 0) {
					currentBoard[curRow][j] = currentBoard[i][j];

					coor1 = new Coordinate(i, j);
					coor2 = new Coordinate(curRow, j);
					tmpPair = new Pair<Coordinate, Coordinate>(coor1, coor2);
					list.add(tmpPair);

					currentBoard[i][j] = 0;
					curRow--;
				}
			}
		}
		printListMove(list);
	}

	// printArrayList of Coordinate and Type of new Candies Fall (for testing)
	private void printListFall(ArrayList<Coordinate> listCoor, ArrayList<Integer> listCandy) {
		for (int i = 0; i < listCoor.size(); i++) {
			System.out.printf("Coor: %d %d Type: %d\n", listCoor.get(i).getRow(), listCoor.get(i).getColumn(),
					listCandy.get(i));
		}
		System.out.println("");
	}

	// for using inside DropNewCandy
	private void fallCandy(int[][] currentBoard) {
		Integer candy;
		ArrayList<Coordinate> listCoor = new ArrayList<Coordinate>();
		ArrayList<Integer> listCandy = new ArrayList<Integer>();
		Coordinate coor;

		for (int j = 1; j <= numberOfColumn; j++) {
			for (int i = numberOfRow; i >= 1; i--) {
				if (currentBoard[i][j] == 0) {
					candy = Candy.getRandCandy();
					currentBoard[i][j] = candy;

					coor = new Coordinate(i, j);
					listCoor.add(coor);
					listCandy.add(candy);
				}
			}
		}
		printListFall(listCoor, listCandy);
	}

	public void dropNewCandy(int[][] currentBoard) {
		moveCandy(currentBoard);
		printBoard();
		fallCandy(currentBoard);
		printBoard();
	}

	// for testing
	public void printCrushCandy(ArrayList<Coordinate> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("Coor: %d %d \n", list.get(i).getRow(), list.get(i).getColumn());
		}
		System.out.println("");
	}

	public ArrayList<Coordinate> checkSequenceCandy(int[][] currentBoard) {
		int[][] hrow = new int[numberOfRow + 5][numberOfColumn + 5],
				hcol = new int[numberOfRow + 5][numberOfColumn + 5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;

		for (int i = 1; i <= numberOfRow; i++) {
			for (int j = 1; j <= numberOfColumn; j++) {
				if (currentBoard[i][j] == currentBoard[i][j - 1]) {
					hrow[i][j] = hrow[i][j - 1] + 1;
				} else
					hrow[i][j] = 1;

				if (currentBoard[i][j] == currentBoard[i - 1][j]) {
					hcol[i][j] = hcol[i - 1][j] + 1;
				} else
					hcol[i][j] = 1;

			}
		}

		for (int i = numberOfRow; i >= 1; i--) {
			for (int j = numberOfColumn; j >= 1; j--) {
				if (hrow[i][j] >= 3) {
					for (int p = 0; p < hrow[i][j]; p++) {
						coor = new Coordinate(i, j - p);
						SetList.add(coor);
						currentBoard[i][j - p] = 0;
					}
				}

				if (hcol[i][j] >= 3) {
					for (int p = 0; p < hcol[i][j]; p++) {
						coor = new Coordinate(i - p, j);
						SetList.add(coor);
						currentBoard[i - p][j] = 0;
					}
				}
			}
		}

		Iterator<Coordinate> it = SetList.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}

		return list;
	}

	// for using inside isValid function
	private boolean CheckInside(int curRow, int curCol) {
		if (1 <= curRow && curRow <= numberOfRow && 1 <= curCol && curCol <= numberOfColumn)
			return true;
		return false;
	}

	public boolean isValid(int[][] currentBoard) {
		int curRow, curCol, cnt;

		for (int i = 1; i <= numberOfRow; i++) {
			for (int j = 1; j <= numberOfColumn; j++) {

				// Case 1
				curRow = i + 1;
				curCol = j + 1;
				cnt = 0;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol++;
					cnt++;
				}
				curCol = j - 1;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2)
					return true;

				// Case 2
				curRow = i - 1;
				curCol = j + 1;
				cnt = 0;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol++;
					cnt++;
				}
				curCol = j - 1;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2)
					return true;

				// Case 3
				curRow = i - 1;
				curCol = j - 1;
				cnt = 0;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow--;
					cnt++;
				}
				curRow = i + 1;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2)
					return true;

				// Case 4
				curRow = i - 1;
				curCol = j + 1;
				cnt = 0;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow--;
					cnt++;
				}
				curRow = i + 1;
				while (CheckInside(curRow, curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2)
					return true;
			}
		}
		return false;
	}

	// Just for testing the code above
	public static void main(String[] args) {
		Board board1 = new Board();

		int[][] tmpBoard = new int[8][8];
		Scanner reader = new Scanner(System.in);

		int r = reader.nextInt();
		int c = reader.nextInt();
		Candy.setNumberOfType(reader.nextInt());

		board1.setSize(r, c);
		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				tmpBoard[i][j] = reader.nextInt();
			}
		}
		reader.close();
		board1.setBoard(tmpBoard);
		board1.dropNewCandy(board1.grid);
		board1.printCrushCandy(board1.checkSequenceCandy(board1.grid));
		board1.printBoard();
		if (board1.isValid(tmpBoard))
			System.out.println("True");
		else
			System.out.println("False");
	}

}
