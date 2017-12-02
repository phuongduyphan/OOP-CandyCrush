import javafx.util.Pair;

/**
 * The Coordinate class represents the coordinate of a specific cell on the board.
 * This includes methods to manipulate the coordinate of the cell.
 */
public class Coordinate {
	/**
	 * Check if the two cells are adjacent
	 * @return <b>true</b> if two cells are adjacent
	 */
	public static boolean isNeighbours(Coordinate coor1, Coordinate coor2) {
		if (Math.abs(coor1.getRow() - coor2.getRow()) + Math.abs(coor1.getColumn() - coor2.getColumn()) == 1)
			return true;
		return false;
	}

	private Pair<Integer, Integer> p;
	
	public Coordinate(int key, int value) {
		this(new Integer(key), new Integer(value));
	}

	public Coordinate(Integer key, Integer value) {
		p = new Pair<Integer, Integer>(key, value);
	}
	/**
	 * @return <b>true</b> column index of the cell
	 */
	public int getColumn() {
		return p.getValue().intValue();
	}
	/**
	 * @return <b>true</b> row index of the cell
	 */
	public int getRow() {
		return p.getKey().intValue();
	}

	@Override
	public String toString() {
		return "Coordinate [" + this.getRow() + " " + this.getColumn() + "]";
	}
}