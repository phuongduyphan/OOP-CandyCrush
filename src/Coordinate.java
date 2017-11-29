import javafx.util.Pair;

public class Coordinate {
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

	public int getColumn() {
		return p.getValue().intValue();
	}

	public int getRow() {
		return p.getKey().intValue();
	}

	@Override
	public String toString() {
		return "Coordinate [" + this.getRow() + " " + this.getColumn() + "]";
	}
}