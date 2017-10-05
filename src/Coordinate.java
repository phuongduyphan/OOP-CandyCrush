import java.lang.Math;

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
public class Coordinate {
	final private Integer row;
	final private Integer column;

	public Coordinate(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}

	public Coordinate(int row, int column) {
		this.row = new Integer(row);
		this.column = new Integer(column);
	}

	static public boolean isNeighbours(Coordinate a, Coordinate b) {
		if (Math.abs(a.getColumn() - b.getColumn()) + Math.abs(a.getRow() - b.getRow()) == 1) return true;
		return false;
	}

	static public boolean isNeighbours(int rowA, int colA, int rowB, int colB) {
		Coordinate a = new Coordinate(rowA, colA);
		Coordinate b = new Coordinate(rowB, colB);
		return Coordinate.isNeighbours(a, b);

	}

	public Integer getRow() {
		return row.intValue();
	}

	public Integer getColumn() {
		return column.intValue();
	}

}