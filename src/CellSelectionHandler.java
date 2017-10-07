import java.util.ArrayList;

/**
 * A class which handles user's cells' selection.
 * 
 * @getSelected()
 * Return current selected cell as a Coordinate object.
 * Return a null object if no cell is selected at the time.
 * 
 * @select(Coordinate coor)
 * @select(int row, int col)
 * Perform a selection action.
 *  	If no cell is currently selected, select the given one.
 *  	If the given cell is adjacent to the previous selected cells, return the two coordinates and reset.
 *  	If the given cell is not adjacent or is the same with the previous selected cell, reset.
 * Return a null object if this is the first cell.
 * Return a ArrayList<Coordinate> object contains two Coordinate objects if two adjacent cells are selected.
 * Return a null object if the given cell is not adjacent or is the same with the previous selected cell.
 */
public class CellSelectionHandler {
	private Coordinate selected;
	 
	public CellSelectionHandler() {
		selected = null;
	}

	public Coordinate getSelected() {
		return selected;
	}
	
	public ArrayList<Coordinate> select(Coordinate coordinate) {
		if(this.selected == null) { //Nothing has been selected, select
			this.selected = coordinate;
			return null;
		} else if (Coordinate.isNeighbours(this.selected, coordinate)) { //The previous selected is a neighbour with the lastest, return both, delete both
			ArrayList<Coordinate> array = new ArrayList<Coordinate>();
			array.add(this.selected);
			array.add(coordinate);
			reset();
			return array;
		} else { //The previous selected is not a neighbour with the lastest, or is the same
			reset();
			return null;			
		}
	}
	
	private void reset() {
		selected = null;
	}
}
