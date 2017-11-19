import java.util.ArrayList;

import javafx.scene.image.Image;

public class CandyVerticalBomb extends Candy {

	private static final String typeImageDir = "overlayVerticalBomb.png";
	private static Image typeImage = new Image(typeImageDir);

	public CandyVerticalBomb() {
		super();
	}

	public CandyVerticalBomb(int color) {
		super(color);
	}

	@Override
	public Image getTypeImage() {
		return typeImage;
	}

	@Override
	public ArrayList<Coordinate> specialExplode(Coordinate curCoor) {
		ArrayList<Coordinate> list = new ArrayList<>();
		int j = curCoor.getColumn();
		for (int i = 0; i < Main.getNumberofrow(); ++i)
			if ((i != curCoor.getRow() || j != curCoor.getColumn()) && checkInside(i, j))
				list.add(new Coordinate(i, j));
		return list;
	}

	private boolean checkInside(int curRow, int curCol) {
		if (0 <= curRow && curRow < Main.getNumberofrow() && 0 <= curCol && curCol < Main.getNumberofcolumn())
			return true;
		return false;
	}
}
