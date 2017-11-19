import java.util.ArrayList;

import javafx.scene.image.Image;

public class Candy3x3Bomb extends Candy {

	private static final String typeImageDir = "overlay3x3Bomb.png";
	private static Image typeImage = new Image(typeImageDir);

	public Candy3x3Bomb() {
		super();
	}

	public Candy3x3Bomb(int color) {
		super(color);
	}

	@Override
	public Image getTypeImage() {
		return typeImage;
	}

	@Override
	public ArrayList<Coordinate> specialExplode(Coordinate curCoor) {
		ArrayList<Coordinate> list = new ArrayList<>();
		for (int i = curCoor.getRow() - 1; i <= curCoor.getRow() + 1; ++i)
			for (int j = curCoor.getColumn() - 1; j <= curCoor.getColumn() + 1; ++j)
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
