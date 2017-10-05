
public class Board {
	static private int numberOfRow = 5, numberOfColumn = 5;
	static private int cellHeight = 10, cellWidth = 10;
	private Drawer drawer;
	private CellSelectionHandler cellSelectionHandler;
	private Candy grid[][];
	
	public Board() {
		grid = new Candy[numberOfRow][numberOfColumn];
		drawer = new Drawer();
	}
	
	public boolean initialize() {
		fillGrid();
		resetScore();
		updateBoard();
		while(checkBoard() == false) {
			updateBoard();
			drawer.drawBoard();
		}
		return true;
	}

	public boolean receiveInput() {
		if(checkSelected() == true) {
			swapCandy();
			while(checkBoard() == false) {
				updateBoard();
				updateScore();
				drawer.drawBoard();
			}
		}
	}
	
	/**
	 * @return if all board is stable
	 */
	private boolean checkBoard() {
		
	}
	
	/**
	 * @return if all board is stable
	 * providing the previous state is stable and the only change is at given coordinate
	 */
	private boolean checkBoard(int row, int column) {
		
	}
	
	private boolean checkSelected() {
		
	}
	
	private boolean resetScore() {
	
	}
	
	private boolean updateBoard() {
		
	}
	
	private boolean updateScore() {
		
	}
	
	private boolean fillGrid() {
		
	}
	
	private boolean swapCandy() {
		
	}
	
}
