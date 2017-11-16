import javafx.util.Pair;

public class Coordinate extends Pair<Integer, Integer> {
	public Coordinate(Integer key, Integer value) {
		super(key, value);
	}

	public int getRow() {
		return this.getKey().intValue();
	}

	public int getColumn() {
		return this.getValue().intValue();
	}

	public static boolean isNeighbours(Coordinate coor1, Coordinate coor2) {
		if(Math.abs(coor1.getRow() - coor2.getRow()) + Math.abs(coor1.getColumn() - coor2.getColumn()) == 1) return true;
		return false;
	}
}