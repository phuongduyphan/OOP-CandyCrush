import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * The Candy class represents a specific candy of type Normal.
 * This includes methods to handle behaviours of the candy.
 */

public class CandyNormal extends Candy {
	private static final String[] imageDirectory = new String[] { "red.png", "orange.png",
			"yellow.png", "green.png", "blue.png", "purple.png"};
	private static ArrayList<Image> imgList = new ArrayList<Image>();

	public CandyNormal() {
		super();
	}

	public CandyNormal(int color) {
		super(color);
	}

	@Override
	public ArrayList<Coordinate> specialExplode(Coordinate curCoor) {
		return new ArrayList<Coordinate>();
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
