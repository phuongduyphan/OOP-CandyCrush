import java.util.*;
import javafx.util.*;

/** DESCRIPTION
 * 
 * @setSize(int row, int col)
 * 
 * @setNumType(int numType)
 * 
 * @DropNewCandy()
 * 
 * @MoveCandy()
 * 
 * @FallCandy()
 * 
 * @CheckSequenceCandy()
 * 
 * @CheckInside(int row, int col)
 * 
 * @isValid()
 * 
 * @generateBoard()
 * 
 * @swap(Coordinate candy1, Coordinate candy2)
 * 
 * @swapCandies(Coordinate candy1, Coordinate candy2)
 * 
 * @updateBoard()
 * 
 * @updateScore()
 * 
 */

public class Board {
	private int row = 5,col = 5;
	private int[][] grid;
	private int numType;
	//private Drawer drawer;
	
	// set initial size
	public void setSize(int row, int col) {
		this.row = row;
		this.col = col;
		grid = new int[row+1][col + 1];
	}
	
	public void setNumType(int numType) {
		this.numType = numType;
	}
	
	// Set initial Board
//	public void setBoard(int[][] grid) {
//		for (int i = 1; i<= row; i++) {
//			for (int j=1; j <= col; j++) {
//				grid[i][j] = grid[i][j];
//			}
//		}
//	}
	
	/****PRINT FOR TESTING
	public void printBoard() {
		for (int i=1; i<= row; i++) {
			for (int j=1; j<= col; j++) {
				System.out.printf("%d ", grid[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// print ArrayList of Old and New Coordinate when moving candies (for testing)
	private void printListMove(ArrayList<Pair<Coordinate,Coordinate>> list) { 
		for (int i=0; i< list.size(); i++) {
			System.out.printf("Old Coor: %d %d ", ((list.get(i)).getKey()).getRow(),((list.get(i)).getKey()).getColumn()); 
			System.out.printf("New Coor: %d %d\n", ((list.get(i)).getValue()).getRow(),((list.get(i)).getValue()).getColumn());
		}
		System.out.println("");
	}
	*****/
	
	// for using inside DropNewCandy
	private void MoveCandy() {
		
		int startRow = 0, curRow = 0;
		ArrayList<Pair<Coordinate,Coordinate>> list = new ArrayList<Pair<Coordinate,Coordinate>>();
		Coordinate coor1, coor2;
		Pair<Coordinate, Coordinate> tmpPair;
		
		for (int j = 1; j<= col; j++) {
			startRow = 0;
			for (int i=row; i>=1; i--) {
				if (grid[i][j] == 0) {
					startRow = i; // The first Row whose value is 0
					break;
				}
			}
			
			curRow = startRow; //holds the lowest position of 0 in a column
			for (int i=startRow; i>=1; i--) {
				if (grid[i][j] != 0) {
					grid[curRow][j] = grid[i][j];
					
					coor1 = new Coordinate(i,j);
					coor2 = new Coordinate(curRow,j);
					tmpPair = new Pair<Coordinate,Coordinate>(coor1,coor2); 
					list.add(tmpPair);
				
					grid[i][j] = 0;
					curRow--;
				}
			}
		}	
		//printListMove(list);
		//drawer.move(list);
	}
	
	/****PRINTING FOR TESTING
	// printArrayList of Coordinate and Type of new Candies Fall (for testing)
	private void printListFall(ArrayList<Coordinate> listCoor,ArrayList<Integer> listCandy) {
		for (int i=0; i<listCoor.size(); i++) {
			System.out.printf("Coor: %d %d Type: %d\n", listCoor.get(i).getRow(), listCoor.get(i).getColumn(),
					listCandy.get(i));
		}
		System.out.println("");
	}
	*****/
	
	// for using inside DropNewCandy
	private void FallCandy() {
		Integer candy;
		Random rand = new Random();
		ArrayList<Coordinate> listCoor = new ArrayList<Coordinate>();
		ArrayList<Integer> listCandy = new ArrayList<Integer>();
		Coordinate coor;
		
		for (int j = 1; j <= col; j++) {
			for (int i=row; i>=1; i--) {
				if (grid[i][j] == 0) {
					candy = rand.nextInt(numType) + 1; 
					grid[i][j] = candy;
					
					coor = new Coordinate(i,j);
					listCoor.add(coor);
					listCandy.add(candy);
				}
			}
		}
		//printListFall(listCoor,listCandy);
		//drawer.fall(listCoor,listCandy);
	}
	
	public void DropNewCandy() {
		MoveCandy();
		//printBoard();
		FallCandy();
		//printBoard();
	}
	
	/****PRINTING FOR TESTING
	public void printCrushCandy(ArrayList<Coordinate> list) {
		for (int i=0; i < list.size(); i++) {
			System.out.printf("Coor: %d %d \n", list.get(i).getRow(), list.get(i).getColumn());
		}
		System.out.println("");
	}
	*****/
	
