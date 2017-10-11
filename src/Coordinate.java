import java.lang.Math;
import javafx.util.Pair;

/**
 * @Constructor Coordinate(int row, int col)
 * 
 * @isNeighbours(int row1, int col1, int row2, int col2)
 * @isNeighbours(Coordinate cell1, Coordinate cell2)
 * Check if two given cell are next to each others.
 * Return true if the two cells are neighbours
 * Return false if not, or are the same cell.
 *
 */
public class Coordinate extends Pair<Integer, Integer>{
		public Coordinate(Integer key, Integer value) {
		super(key, value);
		// 	TODO Auto-generated constructor stub
	}
}