import java.util.*;
import javafx.util.*;

public class Board {
	private int row = 5,col = 5;
	private int[][] grid;
	
	// set initial size
	public void setSize(int row, int col) {
		this.row = row;
		this.col = col;
		grid = new int[row+1][col + 1];
	}
	
	// Set initial Board
	public void setBoard(int[][] currentBoard) {
		for (int i = 1; i<= row; i++) {
			for (int j=1; j <= col; j++) {
				grid[i][j] = currentBoard[i][j];
			}
		}
	}
	
	// print for testing
	public void printBoard() {
		for (int i=1; i<= row; i++) {
			for (int j=1; j<= col; j++) {
				System.out.printf("%d ", grid[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// print ArrayList of Old and New Coorodinate when moving candies (for testing)
	private void printListMove(ArrayList<Pair<Coordinate,Coordinate>> list) { 
		for (int i=0; i< list.size(); i++) {
			System.out.printf("Old Coor: %d %d ", ((list.get(i)).getKey()).getRow(),((list.get(i)).getKey()).getColumn()); 
			System.out.printf("New Coor: %d %d\n", ((list.get(i)).getValue()).getRow(),((list.get(i)).getValue()).getColumn());
		}
		System.out.println("");
	}
	
	// for using inside DropNewCandy
	private void MoveCandy(int[][] currentBoard) {
		
		int startRow = 0, curRow = 0;
		ArrayList<Pair<Coordinate,Coordinate>> list = new ArrayList<Pair<Coordinate,Coordinate>>();
		Coordinate coor1, coor2;
		Pair<Coordinate, Coordinate> tmpPair;
		
		for (int j = 1; j<= col; j++) {
			for (int i=row; i>=1; i--) {
				if (currentBoard[i][j] == 0) {
					startRow = i; // The first Row whose value is 0
					break;
				}
			}
			
			curRow = startRow; 
			for (int i=startRow; i>=1; i--) {
				if (currentBoard[i][j] != 0) {
					currentBoard[curRow][j] = currentBoard[i][j];
					
					coor1 = new Coordinate(i,j);
					coor2 = new Coordinate(curRow,j);
					tmpPair = new Pair<Coordinate,Coordinate>(coor1,coor2); 
					list.add(tmpPair);
					
					currentBoard[i][j] = 0;
					curRow--;
				}
			}
		}	
		printListMove(list);
	}
	
	// printArrayList of Coordinate and Type of new Candies Fall (for testing)
	private void printListFall(ArrayList<Coordinate> listCoor,ArrayList<Integer> listCandy) {
		for (int i=0; i<listCoor.size(); i++) {
			System.out.printf("Coor: %d %d Type: %d\n", listCoor.get(i).getRow(), listCoor.get(i).getColumn(),
					listCandy.get(i));
		}
		System.out.println("");
	}
	
	// for using inside DropNewCandy
	private void FallCandy(int[][] currentBoard) {
		Integer candy;
		Random rand = new Random();
		ArrayList<Coordinate> listCoor = new ArrayList<Coordinate>();
		ArrayList<Integer> listCandy = new ArrayList<Integer>();
		Coordinate coor;
		
		for (int j = 1; j <= col; j++) {
			for (int i=row; i>=1; i--) {
				if (currentBoard[i][j] == 0) {
					candy = rand.nextInt(Candy.numType) + 1; 
					currentBoard[i][j] = candy;
					
					coor = new Coordinate(i,j);
					listCoor.add(coor);
					listCandy.add(candy);
				}
			}
		}
		printListFall(listCoor,listCandy);
	}
	
	public void DropNewCandy(int[][] currentBoard) {
		MoveCandy(currentBoard);
		printBoard();
		FallCandy(currentBoard);
		printBoard();
	}
	
	// for testing
	public void printCrushCandy(ArrayList<Coordinate> list) {
		for (int i=0; i < list.size(); i++) {
			System.out.printf("Coor: %d %d \n", list.get(i).getRow(), list.get(i).getColumn());
		}
		System.out.println("");
	}
	
	public ArrayList<Coordinate> CheckSequenceCandy(int[][] currentBoard) {
		int[][] hrow = new int[row+5][col+5], hcol = new int[row+5][col+5];
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		TreeSet<Coordinate> SetList = new TreeSet<Coordinate>(new CoorComp());
		Coordinate coor;
		
		for (int i=1; i<= row; i++) {
			for (int j=1; j<= col; j++) {
				if (currentBoard[i][j] == currentBoard[i][j-1]) {
					hrow[i][j] = hrow[i][j-1] + 1;
				}
				else hrow[i][j] = 1;
				
				if (currentBoard[i][j] == currentBoard[i-1][j]) {
					hcol[i][j] = hcol[i-1][j] + 1;
				}
				else hcol[i][j] = 1;
				
			}
		}
		
		for (int i=row; i>=1; i--) {
			for (int j=col; j>=1; j--) {
				if (hrow[i][j] >= 3) {
					for (int p=0; p < hrow[i][j];p++) {
						coor = new Coordinate(i,j-p);
						SetList.add(coor);
						currentBoard[i][j-p] = 0;
					}
				}
				
				if (hcol[i][j] >= 3) {
					for (int p=0; p < hcol[i][j]; p++) {
						coor = new Coordinate(i-p,j);
						SetList.add(coor);
						currentBoard[i-p][j] = 0;
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
		if (1<=curRow && curRow <= row && 1 <= curCol && curCol <= col) return true;
		return false;
	}
	
	public boolean isValid(int[][] currentBoard) {
		int curRow,curCol, cnt;
		
		for (int i=1; i<= row; i++) {
			for (int j=1; j<=col; j++) {
				
				// Case 1
				curRow = i+1;
				curCol = j+1;
				cnt = 0;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol++;
					cnt++;
				}
				curCol = j-1;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2) return true;
				
				// Case 2
				curRow = i-1;
				curCol = j+1;
				cnt = 0;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol++;
					cnt++;
				}
				curCol = j-1;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curCol--;
					cnt++;
				}
				if (cnt >= 2) return true;
				
				// Case 3
				curRow = i-1;
				curCol = j-1;
				cnt = 0;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow--;
					cnt++;
				}
				curRow = i+1;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2) return true;
				
				// Case 4
				curRow = i-1;
				curCol = j+1;
				cnt = 0;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow--;
					cnt++;
				}
				curRow = i+1;
				while (CheckInside(curRow,curCol) && currentBoard[curRow][curCol] == currentBoard[i][j]) {
					curRow++;
					cnt++;
				}
				if (cnt >= 2) return true;
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
		Candy.numType = reader.nextInt();
		
		board1.setSize(r, c);
		for (int i=1; i<=r;i++) {
			for (int j=1; j<=c;j++) {
				tmpBoard[i][j] = reader.nextInt();
			}
		}
		board1.setBoard(tmpBoard);
		board1.DropNewCandy(board1.grid);
		board1.printCrushCandy(board1.CheckSequenceCandy(board1.grid));
		board1.printBoard();
		if (board1.isValid(tmpBoard)) System.out.println("True");
		else System.out.println("False");
	}

}