	public ArrayList<Coordinate> CheckSequenceCandy() {
		int[][] hrow = new int[row+5][col+5], hcol = new int[row+5][col+5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;
		
		//search for sequences and store row/column sequences in hrow/hcol
		for (int i=1; i<= row; i++) {
			for (int j=1; j<= col; j++) {
				if (grid[i][j] == grid[i][j-1]) {
					hrow[i][j] = hrow[i][j-1] + 1;
				}
				else hrow[i][j] = 1;
				
				if (grid[i][j] == grid[i-1][j]) {
					hcol[i][j] = hcol[i-1][j] + 1;
				}
				else hcol[i][j] = 1;
				
			}
		}
		
		//set candies in sequences to 0 and add coordinates to TreeSet
		for (int i=row; i>=1; i--) {
			for (int j=col; j>=1; j--) {
				if (hrow[i][j] >= 3) {
					for (int p=0; p < hrow[i][j];p++) {
						coor = new Coordinate(i,j-p);
						SetList.add(coor);
						grid[i][j-p] = 0;
					}
				}
				
				if (hcol[i][j] >= 3) {
					for (int p=0; p < hcol[i][j]; p++) {
						coor = new Coordinate(i-p,j);
						SetList.add(coor);
						grid[i-p][j] = 0;
					}
				}
			}
		}
		
		//append the coordinates to a list
		Iterator<Coordinate> it = SetList.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		
		//if (list.size() > 0)
		//	drawer.crushCandies(list);
		
		return list;
	}
	
	// for using inside isValid function
	private boolean CheckInside(int curRow, int curCol) {
		if (1<=curRow && curRow <= row && 1 <= curCol && curCol <= col) return true;
		return false;
	}
	
	public boolean isValid() {
		int curRow,curCol, cnt;
		
		for (int i=1; i<= row; i++) {
			for (int j=1; j<=col; j++) {
				
				/**CASE 1: Row sequence created by an upward swap**/
				curRow = i+1;
				cnt = 0;
				
				//Check RHS positions
				curCol = j+1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol++;
					cnt++;
				}
				
				//Check LHS positions
				curCol = j-1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i,j);
					return true;
				}
				
				/**CASE 2: Row sequence created by a downward swap**/
				curRow = i-1;
				cnt = 0;
				
				//Check RHS positions
				curCol = j+1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol++;
					cnt++;
				}
				//Check LHS positions
				curCol = j-1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curCol--;
					cnt++;
				}
				
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i,j);
					return true;
				}
				
				/**CASE 3: Column sequence created by a leftward swap**/
				curCol = j-1;
				cnt = 0;
				
				//Check upper positions
				curRow = i-1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow--;
					cnt++;
				}
				//Check lower positions
				curRow = i+1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow++;
					cnt++;
				}
				
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n",i,j);
					return true;
				}
				
				/**CASE 4: Column sequence created by a rightward swap**/
				curCol = j+1;
				cnt = 0;
				
				//Check upper positions
				curRow = i-1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow--;
					cnt++;
				}
				//Check lower positions
				curRow = i+1;
				while (CheckInside(curRow,curCol) && grid[curRow][curCol] == grid[i][j]) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2) {
					System.out.printf("Next move : %d %d\n", i,j);
					return true;
				}
			}
		}
		return false;
	}
	
	public void generateBoard() {
		/**Randomly generate board**/
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				grid[i][j] = new Random().nextInt(numType) + 1;
			}
		}
		
		/**Eliminate sequences**/
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				//CASE 1
				if ((i >= 3) && (j < 3)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) ) {
						while (grid[i][j] == grid[i - 1][j])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}
				
				//CASE 2
				if ((i < 3) && (j >= 3)) {
					if ( (grid[i][j - 1] == grid[i][j - 2]) ) {
						while (grid[i][j] == grid[i][j - 1])
							grid[i][j] = new Random().nextInt(numType) + 1;
					}
				}
				
				//CASE 3
				if ((i >= 3) && (j >= 3)) {
					if ( (grid[i - 1][j] == grid[i - 2][j]) 
							&& (grid[i][j - 1] == grid[i][j - 2]) ) {
						while ( (grid[i][j] == grid[i - 1][j]) 
								|| (grid[i][j] == grid[i][j - 1]) )
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
	
	
	private boolean haveCombo() {
		return (CheckSequenceCandy().size() > 0);
	}
	
	private void swap(Coordinate candy1, Coordinate candy2) {
		Integer temp, row1, col1, row2, col2;
		row1 = candy1.getRow();
		col1 = candy1.getColumn();
		row2 = candy2.getRow();
		col2 = candy2.getColumn();
		temp = grid[row1][col1];
		grid[row1][col1] = grid[row2][col2];
		grid[row2][col2] = temp;
	}
	
	public boolean swapCandies(Coordinate candy1, Coordinate candy2) {

		swap(candy1, candy2);

		if (!haveCombo()) { // if move doesn't generate combo, swap back
			swap(candy1, candy2);
		}

		return haveCombo();
	}
	
	public void updateBoard() {
		do {
			//updateScore
			printBoard();
			DropNewCandy();
		} while (haveCombo());
		
		if (!isValid()) {
			generateBoard();
		}
	}

}
