import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * The Candy class represents a specific candy of type 3x3Bomb.
 * This includes methods to handle behaviours of the candy.
 */

public class Candy3x3Bomb extends Candy {
	private static final String[] imageDirectory = new String[] { "red-3x3bomb.png", "orange-3x3bomb.png",
			"yellow-3x3bomb.png", "green-3x3bomb.png", "blue-3x3bomb.png", "purple-3x3bomb.png"};
	private static ArrayList<Image> imgList = new ArrayList<Image>();

	public Candy3x3Bomb() {
		super();
	}

	public Candy3x3Bomb(int color) {
		super(color);
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
	
	@Override
	public String[] getImageDirectory() {
		return imageDirectory;
	}

	@Override
	public ArrayList<Image> getImgList() {
		return imgList;
	}
}
