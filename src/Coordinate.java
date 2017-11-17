import javafx.util.Pair;

public class Coordinate {
	private Pair<Integer, Integer> p;
	
	public Coordinate(Integer key, Integer value) {
		p = new Pair<Integer, Integer>(key, value);
	}
	
	public Coordinate(int key, int value) {
		this(new Integer(key), new Integer(value));
	}

	public int getRow() {
		return p.getKey().intValue();
	}

	public int getColumn() {
		return p.getValue().intValue();
	}

	public static boolean isNeighbours(Coordinate coor1, Coordinate coor2) {
		if(Math.abs(coor1.getRow() - coor2.getRow()) + Math.abs(coor1.getColumn() - coor2.getColumn()) == 1) return true;
		return false;
	}

	@Override
	public String toString() {
		return "Coordinate [" + this.getRow() + " " + this.getColumn() + "]";
	}
}