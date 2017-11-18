import java.util.ArrayList;

public class CellSelectionHandler {
	private Coordinate selected;

	public CellSelectionHandler() {
		selected = null;
	}

	public Coordinate getSelected() {
		return selected;
	}

	/**
	 * Perform a selection action. If no cell is currently selected, select the
	 * given one. If the given cell is adjacent to the previous selected cells,
	 * return the two coordinates and reset. If the given cell is not adjacent or is
	 * the same with the previous selected cell, reset.
	 * 
	 * @param coordinate which has been clicked
	 * 
	 * @return A null object if this is the first cell.<br>ArrayList<Coordinate> object contains two Coordinate objects if two adjacent cells are selected.<br>A null object if the given cell is not adjacent or is the same with the previous selected cell.
	 */
	public ArrayList<Coordinate> select(Coordinate coordinate) {
		if (selected == null) {
			// Nothing has been selected, select
			selected = coordinate;
			ArrayList<Coordinate> array = new ArrayList<Coordinate>();
			array.add(selected);
			return array;
		} else if (Coordinate.isNeighbours(this.selected, coordinate)) {
			// The previous selected is a neighbour with the lastest, return both, delete both
			ArrayList<Coordinate> array = new ArrayList<Coordinate>();
			array.add(this.selected);
			array.add(coordinate);
			reset();
			return array;
		} else {
			// The previous selected is not a neighbour with the lastest, or is the same
			reset();
			return null;
		}
	}

	private void reset() {
		selected = null;
	}
}
