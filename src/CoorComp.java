import java.util.*;

public class CoorComp implements Comparator<Coordinate> {
	public int compare(Coordinate p1, Coordinate p2) {
		if (p1.getRow() < p2.getRow()) return -1;
		if (p1.getRow() == p2.getRow()) {
			if (p1.getColumn() < p2.getColumn()) return -1;
			if (p1.getColumn() == p2.getColumn()) return 0;
		}
		return 1;
	}
}
