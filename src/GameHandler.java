
public class GameHandler {
	
	public void play() {
		Board b = new Board();
		b.initBoard();
		
		while (true) {
			if (!isValid())
				b.initBoard();
			
			//TODO receive user input in candy1 and candy2
			
			if (swapCandies(candy1, candy2))
				b.updateBoard();
		}
	}
	
	public void startNewGame() {
		
	}
	
	public void resetBoard() {
		
	}
	
	public void gameOver() {
		
	}
	
	public void updateScore() {
		
	}
	
	private Pair checkInput() { //check if user has clicked anything and return the  clicked cell coor
		
	}
	
	private void update() { //from user input
		
	}
}
