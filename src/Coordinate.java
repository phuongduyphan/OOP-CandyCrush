import javafx.util.Pair;

/**
 * @Constructor Coordinate(int row, int col)
 */
public class Coordinate extends Pair<Integer, Integer> {
	/** 
	 * @param key : row
	 * @param value : column
	 */
	public Coordinate(Integer key, Integer value) {
		super(key, value);
	}

	public int getRow() {
		return this.getKey().intValue();
	}

	public int getColumn() {
		return this.getValue().intValue();
	}
}