import java.util.*;

public class GameHandler {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		
		Board board = new Board();
		board.setSize(7, 7);
		board.setNumType(7);

		board.generateBoard();
		board.isValid();
		Coordinate Coor1, Coor2; 
		
		do {
			board.printBoard();
			//user input
			do {
				System.out.println("Input: ");
				Coor1 = new Coordinate(reader.nextInt(), reader.nextInt());
				Coor2 = new Coordinate(reader.nextInt(), reader.nextInt());
			} while (!board.swapCandies(Coor1, Coor2));
			board.updateBoard();
			
		} while (1 == 1);
	}

}
