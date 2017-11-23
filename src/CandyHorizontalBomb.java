import java.util.ArrayList;

import javafx.scene.image.Image;

public class CandyHorizontalBomb extends Candy {
	private static final String[] imageDirectory = new String[] { "red-horizontalbomb.png", "orange-horizontalbomb.png",
			"yellow-horizontalbomb.png", "green-horizontalbomb.png", "blue-horizontalbomb.png", "purple-horizontalbomb.png"};
	private static ArrayList<Image> imgList = new ArrayList<Image>();

	public CandyHorizontalBomb() {
		super();
	}

	public CandyHorizontalBomb(int color) {
		super(color);
	}

	@Override
	public ArrayList<Coordinate> specialExplode(Coordinate curCoor) {
		ArrayList<Coordinate> list = new ArrayList<>();
		int i = curCoor.getRow();
		for (int j = 0; j < Main.getNumberofcolumn(); ++j)
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
